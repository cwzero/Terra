package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;

public class LinkCommand extends AbstractCommand {
    @Inject
    public LinkCommand(CommandContext context) {
        super(context);
        setDependencies("linkMMC", "linkServer");
    }

    @Override
    public void doRun() {

    }
}
