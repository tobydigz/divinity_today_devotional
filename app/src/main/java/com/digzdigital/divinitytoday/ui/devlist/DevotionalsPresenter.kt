package com.digzdigital.divinitytoday.ui.devlist

import android.util.Log
import com.digzdigital.divinitytoday.data.DataManager
import com.digzdigital.divinitytoday.data.model.Devotional
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class DevotionalsPresenter(val dataManager: DataManager) : DevListContract.Presenter {

    lateinit var view: DevListContract.View
    private val devotionals = ArrayList<Devotional>()

    private var subscriptions: CompositeSubscription = CompositeSubscription()

    override fun onAttach(view: DevListContract.View) {
        this.view = view
    }

    override fun onDetach() {
        subscriptions.clear()
    }


    override fun loadDevotionals(endpoint: Int) {
        val subscription = dataManager.getDevotionals(offset = devSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnCompleted { view.dismissProgressDialog() }
                .doOnError({ error ->
                    view.dismissProgressDialog()
                    Log.d("DigzApp", "error getting devotionals ${error.message}")
                })
                .subscribe(
                        {
                            retrievedDevotionals ->

                            view.dismissProgressDialog()

                            if (retrievedDevotionals.isEmpty()){
                                view.makeToast("Try loading again")
                            }else{
                                devotionals.addAll(retrievedDevotionals)
                                view.loadDevotionalsAndAds(retrievedDevotionals as ArrayList<Devotional>)

                            }
                        }
                )
        subscriptions.add(subscription)
    }

    override fun reloadDevotionals(endpoint: Int) {
        val subscription = dataManager.getDevotionals(offset = "0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnCompleted {  }
                .doOnError({ error ->
                    Log.d("DigzApp", "error getting devotionals ${error.message}")
                })
                .subscribe(
                        {
                            retrievedDevotionals ->

                            if (retrievedDevotionals.isEmpty())view.makeToast("Try loading again")
                            else{
                                devotionals.clear()
                                devotionals.addAll(retrievedDevotionals)
                                view.reShowDevotionals(retrievedDevotionals as ArrayList<Devotional>)
                            }
                        }
                )
        subscriptions.add(subscription)
    }

    override val devSize: String
        get() = devotionals.size.toString()

    override fun showDevotionals() {
        view.loadDevotionalsAndAds(devotionals)
    }
}
