package com.liquidforte.terra.config;

import com.google.inject.Inject;
import lombok.Data;

@Data
public class AppConfig {
    private final AppOptions appOptions;
    private String[] minecraftVersion =  { "1.16", "1.16.4", "1.16.5" };
    private String groupsDir = "groups";
    private String modsDir = "mods";

    @Inject
    public AppConfig(AppOptions appOptions) {
        this.appOptions = appOptions;
    }
}
