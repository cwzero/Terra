package com.liquidforte.terra.api.curse;

import com.liquidforte.terra.curse.model.CurseAddonSearchRequest;
import com.liquidforte.terra.curse.model.CurseAddonSearchResult;

import java.util.List;
import java.util.Optional;

public interface AddonSearchService extends AddonSearchAPI {
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

    Optional<CurseAddonSearchResult> findBySlug(String mcVer, String[] altVers, String slug);
}
