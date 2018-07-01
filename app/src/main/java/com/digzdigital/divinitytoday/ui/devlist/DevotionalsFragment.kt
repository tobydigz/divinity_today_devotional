package com.digzdigital.divinitytoday.ui.devlist

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.digzdigital.divinitytoday.DivinityTodayApp
import com.digzdigital.divinitytoday.R
import com.digzdigital.divinitytoday.commons.DevotionalClickListener
import com.digzdigital.divinitytoday.commons.InfiniteScrollListener
import com.digzdigital.divinitytoday.commons.adapter.SimpleDividerItemDecoration
import com.digzdigital.divinitytoday.data.model.Devotional
import com.digzdigital.divinitytoday.ui.devlist.adapter.DevotionalAdapter
import com.digzdigital.divinitytoday.ui.devlist.di.DevotionalsListPresenterModule
import com.digzdigital.divinitytoday.ui.reader.ReaderActivity
import kotlinx.android.synthetic.main.fragment_devotionals.*
import xyz.digzdigital.keddit.commons.extensions.inflate
import javax.inject.Inject

class DevotionalsFragment : Fragment(), DevListContract.View, DevotionalClickListener {

    @Inject
    lateinit var presenter: DevotionalsPresenter
    private val adapter: DevotionalAdapter by lazy {
        DevotionalAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity!!.application as DivinityTodayApp)
                .appComponent
                .plus(DevotionalsListPresenterModule(this))
                .inject(this)

        presenter.onAttach(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_devotionals)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        devotionalsList.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener({
                presenter.loadMoreDevotionals(this@DevotionalsFragment.adapter.getDevotionalsCount())
            }, linearLayout))
        }
        devotionalsList.adapter = adapter
        devotionalsList.addItemDecoration(SimpleDividerItemDecoration(context!!))
        showProgressDialog()
        presenter.loadDevotionals()
        swipe_refresh.setOnRefreshListener { presenter.refreshDevotionals() }
    }

    override fun showProgressDialog() {

    }

    override fun dismissProgressDialog() {

    }

    override fun makeToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        presenter.onDetach()
    }

    override fun showDevotionalsAndAds(devotionals: List<Devotional>) {
        if (devotionals.isEmpty()) return
        adapter.clearAndAddDevotionals(devotionals)
    }

    override fun showMoreDevotionals(devotionals: List<Devotional>) {
        if (devotionals.isEmpty()) return
        adapter.clearAndAddDevotionals(devotionals)
    }

    override fun onItemClick(devotional: Devotional) {
        val intent = Intent(context, ReaderActivity::class.java)
        intent.putExtra(ReaderActivity.DEVOTIONAL_ID, devotional.id)
        startActivity(intent)
    }

    override fun onBookmarkClick(devotional: Devotional) {

    }

    override fun setRefreshingOff() {
        swipe_refresh.isRefreshing = false
    }
}