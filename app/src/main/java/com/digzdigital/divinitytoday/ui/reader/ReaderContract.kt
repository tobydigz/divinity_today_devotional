package com.digzdigital.divinitytoday.ui.reader

import com.digzdigital.divinitytoday.data.model.Devotional
import com.digzdigital.divinitytoday.ui.base.BasePresenter
import com.digzdigital.divinitytoday.ui.base.BaseView

interface ReaderContract {
    interface View : BaseView {
        fun showToast(message: String)

        fun showDevotional(devotional: Devotional)

        fun setDevotionalSavedState(isSaved: Boolean)
    }

    interface Presenter : BasePresenter<View> {
        fun loadDevotional(devotionalId: String)

        fun bookmarkDevotional(devotional: Devotional)

        fun removeDevotionalFromBookmarks(devotional: Devotional)
    }
}
