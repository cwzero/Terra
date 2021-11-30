package com.liquidforte.terra.api.curse;

import com.liquidforte.terra.api.service.SearchService;
import com.liquidforte.terra.curse.model.CurseAddonSearchRequest;
import com.liquidforte.terra.curse.model.CurseAddonSearchResult;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public interface AddonSearchService extends AddonSearchAPI, SearchService {
    default List<CurseAddonSearchResult> searchAddons(CurseAddonSearchRequest request) {
        return searchAddons(
                request.getCategoryId(),
                request.getGameId(),
                request.getGameVersion(),
                request.getIndex(),
                request.getPageSize(),
                request.getSearchFilter(),
                request.getSectionId(),
                request.getSort()
        );
    }

    @Deprecated
    Optional<CurseAddonSearchResult> findBySlug(String mcVer, String[] altVers, String slug);

    default boolean findBySlug(String minecraftVersion, String slug, Consumer<CurseAddonSearchResult> callback) {
        return findBySlug(minecraftVersion, slug, slug, callback);
    }

    boolean findBySlug(String minecraftVersion, String slug, String filter, Consumer<CurseAddonSearchResult> callback);

    @Deprecated
    boolean findBySlug(String mcVer, String[] altVers, String slug, Consumer<CurseAddonSearchResult> successCallback);
}
