package com.liquidforte.terra.main;

import com.google.inject.Injector;
import com.liquidforte.terra.api.command.CommandParser;
import com.liquidforte.terra.cache.inject.CacheModule;
import com.liquidforte.terra.client.inject.TerraClientModule;
import com.liquidforte.terra.curse.inject.CurseClientModule;
import com.liquidforte.terra.database.inject.DatabaseModule;
import com.liquidforte.terra.inject.AppModule;
import com.liquidforte.terra.inject.CommandModule;
import com.liquidforte.terra.inject.CoreModule;
import com.netflix.governator.guice.LifecycleInjector;
import com.netflix.governator.lifecycle.LifecycleManager;

public class Main {
    public static void main(String[] args) throws Exception {
        Injector injector = LifecycleInjector.builder()
                .withModules(new AppModule(),
                        new CacheModule(),
                        new CommandModule(),
                        new CoreModule(),
                        new CurseClientModule(),
                        new DatabaseModule(),
                        //new JacksonModule(),
                        new TerraClientModule())
                .build().createInjector();

        try (LifecycleManager manager = injector.getInstance(LifecycleManager.class)) {
            manager.start();

            runApp(injector, args);
        }
    }

    public static void runApp(Injector injector, String[] args) {
        CommandParser parser = injector.getInstance(CommandParser.class);
        parser.parse(args).run();
    }
}
