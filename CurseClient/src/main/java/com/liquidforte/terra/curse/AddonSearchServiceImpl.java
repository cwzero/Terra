package com.liquidforte.terra.curse;

import com.google.inject.Inject;
import com.liquidforte.terra.api.curse.AddonSearchAPI;
import com.liquidforte.terra.api.curse.AddonSearchService;
import com.liquidforte.terra.curse.model.CurseAddonSearchRequest;
import com.liquidforte.terra.curse.model.CurseAddonSearchResult;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
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
        return addonSearchAPI.searchAddons(categoryId, gameId, gameVersion, index, pageSize, searchFilter, sectionId, sort);
    }

    public Optional<CurseAddonSearchResult> findBySlug(String mcVer, String[] altVers, String filter, String slug) {
        Optional<CurseAddonSearchResult> res = Optional.empty();

        if (res.isPresent()) {
            return res;
        }

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

    public boolean findBySlug(String mcVer, String filter, String slug, Consumer<CurseAddonSearchResult> successCallback, Consumer<CurseAddonSearchResult> failureCallback) {
        CurseAddonSearchRequest request = new CurseAddonSearchRequest();
        request.setSearchFilter(filter);
        request.setGameVersion(mcVer);

        List<CurseAddonSearchResult> results = searchAddons(request);
        System.out.println("Searching: " + filter + " version: " + mcVer);
        System.out.println("Scanning page: " + request.getIndex());

        while (!results.isEmpty()) {
            results.forEach(it -> {
                if (it.getSlug().equalsIgnoreCase(slug)) {
                    successCallback.accept(it);
                    return;
                } else {
                    failureCallback.accept(it);
                }
            });

            request.setIndex(request.getIndex() + 1);
            System.out.print("\033[1A");
            System.out.println("Scanning page " + request.getIndex());
            results = searchAddons(request);
        }

        if (filter.contains("+")) {
            String[] f = filter.split("\\+");
            for (String fi : f) {
                if (fi.length() >= 3) {
                    return findBySlug(mcVer, fi, slug, successCallback, failureCallback);
                }
            }
        }

        return false;
    }

    @Override
    public Optional<CurseAddonSearchResult> findBySlug(String mcVer, String[] altVers, String slug) {
        System.out.println("Searching for addon id for slug: " + slug);
        return findBySlug(mcVer, altVers, slug, slug);
    }

    @Override
    public boolean findBySlug(String mcVer, String[] altVers, String slug, Consumer<CurseAddonSearchResult> successCallback, Consumer<CurseAddonSearchResult> failureCallback) {
        return findBySlug(mcVer, altVers, slug, slug, successCallback, failureCallback);
    }

    private boolean findBySlug(String mcVer, String[] altVers, String filter, String slug, Consumer<CurseAddonSearchResult> successCallback, Consumer<CurseAddonSearchResult> failureCallback) {
        if (!findBySlug(mcVer, filter, slug, successCallback, failureCallback)) {
            for (String altVer : altVers) {
                if (findBySlug(altVer, filter, slug, successCallback, failureCallback)) {
                    return true;
                }
            }
        }
        return false;
    }
}
