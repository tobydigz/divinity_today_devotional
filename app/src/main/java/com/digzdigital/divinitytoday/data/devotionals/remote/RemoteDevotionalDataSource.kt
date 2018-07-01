package com.digzdigital.divinitytoday.data.devotionals.remote

import com.digzdigital.divinitytoday.data.commons.ApiService
import com.digzdigital.divinitytoday.data.commons.ModelExtension
import com.digzdigital.divinitytoday.data.devotionals.DevotionalDataSource
import com.digzdigital.divinitytoday.data.devotionals.remote.mapper.RemoteDevotionalToDevotionalMapper
import com.digzdigital.divinitytoday.data.model.Devotional
import io.reactivex.Single
import javax.inject.Inject

class RemoteDevotionalDataSource @Inject constructor(private val apiService: ApiService,
                                                     private val mapper: RemoteDevotionalToDevotionalMapper) : DevotionalDataSource {
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

    override fun getDevotionalByDate(date: Long): Single<Devotional> {
        return apiService.getDevotional(afterDate = ModelExtension.getServerFormattedDate(date), per_page = "1")
                .map { devotional -> mapper.map1(devotional) }
    }
}