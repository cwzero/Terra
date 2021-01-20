package com.liquidforte.terra.service;

import com.liquidforte.curse.client.CurseClient;
import com.liquidforte.terra.model.Mod;
import com.liquidforte.terra.repository.ModRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ModServiceImpl implements ModService {
    private CurseClient curseClient = new CurseClient();

    @Autowired
    private ModRepository repository;

    @Override
    @Transactional
    public Mod save(Mod mod) {
        return repository.save(mod);
    }

    @Override
    public Optional<Mod> findById(long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Optional<Mod> findBySlug(String slug) {
        return repository.findBySlug(slug)
                .or(() -> curseClient.findBySlug(slug)
                        .map(a -> new Mod(a.getSlug(), a.getId()))
                        .map(m -> repository.save(m)));
    }

    @Override
    @Transactional
    public Optional<Mod> findByCurseId(long curseId) {
        return repository.findByCurseId(curseId)
                .or(() -> curseClient.getAddon(curseId)
                        .map(a -> new Mod(a.getSlug(), a.getId()))
                        .map(m -> repository.save(m)));
    }
}