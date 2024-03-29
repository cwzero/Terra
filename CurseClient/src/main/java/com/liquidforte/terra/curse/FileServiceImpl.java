package com.liquidforte.terra.curse;

import com.fasterxml.jackson.databind.util.ByteBufferBackedOutputStream;
import com.google.inject.Inject;
import com.liquidforte.terra.api.cache.FileCache;
import com.liquidforte.terra.api.curse.AddonSearchService;
import com.liquidforte.terra.api.curse.CurseFileAPI;
import com.liquidforte.terra.api.model.File;
import com.liquidforte.terra.api.service.FileService;
import com.liquidforte.terra.curse.model.CurseAddonSearchResult;
import com.liquidforte.terra.curse.model.CurseFile;
import com.liquidforte.terra.curse.model.CurseFileDependency;
import com.liquidforte.terra.util.FingerprintUtil;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class FileServiceImpl implements FileService {
    private final AddonSearchService addonSearchService;
    private final CurseFileAPI curseFileAPI;
    private final FileCache fileCache;

    @Override
    public long getLatestFile(String mcVer, String[] altVer, long addonId) {
        if (addonId > 0) {
            List<CurseFile> files = curseFileAPI.getFiles(addonId);
            Collections.sort(files);

            Predicate<CurseFile> fabricTest = file -> file.getGameVersion().stream().noneMatch(it -> it.equalsIgnoreCase("fabric"));

            Predicate<CurseFile> versionTest = file -> file.getGameVersion().contains(mcVer) ||
                    Arrays.stream(altVer).anyMatch(file.getGameVersion()::contains);

            Predicate<CurseFile> fileTest = fabricTest.and(versionTest);

            Optional<Long> result = files.stream()
                    .filter(fileTest)
                    .findFirst()
                    .map(CurseFile::getId);

            if (result.isPresent()) {
                return result.get();
            } else {
                CurseAddonSearchResult res = addonSearchService.getAddon(addonId).get();
                throw new RuntimeException("Failed to get addon(" + addonId + "): " + res.getSlug());
            }
        } else {
            return -1;
        }
    }

    @Override
    public File getFile(long addonId, long fileId) {
        if (addonId > 0 && fileId > 0) {
            CurseFile curseFile = curseFileAPI.getFile(addonId, fileId).get();
            return new File(
                    curseFile.getId(),
                    curseFile.getFileName(),
                    curseFile.getFileDate(),
                    curseFile.getFileLength(),
                    curseFile.getDownloadUrl(),
                    curseFile.getPackageFingerprint()
            );
        } else {
            return null;
        }
    }

    public byte[] downloadFile(File file, String downloadUrl) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate((int) file.getFileLength());

            CloseableHttpClient client = HttpClientBuilder.create()
                    .setRedirectStrategy(new DefaultRedirectStrategy() {
                        @Override
                        public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context)
                                throws ProtocolException {
                            boolean result = super.isRedirected(request, response, context);
                            int responseCode = response.getStatusLine().getStatusCode();
                            return result || (responseCode >= 300 && responseCode < 400);
                        }

                        @Override
                        public URI getLocationURI(HttpRequest request, HttpResponse response, HttpContext context) throws ProtocolException {
                            URI result = super.getLocationURI(request, response, context);

                            if (result.toString().contains("+") || result.toString().contains(" ")) {
                                try {
                                    return new URL(result.toURL().toExternalForm()
                                            .replace("+", "%2B")
                                            .replace(" ", "%20")
                                    ).toURI();
                                } catch (MalformedURLException | URISyntaxException e) {
                                    e.printStackTrace();
                                }
                            }

                            return result;
                        }
                    })
                    .build();

            URL url = new URL(downloadUrl
                    .replace(" ", "%20")
                    .replace("+", "%2B")
                    .replace("[", "%5b")
                    .replace("]", "%5d"));

            HttpGet request = new HttpGet(url.toExternalForm());

            HttpResponse response = client.execute(request);

            response.getEntity().writeTo(new ByteBufferBackedOutputStream(buffer));

            int len = buffer.position();

            client.close();

            long expectedFingerprint = file.getFingerprint();
            long actualFingerprint = FingerprintUtil.getByteArrayHash(buffer.array());

            if (len == file.getFileLength() && actualFingerprint == expectedFingerprint) {
                return buffer.array();
            } else {
                if (downloadUrl.contains("edge.forgecdn.net")) {
                    return downloadFile(file, downloadUrl.replace("edge.forgecdn.net", "media.forgecdn.net"));
                } else {
                    System.out.println("Download of file: " + file.getFileName() + " failed.");
                    System.out.println("Download url: " + url.toExternalForm());
                    System.out.println("Expected Length: " + file.getFileLength());
                    System.out.println("Actual Length: " + len);
                    System.out.println("Expected fingerprint: " + expectedFingerprint);
                    System.out.println("Actual fingerprint: " + actualFingerprint);

                    Files.write(Paths.get(file.getFileName()), buffer.array());

                    //return downloadFile(file);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new byte[0];
    }

    @Override
    public byte[] downloadFile(File file) {
        return downloadFile(file, file.getDownloadUrl());
    }

    @Override
    public List<Long> getDependencies(long addonId, long fileId) {
        if (addonId > 0) {
            CurseFile curseFile = curseFileAPI.getFile(addonId, fileId).get();
            List<Long> dependencies = new ArrayList<>();

            for (CurseFileDependency dep : curseFile.getDependencies()) {
                if (dep.getType() == 3) {
                    dependencies.add(dep.getAddonId());
                }
            }

            return dependencies;
        } else {
            return null;
        }
    }
}
