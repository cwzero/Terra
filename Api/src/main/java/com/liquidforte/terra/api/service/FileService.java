package com.liquidforte.terra.api.service;

import com.liquidforte.terra.api.model.File;

import java.util.List;

public interface FileService {
    long getLatestFile(String mcVer, String[] altVer, long addonId);
    File getFile(long addonId, long fileId);

    byte[] downloadFile(File file);

    List<Long> getDependencies(long addonId, long fileId);
}
