package com.liquidforte.terra.api.command;

import java.util.Set;

public interface Command extends Runnable {
    default String getCommand() {
        String name = getClass().getName();

        if (name.contains(".")) {
            name = name.substring(name.lastIndexOf(".") + 1);
        }

        if (name.endsWith("Command")) {
            name = name.substring(0, name.lastIndexOf("Command"));
        }

        return name.toLowerCase().charAt(0) + name.substring(1);
    }

    default Set<String> getAlias() {
        return Set.of();
    }

    default boolean needsDatabase() { return false; }

    default boolean needsLockCache() { return false; }

    default boolean isEmpty() { return false; }

    String getDescription();
}
