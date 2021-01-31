package com.liquidforte.terra.curse;

import com.google.inject.Inject;
import com.liquidforte.terra.api.cache.FileCache;
import com.liquidforte.terra.api.curse.CurseFileAPI;
import com.liquidforte.terra.api.model.File;
import com.liquidforte.terra.api.service.FileService;
import com.liquidforte.terra.curse.model.CurseFile;
import com.liquidforte.terra.curse.model.CurseFileDependency;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class FileServiceImpl implements FileService {
    private final CurseFileAPI curseFileAPI;
    private final FileCache fileCache;

    @Override
    public long getLatestFile(String mcVer, String[] altVer, long addonId) {
        List<CurseFile> files = curseFileAPI.getFiles(addonId);
        Collections.sort(files);

        return files.stream()
                .filter(file ->
                        file.getGameVersion().contains(mcVer) ||
                                Arrays.stream(altVer).anyMatch(file.getGameVersion()::contains))
                .findFirst()
                .map(it -> it.getId())
                .get();
    }

    @Override
    public File getFile(long addonId, long fileId) {
        CurseFile curseFile = curseFileAPI.getFile(addonId, fileId).get();
        return new File(
                curseFile.getId(),
                curseFile.getFileName(),
                curseFile.getFileDate(),
                curseFile.getFileLength(),
                curseFile.getDownloadUrl(),
                curseFile.getPackageFingerprint()
        );
    }

    @Override
    public byte[] downloadFile(long addonId, long fileId) {
        try {
            File file = fileCache.getFile(addonId, fileId);
            String downloadUrl = file.getDownloadUrl();

            ByteBuffer buffer = ByteBuffer.allocate((int) file.getFileLength());

            CloseableHttpClient client = HttpClientBuilder.create().setRedirectStrategy(new DefaultRedirectStrategy() {
                @Override
                public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context)
                        throws ProtocolException {
                    boolean result = super.isRedirected(request, response, context);
                    int responseCode = response.getStatusLine().getStatusCode();
                    return result || (responseCode >= 300 && responseCode < 400);
                }
            }).build();

            HttpGet request = new HttpGet(
                    downloadUrl.replace(" ", "%20").replace("+", "%2B").replace("[", "%5b").replace("]", "%5d"));

            HttpResponse response = client.execute(request);

            InputStream input = response.getEntity().getContent();

            input.read(buffer.array());

            input.close();
            client.close();

            return buffer.array();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new byte[0];
    }

    @Override
    public List<Long> getDependencies(long addonId, long fileId) {
        CurseFile curseFile = curseFileAPI.getFile(addonId, fileId).get();
        List<Long> dependencies = new ArrayList<>();

        for (CurseFileDependency dep : curseFile.getDependencies()) {
            dependencies.add(dep.getAddonId());
        }

        return dependencies;
    }
}
