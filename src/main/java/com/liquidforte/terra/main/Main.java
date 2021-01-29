package com.liquidforte.terra.main;

import com.liquidforte.terra.app.App;
import com.liquidforte.terra.config.AppOptions;
import com.liquidforte.terra.inject.InjectorBuilder;
import lombok.val;

public class Main {
    public static void main(String[] args) {
        val injector = InjectorBuilder.buildApp(args);

        val app = injector.getInstance(App.class);
        app.run();
    }
}
