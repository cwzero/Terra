package com.liquidforte.terra.api.command;

public interface CommandFactory {
    Command createCommand(CommandContext context);
    Command createCommand(CommandContext context, String[] command);
    Command createCommand(CommandContext context, String command);
}
