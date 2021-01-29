package com.liquidforte.terra.cache;

import com.google.inject.Inject;
import com.liquidforte.curse.client.Client;
import com.liquidforte.terra.database.CacheDatabase;
import com.liquidforte.terra.model.lock.ModLock;

public class TerraModCache extends AbstractCache implements ModCache {
    private final Client client;
    private final CacheDatabase cacheDatabase;

    @Inject
    public TerraModCache(Client client, CacheDatabase cacheDatabase) {
        this.client = client;
        this.cacheDatabase = cacheDatabase;
    }

    @Override
    public long getAddonId(String slug) {
        long result = cacheDatabase.getAddonId(slug);

        if (result <= 0) {
            result = client.findBySlug(slug).map(mod -> {
                long id = mod.getId();

                cacheDatabase.insertMod(new ModLock(id, slug));

                return id;
            }).orElse(-1L);
        }

        return result;
    }
}
