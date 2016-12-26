package com.digzdigital.divinitytoday;

import com.digzdigital.divinitytoday.reader.ReaderPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Digz on 26/12/2016.
 */
@Module
public class AppModule {
    @Provides
    @Singleton
    public ReaderPresenter provideReaderPresenter(){
        return new ReaderPresenter();
    }
}
