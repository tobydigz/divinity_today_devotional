package com.digzdigital.divinitytoday.ui.devlist.di;

import com.digzdigital.divinitytoday.dagger.annotations.ScreenScoped;
import com.digzdigital.divinitytoday.ui.devlist.DevotionalsFragment;

import dagger.Subcomponent;

@ScreenScoped
@Subcomponent(modules = DevotionalsListPresenterModule.class)
public interface DevotionalsListComponent {

    void inject(DevotionalsFragment activity);
}
