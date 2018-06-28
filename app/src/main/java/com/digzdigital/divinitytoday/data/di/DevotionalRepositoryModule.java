package com.digzdigital.divinitytoday.data.di;

import com.digzdigital.divinitytoday.dagger.annotations.Local;
import com.digzdigital.divinitytoday.dagger.annotations.Remote;
import com.digzdigital.divinitytoday.data.commons.ApiService;
import com.digzdigital.divinitytoday.data.devotionals.DevotionalDataPersistence;
import com.digzdigital.divinitytoday.data.devotionals.DevotionalDataSource;
import com.digzdigital.divinitytoday.data.devotionals.local.LocalDevotionalDataPersistence;
import com.digzdigital.divinitytoday.data.devotionals.local.LocalDevotionalDataSource;
import com.digzdigital.divinitytoday.data.devotionals.local.mapper.DevotionalRealmToDevotionalMapper;
import com.digzdigital.divinitytoday.data.devotionals.remote.RemoteDevotionalDataSource;
import com.digzdigital.divinitytoday.data.devotionals.remote.mapper.RemoteDevotionalToDevotionalMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DevotionalRepositoryModule {

    @Provides
    @Singleton
    @Remote
    DevotionalDataSource providesRemoteDataSource(ApiService apiService, RemoteDevotionalToDevotionalMapper mapper) {
        return new RemoteDevotionalDataSource(apiService, mapper);
    }

    @Provides
    @Singleton
    @Local
    DevotionalDataSource providesLocalDataSource(DevotionalRealmToDevotionalMapper mapper) {
        return new LocalDevotionalDataSource(mapper);
    }

    @Provides
    @Singleton
    @Local
    DevotionalDataPersistence providesLocalDataPersistence() {
        return new LocalDevotionalDataPersistence();
    }
}
