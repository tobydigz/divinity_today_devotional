package com.digzdigital.divinitytoday.dagger;

import com.digzdigital.divinitytoday.data.di.DataModule;
import com.digzdigital.divinitytoday.data.di.DevotionalRepositoryModule;
import com.digzdigital.divinitytoday.ui.bookmarkeddevotionals.di.SavedDevotionalsListComponent;
import com.digzdigital.divinitytoday.ui.bookmarkeddevotionals.di.SavedDevotionalsListPresenterModule;
import com.digzdigital.divinitytoday.ui.devlist.di.DevotionalsListComponent;
import com.digzdigital.divinitytoday.ui.devlist.di.DevotionalsListPresenterModule;
import com.digzdigital.divinitytoday.ui.reader.di.ReaderComponent;
import com.digzdigital.divinitytoday.ui.reader.di.ReaderPresenterModule;
import com.digzdigital.divinitytoday.ui.splash.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DataModule.class, DevotionalRepositoryModule.class})
public interface AppComponent {
    ReaderComponent plus(ReaderPresenterModule module);

    SavedDevotionalsListComponent plus(SavedDevotionalsListPresenterModule module);

    DevotionalsListComponent plus(DevotionalsListPresenterModule module);

    void inject(SplashActivity activity);
}