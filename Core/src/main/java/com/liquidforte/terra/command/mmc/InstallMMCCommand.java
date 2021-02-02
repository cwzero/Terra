package com.liquidforte.terra.command.mmc;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.command.AbstractCommand;
import com.liquidforte.terra.util.MMCUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class InstallMMCCommand extends AbstractCommand {
    @Inject
    public InstallMMCCommand(CommandContext context) {
        super(context);
        setDependencies("linkMMC", "generateMMCFiles");
    }

    @Override
    public void doRun() {
        Path mmcExe = getAppPaths().getMMCPath().resolve("MultiMC.exe");

        if (!Files.exists(mmcExe)) {
            try {
                MMCUtil.installMMC(getAppPaths().getMMCPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
