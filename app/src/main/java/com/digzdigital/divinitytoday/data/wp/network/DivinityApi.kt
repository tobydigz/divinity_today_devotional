package com.digzdigital.divinitytoday.data.wp.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DivinityApi{
    @GET("/index.php/wp-json/wp/v2/posts")
    fun getTop(@Query("offset") offset:String,
               @Query("per_page") per_page:String): Call<List<DevotionalObject>>

}