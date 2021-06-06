package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.api.model.Group;
import com.liquidforte.terra.api.model.ModSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DownloadCommand extends LockCommand {
    private static final ExecutorService exec = Executors.newCachedThreadPool();
    private static final Logger LOG = LoggerFactory.getLogger(DownloadCommand.class);

    @Inject
    public DownloadCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void doRun() {
        LOG.info("Download Command running!");

        for (Group group : getGroupLoader().loadGroups()) {
            LOG.info("Found group: " + group.getId());
            for (ModSpec spec : group.getMods()) {
                exec.execute(() -> {
                    String slug = spec.getSlug();
                    long addonId = getModCache().getAddonId(slug);
                    long fileId = getLockCache().getLock(addonId);

                    getFileCache().getFileData(addonId, fileId);
                });
            }
        }

        exec.shutdown();
        try {
            while (!exec.awaitTermination(5, TimeUnit.MINUTES)) {

            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String getDescription() {
        return "Download mods for enabled groups into the cache database";
    }
}
