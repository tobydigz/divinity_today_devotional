package com.digzdigital.divinitytoday.ui.reader.di;

import com.digzdigital.divinitytoday.dagger.annotations.ScreenScoped;
import com.digzdigital.divinitytoday.ui.reader.ReaderContract;

import dagger.Module;
import dagger.Provides;

@Module
public class ReaderPresenterModule {
    private final ReaderContract.View view;

    public ReaderPresenterModule(ReaderContract.View view) {
        this.view = view;
    }

    @Provides
    @ScreenScoped
    ReaderContract.View provideView() {
        return view;
    }
}
