package com.digzdigital.divinitytoday.ui.devlist

import com.digzdigital.divinitytoday.data.model.Devotional
import com.digzdigital.divinitytoday.ui.base.BasePresenter
import com.digzdigital.divinitytoday.ui.base.BaseView

interface DevListContract {
    interface View : BaseView {

        fun showDevotionals(devotionals: ArrayList<Devotional>)

        fun showProgressDialog()

        fun dismissProgressDialog()

        fun makeToast(message: String)

    }

    interface Presenter : BasePresenter<View> {

        fun loadDevotionals(endpoint: Int=0)

        val devSize: String

    }
}

