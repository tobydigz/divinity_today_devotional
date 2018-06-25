package com.digzdigital.divinitytoday.ui.bookmarkeddevotionals

import com.digzdigital.divinitytoday.data.model.Devotional
import com.digzdigital.divinitytoday.ui.base.BasePresenter
import com.digzdigital.divinitytoday.ui.base.BaseView

interface SavedDevotionalContract{
    interface View:BaseView{
        fun showDevotionals(devotionals: List<Devotional>)

        fun showProgressDialog()

        fun dismissProgressDialog()

        fun makeToast(message: String)

        fun removeDevotionalFromAdapter(devotional: Devotional)
    }

    interface Presenter:BasePresenter<View>{
        fun loadDevotionals()

        fun removeDevotionalFromBookmark(devotional: Devotional)
    }
}