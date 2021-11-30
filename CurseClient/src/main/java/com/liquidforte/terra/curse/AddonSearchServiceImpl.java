package com.liquidforte.terra.curse;

import com.google.inject.Inject;
import com.liquidforte.terra.api.curse.AddonSearchAPI;
import com.liquidforte.terra.api.curse.AddonSearchService;
import com.liquidforte.terra.curse.model.CurseAddonSearchRequest;
import com.liquidforte.terra.curse.model.CurseAddonSearchResult;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.function.Consumer;
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
        if (pageSize > 50) {
            int requestCount = (int) (pageSize / 50) + 1;

            List<CurseAddonSearchResult> searchResults = new ArrayList<>();
            for (int a = 0; a < requestCount; a++) {
                searchResults.addAll(searchAddons(categoryId, gameId, gameVersion, index + a, 50, searchFilter, sectionId, sort));
            }
            return searchResults;
        }
        return addonSearchAPI.searchAddons(categoryId, gameId, gameVersion, index, pageSize, searchFilter, sectionId, sort);
    }

    public Optional<CurseAddonSearchResult> findBySlug(String mcVer, String[] altVers, String filter, String slug) {
        Optional<CurseAddonSearchResult> res;

        for (String v : altVers) {
            res = findBySlug(v, filter, slug);

            if (res.isPresent()) {
                return res;
            }
        }

        res = findBySlug(mcVer, filter, slug);

        if (res.isPresent()) {
            return res;
        }

        if (filter.contains("-")) {
            res = findBySlug(mcVer, altVers, filter.replace("-", "+"), slug);

            if (res.isPresent()) {
                return res;
            }
        }

        if (filter.length() > 3) {
            String f = filter.substring(0, filter.length() - 1);

            return findBySlug(mcVer, altVers, f, slug);
        }

        return findBySlug(mcVer, altVers, "", slug);
    }

    public Optional<CurseAddonSearchResult> findBySlug(String mcVer, String filter, String slug) {
        CurseAddonSearchRequest request = new CurseAddonSearchRequest();
        request.setSearchFilter(filter);
        request.setGameVersion(mcVer);

        List<CurseAddonSearchResult> results = searchAddons(request);
        System.out.println("Searching: " + filter + " version: " + mcVer);
        System.out.println("Scanning page: " + request.getIndex());

        while (!results.isEmpty()) {
            Stream<CurseAddonSearchResult> matches = results.stream().filter(it -> it.getSlug().contentEquals(slug));
            Optional<CurseAddonSearchResult> r = matches.findAny();
            if (r.isPresent()) {
                return r;
            }

            request.setIndex(request.getIndex() + 1);
            System.out.print("\033[1A");
            System.out.println("Scanning page " + request.getIndex());
            results = searchAddons(request);
        }


        if (filter.contains("+")) {
            String[] f = filter.split("\\+");
            for (String fi : f) {
                if (fi.length() >= 3) {
                    Optional<CurseAddonSearchResult> res = findBySlug(mcVer, fi, slug);
                    if (res.isPresent()) {
                        return res;
                    }
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean findBySlug(String mcVer, String slug, String filter, Consumer<CurseAddonSearchResult> successCallback) {
        CurseAddonSearchRequest request = new CurseAddonSearchRequest();
        request.setSearchFilter(filter);
        request.setGameVersion(mcVer);

        List<CurseAddonSearchResult> results = searchAddons(request);
        System.out.println("Searching: " + filter + " version: " + mcVer);
        System.out.println("Scanning page: " + request.getIndex());

        while (!results.isEmpty()) {
            for (CurseAddonSearchResult result : results) {
                if (result.getSlug().equalsIgnoreCase(slug)) {
                    successCallback.accept(result);
                    return true;
                }
            }

            request.setIndex(request.getIndex() + 1);
            System.out.print("\033[1A");
            System.out.println("Scanning page " + request.getIndex());
            results = searchAddons(request);
        }

        return false;
    }

    @Override
    public Optional<CurseAddonSearchResult> findBySlug(String mcVer, String[] altVers, String slug) {
        System.out.println("Searching for addon id for slug: " + slug);
        // TODO: Search Technique
        return findBySlug(mcVer, altVers, slug, slug);
    }

    @Override
    public boolean findBySlug(String mcVer, String[] altVers, String slug, Consumer<CurseAddonSearchResult> successCallback) {
        return findBySlug(mcVer, altVers, slug, slug, successCallback);
    }

    private boolean findBySlug(String mcVer, String[] altVers, String filter, String slug, Consumer<CurseAddonSearchResult> successCallback) {
        if (!findBySlug(mcVer, filter, slug, successCallback)) {
            for (String altVer : altVers) {
                if (findBySlug(altVer, filter, slug, successCallback)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Map<String, Long> search(String minecraftVersion, int count) {
        CurseAddonSearchRequest request = new CurseAddonSearchRequest();
        request.setGameVersion(minecraftVersion);
        request.setPageSize(count);

        List<CurseAddonSearchResult> result = searchAddons(request);

        Map<String, Long> res = new HashMap<>();

        for (CurseAddonSearchResult mod : result) {
            res.put(mod.getSlug(), mod.getId());
        }

        return res;
    }
}
