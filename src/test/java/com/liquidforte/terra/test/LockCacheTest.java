package com.liquidforte.terra.test;

import com.liquidforte.curse.client.Client;
import com.liquidforte.curse.file.CurseFile;
import com.liquidforte.terra.cache.FileCache;
import com.liquidforte.terra.cache.LockCache;
import com.liquidforte.terra.cache.ModCache;
import com.liquidforte.terra.cache.TerraLockCache;
import com.liquidforte.terra.config.AppConfig;
import com.liquidforte.terra.database.LockDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LockCacheTest {
    @Mock
    private CurseFile file;

    @Mock
    private AppConfig appConfig;
    @Mock
    private Client client;
    @Mock
    private ModCache modCache;
    @Mock
    private FileCache fileCache;
    @Mock
    private LockDatabase lockDatabase;

    private final String[] mcVer = { "1.16", "1.16.5" };
    private final String filter = "true";
    private final String slug = "jei";
    private final long addonId = 238222L;
    private final long fileId = 3157864L;

    @BeforeEach
    public void setup() {
        when(appConfig.getMinecraftVersion()).thenReturn(mcVer);
        when(modCache.getAddonId(slug)).thenReturn(addonId);
        when(file.getId()).thenReturn(fileId);
        when(client.getLatestFile(any(), eq(addonId), eq(filter))).thenReturn(Optional.of(file));
    }

    @Test
    public void testLockCache() {
        LockCache lockCache = new TerraLockCache(appConfig, client, modCache, fileCache, lockDatabase);

        long f = lockCache.getLock("jei", "true");

        assertThat(f).isEqualTo(fileId);

        verifyNoMoreInteractions(appConfig, fileCache);

        verify(file, only()).getId();
        verify(modCache, only()).getAddonId(slug);
        verify(client, only()).getLatestFile(any(), eq(addonId), eq(filter));

        verify(lockDatabase, times(1))
                .lock(addonId, filter, fileId);
        verify(lockDatabase, times(1))
                .getLock(addonId, filter);
        verifyNoMoreInteractions(lockDatabase);
    }
}
