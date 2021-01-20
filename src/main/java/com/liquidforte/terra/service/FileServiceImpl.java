package com.liquidforte.terra.service;

import com.liquidforte.curse.client.CurseClient;
import com.liquidforte.curse.file.CurseFile;
import com.liquidforte.terra.model.File;
import com.liquidforte.terra.model.Mod;
import com.liquidforte.terra.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class FileServiceImpl implements FileService {
    @Autowired
    private FileRepository repository;

    @Autowired
    private ModService modService;

    private CurseClient curseClient = new CurseClient();

    private File mapFile(Mod mod, CurseFile cf) {
        return new File(
                mod,
                cf.getId(),
                cf.getDisplayName(),
                cf.getFileName(),
                cf.getFileDate(),
                cf.getFileLength(),
                cf.getReleaseType(),
                cf.getFileStatus(),
                cf.getDownloadUrl(),
                cf.getPackageFingerprint()
        );
    }

    @Override
    public List<File> getFiles(Mod mod) {
        return curseClient.getFiles(mod.getCurseId())
                .stream()
                .map(cf -> mapFile(mod, cf))
                .collect(Collectors.toList());
    }

    @Override
    public List<File> getFiles(String slug) {
        return getFiles(modService.findBySlug(slug).get());
    }

    @Override
    public List<File> getFiles(long modId) {
        return getFiles(modService.findById(modId).get());
    }

    @Override
    public Optional<File> getFileByCurseId(long modId, long fileId) {
        return getFileByCurseId(modService.findById(modId).get(), fileId);
    }

    @Override
    public Optional<File> getFile(long modId, long fileId) {
        return repository.findById(fileId).filter(f -> f.getMod().getId().get() == modId);
    }

    @Override
    @Transactional
    public Optional<File> getFileByCurseId(Mod mod, long fileId) {
        return curseClient.getFile(mod.getCurseId(), fileId)
                .map(cf -> mapFile(mod, cf))
                .map(f -> repository.save(f));
    }

    @Override
    @Transactional
    public Optional<File> getFile(Mod mod, long fileId) {
        return repository.findById(fileId).filter(f -> f.getMod().getId() == mod.getId());
    }
}
