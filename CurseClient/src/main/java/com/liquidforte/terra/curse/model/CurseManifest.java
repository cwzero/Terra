package com.liquidforte.terra.curse.model;

import java.util.ArrayList;
import java.util.List;

public class CurseManifest {
    public static class CMModLoader {
        private String id = "";
        private boolean primary = true;

        public CMModLoader() {
        }

        public CMModLoader(String id) {
            this(id, true);
        }

        public CMModLoader(String id, boolean primary) {
            this.id = id;
            this.primary = primary;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isPrimary() {
            return primary;
        }

        public void setPrimary(boolean primary) {
            this.primary = primary;
        }
    }

    public static class CMMinecraft {
        private String version = "";
        private List<CMModLoader> modLoaders = new ArrayList<>();

        public CMMinecraft() {
        }

        public CMMinecraft(String version) {
            this.version = version;
        }

        public void addForge(String forgeVersion) {
            modLoaders.add(new CMModLoader("forge-" + forgeVersion));
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public List<CMModLoader> getModLoaders() {
            return modLoaders;
        }

        public void setModLoaders(List<CMModLoader> modLoaders) {
            this.modLoaders = modLoaders;
        }
    }

    public static class CMFile {
        private long projectID;
        private long fileID;
        private boolean required = true;

        public CMFile() {

        }

        public CMFile(long projectID, long fileID) {
            this.projectID = projectID;
            this.fileID = fileID;
        }

        public long getProjectID() {
            return projectID;
        }

        public void setProjectID(long projectID) {
            this.projectID = projectID;
        }

        public long getFileID() {
            return fileID;
        }

        public void setFileID(long fileID) {
            this.fileID = fileID;
        }

        public boolean isRequired() {
            return required;
        }

        public void setRequired(boolean required) {
            this.required = required;
        }
    }

    private CMMinecraft minecraft = new CMMinecraft();
    private String manifestType = "minecraftModpack";
    private int manifestVersion = 1;
    private String name;
    private String version;
    private String author = "";
    private List<CMFile> files = new ArrayList<>();

    public CurseManifest() {

    }

    public CurseManifest(String minecraftVersion, String forgeVersion) {
        this.minecraft.setVersion(minecraftVersion);
        addForge(forgeVersion);
    }

    public CurseManifest(String minecraftVersion, String forgeVersion, String packVersion, String packName) {
        this(minecraftVersion, forgeVersion);
        this.version = packVersion;
        this.name = packName;
    }

    public void addForge(String forgeVersion) {
        minecraft.addForge(forgeVersion);
    }

    public String getManifestType() {
        return manifestType;
    }

    public void setManifestType(String manifestType) {
        this.manifestType = manifestType;
    }

    public int getManifestVersion() {
        return manifestVersion;
    }

    public void setManifestVersion(int manifestVersion) {
        this.manifestVersion = manifestVersion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<CMFile> getFiles() {
        return files;
    }

    public void addFile(long projectId, long fileId) {
        this.files.add(new CMFile(projectId, fileId));
    }

    public void setFiles(List<CMFile> files) {
        this.files = files;
    }

    public CMMinecraft getMinecraft() {
        return minecraft;
    }

    public void setMinecraft(CMMinecraft minecraft) {
        this.minecraft = minecraft;
    }
}
