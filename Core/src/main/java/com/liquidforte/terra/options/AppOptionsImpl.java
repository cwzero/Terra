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

            Option appConfigPathOption = new Option("ACP", "appConfigPath", true, "Path to app config app.json");
            options.addOption(appConfigPathOption);

            Option groupsPathOption = new Option("GP", "groupsPath", true, "Path to groups dir");
            options.addOption(groupsPathOption);

            Option modsPathOption = new Option("MP", "modsPath", true, "Path to mods dir");
            options.addOption(modsPathOption);

            Option cachePathOption = new Option("CP", "cachePath", true, "path to cache");
            options.addOption(cachePathOption);

            Option localCachePathOption = new Option("LCP", "localCachePath", true, "path to local cache");
            options.addOption(localCachePathOption);

            Option localOption = new Option("L", "local", false, "run in local mode");
            options.addOption(localOption);

            Option lockPathOption = new Option("LP", "lockPath", true, "path to pack.lock file");
            options.addOption(lockPathOption);

            Option helpOption = new Option("H", "help", false, "Help!");
            options.addOption(helpOption);
        }

        return options;
    }

    private String[] command;
    private final String[] args;
    private CommandLine cmd;

    private Path groupsPath = Paths.get("groups");
    private Path modsPath = Paths.get("mods");

    public AppOptionsImpl(String... args) {
        this.args = args;

        parse();
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
            command = cmd.getArgs();
        } catch (ParseException e) {
            formatter.printHelp("terra", options);
            System.exit(1);
        }

        if (cmd.hasOption("help")) {
            formatter.printHelp("terra", options);
            System.exit(0);
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
    public String getLockPathString() {
        parse();
        return cmd.getOptionValue("lockPath", "src/terra/pack.lock");
    }

    @Override
    public Path getLockPath() {
        return Paths.get(getLockPathString());
    }

    @Override
    public Path getAppConfigPath() {
        parse();
        return Paths.get(cmd.getOptionValue("appConfigPath", "src/terra/app.json"));
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
    public String getLocalCachePathString() {
        parse();

        return cmd.getOptionValue("localCachePath", ".terra/cache");
    }

    @Override
    public String getCachePathString() {
        if (isLocal()) {
            return getLocalCachePathString();
        } else {
            parse();
            return cmd.getOptionValue("cachePath", "~/.terra/cache");
        }
    }

    @Override
    public Path getCachePath() {
        return Paths.get(getCachePathString().replace("~", System.getProperty("user.home")));
    }

    @Override
    public Path getLocalCachePath() {
        return Paths.get(getLocalCachePathString());
    }
}
