package com.liquidforte.terra.controller;

import com.liquidforte.curse.client.CurseClient;
import com.liquidforte.terra.model.File;
import com.liquidforte.terra.model.Mod;
import com.liquidforte.terra.service.FileService;
import com.liquidforte.terra.service.ModService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("search")
public class ModSearchController {
    private CurseClient curseClient = new CurseClient();

    @Autowired
    private ModService modService;

    @Autowired
    private FileService fileService;

    private List<File> getFiles(Mod mod) {
        return fileService.getFiles(mod);
    }

    @RequestMapping("/fileByMod/{modId}/{fileId}")
    public Optional<File> fileByModAndCurseId(@PathVariable long modId, @PathVariable long fileId) {
        return fileService.getFileByCurseId(modService.findById(modId).get(), fileId);
    }

    @RequestMapping("/modBySlug/{slug}")
    public Optional<Mod> findBySlug(@PathVariable String slug) {
        return modService.findBySlug(slug);
    }

    @RequestMapping("/filesBySlug/{slug}")
    public List<File> findFilesBySlug(@PathVariable String slug) {
        return getFiles(findBySlug(slug).get());
    }

    @RequestMapping("/fileBySlug/{slug}/{fileId}")
    public Optional<File> findFileBySlug(@PathVariable String slug, @PathVariable long fileId) {
        return fileService.getFile(findBySlug(slug).get(), fileId);
    }

    @RequestMapping("/fileBySlugAndCurseId/{slug}/{fileId}")
    public Optional<File> findFileBySlugAndCurseId(@PathVariable String slug, @PathVariable long fileId) {
        return fileService.getFileByCurseId(findBySlug(slug).get(), fileId);
    }

    @RequestMapping("/modByCurseId/{modId}")
    public Optional<Mod> findByCurseId(@PathVariable long modId) {
        return modService.findByCurseId(modId);
    }

    @RequestMapping("/filesByCurseId/{modId}/files")
    public List<File> findFilesByCurseId(@PathVariable long modId) {
        return getFiles(findByCurseId(modId).get());
    }

    @RequestMapping("/fileByCurseId/{modId}/{fileId}")
    public Optional<File> findFileByCurseId(@PathVariable long modId, @PathVariable long fileId) {
        return fileService.getFile(findByCurseId(modId).get(), fileId);
    }

    @RequestMapping("/fileByCurseId2/{modId}/{fileId}")
    public Optional<File> findFileByCurseId2(@PathVariable long modId, @PathVariable long fileId) {
        return fileService.getFile(findByCurseId(modId).get(), fileId);
    }
}
