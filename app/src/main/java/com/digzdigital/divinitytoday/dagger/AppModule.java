package com.digzdigital.divinitytoday.dagger;

import android.content.Context;

import com.digzdigital.divinitytoday.DivinityTodayApp;
import com.digzdigital.divinitytoday.data.AppDataManager;
import com.digzdigital.divinitytoday.data.DataManager;
import com.digzdigital.divinitytoday.data.db.AppDbHelper;
import com.digzdigital.divinitytoday.data.db.DbHelper;
import com.digzdigital.divinitytoday.data.wp.AppWpHelper;
import com.digzdigital.divinitytoday.data.wp.WpHelper;
import com.digzdigital.divinitytoday.ui.devlist.DevListPresenter;
import com.digzdigital.divinitytoday.ui.reader.ReaderPresenter;
import com.digzdigital.divinitytoday.ui.saveddevlist.SavedDevotionalsPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final DivinityTodayApp app;

    public AppModule(DivinityTodayApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Context providesContext() {
        return app;
    }

    @Provides
    @Singleton
    public DataManager providesDataManager() {
        return new AppDataManager();
    }

    @Provides
    @Singleton
    public WpHelper providesWpHelper(Context context) {
        return new AppWpHelper(context);
    }

    @Provides
    @Singleton
    public DbHelper providesDbHelper() {
        return new AppDbHelper();
    }

    @Provides
    @Singleton
    public ReaderPresenter provideReaderPresenter() {
        return new ReaderPresenter();
    }

    @Provides
    @Singleton
    public DevListPresenter provideDevListPresenter() {
        return new DevListPresenter();
    }

    @Provides
    @Singleton
    public SavedDevotionalsPresenter provideSavedDevsPresenter() {
        return new SavedDevotionalsPresenter();
    }
}
