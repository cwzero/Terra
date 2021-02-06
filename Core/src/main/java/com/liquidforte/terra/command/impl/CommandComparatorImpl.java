package com.liquidforte.terra.command.impl;

import com.liquidforte.terra.api.command.Command;
import com.liquidforte.terra.command.CompositeCommand;

import java.util.Arrays;
import java.util.Comparator;

public class CommandComparatorImpl implements Comparator<Command> {
    private enum CommandType {
        Init, Commands, Clean, CleanMods, Update, Resolve, Download, Install, Link, InstallJava, BuildServer, GenerateMMCFiles, InstallMMC, LinkMMC, RunMMC, RunMMCInstance,
        GenerateServerFiles, InstallForge, LinkServer, RunServer
    }

    private CommandType getType(Command command) {
        String type = command.getCommand();

        type = type.toUpperCase().charAt(0) + type.substring(1);

        return CommandType.valueOf(type);
    }

    private int getPriority(Command command) {
        if (command.isEmpty()) {
            return 102;
        }

        if (command instanceof CompositeCommand) {
            CompositeCommand composite = (CompositeCommand) command;
            Command[] children = composite.getCommands();

            int min = Arrays.stream(children).map(this::getPriority).min(Integer::compareTo).orElse(101);
            return min;
        } else {
            return getType(command).ordinal();
        }
    }

    @Override
    public int compare(Command a, Command b) {
        if (a.getClass().isInstance(b)) {
            return 0;
        }

        int ap = getPriority(a);
        int bp = getPriority(b);

        return ap - bp;
    }
}
