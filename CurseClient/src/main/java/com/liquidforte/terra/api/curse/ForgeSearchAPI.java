package com.liquidforte.terra.api.curse;

import com.liquidforte.terra.curse.model.ModLoader;
import feign.RequestLine;

import java.util.List;

public interface ForgeSearchAPI {
    @RequestLine("GET /minecraft/modloader")
    List<ModLoader> getLoaders();
}
