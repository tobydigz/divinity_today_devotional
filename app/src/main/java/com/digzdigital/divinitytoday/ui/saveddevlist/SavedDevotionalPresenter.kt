package com.digzdigital.divinitytoday.ui.saveddevlist

import com.digzdigital.divinitytoday.data.DataManager
import com.digzdigital.divinitytoday.data.model.Devotional
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class SavedDevotionalPresenter(val dataManager: DataManager):SavedDevotionalContract.Presenter {

    lateinit var view: SavedDevotionalContract.View
    private val devotionals = ArrayList<Devotional>()
    private var subscriptions: CompositeSubscription= CompositeSubscription()

    override fun onAttach(view: SavedDevotionalContract.View) {
        this.view = view
    }

    override fun onDetach() {
        subscriptions.clear()
    }

    override fun loadDevotionals(endpoint: Int) {
        val subscription = dataManager.queryForPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    retrievedDevotionals ->

                    devotionals.addAll(retrievedDevotionals)
                    view.dismissProgressDialog()
                    view.showDevotionals(devotionals)
                }
        subscriptions.add(subscription)
    }

    override fun deleteDevotional(devotional: Devotional) {
        dataManager.deletepost(devotional)
        val position = devotionals.indexOf(devotional)
        devotionals.removeAt(position)
        view.notifyItemChanged(position)
    }

    override val devSize: String
        get() = devotionals.size.toString()
}
