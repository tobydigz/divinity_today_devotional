package com.digzdigital.divinitytoday.ui.devlist

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.digzdigital.divinitytoday.DivinityTodayApp
import com.digzdigital.divinitytoday.R
import com.digzdigital.divinitytoday.commons.DevotionalClickListener
import com.digzdigital.divinitytoday.commons.InfiniteScrollListener
import com.digzdigital.divinitytoday.data.model.Devotional
import com.digzdigital.divinitytoday.data.model.DevotionalAd
import com.digzdigital.divinitytoday.ui.devlist.adapter.DevotionalAdapter
import com.digzdigital.divinitytoday.ui.devlist.di.DevotionalsListPresenterModule
import com.digzdigital.divinitytoday.ui.reader.ReaderActivity
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.NativeExpressAdView
import kotlinx.android.synthetic.main.fragment_devotionals.*
import xyz.digzdigital.keddit.commons.extensions.inflate
import javax.inject.Inject

class DevotionalsFragment : Fragment(), DevListContract.View, DevotionalClickListener {

    @Inject
    lateinit var presenter: DevotionalsPresenter
    lateinit var progressDialog: ProgressDialog
    private val adapter: DevotionalAdapter by lazy {
        DevotionalAdapter(this)
    }
    private val ITEMS_PER_AD = 4
    private var AD_ID = "ca-app-pub-6610707566113750/5094407226"
    val adSize by lazy {
        val scale: Float = resources.displayMetrics.density
        val adWidth = devotionalsList.width
        AdSize((adWidth / scale).toInt(), 100)
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
        showProgressDialog()
        presenter.loadDevotionals()
    }

    private fun addNativeExpressAds(start: Int) {
        val size = adapter.itemCount
        for (i in start..size step ITEMS_PER_AD) {
            val adView = NativeExpressAdView(context)
            adView.adSize = adSize
            adView.adUnitId = AD_ID
            val devotionalAd = DevotionalAd(adView)
            adapter.addAds(i, devotionalAd)
        }
        loadNativeExpressAd(start)
    }

    private fun loadNativeExpressAd(index: Int) {
        if (index >= adapter.itemCount) return

        val item = adapter.items[index]
        when (item) {
            is DevotionalAd -> {
                val adView = item.ad
                adView.adListener = object : AdListener() {
                    override fun onAdLoaded() {
                        super.onAdLoaded()
                        loadNativeExpressAd(index + ITEMS_PER_AD)
                    }

                    override fun onAdFailedToLoad(errorCode: Int) {
                        Log.e("MainActivity", "The previous Native Express ad failed to load. Attempting to" + " load the next Native Express ad in the items list.")
                        loadNativeExpressAd(index + ITEMS_PER_AD)
                    }
                }
                adView.loadAd(AdRequest.Builder().build())
            }
            else -> loadNativeExpressAd(index + 1)
        }
    }

    override fun showProgressDialog() {
        progressDialog = ProgressDialog(context)
        progressDialog.apply {
            setTitle("Loading")
            setMessage("Please wait...")
            show()
        }
    }

    override fun dismissProgressDialog() {
        progressDialog.dismiss()
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
        val size = adapter.itemCount
        adapter.clearAndAddDevotionals(devotionals)
        addNativeExpressAds(size)
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
}