package com.liquidforte.terra.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.liquidforte.terra.config.AppOptions;
import com.liquidforte.terra.util.FileUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GroupLoader {
    private final ObjectMapper mapper;
    private final AppOptions appOptions;

    @Inject
    public GroupLoader(ObjectMapper mapper, AppOptions appOptions) {
        this.mapper = mapper;
        this.appOptions = appOptions;
    }

    public List<Group> loadGroups() {
        List<Group> result = new ArrayList<>();

        File groupsDir = appOptions.getGroupsDir().toFile();
        groupsDir.mkdirs();

        String[] extensions = { "json" };

        for (File groupFile : FileUtils.listFiles(groupsDir, extensions, true)) {
            try {
                Group group = mapper.readValue(groupFile, Group.class);
                if (Strings.isNullOrEmpty(group.getId())) {
                    String id = FileUtil.getRelativePath(groupsDir, groupFile).replace("\\", "/").replace("/", ".");
                    group.setId(id);
                }

                if (group.isEnabled()) {
                    result.add(group);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return result;
    }
}
