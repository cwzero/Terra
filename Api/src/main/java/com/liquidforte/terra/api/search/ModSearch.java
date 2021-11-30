package com.liquidforte.terra.api.search;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Represents the search technique
 */
public interface ModSearch {
    void getAddonId(String minecraftVersion, String[] alternateMinecraftVersions, String slug, BiConsumer<String, Long> successCallback, Consumer<String> failureCallback);
}
