package com.liquidforte.terra.api.context;

public interface ContextRunnable<C extends Context> extends Runnable {
    C getContext();
}
