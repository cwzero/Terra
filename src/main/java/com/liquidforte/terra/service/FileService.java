package com.liquidforte.terra.service;

import com.liquidforte.terra.model.File;
import com.liquidforte.terra.model.Mod;

import java.util.List;
import java.util.Optional;

public interface FileService {
    List<File> getFiles(Mod mod);

    List<File> getFiles(String slug);

    List<File> getFiles(long modId);

    Optional<File> getFileByCurseId(Mod mod, long curseId);

    Optional<File> getFileByCurseId(long modId, long curseId);

    Optional<File> getFile(long modId, long fileId);

    Optional<File> getFile(Mod mod, long fileId);
}
