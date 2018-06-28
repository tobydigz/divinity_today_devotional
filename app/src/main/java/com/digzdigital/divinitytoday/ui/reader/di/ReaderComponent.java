package com.digzdigital.divinitytoday.ui.reader.di;

import com.digzdigital.divinitytoday.dagger.annotations.ScreenScoped;
import com.digzdigital.divinitytoday.ui.reader.ReaderActivity;

import dagger.Subcomponent;

@ScreenScoped
@Subcomponent(modules = ReaderPresenterModule.class)
public interface ReaderComponent {

    void inject(ReaderActivity activity);
}
