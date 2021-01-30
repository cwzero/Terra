package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.liquidforte.terra.api.cache.LockCache;
import com.liquidforte.terra.api.command.Command;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.loader.GroupLoader;
import com.liquidforte.terra.model.Group;
import com.liquidforte.terra.model.ModSpec;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class ResolveCommand extends AbstractCommand {
    private static final Logger LOG = LoggerFactory.getLogger(ResolveCommand.class);
    private final Provider<GroupLoader> groupLoaderProvider;
    private final Provider<LockCache> lockCacheProvider;

    public ResolveCommand(CommandContext context, Provider<GroupLoader> groupLoaderProvider, Provider<LockCache> lockCacheProvider) {
        super(context);
        this.groupLoaderProvider = groupLoaderProvider;
        this.lockCacheProvider = lockCacheProvider;
    }


    @Override
    public Command getInstance(CommandContext context) {
        return new ResolveCommand(context, groupLoaderProvider, lockCacheProvider);
    }

    @Override
    public void run() {
        LOG.info("Resolve Command running!");

        GroupLoader groupLoader = groupLoaderProvider.get();
        LockCache lockCache = lockCacheProvider.get();

        for (Group group : groupLoader.loadGroups()) {
            LOG.info("Found group: " + group.getId());
            for (ModSpec spec : group.getMods()) {
                LOG.info("Resolving: " + spec.getSlug());
                lockCache.getLock(spec.getSlug());
            }
        }
    }
}
