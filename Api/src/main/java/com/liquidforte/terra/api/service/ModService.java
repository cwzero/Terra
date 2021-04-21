package com.liquidforte.terra.api.service;

import java.util.function.BiConsumer;

public interface ModService {
    long getAddonId(String mcVer, String[] altVers, String slug);

    void getAddonId(String minecraftVersion, String[] alternateVersions, String slug, BiConsumer<String, Long> successCallback, BiConsumer<String, Long> failureCallback);
}
