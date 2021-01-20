package com.liquidforte.terra.controller;

import com.liquidforte.curse.client.CurseClient;
import com.liquidforte.terra.model.File;
import com.liquidforte.terra.model.Mod;
import com.liquidforte.terra.service.FileService;
import com.liquidforte.terra.service.ModService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController("/mod")
@ResponseBody
public class ModController {
    private CurseClient curseClient = new CurseClient();

    @Autowired
    private ModService modService;

    @Autowired
    private FileService fileService;

    @RequestMapping("/{id}")
    public Optional<Mod> getMod(@PathVariable long id) {
        return modService.findById(id);
    }

    @RequestMapping("/{id}/files")
    public List<File> getFiles(@PathVariable long id) {
        return fileService.getFiles(modService.findById(id).get());
    }

    @RequestMapping("/{modId}/file/{fileId}")
    public Optional<File> getFile(@PathVariable long modId, @PathVariable long fileId) {
        return fileService.getFile(modId, fileId);
    }

    @RequestMapping("/{modId}/file/{fileId}/downloadUrl")
    public Optional<String> getDownloadUrl(@PathVariable long modId, @PathVariable long fileId) {
        return fileService.getFile(modId, fileId).map(m -> m.getDownloadUrl());
    }
}
