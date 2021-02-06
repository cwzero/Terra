# Terra

Project Terra is a build tool for minecraft modpacks.
The goal of this is to allow users to create a modpack using minecraft forge, and to store the mod information and configs in git.
The mods themselves are specified using a json format, where you create json files in src/terra/groups.

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

It is currently windows-only.

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

I could use some help with documentation and testing.
Need to determine what permissions, etc are required for the symbolic linking steps