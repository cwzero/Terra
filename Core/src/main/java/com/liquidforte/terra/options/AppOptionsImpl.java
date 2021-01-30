package com.liquidforte.terra.options;

import com.liquidforte.terra.api.options.AppOptions;
import org.apache.commons.cli.*;

import java.nio.file.Path;
import java.nio.file.Paths;

public class AppOptionsImpl implements AppOptions {
    private static Options options;

    private static Options getOptions() {
        if (options == null) {
            options = new Options();

            Option appConfigPathOption = new Option("appConfigPath", "acp", true, "Path to app config app.json");
            options.addOption(appConfigPathOption);

            Option groupsPathOption = new Option("groupsPath", "gp", true, "Path to groups/");
            options.addOption(groupsPathOption);

            Option modsPathOption = new Option("modsPath", "mp", true, "Path to mods/");
            options.addOption(modsPathOption);

            Option cachePathOption = new Option("cachePath", "cp", true, "path to cache");
            options.addOption(cachePathOption);

            Option localCachePathOption = new Option("localCachePath", "lcp", true, "path to local cache");
            options.addOption(localCachePathOption);

            Option localOption = new Option("local", "L", false, "run in local mode");
            options.addOption(localOption);
        }

        return options;
    }

    private static Options createOptions() {
        Options result = new Options();

        Option appConfigPathOption = new Option("appConfigPath", "acp", true, "Path to app config app.json");
        result.addOption(appConfigPathOption);

        return result;
    }

    private String[] command;
    private final String[] args;
    private CommandLine cmd;

    private Path groupsPath = Paths.get("groups");
    private Path modsPath = Paths.get("mods");

    public AppOptionsImpl(String... args) {
        this.args = args;

        command = new String[]{"resolve"};
    }

    public CommandLine parse() {
        if (cmd == null) {
            cmd = parse(args);
        }
        return cmd;
    }

    public CommandLine parse(String[] args) {
        return parse(getOptions(), args);
    }

    public CommandLine parse(Options options, String[] args) {
        return parse(getParser(), getHelp(), options, args);
    }

    public CommandLine parse(CommandLineParser parser, HelpFormatter formatter, Options options, String[] args) {
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("terra", options);

            System.exit(1);
        }

        return cmd;
    }

    public HelpFormatter getHelp() {
        return new HelpFormatter();
    }

    public CommandLineParser getParser() {
        return new DefaultParser();
    }

    public String[] getCommand() {
        return command;
    }

    @Override
    public boolean isLocal() {
        parse();
        return cmd.hasOption("local");
    }

    @Override
    public Path getAppConfigPath() {
        parse();
        return Paths.get(cmd.getOptionValue("appConfigPath", "src/terra/config/app.json"));
    }

    @Override
    public Path getGroupsPath() {
        parse();
        return Paths.get(cmd.getOptionValue("groupsPath", "src/terra/groups"));
    }

    @Override
    public Path getModsPath() {
        parse();
        return Paths.get(cmd.getOptionValue("groupsPath", "src/minecraft/mods"));
    }

    @Override
    public Path getCachePath() {
        parse();

        if (isLocal()) {
            return getLocalCachePath();
        } else {
            return Paths.get(cmd.getOptionValue("cachePath", "~/.terra/cache"));
        }
    }

    @Override
    public Path getLocalCachePath() {
        parse();
        return Paths.get(cmd.getOptionValue("localCachePath", ".terra/cache"));
    }
}
