package com.liquidforte.terra.cache;

import com.google.inject.Inject;
import com.liquidforte.curse.client.Client;
import com.liquidforte.curse.file.CurseFile;
import com.liquidforte.terra.database.CacheDatabase;
import com.liquidforte.terra.model.lock.FileDependency;
import com.liquidforte.terra.model.lock.FileLock;
import com.liquidforte.terra.util.FingerprintUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TerraFileCache extends AbstractCache implements FileCache {
    private final Client client;
    private final LockCache lockCache;
    private final CacheDatabase cacheDatabase;

    @Inject
    public TerraFileCache(Client client, LockCache lockCache, CacheDatabase cacheDatabase) {
        this.client = client;
        this.lockCache = lockCache;
        this.cacheDatabase = cacheDatabase;
    }

    @Override
    public FileLock getFile(long addonId, long fileId) {
        AtomicReference<FileLock> lock = new AtomicReference<>(cacheDatabase.getFile(addonId, fileId));

        if (lock.get() == null) {
            client.getFile(addonId, fileId).ifPresent(file -> {
                if (addonId > 0 && fileId > 0) {
                    var l = new FileLock();
                    l.setFileId(fileId);
                    l.setAddonId(addonId);
                    l.setDownloadUrl(file.getDownloadUrl());
                    l.setFileDate(file.getFileDate());
                    l.setFileName(file.getFileName());
                    l.setFingerprint(file.getPackageFingerprint());

                    List<FileDependency> deps = new ArrayList<>();
                    file.getDependencies().forEach(d -> {
                        if (d.getType() == 3) {
                            var fd = new FileDependency();
                            fd.setSourceAddonId(addonId);
                            fd.setSourceFileId(fileId);
                            fd.setAddonId(d.getAddonId());
                            var fid = d.getFileId();
                            if (fid == 0) {
                                // TODO: find filter?
                                fid = lockCache.getLock(d.getAddonId(), "true");
                            }
                            fd.setFileId(fid);

                            cacheDatabase.insertDependency(fd);

                            deps.add(fd);
                        }
                    });
                    l.setDependencies(deps);

                    cacheDatabase.saveFile(l);
                    lock.set(l);
                }
            });
        }

        return lock.get();
    }

    @Override
    public void downloadFileToCache(long addonId, long fileId) throws IOException {
        if (!hasFile(fileId)) {
            FileLock lock = getFile(addonId, fileId);

            String downloadUrl = lock.getDownloadUrl();

            downloadFile(addonId, fileId, downloadUrl);
        }
    }

    private void downloadFile(long addonId, long fileId, String downloadUrl) throws IOException {
        if (!hasFile(fileId)) {
            CloseableHttpClient client = HttpClientBuilder.create().setRedirectStrategy(new DefaultRedirectStrategy() {
                @Override
                public boolean isRedirected(org.apache.http.HttpRequest request, org.apache.http.HttpResponse response, org.apache.http.protocol.HttpContext context) throws org.apache.http.ProtocolException {
                    boolean result = super.isRedirected(request, response, context);
                    int responseCode = response.getStatusLine().getStatusCode();
                    return result || (responseCode >= 300 && responseCode < 400);
                }
            }).build();

            HttpGet request = new HttpGet(
                    downloadUrl.replace(" ", "%20").replace("+", "%2B").replace("[", "%5b").replace("]", "%5d"));

            HttpResponse response = client.execute(request);

            int responseCode = response.getStatusLine().getStatusCode();

            HttpEntity entity = response.getEntity();

            if (responseCode == 403 && downloadUrl.contains("https://edge.forgecdn.net/files/")) {
                downloadFile(addonId, fileId,
                        downloadUrl.replace("https://edge.forgecdn.net/files/", "https://media.forgecdn.net/files/"));
            }

            InputStream input = entity.getContent();

            CurseFile file = this.client.getFile(addonId, fileId).get();

            ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.getFileLength());

            while (input.available() > 0) {
                byteBuffer.put((byte) input.read());
            }

            client.close();
            input.close();

            byte[] array = byteBuffer.array();

            long fingerprint = FingerprintUtil.getByteArrayHash(array);

            if (file.getPackageFingerprint() == fingerprint) {
                cacheDatabase.saveFileData(fileId, byteBuffer.array());
            } else {
                downloadFileToCache(addonId, fileId);
            }
        }
    }

    @Override
    public boolean hasFile(long fileId) {
        return cacheDatabase.hasFile(fileId);
    }

    public byte[] getFileData(long addonId, long fileId) throws IOException {
        if (!hasFile(fileId)) {
            downloadFileToCache(addonId, fileId);
        }

        return cacheDatabase.getFileData(fileId).get();
    }

    @Override
    public List<FileDependency> getDependencies(long addonId, long fileId) throws IOException {
        FileLock lock = getFile(addonId, fileId);

        cacheDatabase.getDependencies(addonId, fileId).forEach(d -> {
            if (!lock.getDependencies().stream().anyMatch(dep -> dep.getAddonId() == d.getAddonId())) {
                lock.getDependencies().add(d);
            }
        });

        return lock.getDependencies();
    }

    @Override
    public void installFile(Path destDir, long addonId, long fileId) throws IOException {
        FileLock lock = getFile(addonId, fileId);
        Path destPath = destDir.resolve(lock.getFileName());

        for (FileDependency dep : getDependencies(addonId, fileId)) {
            long depAddonId = dep.getAddonId();
            long depFileId = lockCache.getLock(depAddonId, "true");

            if (depAddonId > 0 && depFileId > 0) {
                installFile(destDir, dep.getAddonId(), depFileId);
            }
        }

        if (destPath.toFile().exists()) {
            if (lock.getFingerprint() > 0) {
                long check = FingerprintUtil.getFileHash(destPath);

                if (check == lock.getFingerprint()) {
                    return;
                }
            }
        }

        downloadFileToCache(addonId, fileId);

        byte[] data = getFileData(addonId, fileId);

        FileOutputStream fos = new FileOutputStream(destPath.toFile());

        fos.write(data);

        fos.flush();
        fos.close();
    }
}
