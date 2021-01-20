package com.liquidforte.terra.service;

import com.liquidforte.terra.model.Mod;

import java.util.Optional;

public interface ModService {
    Optional<Mod> findById(long id);

    Optional<Mod> findByCurseId(long curseId);

    Optional<Mod> findBySlug(String slug);

    Mod save(Mod mod);
}
