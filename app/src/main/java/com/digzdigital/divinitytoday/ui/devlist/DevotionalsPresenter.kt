package com.digzdigital.divinitytoday.ui.devlist

import android.util.Log
import com.digzdigital.divinitytoday.data.DataManager
import com.digzdigital.divinitytoday.data.model.Devotional
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DevotionalsPresenter(val dataManager: DataManager) : DevListContract.Presenter {

    lateinit var view: DevListContract.View
    private val devotionals = ArrayList<Devotional>()

    private var subscriptions = CompositeDisposable()

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
                .doOnComplete { view.dismissProgressDialog() }
                .doOnError({ error: Throwable ->
                    view.dismissProgressDialog()
                    Log.d("DigzApp", "error getting devotionals ${error.message}")
                })
                .subscribe(
                        { retrievedDevotionals: List<Devotional> ->

                            view.dismissProgressDialog()

                            if (retrievedDevotionals.isEmpty()) {
                                view.makeToast("Try loading again")
                            } else {
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
                .doOnComplete { }
                .doOnError({ error: Throwable ->
                    Log.d("DigzApp", "error getting devotionals ${error.message}")
                })
                .subscribe(
                        { retrievedDevotionals: List<Devotional> ->

                            if (retrievedDevotionals.isEmpty()) view.makeToast("Try loading again")
                            else {
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
