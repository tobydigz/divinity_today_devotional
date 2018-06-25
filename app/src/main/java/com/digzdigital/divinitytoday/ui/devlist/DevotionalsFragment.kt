package com.digzdigital.divinitytoday.ui.devlist

import android.app.ProgressDialog
import android.content.Context
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
import com.digzdigital.divinitytoday.commons.InfiniteScrollListener
import com.digzdigital.divinitytoday.commons.MyClickListener
import com.digzdigital.divinitytoday.commons.OnDevotionalSelectedListener
import com.digzdigital.divinitytoday.data.model.Devotional
import com.digzdigital.divinitytoday.data.model.DevotionalAd
import com.digzdigital.divinitytoday.data.model.DevotionalWrapper
import com.digzdigital.divinitytoday.ui.devlist.adapter.DevotionalAdapter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.NativeExpressAdView
import kotlinx.android.synthetic.main.fragment_devotionals.*
import kotlinx.android.synthetic.main.item_ad.*
import xyz.digzdigital.keddit.commons.extensions.inflate
import javax.inject.Inject

class DevotionalsFragment : Fragment(), DevListContract.View {

    @Inject
    lateinit var presenter: DevListContract.Presenter
    lateinit var progressDialog: ProgressDialog
    private var isLoaded = false
    lateinit var adapter: DevotionalAdapter
    lateinit var callback: OnDevotionalSelectedListener
    private val ITEMS_PER_AD = 4
    private var AD_ID = "ca-app-pub-6610707566113750/5094407226"
    val adSize by lazy{
        val scale: Float = resources.displayMetrics.density
        val adWidth = devotionalsList.width
        AdSize((adWidth / scale).toInt(), 100)
    }

    companion object {
        private val KEY_DIVINITY = "divinity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity!!.application as DivinityTodayApp).appComponent.inject(this)

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
            addOnScrollListener(InfiniteScrollListener({ presenter.loadDevotionals() }, linearLayout))
        }
        initAdapter()
        if (isLoaded) {
            presenter.showDevotionals()
            return
        }
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_DIVINITY)) {
            val devotional = savedInstanceState.get(KEY_DIVINITY) as DevotionalWrapper
            adapter.clearAndAddDevotionals(devotional.devotionals)
            return
        }
        showProgressDialog()
        presenter.loadDevotionals()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val devotionals = adapter.getDevotionals()
        val devotional = DevotionalWrapper(devotionals)
        if (!devotionals.isEmpty()) outState.putParcelable(KEY_DIVINITY, devotional.copy(devotionals = devotionals))
    }

    private fun initAdapter() {
        if (devotionalsList.adapter == null) {
            devotionalsList.adapter = DevotionalAdapter(object : MyClickListener {
                override fun onItemClick(devotional: Devotional) {
                    callback.onDevotionalSelected(devotional, true)
                }
            })
            adapter = devotionalsList.adapter as DevotionalAdapter
        }
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

    override fun loadDevotionalsAndAds(devotionals: ArrayList<Devotional>) {
        if (devotionals.isEmpty()) return
        isLoaded = true
        val size = adapter.itemCount
        (devotionalsList.adapter as DevotionalAdapter).addDevotionals(devotionals)
        addNativeExpressAds(size)
    }

    override fun reShowDevotionals(devotionals: ArrayList<Devotional>) {
        if (devotionals.isEmpty()) return
        isLoaded = true
        (devotionalsList.adapter as DevotionalAdapter).clearAndAddDevotionals(devotionals)
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            callback = context as OnDevotionalSelectedListener
        } catch (e: ClassCastException) {
            Throwable(ClassCastException(context.toString() + " must implement OnDevotionalSelectedListener"))
        }
    }

    override fun onPause() {
        super.onPause()
        presenter.onDetach()
    }
}