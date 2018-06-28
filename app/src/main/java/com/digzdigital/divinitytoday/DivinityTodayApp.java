package com.digzdigital.divinitytoday;

import android.app.Application;

import com.digzdigital.divinitytoday.commons.Constants;
import com.digzdigital.divinitytoday.dagger.AppComponent;
import com.digzdigital.divinitytoday.dagger.AppModule;
import com.digzdigital.divinitytoday.dagger.DaggerAppComponent;

import io.paperdb.Paper;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class DivinityTodayApp extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(Constants.REALM_SCHEMA_VERSION)
                .name("divinity_today")
                .build();
        Realm.setDefaultConfiguration(config);
        Realm.init(this);
        Paper.init(this);
        getAppComponent();
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
        return appComponent;
    }

}
