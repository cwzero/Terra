package com.liquidforte.terra.inject;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InjectorBuilder {
    public static Injector buildApp(String[] args) {
        return buildApp(new AppModule(args));
    }

    public static Injector buildApp(Module... m) {
        List<Module> modules = new ArrayList<>(Arrays.asList(m));
        Module[] defaultModules = {
                new CacheModule(),
                new DatabaseModule(),
                new ClientModule(),
                new CommandModule(),
                new JacksonModule()
        };
        modules.addAll(Arrays.asList(defaultModules));
        return Guice.createInjector(modules);
    }
}
