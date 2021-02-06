# Terra

Project Terra is a build tool for minecraft modpacks.

Currently in early Alpha.

The goal of this is to allow users to create a modpack using minecraft forge, and to store the mod information and configs in git.
The mods themselves are specified using a json format, where you create json files in src/terra/groups.

Why should I use this tool?
- I think it should work well for pretty much any modpack developer, especially ones who are familiar with git or already using git.  It's designed to provide a superior 
experience to using the CurseForge client or similar software to develop modpacks, presuming one is somewhat comfortable using a (hopefully) simple command-line tool.  The workflow should hopefully be relatively accessible even if you use only one or two of the commands available.
The specific advantages over using something like a curseforge manifest in git:
    - json format with slugs, so the modlist is human readable
    - caching which works with git branch-switching if you run the right commands ```terra cleanMods install``` for example
    - synching of configs with MultiMC and a local server, so you can for example run commands on the server which modify configs, and it will show up in git
        (One of my packs I remember losing a lot of /projecte setEMCs when I ran them on the server and forgot to copy the config to the git repo)

Uses https://twitchappapi.docs.apiary.io/ to get mod info.

Everything is cached to an h2 database in ~/.terra, including the mod jars themselves.  This tool is designed or at least intented to work well with huge modpacks, especially ones which might have to make small or large changes to their mod list on the fly.

There is a lock file in src/terra/pack.lock - this allows the actual tested versions of the mods to be stored in
the git repo.  Without running the ```terra update``` command, the same version of the mods will be used each time.

The groups are relatively simple, an example:
```
{
    "enabled": true,
    "mods": [
        "iron-chests"
    ]
}
```

There's a pack config in src/terra/app.json which determines the minecraft/forge version.

The contents of the mods array is using curseforge slugs, which are used with the curseforge API. To determine the
slug for a mod, search it at https://www.curseforge.com/minecraft/mc-mods, and use the slug from the page for example
https://www.curseforge.com/minecraft/mc-mods/{slug}

If there's an error when searching for the mod's ID, it will ask you to manually input it.  This could be bypassed, but would result in drastic time increases for these cases instead.

TODO: is there a better way to check slugs?

It is currently windows-only.  If there's anybody interested who's running mac/linux who wants to help me fix it for those systems, I don't expect it to be difficult.

It can be installed by running the command ```./gradlew install``` from the source directory.  Add ```%Home%/.terra/bin``` to your ```%PATH%``` to enable.

running the command ```terra help``` will list the available commands.  You can run multiple commands in one go, example: ```terra cleanMods update install```

TODO: add descriptions to help command

A typical initial workflow would be:
1. make a new directory/git repo
1. run ```terra init```
1. add mods by creating groups in src/terra/groups
1. run multimc by running ```terra runMMCInstance```
1. test it out/edit configs, etc

Example tasks:
If you need to add mods: ```terra install```
If you need to update your mods: ```terra cleanMods update install```

You can also run a local server by running ```terra runServer```, or build a server dist zip by running ```terra buildServer```

- Any constructive criticism/feedback is welcome.
- I could use some help with documentation and testing.
- Need to determine what permissions, etc are required for the symbolic linking steps
- I plan to add exporters for the CurseForge client and MultiMC. The pack.lock file is practically a curse manifest already, so this should be quite easy.
- I could also add a CurseForge zip importer, if that seems useful.
- The README is a mess, and the rest is entirely undocumented beyond the ```terra help``` command.
- The tool's output is an absolute mess, need to move stuff into log file(s)

Reddit Discussion: https://www.reddit.com/r/ModdedMinecraft/comments/le6m8m/tool_for_building_modpacks/
