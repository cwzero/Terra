package com.liquidforte.terra.context;

import com.liquidforte.terra.api.context.Context;
import com.liquidforte.terra.api.context.ContextRunnable;

public abstract class ContextRunnableBase<C extends Context> implements ContextRunnable<C> {
    private C context;

    public ContextRunnableBase(C context) {
        this.context = context;
    }

    @Override
    public C getContext() {
        return context;
    }
}
