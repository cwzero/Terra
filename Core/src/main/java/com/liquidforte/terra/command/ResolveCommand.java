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

public class ResolveCommand extends LockCommand {
    private static final Logger LOG = LoggerFactory.getLogger(ResolveCommand.class);

    @Inject
    public ResolveCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void doRun() {
        LOG.info("Resolve Command running!");
        ExecutorService exec = getExecutorService();

        for (Group group : getGroupLoader().loadGroups()) {
            exec.execute(() -> {
                LOG.info("Found group: " + group.getId());
                for (ModSpec spec : group.getMods()) {
                    exec.submit(() -> {
                        LOG.info("Resolving: " + spec.getSlug());
                        getLockCache().getLock(spec.getSlug());
                    });
                }
            });
        }

        exec.shutdown();
        try {
            while (!exec.awaitTermination(5, TimeUnit.MINUTES)) {}
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDescription() {
        return "Resolve the mods, download info from the CurseForge API into the local cache";
    }
}
