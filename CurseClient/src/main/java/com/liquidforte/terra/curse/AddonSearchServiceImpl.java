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
import java.util.stream.Stream;

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
            System.out.println(results.get(0).getSlug());

            Stream<CurseAddonSearchResult> matches = results.stream().filter(it -> it.getSlug().contentEquals(slug));
            Optional<CurseAddonSearchResult> r = matches.findAny();
            if (r.isPresent()) {
                return r;
            }

            request.setIndex(request.getIndex() + 1);
            System.out.println("Scanning page " + request.getIndex());
            results = searchAddons(request);
        }

        if (!Strings.isNullOrEmpty(filter)) {
            String f = "";

            if (filter.length() > 1) {
                f = filter.substring(0, filter.length() - 1);

                return findBySlug(mcVer, f, slug, deep);
            } else if (deep) {
                return findBySlug(mcVer, "", slug, true);
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<CurseAddonSearchResult> findBySlug(String mcVer, String[] altVers, String slug) {
        if (slug.contains("-")) {
            return findBySlug(mcVer, altVers, slug.substring(0, slug.indexOf("-")), slug);
        } else {
            return findBySlug(mcVer, altVers, slug, slug);
        }
    }
}
