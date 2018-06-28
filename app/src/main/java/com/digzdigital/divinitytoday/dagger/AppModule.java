package com.digzdigital.divinitytoday.dagger;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final Application app;

    public AppModule(Application app) {
        this.app = app;

    }

    @Provides
    @Singleton
    public Context providesContext() {
        return app;
    }

    @Provides
    @Singleton
    public Application providesApplication() {
        return app;
    }
}
