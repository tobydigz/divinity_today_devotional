package com.digzdigital.divinitytoday.data

import com.digzdigital.divinitytoday.data.db.DbHelper
import com.digzdigital.divinitytoday.data.model.Devotional
import com.digzdigital.divinitytoday.data.wp.WpHelper
import rx.Observable

class AppDataManager(val dbHelper: DbHelper,val wpHelper: WpHelper): DataManager {
    override fun getDevotionals(offset: String, per_page: String): Observable<List<Devotional>> {
        return wpHelper.getDevotionals(offset, per_page)
    }

    override fun savePost(devotional: Devotional): Boolean {
        return dbHelper.savePost(devotional)
    }

    override fun queryForPosts(): Observable<List<Devotional>> {
        return dbHelper.queryForPosts()
    }

    override fun deletepost(devotional: Devotional) {
        dbHelper.deletepost(devotional)
    }
}
