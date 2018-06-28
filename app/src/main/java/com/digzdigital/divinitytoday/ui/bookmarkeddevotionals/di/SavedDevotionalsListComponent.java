package com.digzdigital.divinitytoday.ui.bookmarkeddevotionals.di;

import com.digzdigital.divinitytoday.dagger.annotations.ScreenScoped;
import com.digzdigital.divinitytoday.ui.bookmarkeddevotionals.SavedDevotionalFragment;

import dagger.Subcomponent;

@ScreenScoped
@Subcomponent(modules = SavedDevotionalsListPresenterModule.class)
public interface SavedDevotionalsListComponent {

    void inject(SavedDevotionalFragment activity);
}
