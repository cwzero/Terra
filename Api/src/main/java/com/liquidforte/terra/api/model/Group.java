package com.liquidforte.terra.api.model;

public interface Group {
    String getId();

    boolean isEnabled();

    java.util.List<ModSpec> getMods();
}
