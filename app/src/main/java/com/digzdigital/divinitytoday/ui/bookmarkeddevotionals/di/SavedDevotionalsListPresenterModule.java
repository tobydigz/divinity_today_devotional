package com.digzdigital.divinitytoday.ui.bookmarkeddevotionals.di;

import com.digzdigital.divinitytoday.dagger.annotations.ScreenScoped;
import com.digzdigital.divinitytoday.ui.bookmarkeddevotionals.SavedDevotionalContract;
import com.digzdigital.divinitytoday.ui.reader.ReaderContract;

import dagger.Module;
import dagger.Provides;

@Module
public class SavedDevotionalsListPresenterModule {
    private final SavedDevotionalContract.View view;

    public SavedDevotionalsListPresenterModule(SavedDevotionalContract.View view) {
        this.view = view;
    }

    @Provides
    @ScreenScoped
    SavedDevotionalContract.View provideView() {
        return view;
    }
}
