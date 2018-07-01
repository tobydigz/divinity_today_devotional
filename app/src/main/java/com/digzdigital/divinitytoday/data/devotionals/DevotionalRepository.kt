package com.digzdigital.divinitytoday.data.devotionals

import com.digzdigital.divinitytoday.dagger.annotations.Local
import com.digzdigital.divinitytoday.dagger.annotations.Remote
import com.digzdigital.divinitytoday.data.commons.ModelExtension
import com.digzdigital.divinitytoday.data.model.Devotional
import com.digzdigital.divinitytoday.data.session.SessionManager
import io.reactivex.Single
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.set

class DevotionalRepository @Inject constructor(@Local private val localDataSource: DevotionalDataSource,
                                               @Remote private val remoteDataSource: DevotionalDataSource,
                                               @Local private val localDataPersistence: DevotionalDataPersistence,
                                               private val sessionManager: SessionManager) {

    private val inMemoryDataSource: MutableMap<String, Devotional> = LinkedHashMap()
    private var refresh = false

    fun getDevotionals(): Single<List<Devotional>> {
        val remoteSource = getAndSaveRemoteDevotionals(0)

        if (refresh) {
            refresh = false
            return remoteSource
        }

        if (sessionManager.isFirstLoad()) {
            return remoteSource
                    .doAfterSuccess { sessionManager.setFirstLoad() }
        }

        if (!inMemoryDataSource.isEmpty()) {
            return getInMemoryDevotionals()
        }

        val localSource = getLocalDevotionals()

        return Single.concat(
                localSource,
                remoteSource)
                .filter { !it.isEmpty() }
                .firstOrError()

    }


    fun getBookmarkedDevotionals(): Single<List<Devotional>> {

        return localDataSource.getBookmarkedDevotionals()

    }

    fun forceRefresh() {
        refresh = true
    }

    fun getDevotional(id: String): Single<Devotional> {

        val remoteSource = getAndSaveSingleRemoteDevotional(id)
        val localSource = getLocalDevotionals(id)

        return localSource
                .onErrorResumeNext { remoteSource }
    }

    fun getDevotionalForToday(): Single<Devotional> {
        val date = ModelExtension.getDateForSearch(Date().time)
        val remoteSource = remoteDataSource.getDevotionalByDate((date - TimeUnit.DAYS.toMillis(1)))
                .doAfterSuccess { devotional -> inMemoryDataSource[devotional.id] = devotional }
        val localSource = localDataSource.getDevotionalByDate(date)
                .doAfterSuccess { devotional -> inMemoryDataSource[devotional.id] = devotional }

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