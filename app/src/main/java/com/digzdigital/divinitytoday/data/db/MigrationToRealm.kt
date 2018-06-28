package com.digzdigital.divinitytoday.data.db

import com.digzdigital.divinitytoday.data.commons.ModelExtension
import com.digzdigital.divinitytoday.data.devotionals.local.LocalDevotionalDataPersistence
import com.digzdigital.divinitytoday.data.model.Devotional
import javax.inject.Inject

class MigrationToRealm @Inject constructor(private val dataPersistence: LocalDevotionalDataPersistence,
                                           private val paperDbHelper: PaperDbHelper) {

    fun doMigration() {
        paperDbHelper.queryForPosts()
                .map { devotionals ->
                    val newDevotionals = ArrayList<Devotional>()
                    for (devotional in devotionals) {
                        newDevotionals.add(Devotional(
                                id = devotional.id,
                                date = ModelExtension.getFormattedDate(devotional.date),
                                title = ModelExtension.getCleanedTitle(devotional.title),
                                content = ModelExtension.getCleanedContent(devotional.content),
                                excerpt = "",
                                bookmarked = true
                        ))
                    }
                    newDevotionals
                }
                .doAfterSuccess { devotionals ->
                    for (devotional in devotionals) dataPersistence.addToBookmarked(devotional)
                    paperDbHelper.deleteAll()
                }
    }
}