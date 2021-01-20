package com.liquidforte.terra.repository;

import com.liquidforte.terra.model.File;
import com.liquidforte.terra.model.Mod;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends CrudRepository<File, Long> {
    Optional<File> findById(long id);

    List<File> findByMod(Mod mod);
}
