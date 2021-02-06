package com.liquidforte.terra.command;

import com.liquidforte.terra.api.command.Command;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.command.impl.CommandComparatorImpl;

import java.util.Arrays;
import java.util.Objects;

public class CompositeCommand extends AbstractCommand {
    private final Command[] commands;

    public CompositeCommand(CommandContext context, Command[] commands) {
        super(context);
        this.commands = Arrays.stream(commands).filter(Objects::nonNull).toArray(Command[]::new);
    }

    @Override
    public boolean needsDatabase() {
        return super.needsDatabase() || Arrays.stream(commands).anyMatch(Command::needsDatabase);
    }

    @Override
    public boolean needsLockCache() {
        return super.needsLockCache() || Arrays.stream(commands).anyMatch(Command::needsLockCache);
    }

    @Override
    protected void before() {
        super.before();
    }

    @Override
    public void doRun() {
        Arrays.sort(commands, new CommandComparatorImpl());
        for (Command command : commands) {
            command.run();
        }
    }

    public Command[] getCommands() {
        return commands;
    }

    @Override
    public boolean isEmpty() {
        return commands.length <= 0 || Arrays.stream(commands).allMatch(it -> it.isEmpty());
    }

    @Override
    public String getDescription() {
        return "Error - composite command description should not be displayed!";
    }
}
