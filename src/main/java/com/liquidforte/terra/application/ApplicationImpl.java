package com.liquidforte.terra.application;

import com.google.inject.Inject;
import com.liquidforte.terra.api.application.Application;
import com.liquidforte.terra.api.application.ApplicationContext;
import com.liquidforte.terra.context.ContextRunnableBase;

public class ApplicationImpl extends ContextRunnableBase<ApplicationContext> implements Application {
    @Inject
    public ApplicationImpl(ApplicationContext context) {
        super(context);
    }

    @Override
    public void run() {

    }
}
