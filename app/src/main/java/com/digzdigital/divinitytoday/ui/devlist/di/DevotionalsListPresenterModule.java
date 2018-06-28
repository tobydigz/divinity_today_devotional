package com.digzdigital.divinitytoday.ui.devlist.di;

import com.digzdigital.divinitytoday.dagger.annotations.ScreenScoped;
import com.digzdigital.divinitytoday.ui.devlist.DevListContract;
import com.digzdigital.divinitytoday.ui.reader.ReaderContract;

import dagger.Module;
import dagger.Provides;

@Module
public class DevotionalsListPresenterModule {
    private final DevListContract.View view;

    public DevotionalsListPresenterModule(DevListContract.View view) {
        this.view = view;
    }

    @Provides
    @ScreenScoped
    DevListContract.View provideView() {
        return view;
    }
}
