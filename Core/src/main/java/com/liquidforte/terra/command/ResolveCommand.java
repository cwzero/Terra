package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.cache.LockCache;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.api.config.AppConfig;
import com.liquidforte.terra.loader.GroupLoader;
import com.liquidforte.terra.model.Group;
import com.liquidforte.terra.model.ModSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResolveCommand extends AbstractCommand {
    private static final Logger LOG = LoggerFactory.getLogger(ResolveCommand.class);
    private final AppConfig appConfig;
    private final GroupLoader groupLoader;
    private final LockCache lockCache;

    @Inject
    public ResolveCommand(CommandContext context, AppConfig appConfig, GroupLoader groupLoader, LockCache lockCache) {
        super(context);
        this.appConfig = appConfig;
        this.groupLoader = groupLoader;
        this.lockCache = lockCache;
    }

    @Override
    public void run() {
        LOG.info("Resolve Command running!");

        for (Group group : groupLoader.loadGroups()) {
            LOG.info("Found group: " + group.getId());
            for (ModSpec spec : group.getMods()) {
                LOG.info("Resolving: " + spec.getSlug());
                lockCache.getLock(spec.getSlug());
            }
        }
    }
}
