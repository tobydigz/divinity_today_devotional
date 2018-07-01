package com.digzdigital.divinitytoday.ui.devlist

import com.digzdigital.divinitytoday.data.devotionals.DevotionalRepository
import com.digzdigital.divinitytoday.data.model.Devotional
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DevotionalsPresenter @Inject constructor(private val repository: DevotionalRepository) : DevListContract.Presenter {

    lateinit var view: DevListContract.View

    private var compositeDisposable = CompositeDisposable()

    override fun onAttach(view: DevListContract.View) {
        this.view = view
    }

    override fun onDetach() {
        compositeDisposable.clear()
    }

    override fun loadDevotionals() {
        view.showProgressDialog()
        val disposable = repository.getDevotionals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onError = {
                            view.dismissProgressDialog()
                            view.makeToast("There was an error loading the devotionals")
                        },
                        onSuccess = { devotionals ->
                            view.dismissProgressDialog()
                            view.showDevotionalsAndAds(devotionals)
                        }
                )
        compositeDisposable.add(disposable)
    }

    override fun loadMoreDevotionals(endpoint: Int) {
        view.showProgressDialog()
        val disposable = repository.loadMore(endpoint)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onError = {
                            view.dismissProgressDialog()
                            view.setRefreshingOff()
                            view.makeToast("There was an error loading the devotionals")
                        },
                        onSuccess = { devotionals ->
                            view.dismissProgressDialog()
                            view.setRefreshingOff()
                            view.showMoreDevotionals(devotionals)
                        }
                )
        compositeDisposable.add(disposable)
    }

    override fun refreshDevotionals() {
        repository.forceRefresh()
        loadDevotionals()
    }

    override fun addDevotionalToBookmarks(devotional: Devotional) {
        repository.bookmarkDevotional(devotional)
    }

    override fun removeDevotionalFromBookmarks(devotional: Devotional) {
        repository.removeBookmarkedDevotional(devotional.id)
    }
}
