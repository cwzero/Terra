package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.cache.LockCache;
import com.liquidforte.terra.model.Group;
import com.liquidforte.terra.model.GroupLoader;
import com.liquidforte.terra.model.ModSpec;

public class ResolveCommand extends AbstractCommand {
    private final GroupLoader groupLoader;
    private final LockCache lockCache;

    @Inject
    public ResolveCommand(GroupLoader groupLoader, LockCache lockCache) {
        this.groupLoader = groupLoader;
        this.lockCache = lockCache;
    }

    @Override
    public Object call() throws Exception {
        for (Group group: groupLoader.loadGroups()) {
            for (ModSpec spec: group.getMods()) {
                lockCache.getLock(spec.getSlug(), spec.getFilter());
            }
        }

        return null;
    }
}
