package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.util.FileUtil;

import java.util.concurrent.ExecutorService;

public class CleanCommand extends AbstractCommand {
    @Inject
    public CleanCommand(CommandContext context) {
        super(context);
    }

    @Override
    protected void doRun() {
        FileUtil.rmDir(getAppPaths().getBuildPath());
    }

    @Override
    public String getDescription() {
        return "Clean the output from 'buildServer'";
    }
}
