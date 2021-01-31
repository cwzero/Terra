package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.util.JavaUtil;

import java.nio.file.Files;
import java.nio.file.Path;

public class InstallJavaCommand extends AbstractCommand {
    @Inject
    public InstallJavaCommand(CommandContext context) {
        super(context);
    }

    @Override
    protected void doRun() {
        Path javaExe = getAppPaths().getJavaBinPath().resolve("java.exe");
        if (!Files.exists(javaExe)) {
            JavaUtil.installJava(getAppPaths().getJavaPath());
        }
    }
}
