package com.liquidforte.terra.cache;

import com.google.inject.Inject;
import com.liquidforte.terra.api.cache.ModCache;
import com.liquidforte.terra.api.config.AppConfig;
import com.liquidforte.terra.api.service.ModService;
import com.liquidforte.terra.api.service.SearchService;
import com.liquidforte.terra.api.storage.ModStorage;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class ModCacheImpl implements ModCache {
    private static final int MIN = 3000;
    private final AppConfig appConfig;
    private final ModService modService;
    private final ModStorage modStorage;
    private final SearchService searchService;
    private final Map<String, Long> manual = new HashMap<>();

    @Override
    public void save() {
        try {
            PrintWriter fos = new PrintWriter(new FileOutputStream(new File("manual.csv")), true);
            fos.println("slug, id");
            for (String slug: manual.keySet()) {
                long id = manual.get(slug);

                fos.printf("%s, %d", slug, id);
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
        System.out.println("Failed to find id for slug: \"" + slug + "\"");
        Scanner input = new Scanner(System.in);
        System.out.print("Enter Id: ");
        long id = Long.parseLong(input.nextLine().trim());
        addManual(slug, id);
    }

    private void addManual(String slug, long id) {
        manual.put(slug, id);
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
                modStorage.setAddonId(foundId, foundSlug);
            };

            Consumer<String> failureCallback = fSlug -> {
                if (!manual.containsKey(fSlug)) {
                    readManual(fSlug);
                }
                modStorage.setAddonId(manual.get(fSlug), fSlug);
            };

            modService.getAddonId(appConfig.getMinecraftVersion(),
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
