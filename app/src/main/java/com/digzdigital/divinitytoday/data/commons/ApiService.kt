package com.digzdigital.divinitytoday.data.commons

import com.digzdigital.divinitytoday.data.model.RemoteDevotional
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    companion object {
        val BASE_URL: String
            get() = "http://divinitytodaydevotional.org"
    }

    @GET("/index.php/wp-json/wp/v2/posts")
    fun getDevotionals(@Query("offset") offset: String,
                       @Query("per_page") per_page: String): Single<List<RemoteDevotional>>

    @GET("/index.php/wp-json/wp/v2/posts/{id}")
    fun getDevotional(@Path("id") postId: String): Single<RemoteDevotional>
}