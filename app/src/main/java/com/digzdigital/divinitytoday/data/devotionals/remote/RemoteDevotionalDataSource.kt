package com.digzdigital.divinitytoday.data.devotionals.remote

import com.digzdigital.divinitytoday.data.commons.ApiService
import com.digzdigital.divinitytoday.data.commons.Mapper
import com.digzdigital.divinitytoday.data.devotionals.DevotionalDataSource
import com.digzdigital.divinitytoday.data.model.Devotional
import com.digzdigital.divinitytoday.data.model.RemoteDevotional
import io.reactivex.Single
import javax.inject.Inject

class RemoteDevotionalDataSource @Inject constructor(private val apiService: ApiService,
                                                     private val mapper: Mapper<RemoteDevotional, Devotional>) : DevotionalDataSource {
    override fun getDevotionals(startFrom: String, size: String): Single<List<Devotional>> {
        return apiService.getDevotionals(startFrom, size)
                .map { devotionals -> ArrayList(mapper.mapMany(devotionals)) }

    }

    override fun getBookmarkedDevotionals(): Single<List<Devotional>> {
        return Single.just(ArrayList())
    }

    override fun getDevotional(id: String): Single<Devotional> {
        return apiService.getDevotional(id)
                .map { devotional -> mapper.map1(devotional) }
    }
}