package com.liquidforte.terra.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class File {
    @Id
    @GeneratedValue
    private Long id = -1L;

    @ManyToOne
    @JoinColumn(name = "mod_id")
    private Mod mod;

    private long curseId;
    private String displayName;
    private String fileName;
    private Date fileDate;
    private long fileLength;
    private long releaseType;
    private long fileStatus;
    private String downloadUrl;
    private long packageFingerprint;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "file_dependency",
            joinColumns = @JoinColumn(name = "file_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "dependency_id", referencedColumnName = "id"))
    private List<File> dependencies = new ArrayList<>();

    @ManyToMany(mappedBy = "dependencies")
    private List<File> dependents = new ArrayList<>();

    public File(Mod mod, long curseId, String displayName, String fileName, Date fileDate, long fileLength, long releaseType, long fileStatus, String downloadUrl, long packageFingerprint) {
        this.mod = mod;
        this.curseId = curseId;
        this.displayName = displayName;
        this.fileName = fileName;
        this.fileDate = fileDate;
        this.fileLength = fileLength;
        this.releaseType = releaseType;
        this.fileStatus = fileStatus;
        this.downloadUrl = downloadUrl;
        this.packageFingerprint = packageFingerprint;
    }
}