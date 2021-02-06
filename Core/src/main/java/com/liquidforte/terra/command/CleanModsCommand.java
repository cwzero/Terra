package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.util.FileUtil;

import java.util.Set;

public class CleanModsCommand extends AbstractCommand {
    @Inject
    public CleanModsCommand(CommandContext context) {
        super(context);
    }

    @Override
    public Set<String> getAlias() {
        return Set.of("clean-mods");
    }

    @Override
    protected void doRun() {
        FileUtil.rmFiles(getAppPaths().getMCModsPath());
    }

    @Override
    public String getDescription() {
        return "Delete downloaded mods from src/minecraft/mods, if mods update this prevents you from having two copies of the same mod";
    }
}
