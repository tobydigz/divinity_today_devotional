package com.digzdigital.divinitytoday.ui.devlist

import com.digzdigital.divinitytoday.data.model.Devotional
import com.digzdigital.divinitytoday.ui.base.BasePresenter
import com.digzdigital.divinitytoday.ui.base.BaseView

interface DevListContract {
    interface View : BaseView {

        fun showDevotionalsAndAds(devotionals: List<Devotional>)

        fun showMoreDevotionals(devotionals: List<Devotional>)

        fun showProgressDialog()

        fun dismissProgressDialog()

        fun makeToast(message: String)

        fun setRefreshingOff()
    }

    interface Presenter : BasePresenter<View> {

        fun loadDevotionals()

        fun loadMoreDevotionals(endpoint: Int = 0)

        fun refreshDevotionals()

        fun addDevotionalToBookmarks(devotional: Devotional)

        fun removeDevotionalFromBookmarks(devotional: Devotional)
    }
}

