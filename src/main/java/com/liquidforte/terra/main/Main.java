package com.liquidforte.terra.main;

import com.google.inject.Injector;
import com.liquidforte.terra.api.cache.LockCache;
import com.liquidforte.terra.api.cache.ModCache;
import com.liquidforte.terra.api.command.Command;
import com.liquidforte.terra.api.command.CommandParser;
import com.liquidforte.terra.api.database.DatabaseServer;
import com.liquidforte.terra.cache.inject.CacheModule;
import com.liquidforte.terra.curse.inject.CurseClientModule;
import com.liquidforte.terra.database.inject.DatabaseModule;
import com.liquidforte.terra.inject.AppModule;
import com.liquidforte.terra.inject.CommandModule;
import com.liquidforte.terra.inject.CoreModule;
import com.liquidforte.terra.inject.JacksonModule;
import com.netflix.governator.guice.LifecycleInjector;
import com.netflix.governator.lifecycle.LifecycleManager;

public class Main {
    public static void main(String[] args) throws Exception {
        Injector injector = LifecycleInjector.builder()
                .withModules(new AppModule(args),
                        new CacheModule(),
                        new CommandModule(),
                        new CoreModule(),
                        new CurseClientModule(),
                        new DatabaseModule(),
                        new JacksonModule())
                .build().createInjector();

        try (LifecycleManager manager = injector.getInstance(LifecycleManager.class)) {
            manager.start();

            runApp(injector);
        }
    }

    public static void runApp(Injector injector) {
        try (DatabaseServer databaseServer = injector.getInstance(DatabaseServer.class)) {
            databaseServer.start();

            CommandParser parser = injector.getInstance(CommandParser.class);
            Command command = parser.parse();

            if (command != null) {
                if (command.needsLockCache()) {
                    ModCache modCache = injector.getInstance(ModCache.class);
                    modCache.load();
                    LockCache lockCache = injector.getInstance(LockCache.class);
                    lockCache.load();

                    command.run();

                    lockCache.save();

                    modCache.save();
                } else {
                    command.run();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
