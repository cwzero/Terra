package com.liquidforte.terra.api.command;

public interface CommandParser {
    Command parse();

    Command parse(String... commands);

    default void run() {
        parse().run();
    }

    default void run(String... commands) {
        parse(commands).run();
    }
}
