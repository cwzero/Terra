package com.liquidforte.terra.api.command;

public interface CommandContextFactory {
    CommandContext create(String... args);
}
