package com.digzdigital.divinitytoday.ui.bookmarkeddevotionals

import com.digzdigital.divinitytoday.data.devotionals.DevotionalRepository
import com.digzdigital.divinitytoday.data.model.Devotional
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class SavedDevotionalPresenter(val repository: DevotionalRepository) : SavedDevotionalContract.Presenter {

    lateinit var view: SavedDevotionalContract.View
    private var compositeDisposable = CompositeDisposable()

    override fun onAttach(view: SavedDevotionalContract.View) {
        this.view = view
    }

    override fun onDetach() {
        compositeDisposable.clear()
    }

    override fun loadDevotionals() {
        val disposable = repository.getBookmarkedDevotionals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onError = {

                        },
                        onSuccess = { devotionals ->
                            view.dismissProgressDialog()
                            view.showDevotionals(devotionals)
                        })
        compositeDisposable.add(disposable)
    }

    override fun removeDevotionalFromBookmark(devotional: Devotional) {
        repository.removeBookmarkedDevotional(devotional.id)
        view.removeDevotionalFromAdapter(devotional)
    }
}
