# Terra

Project Terra is a build tool for minecraft modpacks.
The goal of this is to allow users to create a modpack using minecraft forge, and to store the mod information and configs in git.
The mods themselves are specified using a json format, where you create json files in src/terra/groups.

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

The contents of the mods array is using curseforge slugs, which are used with the curseforge API. To determine the
slug for a mod, search it at https://www.curseforge.com/minecraft/mc-mods, and use the slug from the page for example
https://www.curseforge.com/minecraft/mc-mods/{slug}

TODO: is there a better way to check slugs?

It is currently windows-only.  If there's anybody interested who's running mac/linux who wants to help me fix it for those systems, I don't expect it to be difficult.

It can be installed by running the command ```./gradlew install``` from the source directory.  Add ```%Home%/.terra/bin``` to your ```%PATH%``` to enable.

running the command ```terra help``` will list the available commands.

TODO: add descriptions to help command

A typical initial workflow would be:
1. make a new directory/git repo
1. run ```terra init```
1. add mods by creating groups in src/terra/groups
1. run multimc by running ```terra runMMCInstance```
1. test it out/edit configs, etc

You can also run a local server by running ```terra runServer```, or build a server dist zip by running ```terra buildServer```

Any constructive criticism/feedback is welcome.
I could use some help with documentation and testing.
Need to determine what permissions, etc are required for the symbolic linking steps
I plan to add exporters for the CurseForge client and MultiMC. The pack.lock file is practically a curse manifest already, so this should be quite easy.
The README is a mess, and the rest is entirely undocumented beyond the ```terra help``` command.
