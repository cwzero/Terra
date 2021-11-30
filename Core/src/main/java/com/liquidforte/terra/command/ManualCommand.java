package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;

public class ManualCommand extends AbstractCommand {
    @Inject
    public ManualCommand(CommandContext context) {
        super(context);
    }

    @Override
    public String getDescription() {
        return "Manually Enter Slug:AddonID pairs";
    }

    @Override
    protected void doRun() {

    }
}
