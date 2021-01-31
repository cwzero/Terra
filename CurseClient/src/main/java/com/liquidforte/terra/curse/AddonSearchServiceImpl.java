package com.liquidforte.terra.curse;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.liquidforte.terra.api.curse.AddonSearchAPI;
import com.liquidforte.terra.api.curse.AddonSearchService;
import com.liquidforte.terra.curse.model.CurseAddonSearchRequest;
import com.liquidforte.terra.curse.model.CurseAddonSearchResult;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class AddonSearchServiceImpl implements AddonSearchService {
    private final AddonSearchAPI addonSearchAPI;

    @Override
    public Optional<CurseAddonSearchResult> getAddon(long id) {
        return addonSearchAPI.getAddon(id);
    }

    @Override
    public List<CurseAddonSearchResult> searchAddons(long categoryId, long gameId, String gameVersion, long index, long pageSize, String searchFilter, long sectionId, long sort) {
        return addonSearchAPI.searchAddons(categoryId, gameId, gameVersion, index, pageSize, searchFilter, sectionId, sort);
    }

    public Optional<CurseAddonSearchResult> findBySlug(String mcVer, String[] altVers, String filter, String slug) {
        Optional<CurseAddonSearchResult> res = findBySlug(mcVer, filter, slug, false);

        if (res.isEmpty()) {
            for (String v : altVers) {
                res = findBySlug(v, filter, slug, false);

                if (res.isPresent()) {
                    return res;
                }
            }
        }

        if (res.isEmpty()) {
            res = findBySlug(mcVer, slug, slug, true);
        }

        if (res.isEmpty()) {
            for (String v : altVers) {
                res = findBySlug(v, filter, slug, true);

                if (res.isPresent()) {
                    return res;
                }
            }
        }

        return res;
    }

    public Optional<CurseAddonSearchResult> findBySlug(String mcVer, String filter, String slug, boolean deep) {
        CurseAddonSearchRequest request = new CurseAddonSearchRequest();
        request.setSearchFilter(filter);
        request.setGameVersion(mcVer);

        List<CurseAddonSearchResult> results = searchAddons(request);

        while (!results.isEmpty()) {
            for (CurseAddonSearchResult result : results) {
                if (result.getSlug().toLowerCase().contentEquals(slug)) {
                    return Optional.of(result);
                }
            }

            request.setIndex(request.getIndex() + 1);
            results = searchAddons(request);
        }

        if (deep && !Strings.isNullOrEmpty(filter)) {
            return findBySlug(mcVer, "", slug, deep);
        }

        return Optional.empty();
    }

    @Override
    public Optional<CurseAddonSearchResult> findBySlug(String mcVer, String[] altVers, String slug) {
        return findBySlug(mcVer, altVers, slug, slug);
    }
}
