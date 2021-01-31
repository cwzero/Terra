package com.liquidforte.terra.curse.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurseFile implements Comparable<CurseFile> {
    private long id;
    private String displayName;
    private String fileName;
    private Date fileDate;
    private long fileLength;
    private long releaseType;
    private long fileStatus;
    private String downloadUrl;
    private boolean isAlternate;
    private long alternateFileId;
    private boolean isAvailable;
    private long packageFingerprint;
    private List<String> gameVersion = new ArrayList<>();
    private long restrictProjectFileAccess;
    private long projectStatus;
    private List<CurseFileDependency> dependencies = new ArrayList<>();
    private List<CurseSortableGameVersion> sortableGameVersions = new ArrayList<>();

    @Override
    public int compareTo(CurseFile o) {
        return o.getFileDate().compareTo(getFileDate());
    }
}