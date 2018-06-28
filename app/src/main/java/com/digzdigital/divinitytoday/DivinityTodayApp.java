package com.digzdigital.divinitytoday;

import android.app.Application;

import com.digzdigital.divinitytoday.dagger.AppComponent;
import com.digzdigital.divinitytoday.dagger.AppModule;
import com.digzdigital.divinitytoday.dagger.DaggerAppComponent;

import io.paperdb.Paper;


public class DivinityTodayApp extends Application {

    private static DivinityTodayApp instance = new DivinityTodayApp();
    private AppComponent appComponent;

    public static DivinityTodayApp getInstance(){
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Paper.init(this);
        getAppComponent();
    }

    public AppComponent getAppComponent() {
        if (appComponent == null){
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
        return appComponent;
    }

}
