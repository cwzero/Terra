package com.liquidforte.terra.cache;

import com.google.inject.Inject;
import com.liquidforte.terra.api.cache.ModCache;
import com.liquidforte.terra.api.config.AppConfig;
import com.liquidforte.terra.api.service.ModService;
import com.liquidforte.terra.api.service.SearchService;
import com.liquidforte.terra.api.storage.ModStorage;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.function.BiConsumer;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class ModCacheImpl implements ModCache {
    private static final int MIN = 3000;
    private final AppConfig appConfig;
    private final ModService modService;
    private final ModStorage modStorage;
    private final SearchService searchService;

    @Override
    public long getAddonId(String slug) {
        if (modStorage.getModCount() < MIN) {
            getAddons(appConfig.getMinecraftVersion(), MIN);
            for (String altVer : appConfig.getAlternateVersions()) {
                getAddons(altVer, MIN);
            }
        }

        long result = modStorage.getAddonId(slug);

        if (result <= 0) {
            BiConsumer<String, Long> callback = (foundSlug, foundId) -> modStorage.setAddonId(foundId, foundSlug);
            modService.getAddonId(appConfig.getMinecraftVersion(),
                    appConfig.getAlternateVersions().toArray(new String[0]),
                    slug, callback, callback);
        }

        return result;
    }

    @Override
    public void getAddons(String minecraftVersion, int count) {
        Map<String, Long> ids = searchService.search(minecraftVersion, count);
        for (String slug : ids.keySet()) {
            modStorage.setAddonId(ids.get(slug), slug);
        }
    }
}
