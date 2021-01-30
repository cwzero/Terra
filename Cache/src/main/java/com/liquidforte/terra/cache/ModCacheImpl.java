package com.liquidforte.terra.cache;

import com.google.inject.Inject;
import com.liquidforte.terra.api.cache.ModCache;
import com.liquidforte.terra.api.service.ModService;
import com.liquidforte.terra.api.storage.ModStorage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class ModCacheImpl implements ModCache {
    private final ModService modService;
    private final ModStorage modStorage;

    @Override
    public long getAddonId(String slug) {
        long result = modStorage.getAddonId(slug);

        if (result == -1) {
            result = modService.getAddonId(slug);
            modStorage.setAddonId(result, slug);
        }

        return result;
    }
}
