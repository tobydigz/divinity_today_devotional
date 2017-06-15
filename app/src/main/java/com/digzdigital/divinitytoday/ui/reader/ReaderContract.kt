package com.digzdigital.divinitytoday.ui.reader

import com.digzdigital.divinitytoday.data.model.Devotional
import com.digzdigital.divinitytoday.ui.base.BasePresenter
import com.digzdigital.divinitytoday.ui.base.BaseView

interface ReaderContract{
    interface View:BaseView{
        fun showToast(message:String)
        fun disableSaveButton()
    }

    interface Presenter:BasePresenter<View>{
        fun saveDevotional(devotional: Devotional)
    }
}
