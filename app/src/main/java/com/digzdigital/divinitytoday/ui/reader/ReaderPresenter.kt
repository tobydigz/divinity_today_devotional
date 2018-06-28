package com.digzdigital.divinitytoday.ui.reader

import com.digzdigital.divinitytoday.data.devotionals.DevotionalRepository
import com.digzdigital.divinitytoday.data.model.Devotional
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ReaderPresenter @Inject constructor(private val repository: DevotionalRepository) : ReaderContract.Presenter {
    private lateinit var view: ReaderContract.View
    private val compositeDisposable = CompositeDisposable()

    override fun onAttach(view: ReaderContract.View) {
        this.view = view
    }

    override fun onDetach() {
        compositeDisposable.clear()
    }

    override fun bookmarkDevotional(devotional: Devotional) {
        repository.bookmarkDevotional(devotional)
        view.setDevotionalSavedState(true)
    }

    override fun loadDevotional(devotionalId: String) {
        val disposable = repository.getDevotional(devotionalId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onError = {

                        },
                        onSuccess = { devotional ->
                            view.showDevotional(devotional)
                        })
        compositeDisposable.add(disposable)
    }

    override fun removeDevotionalFromBookmarks(devotional: Devotional) {
        repository.removeBookmarkedDevotional(devotional.id)
        view.setDevotionalSavedState(false)
    }
}