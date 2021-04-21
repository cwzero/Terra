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

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class ModServiceImpl implements ModService {
    private final AddonSearchService addonSearchService;

    @Override
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
    public void getAddonId(String minecraftVersion, String[] alternateVersions, String slug, BiConsumer<String, Long> successCallback, BiConsumer<String, Long> failureCallback) {

    }
}
