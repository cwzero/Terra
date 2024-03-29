package com.liquidforte.terra.curse;

import com.google.inject.Inject;
import com.liquidforte.terra.api.curse.AddonSearchService;
import com.liquidforte.terra.api.service.ModService;
import com.liquidforte.terra.curse.model.CurseAddonSearchResult;
import lombok.RequiredArgsConstructor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class ModServiceImpl implements ModService {
    private final AddonSearchService addonSearchService;

    @Override
    @Deprecated
    public long getAddonId(String mcVer, String[] altVers, String slug) {
        Optional<CurseAddonSearchResult> result = addonSearchService.findBySlug(mcVer, altVers, slug);
        if (result.isEmpty()) {
            try {
                FileOutputStream fos = new FileOutputStream("manual.txt");
                PrintStream ps = new PrintStream(fos);
                ps.println(slug);
                ps.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Failed to find mod by slug: " + slug);
            System.out.print("Enter id: ");
            Scanner in = new Scanner(System.in);
            return Long.parseLong(in.nextLine());
        } else {
            return result.get().getId();
        }
    }

    @Override
    public void getAddonId(String minecraftVersion, String[] alternateVersions, String slug, BiConsumer<String, Long> successCallback, Consumer<String> failureCallback) {
        // TODO: search technique
        Optional<CurseAddonSearchResult> result = addonSearchService.findBySlug(minecraftVersion, alternateVersions, slug);

        if (result.isEmpty()) {
            failureCallback.accept(slug);
        } else {
            successCallback.accept(slug, result.get().getId());
        }
    }

    @Override
    public boolean getAddonId(String minecraftVersion, String slug, String filter, BiConsumer<String, Long> successCallback, Consumer<String> failureCallback) {
        if (!addonSearchService.findBySlug(minecraftVersion, slug, filter, (result) -> {
            successCallback.accept(slug, result.getId());
        })) {
            failureCallback.accept(slug);
            return false;
        }
        return true;
    }
}
