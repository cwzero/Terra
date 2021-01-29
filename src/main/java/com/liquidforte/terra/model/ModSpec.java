package com.liquidforte.terra.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.liquidforte.terra.json.ModSpecSerializer;

@JsonSerialize(using = ModSpecSerializer.class)
public class ModSpec {
    private String slug = "";
    private String filter = "true";

    public ModSpec() {

    }

    @JsonCreator
    public ModSpec(String slug) {
        this.slug = slug;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}