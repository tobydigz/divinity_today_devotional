package com.digzdigital.divinitytoday.dagger;

import com.digzdigital.divinitytoday.ui.devlist.DevotionalsFragment;
import com.digzdigital.divinitytoday.ui.reader.ReaderActivity;
import com.digzdigital.divinitytoday.ui.bookmarkeddevotionals.SavedDevotionalFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(ReaderActivity target);

    void inject(SavedDevotionalFragment target);

    void inject(DevotionalsFragment target);


}
