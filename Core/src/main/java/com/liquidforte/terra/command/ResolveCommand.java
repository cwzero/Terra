package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.Command;
import com.liquidforte.terra.api.command.CommandContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResolveCommand extends AbstractCommand {
    private static final Logger LOG = LoggerFactory.getLogger(ResolveCommand.class);

    @Override
    public Command getInstance(CommandContext context) {
        ResolveCommand result = new ResolveCommand();
        result.setContext(context);
        return result;
    }

    @Override
    public void run() {
        LOG.info("Resolve Command running!");
    }
}
