package com.digzdigital.divinitytoday.data.wp.retrofit

import com.digzdigital.divinitytoday.data.model.Devotional
import com.digzdigital.divinitytoday.data.wp.WpHelper
import rx.Observable

class AppWpHelper(private val api: RestApi):WpHelper {
    override fun getDevotionals(offset: String, per_page: String): Observable<List<Devotional>> {
        return Observable.create{
            subscriber ->

            val callResponse = api.getDevotionals(offset, per_page)
            val response = callResponse.execute()

            if (response.isSuccessful){
                val devotionals = response.body().map {
                    Devotional(it.title.rendered, it.date, it.content.rendered, it.id)
                }
                subscriber.onNext(devotionals)
                subscriber.onCompleted()
            }else subscriber.onError(Throwable(response.message()))
        }
    }
}
