package com.liquidforte.terra.curse;

import com.google.inject.Inject;
import com.liquidforte.terra.api.search.ModSearch;
import com.liquidforte.terra.api.service.ModService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ModSearchImpl implements ModSearch {
    private final ModService modService;
    private final ExecutorService executorService;

    @Inject
    public ModSearchImpl(ModService modService) {
        this.modService = modService;
        this.executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void getAddonId(String minecraftVersion, String[] alternateMinecraftVersions, String slug, BiConsumer<String, Long> successCallback, Consumer<String> failureCallback) {
        // first search in default mc ver for slug
        if (!modService.getAddonId(minecraftVersion, slug, successCallback, failureCallback)) {
            // then search alternate versions
            for (String altVer : alternateMinecraftVersions) {
                if (modService.getAddonId(altVer, slug, successCallback, failureCallback)) {
                    return;
                }
            }
            // TODO: deep search
        }

        // Then what?
    }
}
