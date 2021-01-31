package com.liquidforte.terra.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.liquidforte.terra.api.model.Group;
import com.liquidforte.terra.api.options.AppOptions;
import com.liquidforte.terra.model.GroupImpl;
import com.liquidforte.terra.util.FileUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GroupLoaderImpl implements com.liquidforte.terra.api.loader.GroupLoader {
    private ObjectMapper mapper;
    private AppOptions options;

    @Inject
    public GroupLoaderImpl(ObjectMapper mapper, AppOptions options) {
        this.mapper = mapper;
        this.options = options;
    }

    @Override
    public List<Group> loadGroups() {
        List<Group> result = new ArrayList<>();

        File groupsDir = options.getGroupsPath().toFile();
        groupsDir.mkdirs();
        String[] extensions = { "json" };

        for (File groupFile : FileUtils.listFiles(groupsDir, extensions, true)) {
            try {
                GroupImpl group = mapper.readValue(groupFile, GroupImpl.class);
                if (Strings.isNullOrEmpty(group.getId())) {
                    String id = FileUtil.getRelativePath(groupsDir, groupFile).replace("\\", "/").replace("/", ".");
                    group.setId(id);
                }
                result.add(group);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
