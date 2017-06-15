package com.digzdigital.divinitytoday.data.db

import com.digzdigital.divinitytoday.data.model.Devotional
import io.paperdb.Paper
import java.util.ArrayList

class SugarDbHelper(): DbHelper{
    override var dbListener: DbHelper.DbListener? = null
        get() = field
        set(value) {field = value}

    override fun savePost(devotional: Devotional) {
         val allKeys = Paper
    }

    override fun queryForPosts() {
    }

    override fun deletepost(devotional: Devotional) {
    }

}
