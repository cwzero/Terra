package com.liquidforte.terra.command;

import com.google.inject.Inject;
import com.liquidforte.terra.api.cache.FileCache;
import com.liquidforte.terra.api.cache.LockCache;
import com.liquidforte.terra.api.cache.ModCache;
import com.liquidforte.terra.api.command.CommandContext;
import com.liquidforte.terra.api.config.AppConfig;
import com.liquidforte.terra.api.options.AppOptions;
import com.liquidforte.terra.loader.GroupLoaderImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class CommandContextImpl implements CommandContext {
    private final AppOptions appOptions;
    private final AppConfig appConfig;
    private final GroupLoaderImpl groupLoader;
    private final FileCache fileCache;
    private final LockCache lockCache;
    private final ModCache modCache;
}
