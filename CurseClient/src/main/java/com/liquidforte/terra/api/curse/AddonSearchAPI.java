package com.liquidforte.terra.api.curse;

import com.liquidforte.terra.curse.model.CurseAddonSearchResult;
import feign.Param;
import feign.RequestLine;

import java.util.List;
import java.util.Optional;

public interface AddonSearchAPI {
    @RequestLine("GET /addon/{id}")
    Optional<CurseAddonSearchResult> getAddon(@Param("id") long id);

    @RequestLine("GET /addon/search?categoryId={categoryId}&gameId={gameId}&gameVersion={gameVersion}&index={index}&pageSize={pageSize}&searchFilter={searchFilter}&sectionId={sectionId}&sort={sort}")
    List<CurseAddonSearchResult> searchAddons(
            @Param("categoryId") long categoryId,
            @Param("gameId") long gameId,
            @Param("gameVersion") String gameVersion,
            @Param("index") long index,
            @Param("pageSize") long pageSize,
            @Param("searchFilter") String searchFilter,
            @Param("sectionId") long sectionId,
            @Param("sort") long sort
    );
}
