package com.digzdigital.divinitytoday.ui.bookmarkeddevotionals

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.digzdigital.divinitytoday.DivinityTodayApp
import com.digzdigital.divinitytoday.R
import com.digzdigital.divinitytoday.commons.DevotionalClickListener
import com.digzdigital.divinitytoday.data.model.Devotional
import com.digzdigital.divinitytoday.ui.bookmarkeddevotionals.adapter.SavedDevotionalAdapter
import com.digzdigital.divinitytoday.ui.bookmarkeddevotionals.di.SavedDevotionalsListPresenterModule
import com.digzdigital.divinitytoday.ui.reader.ReaderActivity
import kotlinx.android.synthetic.main.fragment_devotionals.*
import xyz.digzdigital.keddit.commons.extensions.inflate
import javax.inject.Inject

class SavedDevotionalFragment : Fragment(), SavedDevotionalContract.View, DevotionalClickListener {

    @Inject
    lateinit var presenter: SavedDevotionalContract.Presenter
    private val adapter: SavedDevotionalAdapter by lazy {
        SavedDevotionalAdapter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_devotionals)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity!!.application as DivinityTodayApp).appComponent
                .plus(SavedDevotionalsListPresenterModule(this))
                .inject(this)

        presenter.onAttach(this)
        devotionalsList.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
        }
        devotionalsList.adapter = adapter
        showProgressDialog()
        presenter.loadDevotionals()
    }

    private fun showAlertDialog(devotional: Devotional) {
        val title = "Delete Devotional?"
        val accept = "Yes"
        val reject = "Cancel"
        AlertDialog.Builder(context!!)
                .setTitle(title)
                .setMessage("Are you sure you want remove this devotional from your bookmarks")
                .setPositiveButton(accept) { _, _ -> presenter.removeDevotionalFromBookmark(devotional) }
                .setNegativeButton(reject) { _, _ -> }
                .create()
                .show()
    }

    override fun showDevotionals(devotionals: List<Devotional>) {
        if (devotionals.isEmpty()) return
        adapter.addDevotionals(devotionals)
    }

    override fun showProgressDialog() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun dismissProgressDialog() {
        progress_bar.visibility = View.GONE
    }

    override fun makeToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        presenter.onDetach()
    }

    override fun removeDevotionalFromAdapter(devotional: Devotional) {
        adapter.removeDevotional(devotional)
    }

    override fun onBookmarkClick(devotional: Devotional) {
        showAlertDialog(devotional)
    }

    override fun onItemClick(devotional: Devotional) {
        val intent = Intent(context, ReaderActivity::class.java)
        intent.putExtra(ReaderActivity.DEVOTIONAL_ID, devotional.id)
        startActivity(intent)
    }
}