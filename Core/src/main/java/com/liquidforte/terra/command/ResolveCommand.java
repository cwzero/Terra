package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.api.model.Group;
import com.liquidforte.terra.api.model.ModSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResolveCommand extends LockCommand {
    private static final Logger LOG = LoggerFactory.getLogger(ResolveCommand.class);

    @Inject
    public ResolveCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void doRun() {
        LOG.info("Resolve Command running!");

        for (Group group : getGroupLoader().loadGroups()) {
            LOG.info("Found group: " + group.getId());
            for (ModSpec spec : group.getMods()) {
                LOG.info("Resolving: " + spec.getSlug());
                getLockCache().getLock(spec.getSlug());
            }
        }
    }
}
