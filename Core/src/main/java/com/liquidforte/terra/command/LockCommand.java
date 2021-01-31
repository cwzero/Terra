package com.liquidforte.terra.command;

import com.liquidforte.terra.api.command.CommandContext;

public abstract class LockCommand extends AbstractCommand {
    protected LockCommand(CommandContext context) {
        super(context);
    }

    @Override
    protected void before() {
        super.before();
        getLockCache().load();
    }

    @Override
    protected void after() {
        getLockCache().save();
        super.after();
    }

    protected abstract void doRun();
}
