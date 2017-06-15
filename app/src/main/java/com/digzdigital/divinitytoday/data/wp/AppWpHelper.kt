package com.digzdigital.divinitytoday.data.wp

import android.util.Log
import com.digzdigital.divinitytoday.data.model.Devotional
import com.digzdigital.divinitytoday.data.wp.network.RestApi
import rx.Observable


class AppWpHelper(private val api: RestApi): WpHelper {
    override fun getDevotionals(offset: String, per_page: String): Observable<List<Devotional>> {
        Log.d("DigzApp", "gotten to begging of get")
        return Observable.create{
            subscriber ->

            val callResponse = api.getDevotionals(offset, per_page)
            try{
                val response = callResponse.execute()
                if (response.isSuccessful){
                    Log.d("DigzApp", "gotten devotionals")
                    val devotionals = response.body().map {
                        Devotional(it.title.rendered, it.date, it.content.rendered, it.id)
                    }
                    subscriber.onNext(devotionals)
                    subscriber.onCompleted()
                }else {
                    Log.d("DigzApp", "error getting devotionals")
                    subscriber.onError(Throwable(response.message()))
                }
            }catch (e:Exception){
                Log.d("DigzApp", "error getting devotionals ${e.message}")
                val devotionals = ArrayList<Devotional>()
                subscriber.onNext(devotionals)
                subscriber.onCompleted()
            }

        }

    }
}
