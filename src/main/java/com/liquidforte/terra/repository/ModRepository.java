package com.liquidforte.terra.repository;

import com.liquidforte.terra.model.Mod;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ModRepository extends CrudRepository<Mod, Long> {
    Optional<Mod> findById(long id);

    Optional<Mod> findByCurseId(long curseId);

    //@Query("select m from mod m where m.slug = ?1")
    Optional<Mod> findBySlug(String slug);
}
