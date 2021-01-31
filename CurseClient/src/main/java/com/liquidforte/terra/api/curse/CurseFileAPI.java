package com.liquidforte.terra.api.curse;

import com.liquidforte.terra.curse.model.CurseFile;
import feign.Param;
import feign.RequestLine;

import java.util.List;
import java.util.Optional;

public interface CurseFileAPI {
    @RequestLine("GET /addon/{addonId}/files")
    List<CurseFile> getFiles(@Param("addonId") long addonId);

    @RequestLine("GET /addon/{addonId}/file/{fileId}")
    Optional<CurseFile> getFile(@Param("addonId") long addonId, @Param("fileId") long fileId);

    @RequestLine("GET /addon/{addonId}/file/{fileId}/download-url")
    Optional<String> getDownloadUrl(@Param("addonId") long addonId, @Param("fileId") long fileId);
}
