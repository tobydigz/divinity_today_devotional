package com.digzdigital.divinitytoday.data.devotionals.local

import com.digzdigital.divinitytoday.data.devotionals.DevotionalDataPersistence
import com.digzdigital.divinitytoday.data.devotionals.local.model.DevotionalRealm
import com.digzdigital.divinitytoday.data.model.Devotional
import io.realm.Realm
import javax.inject.Inject

class LocalDevotionalDataPersistence @Inject constructor() : DevotionalDataPersistence {

    override fun save(devotional: Devotional) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { findOrCreateDevotional(devotional, realm) }
    }

    private fun findOrCreateDevotional(devotional: Devotional, realm: Realm): DevotionalRealm {
        var devotionalRealm = realm.where(DevotionalRealm::class.java)
                .equalTo("id", devotional.id)
                .findFirst()

        if (devotionalRealm == null) {
            devotionalRealm = realm.createObject(DevotionalRealm::class.java, devotional.id)
            devotionalRealm.title = devotional.title
            devotionalRealm.date = devotional.date
            devotionalRealm.content = devotional.content
            devotionalRealm.excerpt = devotional.excerpt
            devotionalRealm.bookmarked = false
            return devotionalRealm
        }
        return devotionalRealm
    }

    override fun addToBookmarked(devotional: Devotional) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { findAndAddToBookmarked(devotional, realm) }
    }


    private fun findAndAddToBookmarked(devotional: Devotional, realm: Realm): DevotionalRealm {
        var devotionalRealm = realm.where(DevotionalRealm::class.java)
                .equalTo("id", devotional.id)
                .findFirst()

        if (devotionalRealm == null) {
            devotionalRealm = realm.createObject(DevotionalRealm::class.java, devotional.id)
            devotionalRealm.title = devotional.title
            devotionalRealm.date = devotional.date
            devotionalRealm.content = devotional.content
            devotionalRealm.excerpt = devotional.excerpt
            devotionalRealm.bookmarked = true
            return devotionalRealm
        }
        devotionalRealm.bookmarked = true
        return devotionalRealm
    }

    override fun removeFromBookmarked(devotionalId: String) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { findAndRemoveFromBookmarked(devotionalId, realm) }
    }

    private fun findAndRemoveFromBookmarked(devotionalId: String, realm: Realm) {
        val devotionalRealm: DevotionalRealm? = realm.where(DevotionalRealm::class.java)
                .equalTo("id", devotionalId)
                .findFirst() ?: return

        devotionalRealm!!.bookmarked = false
    }
}