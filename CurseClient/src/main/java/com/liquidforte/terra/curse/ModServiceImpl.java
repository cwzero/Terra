package com.liquidforte.terra.curse;

import com.google.inject.Inject;
import com.liquidforte.terra.api.curse.CurseClient;
import com.liquidforte.terra.api.service.ModService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class ModServiceImpl implements ModService {
    private final CurseClient curseClient;

    @Override
    public long getAddonId(String slug) {
        return -1;
    }
}
