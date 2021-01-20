package com.liquidforte.terra.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Mod {
    @Id
    @GeneratedValue
    private Long id = -1L;
    private String slug;
    private long curseId;

    @OneToMany(mappedBy = "mod")
    private List<File> files = new ArrayList<>();

    public Mod(String slug, long curseId) {
        this.slug = slug;
        this.curseId = curseId;
    }

    public Optional<Long> getId() {
        if (id == -1) {
            return Optional.empty();
        } else {
            return Optional.of(id);
        }
    }
}
