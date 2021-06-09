package com.liquidforte.terra.curse.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getFileDate() {
        return fileDate;
    }

    public void setFileDate(Date fileDate) {
        this.fileDate = fileDate;
    }

    public long getFileLength() {
        return fileLength;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    public long getReleaseType() {
        return releaseType;
    }

    public void setReleaseType(long releaseType) {
        this.releaseType = releaseType;
    }

    public long getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(long fileStatus) {
        this.fileStatus = fileStatus;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public boolean isAlternate() {
        return isAlternate;
    }

    public void setAlternate(boolean alternate) {
        isAlternate = alternate;
    }

    public long getAlternateFileId() {
        return alternateFileId;
    }

    public void setAlternateFileId(long alternateFileId) {
        this.alternateFileId = alternateFileId;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public long getPackageFingerprint() {
        return packageFingerprint;
    }

    public void setPackageFingerprint(long packageFingerprint) {
        this.packageFingerprint = packageFingerprint;
    }

    public List<String> getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(List<String> gameVersion) {
        this.gameVersion = gameVersion;
    }

    public long getRestrictProjectFileAccess() {
        return restrictProjectFileAccess;
    }

    public void setRestrictProjectFileAccess(long restrictProjectFileAccess) {
        this.restrictProjectFileAccess = restrictProjectFileAccess;
    }

    public long getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(long projectStatus) {
        this.projectStatus = projectStatus;
    }

    public List<CurseFileDependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<CurseFileDependency> dependencies) {
        this.dependencies = dependencies;
    }

    public List<CurseSortableGameVersion> getSortableGameVersions() {
        return sortableGameVersions;
    }

    public void setSortableGameVersions(List<CurseSortableGameVersion> sortableGameVersions) {
        this.sortableGameVersions = sortableGameVersions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int compareTo(CurseFile o) {
        return o.getFileDate().compareTo(getFileDate());
    }
}