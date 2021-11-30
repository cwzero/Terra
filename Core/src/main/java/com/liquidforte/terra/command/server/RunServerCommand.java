package com.liquidforte.terra.command.server;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.command.AbstractCommand;
import com.liquidforte.terra.util.ExecUtil;

import java.util.Set;

public class RunServerCommand extends AbstractCommand {
    @Inject
    public RunServerCommand(CommandContext context) {
        super(context);
        setDependencies("generateServerFiles", "install", "installForge", "linkServer");
    }

    @Override
    public Set<String> getAlias() {
        return Set.of("run-server");
    }

    @Override
    protected void doRun() {
        ExecUtil.exec(getAppPaths().getServerPath().toAbsolutePath().toFile(), "cmd /c server_runner.bat");
    }

    @Override
    public String getDescription() {
        return "Run local server";
    }
}
