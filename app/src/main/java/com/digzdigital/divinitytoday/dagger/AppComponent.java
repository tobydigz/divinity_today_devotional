package com.digzdigital.divinitytoday.dagger;

import com.digzdigital.divinitytoday.ui.devlist.DevotionalsFragment;
import com.digzdigital.divinitytoday.ui.reader.ReaderActivity;
import com.digzdigital.divinitytoday.ui.saveddevlist.SavedDevotionalsActivity;
import com.digzdigital.divinitytoday.ui.saveddevlist.SavedDevotionalsPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(ReaderActivity target);

    void inject(DevotionalsFragment target);

    void inject(SavedDevotionalsActivity target);

    void inject(SavedDevotionalsPresenter target);

}
