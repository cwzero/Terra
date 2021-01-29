package com.liquidforte.terra.command;

import java.util.concurrent.Callable;

public interface Command extends Callable {
    default String getCommand() {
        String name = getClass().getName();

        if (name.contains(".")) {
            name = name.substring(name.lastIndexOf(".") + 1);
        }

        if (name.contains("Command")) {
            name = name.replace("Command", "");
        }

        return name.toLowerCase();
    }

    default String[] getAlias() {
        return new String[0];
    }
}
