package com.digzdigital.divinitytoday;

import com.digzdigital.divinitytoday.reader.ReaderActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Digz on 26/12/2016.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(ReaderActivity target);
}
