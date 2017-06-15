package com.digzdigital.divinitytoday.dagger;

import android.app.Application;
import android.content.Context;

import com.digzdigital.divinitytoday.data.AppDataManager;
import com.digzdigital.divinitytoday.data.DataManager;
import com.digzdigital.divinitytoday.data.db.DbHelper;
import com.digzdigital.divinitytoday.data.db.PaperDbHelper;
import com.digzdigital.divinitytoday.data.wp.AppWpHelper;
import com.digzdigital.divinitytoday.data.wp.WpHelper;
import com.digzdigital.divinitytoday.data.wp.network.RestApi;
import com.digzdigital.divinitytoday.ui.devlist.DevListContract;
import com.digzdigital.divinitytoday.ui.devlist.DevotionalsPresenter;
import com.digzdigital.divinitytoday.ui.reader.ReaderContract;
import com.digzdigital.divinitytoday.ui.reader.ReaderPresenter;
import com.digzdigital.divinitytoday.ui.saveddevlist.SavedDevotionalContract;
import com.digzdigital.divinitytoday.ui.saveddevlist.SavedDevotionalPresenter;

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

    @Provides
    @Singleton
    public RestApi providesRestApi() {
        return new RestApi();
    }

    @Provides
    @Singleton
    public WpHelper providesWpHelper(RestApi restApi) {
        return new AppWpHelper(restApi);
    }

    @Provides
    @Singleton
    public DbHelper providesDbHelper() {
        return new PaperDbHelper();
    }

    @Provides
    @Singleton
    public DataManager providesDataManager(DbHelper dbHelper, WpHelper wpHelper) {
        return new AppDataManager(dbHelper, wpHelper);
    }

    @Provides
    @Singleton
    public ReaderContract.Presenter provideReaderPresenter(DataManager dataManager) {
        return new ReaderPresenter(dataManager);
    }

    @Provides
    @Singleton
    public DevListContract.Presenter provideDevListPresenter(DataManager dataManager) {
        return new DevotionalsPresenter(dataManager);
    }

    @Provides
    @Singleton
    public SavedDevotionalContract.Presenter provideSavedDevsPresenter(DataManager dataManager) {
        return new SavedDevotionalPresenter(dataManager);
    }
}
