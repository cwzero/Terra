package com.liquidforte.terra.options;

import com.liquidforte.terra.api.options.AppOptions;
import org.apache.commons.cli.*;

import java.util.Arrays;

public class AppOptionsImpl implements AppOptions {
    private static Options options;

    private static Option[] getOpts() {
        return new Option[] {
                new Option("ACP", "appConfigPath", true, "Path to app config app.json"),
                new Option("GP", "groupsPath", true, "Path to groups dir"),
                new Option("MP", "modsPath", true, "Path to mods dir"),
                new Option("MCP", "mcPath", true, "Path to minecraft dir"),
                new Option("CP", "cachePath", true, "path to cache"),
                new Option("SP", "sourcePath", true, "path to source directory"),
                new Option("LCP", "localCachePath", true, "path to local cache"),
                new Option("L", "local", false, "run in local mode"),
                new Option("LP", "lockPath", true, "path to pack.lock file"),
                new Option("TP", "terraPath", true, "path to terra dir"),
                new Option("H", "help", false, "Help!"),
                new Option("SP", "serverPath", true, "Server Path"),
                new Option("MMCP", "mmcPath", true, "MMC Path"),
                new Option("GP", "globalPath", true, "Global Path"),
                new Option("COP", "configPath", true, "Config Path"),
                new Option("RP", "resourcesPath", true, "Resources Path"),
                new Option("SP", "savesPath", true, "Saves Path"),
                new Option("SCP", "scriptsPath", true, "Scripts Path"),
                new Option("SEP", "serverPath", true, "Server Path"),
                new Option("BP", "buildPath", true, "Build Path"),
                new Option("DCP", "defaultConfigPath", true, "Default Configs Path")
        };
    }

    private static Options getOptions() {
        if (options == null) {
            options = new Options();

            Arrays.stream(getOpts()).forEach(options::addOption);
        }

        return options;
    }

    private String[] command;
    private final String[] args;
    private CommandLine cmd;

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
        return cmd.hasOption("local");
    }

    @Override
    public String getServerPath() {
        return cmd.getOptionValue("serverPath", "server");
    }

    @Override
    public String getGlobalPath() {
        return cmd.getOptionValue("globalPath", "~/.terra");
    }

    @Override
    public String getMMCPath() {
        return cmd.getOptionValue("mmcPath", "mmc");
    }

    @Override
    public String getSrcPath() {
        return cmd.getOptionValue("srcPath", "src");
    }

    @Override
    public String getBuildPath() {
        return cmd.getOptionValue("buildPath", "build");
    }

    @Override
    public String getMCPath() {
        return cmd.getOptionValue("mcPath", "minecraft");
    }

    @Override
    public String getTerraPath() {
        return cmd.getOptionValue("terraPath", "terra");
    }

    @Override
    public String getLockPath() {
        return cmd.getOptionValue("lockPath", "pack.lock");
    }

    @Override
    public String getAppConfigPath() {
        return cmd.getOptionValue("appConfigPath", "app.json");
    }

    @Override
    public String getGroupsPath() {
        return cmd.getOptionValue("groupsPath", "groups");
    }

    @Override
    public String getConfigPath() {
        return cmd.getOptionValue("configPath", "config");
    }

    @Override
    public String getModsPath() {
        return cmd.getOptionValue("modsPath", "mods");
    }

    @Override
    public String getResourcesPath() {
        return cmd.getOptionValue("resourcesPath", "resources");
    }

    @Override
    public String getSavesPath() {
        return cmd.getOptionValue("savesPath", "saves");
    }

    @Override
    public String getScriptsPath() {
        return cmd.getOptionValue("scriptsPath", "scripts");
    }

    @Override
    public String getLocalCachePath() {
        return cmd.getOptionValue("localCachePath", ".terra/cache");
    }

    @Override
    public String getCachePath() {
        return cmd.getOptionValue("cachePath", "~/.terra/cache");
    }

    @Override
    public String getDefaultConfigPath() {
        return cmd.getOptionValue("defaultConfigPath", "defaultconfigs");
    }
}
