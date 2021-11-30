package com.liquidforte.terra.api.service;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface ModService {
    @Deprecated
    long getAddonId(String mcVer, String[] altVers, String slug);

    @Deprecated
    /*
     * Use ModSearch
     */
    void getAddonId(String minecraftVersion, String[] alternateVersions, String slug, BiConsumer<String, Long> successCallback, Consumer<String> failureCallback);

    default boolean getAddonId(String minecraftVersion, String slug, BiConsumer<String, Long> successCallback, Consumer<String> failureCallback) {
        return getAddonId(minecraftVersion, slug, slug, successCallback, failureCallback);
    }

    boolean getAddonId(String minecraftVersion, String slug, String filter, BiConsumer<String, Long> successCallback, Consumer<String> failureCallback);
}
