package com.digzdigital.divinitytoday.ui.devlist

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
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
import com.digzdigital.divinitytoday.data.model.DevotionalWrapper
import com.digzdigital.divinitytoday.ui.devlist.adapter.DevotionalAdapter
import kotlinx.android.synthetic.main.fragment_devotionals.*
import xyz.digzdigital.keddit.commons.extensions.inflate
import javax.inject.Inject

class DevotionalsFragment : Fragment(), DevListContract.View {



    @Inject
    lateinit var presenter: DevListContract.Presenter
    lateinit var progressDialog: ProgressDialog
    private var isLoaded = false
    lateinit var callback: OnDevotionalSelectedListener

    companion object {
        private val KEY_DIVINITY = "divinity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity.application as DivinityTodayApp).appComponent.inject(this)

        presenter.onAttach(this)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
        initSwipeToRefresh()
        if (isLoaded) {
            presenter.showDevotionals()
            return
        }
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_DIVINITY)) {
            val devotional = savedInstanceState.get(KEY_DIVINITY) as DevotionalWrapper
            (devotionalsList.adapter as DevotionalAdapter).clearAndAddDevotionals(devotional.devotionals)
            return
        }
        showProgressDialog()
        presenter.loadDevotionals()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val devotionals = (devotionalsList.adapter as DevotionalAdapter).getDevotionals()
        val devotional = DevotionalWrapper(devotionals)
        if (!devotionals.isEmpty()) outState.putParcelable(KEY_DIVINITY, devotional.copy(devotionals = devotionals))
    }

    private fun initSwipeToRefresh() {
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeContainer.setOnRefreshListener { presenter.reloadDevotionals() }
    }

    override fun dismissSwipeRefresher() {
        if (swipeContainer.isRefreshing) swipeContainer.isRefreshing = false;
    }

    private fun initAdapter() {
        if (devotionalsList.adapter == null) {
            devotionalsList.adapter = DevotionalAdapter(object : MyClickListener {
                override fun onItemClick(devotional: Devotional) {
                    callback.onDevotionalSelected(devotional, true)
                }
            })
        }
    }

    override fun showDevotionals(devotionals: ArrayList<Devotional>) {
        if (devotionals.isEmpty()) return
        isLoaded = true
        (devotionalsList.adapter as DevotionalAdapter).addDevotionals(devotionals)
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

