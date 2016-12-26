package com.digzdigital.divinitytoday;

import android.app.Application;

/**
 * Created by Digz on 26/12/2016.
 */

public class App extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder().appModule(new AppModule()).build();
    }

    public AppComponent getComponent() {
        return component;
    }

}
