package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.config.AppOptions;
import lombok.val;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CommandParser {
    private AppOptions options;
    private Set<Command> commands;

    @Inject
    public CommandParser(AppOptions options, Set<Command> commands) {
        this.options = options;
        this.commands = commands;
    }

    public Object[] run(String... commands) {
        List<Object> result = new ArrayList<>();

        for (String command : commands) {
            result.addAll(Arrays.asList(run(command)));
        }

        return result.toArray();
    }

    public Object[] run(String c) {
        String[] cs = c.split("\\s+");

        if (cs.length > 1) {
            return run(c.split("\\s+"));
        } else {
            String invoked = cs[0];

            for (Command co : this.commands) {
                if (co.getCommand().contentEquals(invoked) || Arrays.asList(co.getAlias()).stream().anyMatch(it -> it.contentEquals(invoked))) {
                    try {
                        val result = co.call();
                        if (result != null) {
                            return new Object[]{result};
                        } else {
                            return new Object[0];
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            if (!c.contentEquals("help")) {
                return run("help");
            } else {
                return new Object[0];
            }
        }
    }

    public Object[] run() {
        return run(options.getCommand());
    }
}
