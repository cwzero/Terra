package com.liquidforte.terra.command.server;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.command.LockCommand;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class GenerateServerFilesCommand extends LockCommand {
    @Inject
    public GenerateServerFilesCommand(CommandContext context) {
        super(context);
        setDependencies("installForge");
    }

    private String setJavaVersion() {
        return "set PATH=PATH;" + getAppPaths().getJavaBinPath().toAbsolutePath().toString();
    }

    private void generateServerBat() {
        Path serverBatPath = getAppPaths().getServerPath().resolve("server.bat");

        try {
            Files.write(serverBatPath, Arrays.asList(
                    "@ECHO OFF",
                    setJavaVersion(),
                    "java -Xms" + getAppConfig().getMinMemory() + "M -Xmx" + getAppConfig().getMaxMemory() + "M -jar forge-" + getAppConfig().getMinecraftVersion() + "-" + getLockCache().getForgeLock() + ".jar nogui",
                    "pause"
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateServerRunnerBat() {
        Path serverRunnerBatPath = getAppPaths().getServerPath().resolve("server_runner.bat");

        try {
            Files.write(serverRunnerBatPath, Arrays.asList(
                    "@ECHO OFF",
                    "start cmd /c " + getAppPaths().getServerPath().toAbsolutePath().toString() + "/server.bat"
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateEula() {
        Path eulaPath = getAppPaths().getServerPath().resolve("eula.txt");

        try {
            Files.write(eulaPath, Arrays.asList("eula=true"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doRun() {
        // TODO: OS Dependent
        generateServerBat();
        generateServerRunnerBat();
        generateEula();
    }
}
