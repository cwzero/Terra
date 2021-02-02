package com.liquidforte.terra.command.server;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.command.AbstractCommand;
import com.liquidforte.terra.util.ExecUtil;

public class RunServerCommand extends AbstractCommand {
    @Inject
    public RunServerCommand(CommandContext context) {
        super(context);
        setDependencies("generateServerFiles", "install");
    }

    @Override
    protected void doRun() {
        ExecUtil.exec(getAppPaths().getServerPath().toAbsolutePath().toFile(), "cmd /c server_runner.bat");
    }
}
