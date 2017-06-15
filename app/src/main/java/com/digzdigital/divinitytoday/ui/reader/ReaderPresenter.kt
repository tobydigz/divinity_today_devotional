package com.digzdigital.divinitytoday.ui.reader

import com.digzdigital.divinitytoday.data.DataManager
import com.digzdigital.divinitytoday.data.model.Devotional

class ReaderPresenter(val dataManager: DataManager):ReaderContract.Presenter{
    lateinit var view: ReaderContract.View
    override fun onAttach(view: ReaderContract.View) {
        this.view = view
    }

    override fun onDetach() {

    }

    override fun saveDevotional(devotional: Devotional) {
        val success = dataManager.savePost(devotional)
        view.disableSaveButton()
        if (!success){
            view.showToast("Devotional already saved")
            return
        }
        view.showToast("Devotional saved succesfully")
    }

}