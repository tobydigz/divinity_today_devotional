package com.digzdigital.divinitytoday.data.db

import com.digzdigital.divinitytoday.data.model.Devotional
import io.paperdb.Paper
import io.reactivex.Single
import javax.inject.Inject

class PaperDbHelper @Inject constructor() {
    fun queryForPosts(): Single<List<Devotional>> {
        return Single.create { subscriber ->
            val allKeys = Paper.book().allKeys
            val devotionals = ArrayList<Devotional>()
            for (key: String in allKeys) {
                val devotional = Paper.book().read<Devotional>(key)
                devotionals.add(devotional)
            }
            subscriber.onSuccess(devotionals)
        }

    }

    fun deleteAll() {
        Paper.book().destroy()
    }

}
