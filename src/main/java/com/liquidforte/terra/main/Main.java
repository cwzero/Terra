package com.liquidforte.terra.main;

import com.google.inject.Injector;
import com.liquidforte.terra.cache.inject.CacheModule;
import com.liquidforte.terra.client.inject.TerraClientModule;
import com.liquidforte.terra.core.inject.CoreModule;
import com.liquidforte.terra.curse.inject.CurseClientModule;
import com.liquidforte.terra.database.inject.DatabaseModule;
import com.liquidforte.terra.inject.AppModule;
import com.netflix.governator.guice.LifecycleInjector;
import com.netflix.governator.lifecycle.LifecycleManager;

public class Main {
    public static void main(String[] args) throws Exception {
        Injector injector = LifecycleInjector.builder()
                .withModules(new AppModule(), new CacheModule(), new TerraClientModule(), new CoreModule(), new CurseClientModule(), new DatabaseModule())
                .build().createInjector();

        try (LifecycleManager manager = injector.getInstance(LifecycleManager.class)) {
            manager.start();
        }
    }
}
