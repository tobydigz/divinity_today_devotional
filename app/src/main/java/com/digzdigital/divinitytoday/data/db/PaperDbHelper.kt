package com.digzdigital.divinitytoday.data.db

import com.digzdigital.divinitytoday.data.model.Devotional
import io.paperdb.Paper
import rx.Observable

class PaperDbHelper() : DbHelper {

    override fun savePost(devotional: Devotional):Boolean {
        val isSaved = Paper.book().exist(devotional.id)
        if (isSaved){
            return false
        }
        Paper.book().write(devotional.id, devotional)
        return true
    }

    override fun queryForPosts(): Observable<List<Devotional>> {
        return Observable.create {
            subscriber ->
            val allKeys = Paper.book().allKeys
            val devotionals = ArrayList<Devotional>()
            for (key: String in allKeys) {
                val devotional = Paper.book().read<Devotional>(key)
                devotionals.add(devotional)
            }
            subscriber.onNext(devotionals)
            subscriber.onCompleted()
        }

    }

    override fun deletepost(devotional: Devotional) {
        Paper.book().delete(devotional.id)
    }

}
