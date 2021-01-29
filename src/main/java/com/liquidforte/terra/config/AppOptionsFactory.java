package com.liquidforte.terra.config;

@FunctionalInterface
public interface AppOptionsFactory {
    AppOptions create(String[] args);
}
