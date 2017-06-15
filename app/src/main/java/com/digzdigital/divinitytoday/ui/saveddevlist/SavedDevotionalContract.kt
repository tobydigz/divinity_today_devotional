package com.digzdigital.divinitytoday.ui.saveddevlist

import com.digzdigital.divinitytoday.data.model.Devotional
import com.digzdigital.divinitytoday.ui.base.BasePresenter
import com.digzdigital.divinitytoday.ui.base.BaseView

interface SavedDevotionalContract{
    interface View:BaseView{
        fun showDevotionals(devotionals: ArrayList<Devotional>)

        fun showProgressDialog()

        fun dismissProgressDialog()

        fun makeToast(message: String)

        fun notifyItemChanged(position:Int)
    }

    interface Presenter:BasePresenter<View>{
        fun loadDevotionals(endpoint: Int=0)

        fun deleteDevotional(devotional: Devotional)

        val devSize: String
    }
}