package com.liquidforte.terra.curse.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.api.model.Group;
import com.liquidforte.terra.api.model.ModSpec;
import com.liquidforte.terra.command.LockCommand;
import com.liquidforte.terra.curse.model.CurseManifest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GenerateManifestCommand extends LockCommand {
    private static final Logger LOG = LoggerFactory.getLogger(GenerateManifestCommand.class);
    private ObjectMapper mapper;

    @Inject
    public GenerateManifestCommand(CommandContext context, ObjectMapper mapper) {
        super(context);
        this.mapper = mapper;
    }

    @Override
    public String getDescription() {
        return "Generate curse manifest";
    }

    @Override
    protected void doRun() {
        Path cursePath = getAppPaths().getBuildPath().resolve("curse");

        if (!Files.exists(cursePath)) {
            try {
                Files.createDirectories(cursePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String minecraftVersion = getAppConfig().getMinecraftVersion();
        String forgeVersion = getLockCache().getForgeLock();
        String packVersion = getAppConfig().getPackVersion();
        String packName = getAppConfig().getPackName();

        LOG.info("Generating curse manifest...");
        CurseManifest manifest = new CurseManifest(minecraftVersion, forgeVersion, packVersion, packName);

        for (Group group : getGroupLoader().loadGroups()) {
            for (ModSpec spec : group.getMods()) {
                String slug = spec.getSlug();

                long addonId = getModCache().getAddonId(slug);
                long fileId = getLockCache().getLock(addonId);

                manifest.addFile(addonId, fileId);

                for (long mod : getFileCache().getModDependencies(addonId, fileId)) {
                    long f = getLockCache().getLock(mod);
                    manifest.addFile(mod, f);
                }
            }
        }

        Path manifestPath = cursePath.resolve("manifest.json");

        try {
            if (!Files.exists(manifestPath)) {
                Files.createDirectories(manifestPath.getParent());
                Files.createFile(manifestPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mapper.writerFor(CurseManifest.class).writeValue(Files.newBufferedWriter(manifestPath), manifest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
