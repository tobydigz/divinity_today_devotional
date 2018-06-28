package com.digzdigital.divinitytoday.data.devotionals.local

import com.digzdigital.divinitytoday.data.devotionals.DevotionalDataSource
import com.digzdigital.divinitytoday.data.devotionals.local.mapper.DevotionalRealmToDevotionalMapper
import com.digzdigital.divinitytoday.data.devotionals.local.model.DevotionalRealm
import com.digzdigital.divinitytoday.data.model.Devotional
import io.reactivex.Single
import io.realm.Realm
import io.realm.Sort
import javax.inject.Inject

class LocalDevotionalDataSource @Inject constructor(private val mapper: DevotionalRealmToDevotionalMapper,
                                                    private val realm: Realm) : DevotionalDataSource {

    override fun getDevotionals(startFrom: String, size: String) = Single.just(findDevotionals())

    private fun findDevotionals(): List<Devotional> {
        val devotionalRealmList = realm.where(DevotionalRealm::class.java)
                .sort("id", Sort.DESCENDING)
                .findAll()

        return ArrayList(mapper.mapMany(devotionalRealmList))
    }

    override fun getBookmarkedDevotionals() = Single.just(findFavoriteDevotionals())

    private fun findFavoriteDevotionals(): List<Devotional> {
        val devotionalRealmList = realm.where(DevotionalRealm::class.java)
                .equalTo("isBookmarked", true)
                .sort("id", Sort.DESCENDING)
                .findAll()

        return ArrayList(mapper.mapMany(devotionalRealmList))
    }

    override fun getDevotional(id: String) = findDevotional(id)

    private fun findDevotional(id: String): Single<Devotional> {
        return Single.create { emitter ->
            val devotionalRealm = realm.where(DevotionalRealm::class.java)
                    .equalTo("id", id)
                    .findFirst()
            if (devotionalRealm == null) {
                emitter.onError(Throwable("Devotional not found"))
                return@create
            }

            emitter.onSuccess(mapper.map1(devotionalRealm))
        }
    }
}