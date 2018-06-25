package com.digzdigital.divinitytoday.data.devotionals

import com.digzdigital.divinitytoday.data.model.Devotional
import io.reactivex.Single

interface DevotionalDataSource {
    fun getDevotionals(startFrom: String, size: String): Single<List<Devotional>>

    fun getDevotional(id: String): Single<Devotional>
}