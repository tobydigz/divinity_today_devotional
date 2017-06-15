package com.digzdigital.divinitytoday.data.wp.retrofit

interface DivinityApi{
    @retrofit2.http.GET("/index.php/wp-json/wp/v2/posts")
    fun getTop(@retrofit2.http.Query("offset") offset:String,
               @retrofit2.http.Query("per_page") per_page:String): retrofit2.Call<List<DevotionalObject>>

}