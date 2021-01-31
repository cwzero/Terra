package com.liquidforte.terra.curse;

import com.google.inject.Inject;
import com.liquidforte.terra.api.curse.AddonSearchService;
import com.liquidforte.terra.api.service.ModService;
import com.liquidforte.terra.curse.model.CurseAddonSearchResult;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class ModServiceImpl implements ModService {
    private final AddonSearchService addonSearchService;

    @Override
    public long getAddonId(String mcVer, String[] altVers, String slug) {
        Optional<CurseAddonSearchResult> result = addonSearchService.findBySlug(mcVer, altVers, slug);
        if (result.isEmpty()) {
            throw new RuntimeException("Failed to find mod by slug: " + slug);
        } else {
            return result.get().getId();
        }
    }
}
