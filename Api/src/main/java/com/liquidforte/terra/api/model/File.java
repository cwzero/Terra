package com.liquidforte.terra.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {
    private long fileId = -1;
    private String fileName = "";
    private Date fileDate;
    private long fileLength = -1;
    private String downloadUrl = "";
    private long fingerprint = -1;
}
