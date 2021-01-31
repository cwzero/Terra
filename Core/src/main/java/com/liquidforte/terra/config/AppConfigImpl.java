package com.liquidforte.terra.config;

import com.liquidforte.terra.api.config.AppConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"packName", "packVersion"})
public class AppConfigImpl implements AppConfig {
    private String packName;
    private String packVersion;
    private List<String> packAuthors = new ArrayList<>();
    private String minecraftVersion;
    private List<String> alternateVersions = new ArrayList<>();
    private String forgeVersion;
}
