package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.util.FileUtil;

public class CleanCommand extends AbstractCommand {
    @Inject
    public CleanCommand(CommandContext context) {
        super(context);
    }

    @Override
    protected void doRun() {
        FileUtil.rmDir(getAppPaths().getBuildPath());
    }
}
