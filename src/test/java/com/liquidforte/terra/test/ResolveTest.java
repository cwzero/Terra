package com.liquidforte.terra.test;

import com.liquidforte.terra.cache.LockCache;
import com.liquidforte.terra.command.ResolveCommand;
import com.liquidforte.terra.model.Group;
import com.liquidforte.terra.model.GroupLoader;
import com.liquidforte.terra.model.ModSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ResolveTest {
    @Mock
    private LockCache lockCache;

    @Mock
    private GroupLoader groupLoader;

    @BeforeEach
    public void setup() {
        List<Group> groups = new ArrayList<>();
        Group group = new Group();

        List<ModSpec> mods = new ArrayList<>();
        mods.add(new ModSpec("jei"));

        group.setMods(mods);

        groups.add(group);

        when(groupLoader.loadGroups()).thenReturn(groups);
    }

    @Test
    public void testResolve() {
        ResolveCommand resolveCommand = new ResolveCommand(groupLoader, lockCache);

        try {
            resolveCommand.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(groupLoader, only()).loadGroups();
        verify(lockCache, only()).getLock("jei", "true");
    }
}
