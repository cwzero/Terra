package com.liquidforte.terra.command.mmc;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.command.AbstractCommand;
import com.liquidforte.terra.util.ExecUtil;

public class RunMMCCommand extends AbstractCommand {
    @Inject
    public RunMMCCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void doRun() {
        getCommandParser().run("installMMC", "install");

        ExecUtil.exec(getAppPaths().getMMCPath().toFile(), "cmd /c start MultiMC.exe");
    }
}
