package com.liquidforte.terra.curse;

import com.google.inject.Inject;
import com.liquidforte.terra.api.curse.ForgeSearchAPI;
import com.liquidforte.terra.api.service.ForgeService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class ForgeServiceImpl implements ForgeService {
    private final ForgeSearchAPI forgeSearchAPI;

    @Override
    public String getForgeVersion(String minecraftVersion) {
        return forgeSearchAPI.getLoaders().stream()
                .filter(it -> it.getGameVersion().contentEquals(minecraftVersion))
                .filter(it -> it.getName().toLowerCase().startsWith("forge-"))
                .filter(it -> it.isLatest())
                .map(it -> it.getName().replace("forge-", "")).findFirst().get();
    }
}
