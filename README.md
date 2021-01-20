# Terra

Project Terra is a Spring Boot Application in Java for building modpacks for Minecraft Forge.  The idea is that there will be a client which will take in a json description of the modpack, and will download the necessary jar files for the mods.  It will do so using caching and an intermediate rest api, which will store the data for the mods after downloading them from https://www.curseforge.com/minecraft/mc-mods using data from the REST api documented at https://twitchappapi.docs.apiary.io/.

The server-side will use a postgres database to store the data which has been accessed by clients.