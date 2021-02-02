package com.liquidforte.terra.command.mmc;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.command.AbstractCommand;
import com.liquidforte.terra.util.ExecUtil;

public class RunMMCInstanceCommand extends AbstractCommand {
    @Inject
    public RunMMCInstanceCommand(CommandContext context) {
        super(context);
        setDependencies("installMMC", "install");
    }

    @Override
    public void doRun() {
        ExecUtil.exec(getAppPaths().getMMCPath().toFile(), "cmd /c start MultiMC.exe --launch " + getAppConfig().getPackName());
    }
}
