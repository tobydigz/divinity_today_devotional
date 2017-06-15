package com.digzdigital.divinitytoday.dagger;

import com.digzdigital.divinitytoday.ui.devlist.DevotionalsFragment;
import com.digzdigital.divinitytoday.ui.reader.ReaderFragment;
import com.digzdigital.divinitytoday.ui.saveddevlist.SavedDevotionalFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(ReaderFragment target);

    void inject(SavedDevotionalFragment target);

    void inject(DevotionalsFragment target);


}
