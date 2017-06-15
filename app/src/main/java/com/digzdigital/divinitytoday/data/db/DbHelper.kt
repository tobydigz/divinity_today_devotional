package com.digzdigital.divinitytoday.data.db

import com.digzdigital.divinitytoday.data.model.Devotional
import rx.Observable

interface DbHelper {

    fun savePost(devotional: Devotional):Boolean

    fun queryForPosts(): Observable<List<Devotional>>

    fun deletepost(devotional: Devotional)

}
