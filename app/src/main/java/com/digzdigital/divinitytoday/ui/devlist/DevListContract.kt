package com.digzdigital.divinitytoday.ui.devlist

import com.digzdigital.divinitytoday.data.model.Devotional
import com.digzdigital.divinitytoday.ui.base.BasePresenter
import com.digzdigital.divinitytoday.ui.base.BaseView

interface DevListContract {
    interface View : BaseView {

        fun loadDevotionalsAndAds(devotionals: ArrayList<Devotional>)

        fun reShowDevotionals(devotionals: ArrayList<Devotional>)

        fun showProgressDialog()

        fun dismissProgressDialog()

        fun makeToast(message: String)
    }

    interface Presenter : BasePresenter<View> {

        fun loadDevotionals(endpoint: Int=0)

        fun reloadDevotionals(endpoint: Int=0)

        val devSize: String

        fun showDevotionals()

    }
}

