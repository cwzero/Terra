package com.liquidforte.terra.command;

import com.liquidforte.terra.api.command.CommandContext;

public abstract class LockCommand extends AbstractCommand {
    protected LockCommand(CommandContext context) {
        super(context);
    }

    @Override
    public boolean needsLockCache() {
        return true;
    }
}
