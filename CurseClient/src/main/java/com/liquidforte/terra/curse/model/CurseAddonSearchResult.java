package com.liquidforte.terra.curse.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurseAddonSearchResult {
    private long id;
    private String name;
    private String websiteUrl;
    private String summary;
    private long status;
    private long primaryCategoryId;
    private String slug;
    private String primaryLanguage;
    private Date dateModified;
    private Date dateCreated;
    private Date dateReleased;
    private boolean isAvailable;
    private boolean isExperimental;
}