package com.digzdigital.divinitytoday.ui.devlist

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.digzdigital.divinitytoday.R
import com.digzdigital.divinitytoday.commons.InfiniteScrollListener
import com.digzdigital.divinitytoday.data.model.Devotional
import com.digzdigital.divinitytoday.ui.devlist.adapter.DevotionalAdapter
import com.digzdigital.divinitytoday.ui.devlist.adapter.MyClickListener
import kotlinx.android.synthetic.main.fragment_devotionals.*
import xyz.digzdigital.keddit.commons.extensions.inflate

class DevotionalsFragment : Fragment(), DevListContract.View {


    lateinit var presenter: DevListContract.Presenter
    lateinit var progressDialog: ProgressDialog

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
    }

    private fun initAdapter() {

        if (devotionalsList.adapter == null) {
            devotionalsList.adapter = DevotionalAdapter(object : MyClickListener {
                override fun onItemClick(devotional: Devotional) {
                }
            })

        }

    }

    fun openReader(devotional: Devotional) {

    }

    override fun showDevotionals(devotionals: ArrayList<Devotional>) {
        if (devotionals.isEmpty()) return
        (devotionalsList.adapter as DevotionalAdapter).addDevotionals(devotionals)
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


}

