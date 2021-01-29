package com.liquidforte.terra.model.lock;

import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileLock implements Comparable<FileLock> {
    private long addonId = -1;
    private long fileId = -1;
    private String fileName = "";
    private Date fileDate;
    private String downloadUrl = "";
    private long fingerprint = -1;
    private List<FileDependency> dependencies = new ArrayList<>();

    public boolean isValid() {
        return !Strings.isNullOrEmpty(fileName) && !Strings.isNullOrEmpty(downloadUrl) && fingerprint > 0;
    }

    @Override
    public int compareTo(FileLock o) {
        return o.getFileDate().compareTo(getFileDate());
    }
}
