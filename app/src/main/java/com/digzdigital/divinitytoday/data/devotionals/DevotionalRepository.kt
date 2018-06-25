package com.digzdigital.divinitytoday.data.devotionals

import com.digzdigital.divinitytoday.dagger.annotations.Local
import com.digzdigital.divinitytoday.dagger.annotations.Remote
import com.digzdigital.divinitytoday.data.model.Devotional
import io.reactivex.Single
import java.util.LinkedHashMap
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.set

class DevotionalRepository @Inject constructor(@Local private val localDataSource: DevotionalDataSource,
                                               @Remote private val remoteDataSource: DevotionalDataSource,
                                               @Local private val localDataPersistence: DevotionalDataPersistence) {

    private val inMemoryDataSource: MutableMap<String, Devotional> = LinkedHashMap()
    private var refresh = false

    fun getDevotionals(): Single<List<Devotional>> {
        val remoteSource = getAndSaveRemoteDevotionals(0)

        if (refresh) {
            refresh = false
            return remoteSource
        }

        if (!inMemoryDataSource.isEmpty()) {
            return getInMemoryDevotionals()
        }

        val localSource = getLocalDevotionals()

        return Single.concat(
                localSource,
                remoteSource)
                .filter { it.isEmpty() }
                .firstOrError()

    }


    fun getBookmarkedDevotionals(): Single<List<Devotional>> {

        return localDataSource.getBookmarkedDevotionals()

    }

    fun forceRefresh() {
        refresh = true
    }

    fun getDevotional(id: String): Single<Devotional> {
        if (inMemoryDataSource[id] != null) {
            return Single.just(inMemoryDataSource[id])
        }

        val remoteSource = getAndSaveSingleRemoteDevotional(id)
        val localSource = getLocalDevotionals(id)

        return localSource
                .onErrorResumeNext { remoteSource }
    }

    fun loadMore(startFrom: Int): Single<List<Devotional>> {
        return getAndSaveRemoteDevotionals(startFrom)
    }


    fun bookmarkDevotional(devotional: Devotional) {
        localDataPersistence.addToBookmarked(devotional)
    }

    fun removeBookmarkedDevotional(devotionalId: String) {
        localDataPersistence.removeFromBookmarked(devotionalId)
    }

    private fun getLocalDevotionals(): Single<List<Devotional>> {
        return localDataSource.getDevotionals("", "")
                .doAfterSuccess { devotionals ->
                    for (devotional in devotionals) inMemoryDataSource[devotional.id] = devotional
                }
    }

    private fun getLocalDevotionals(postId: String): Single<Devotional> {
        return localDataSource.getDevotional(postId)
                .doAfterSuccess { devotional ->
                    inMemoryDataSource[devotional.id] = devotional
                }
    }

    private fun getAndSaveRemoteDevotionals(startFrom: Int): Single<List<Devotional>> {
        return remoteDataSource.getDevotionals(startFrom.toString(), "10")
                .doAfterSuccess { devotionals ->
                    for (devotional in devotionals) {
                        localDataPersistence.save(devotional)
                        inMemoryDataSource[devotional.id] = devotional
                    }
                }
    }

    private fun getAndSaveSingleRemoteDevotional(postId: String): Single<Devotional> {
        return remoteDataSource.getDevotional(postId)
                .doAfterSuccess { devotional ->
                    localDataPersistence.save(devotional)
                    inMemoryDataSource[devotional.id] = devotional
                }
    }

    private fun getInMemoryDevotionals(): Single<List<Devotional>> {
        val inMemoryDevotionals = ArrayList<Devotional>(inMemoryDataSource.values.sortedByDescending { devotional -> devotional.id })
        return Single.just(inMemoryDevotionals)
    }
}