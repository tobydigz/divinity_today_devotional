package com.digzdigital.divinitytoday.ui.devlist

import com.digzdigital.divinitytoday.data.DataManager
import com.digzdigital.divinitytoday.data.model.Devotional
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class DevotionalsPresenter(val dataManager: DataManager): DevListContract.Presenter {
    lateinit var view: DevListContract.View
    private val devotionals = ArrayList<Devotional>()

    protected var subscriptions:CompositeSubscription
    init {
        subscriptions = CompositeSubscription()
    }
    override fun onAttach(view: DevListContract.View) {
        this.view = view
    }

    override fun onDetach() {
        subscriptions.clear()
    }



    override fun loadDevotionals(endpoint: Int) {
        view.showProgressDialog()
        val subscription = dataManager.getDevotionals(offset = devSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            retrievedDevotionals ->
                            devotionals.addAll(retrievedDevotionals)
                            view.dismissProgressDialog()
                            view.showDevotionals(devotionals)
                        }
                )
        subscriptions.add(subscription)
    }

    override val devSize: String
        get() = devotionals.size.toString()
}
