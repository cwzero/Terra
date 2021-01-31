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

        if (result.length <= 0) {
            result = fileService.downloadFile(addonId, fileId);
            fileStorage.setData(addonId, fileId, result);
        }

        return result;
    }

    @Override
    public void installFile(long addonId, long fileId) {
        for (long mod : getModDependencies(addonId, fileId)) {
            long f = lockCache.getLock(mod);

            installFile(mod, f);
        }

        File file = getFile(addonId, fileId);
        byte[] data = getFileData(addonId, fileId);

        Path targetPath = appPaths.getMCModsPath().resolve(file.getFileName());
        java.io.File targetFile = targetPath.toFile();
        java.io.File modsDir = targetFile.getParentFile();

        if (!modsDir.exists()) {
            modsDir.mkdirs();
        }

        try {
            if (!targetFile.exists() || FingerprintUtil.getFileHash(targetFile) != file.getFingerprint()) {
                FileOutputStream fos = new FileOutputStream(targetFile);

                fos.write(data);
                fos.flush();
                fos.close();
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
