package com.liquidforte.terra.cache;

import com.google.inject.Inject;
import com.liquidforte.terra.api.cache.FileCache;
import com.liquidforte.terra.api.cache.LockCache;
import com.liquidforte.terra.api.model.File;
import com.liquidforte.terra.api.options.AppPaths;
import com.liquidforte.terra.api.service.FileService;
import com.liquidforte.terra.api.storage.FileStorage;
import com.liquidforte.terra.util.FingerprintUtil;
import lombok.RequiredArgsConstructor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class FileCacheImpl implements FileCache {
    private final AppPaths appPaths;
    private final FileStorage fileStorage;
    private final FileService fileService;
    private final LockCache lockCache;

    @Override
    public File getFile(long addonId, long fileId) {
        File result = fileStorage.getFile(addonId, fileId);

        if (result == null) {
            result = fileService.getFile(addonId, fileId);
            fileStorage.setFile(addonId, result);
        }

        return result;
    }

    @Override
    public byte[] getFileData(long addonId, long fileId) {
        byte[] result = fileStorage.getData(addonId, fileId);
        File file = getFile(addonId, fileId);

        if (result == null || result.length <= 0 || result.length != file.getFileLength() || file.getFingerprint() == FingerprintUtil.getByteArrayHash(result)) {
            result = fileService.downloadFile(file);

            if (result.length == file.getFileLength() && file.getFingerprint() == FingerprintUtil.getByteArrayHash(result)) {
                fileStorage.setData(addonId, fileId, result);
                return result;
            } else {
                throw new RuntimeException("Download for " + file.getFileName() + " failed.");
            }
        }

        if (result == null || result.length <= 0 || result.length != file.getFileLength() || file.getFingerprint() != FingerprintUtil.getByteArrayHash(result)) {
            throw new RuntimeException("Download for " + file.getFileName() + " failed.");
        } else {
            return result;
        }
    }

    @Override
    public void installFile(long addonId, long fileId) {
        for (long mod : getModDependencies(addonId, fileId)) {
            long f = lockCache.getLock(mod);

            installFile(mod, f);
        }

        File file = getFile(addonId, fileId);

        Path targetPath = appPaths.getMCModsPath().resolve(file.getFileName());
        Path modsPath = targetPath.getParent();

        if (!Files.exists(modsPath)) {
            try {
                Files.createDirectories(modsPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            if (!Files.exists(targetPath) || Files.size(targetPath) != file.getFileLength() || file.getFingerprint() != FingerprintUtil.getFileHash(targetPath)) {
                byte[] data = getFileData(addonId, fileId);

                if (data.length != file.getFileLength() || file.getFingerprint() != FingerprintUtil.getByteArrayHash(data)) {
                    throw new RuntimeException("Download for " + file.getFileName() + " failed.");
                } else {
                    System.out.println("Successful download for " + file.getFileName());
                }

                FileOutputStream fos = new FileOutputStream(targetPath.toFile());

                fos.write(data);
                fos.flush();
                fos.close();
            }

            if (!Files.exists(targetPath) || Files.size(targetPath) != file.getFileLength() || file.getFingerprint() != FingerprintUtil.getFileHash(targetPath)) {
                throw new RuntimeException("Download for " + file.getFileName() + " failed.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Long> getModDependencies(long addonId, long fileId) {
        List<Long> dependencies = fileStorage.getDependencies(addonId, fileId);

        if (dependencies == null || dependencies.isEmpty()) {
            dependencies = fileService.getDependencies(addonId, fileId);
            dependencies.forEach(dep -> fileStorage.addDependency(addonId, fileId, dep));
        }

        return dependencies;
    }
}
