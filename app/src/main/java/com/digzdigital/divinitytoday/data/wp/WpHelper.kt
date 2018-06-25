package com.digzdigital.divinitytoday.data.wp

import com.digzdigital.divinitytoday.data.model.Devotional
import io.reactivex.Observable

interface WpHelper {

    fun getDevotionals(offset: String, per_page: String = "7"): Observable<List<Devotional>>
}
