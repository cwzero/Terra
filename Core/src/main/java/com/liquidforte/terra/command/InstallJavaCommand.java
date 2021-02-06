package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.util.JavaUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public class InstallJavaCommand extends AbstractCommand {
    @Inject
    public InstallJavaCommand(CommandContext context) {
        super(context);
    }

    @Override
    public Set<String> getAlias() {
        return Set.of("install-java");
    }

    @Override
    protected void doRun() {
        Path javaExe = getAppPaths().getJavaBinPath().resolve("java.exe");
        if (!Files.exists(javaExe)) {
            JavaUtil.installJava(getAppPaths().getJavaPath());
        }
    }

    @Override
    public String getDescription() {
        return "Installs a jre into ~/.terra";
    }
}
