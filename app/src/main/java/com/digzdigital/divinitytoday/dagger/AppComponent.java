package com.digzdigital.divinitytoday.dagger;

import com.digzdigital.divinitytoday.data.AppDataManager;
import com.digzdigital.divinitytoday.ui.devlist.DevListActivity;
import com.digzdigital.divinitytoday.ui.devlist.DevListPresenter;
import com.digzdigital.divinitytoday.ui.reader.ReaderActivity;
import com.digzdigital.divinitytoday.ui.saveddevlist.SavedDevotionalsActivity;
import com.digzdigital.divinitytoday.ui.saveddevlist.SavedDevotionalsPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(ReaderActivity target);

    void inject(DevListActivity target);

    void inject(SavedDevotionalsActivity target);

    void inject(SavedDevotionalsPresenter target);

    void inject(AppDataManager target);

    void inject(DevListPresenter target);

}
