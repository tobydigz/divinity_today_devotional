package com.digzdigital.divinitytoday.data.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.digzdigital.divinitytoday.BuildConfig;
import com.digzdigital.divinitytoday.data.commons.ApiService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class DataModule {

    @Singleton
    @Provides
    OkHttpClient.Builder providesClientBuilder() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .readTimeout(240, TimeUnit.SECONDS)
                .connectTimeout(240, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            httpClient.addInterceptor(logging);
        }
        return httpClient;
    }

    @Singleton
    @Provides
    ApiService providesApiService(OkHttpClient.Builder httpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.Companion.getBASE_URL())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .client(httpClient.build())
                .build();
        return retrofit.create(ApiService.class);
    }

    @Singleton
    @Provides
    SharedPreferences providesSharedPreferences(Context context) {
        return context.getSharedPreferences(context.getPackageName() + "_preferences", Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    Realm providesRealm() {
        return Realm.getDefaultInstance();
    }

}

