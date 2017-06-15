package com.digzdigital.divinitytoday.data.wp.retrofit

class RestApi() {
    private val divinityApi: DivinityApi
    init {
        val retrofit = retrofit2.Retrofit.Builder()
                .baseUrl("http://divinitytodaydevotional.org")
                .addConverterFactory(retrofit2.converter.moshi.MoshiConverterFactory.create())
                .build()

        divinityApi = retrofit.create(com.digzdigital.divinitytoday.data.wp.DivinityApi::class.java)

    }

    fun getDevotionals(offset:String, per_page:String): retrofit2.Call<List<DevotionalObject>> = divinityApi.getTop(offset, per_page)


}
