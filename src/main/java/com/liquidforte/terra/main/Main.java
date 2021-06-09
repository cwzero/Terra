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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

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

    private static void finish(ExecutorService exec) {
        exec.shutdown();

        while (true) {
            try {
                if (exec.awaitTermination(5, TimeUnit.MINUTES)) break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void runApp(Injector injector) {
        ExecutorService exec = injector.getInstance(ExecutorService.class);

        try (DatabaseServer databaseServer = injector.getInstance(DatabaseServer.class)) {
            databaseServer.start();
            LockCache lockCache = injector.getInstance(LockCache.class);
            ModCache modCache = injector.getInstance(ModCache.class);

            CommandParser parser = injector.getInstance(CommandParser.class);
            Command command = parser.parse();

            if (command != null) {
                if (command.needsLockCache()) {
                    modCache.load();
                    lockCache.load();
                }

                command.run();

                finish(exec);

                if (command.needsLockCache()) {
                    lockCache.save();
                    modCache.save();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
