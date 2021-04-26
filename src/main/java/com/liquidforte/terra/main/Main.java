package com.liquidforte.terra.main;

import com.google.inject.Injector;
import com.liquidforte.terra.api.application.Application;
import com.liquidforte.terra.api.database.DatabaseServer;
import com.liquidforte.terra.database.inject.DatabaseModule;
import com.liquidforte.terra.inject.AppModule;
import com.netflix.governator.guice.LifecycleInjector;
import com.netflix.governator.lifecycle.LifecycleManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws Exception {
        Injector injector = createInjector(args);

        ExecutorService exec = injector.getInstance(ExecutorService.class);

        try (LifecycleManager manager = injector.getInstance(LifecycleManager.class)) {
            manager.start();
            runApp(injector);
        }

        shutdown(exec);
    }

    private static void shutdown(ExecutorService exec) {
        exec.shutdown();

        try {
            while (!exec.awaitTermination(5, TimeUnit.MINUTES)) {
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private static Injector createInjector(String[] args) {
        return LifecycleInjector.builder().withModules(
                new AppModule(args),
                new DatabaseModule()
                // TODO: Add Modules
        ).build().createInjector();
    }

    private static void runApp(Injector injector) {
        Application application = injector.getInstance(Application.class);
        try (DatabaseServer databaseServer = injector.getInstance(DatabaseServer.class)) {
            databaseServer.start();
            application.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
