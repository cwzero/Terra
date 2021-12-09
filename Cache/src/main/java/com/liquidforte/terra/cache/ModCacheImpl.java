package com.liquidforte.terra.cache;

import com.google.inject.Inject;
import com.liquidforte.terra.api.cache.ModCache;
import com.liquidforte.terra.api.config.AppConfig;
import com.liquidforte.terra.api.search.ModSearch;
import com.liquidforte.terra.api.service.SearchService;
import com.liquidforte.terra.api.storage.ModStorage;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class ModCacheImpl implements ModCache {
    private static final int MIN = 200;
    private final AppConfig appConfig;
    private final ModSearch modSearch;
    private final ModStorage modStorage;
    private final SearchService searchService;
    private final Map<String, Long> manual = new HashMap<>();
    private final List<String> unresolved = new ArrayList<>();

    @Override
    public void save() {
        try {
            PrintWriter fos = new PrintWriter(new FileOutputStream("manual.csv"), true);
            fos.println("slug, id");
            for (String slug : manual.keySet()) {
                long id = manual.get(slug);

                fos.printf("%s, %d\n", slug, id);
            }

            for (String slug : unresolved) {
                fos.printf("%s, %d\n", slug, -1);
            }

            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load() {
        try {
            Scanner fis = new Scanner(new File("manual.csv"));
            fis.nextLine();

            while (fis.hasNextLine()) {
                String[] line = fis.nextLine().split(",");

                String slug = line[0].trim().toLowerCase(Locale.ROOT);
                long id = Long.parseLong(line[1].trim());

                addManual(slug, id);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void readManual(String slug) {
        if (!manual.containsKey(slug)) {
            addManual(slug, -1);
        }
    }

    private void addManual(String slug, long id) {
        manual.put(slug, id);
        if (id == -1) {
            unresolved.add(slug);
        }
        if (id != -1) {
            modStorage.setAddonId(id, slug);
        }
    }

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
            BiConsumer<String, Long> successCallback = (foundSlug, foundId) -> {
                if (foundId > 0) {
                    modStorage.setAddonId(foundId, foundSlug);
                }
            };

            Consumer<String> failureCallback = fSlug -> {
                if (!manual.containsKey(fSlug)) {
                    readManual(fSlug);
                }
                long id = manual.get(fSlug);
                if (id > 0) {
                    modStorage.setAddonId(id, fSlug);
                }
            };

            modSearch.getAddonId(appConfig.getMinecraftVersion(),
                    appConfig.getAlternateVersions().toArray(new String[0]),
                    slug, successCallback, failureCallback);

            result = modStorage.getAddonId(slug);
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
