package com.digzdigital.divinitytoday.ui.saveddevlist

import android.app.ProgressDialog
import android.content.Context
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
import com.digzdigital.divinitytoday.commons.OnDevotionalSelectedListener
import com.digzdigital.divinitytoday.commons.SavedDevotionalClickListener
import com.digzdigital.divinitytoday.data.model.Devotional
import com.digzdigital.divinitytoday.data.model.DevotionalWrapper
import com.digzdigital.divinitytoday.ui.devlist.adapter.DevotionalAdapter
import com.digzdigital.divinitytoday.ui.saveddevlist.adapter.SavedDevotionalAdapter
import kotlinx.android.synthetic.main.fragment_devotionals.*
import xyz.digzdigital.keddit.commons.extensions.inflate
import javax.inject.Inject

class SavedDevotionalFragment : Fragment(), SavedDevotionalContract.View {

    @Inject
    lateinit var presenter: SavedDevotionalContract.Presenter
    lateinit var progressDialog: ProgressDialog
    lateinit var callback: OnDevotionalSelectedListener
    private var isLoaded = false

    companion object {
        private val KEY_DIVINITY = "divinity_saved"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_devotionals)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity.application as DivinityTodayApp).appComponent.inject(this)

        presenter.onAttach(this)
        devotionalsList.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
        }
        initAdapter()
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_DIVINITY)) {
            val devotional = savedInstanceState.get(KEY_DIVINITY) as DevotionalWrapper
            (devotionalsList.adapter as DevotionalAdapter).clearAndAddDevotionals(devotional.devotionals)
            return
        }
        if (isLoaded) return
        showProgressDialog()
        presenter.loadDevotionals()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val devotionals = (devotionalsList.adapter as DevotionalAdapter).getDevotionals()
        val devotional = DevotionalWrapper(devotionals)
        if (!devotionals.isEmpty()) outState.putParcelable(KEY_DIVINITY, devotional.copy(devotionals = devotionals))
    }

    private fun initAdapter() {
        if (devotionalsList.adapter == null) {
            devotionalsList.adapter = SavedDevotionalAdapter(object : SavedDevotionalClickListener {
                override fun onDeleteClicked(devotional: Devotional) {
                    showAlertDialog(devotional)
                }

                override fun onItemClick(devotional: Devotional) {
                    callback.onDevotionalSelected(devotional, false)
                }
            })
        }
    }

    private fun showAlertDialog(devotional: Devotional) {
        val title = "Delete Devotional?"
        val accept = "Yes"
        val reject = "Cancel"
        val ad = AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage("Are you sure you want to delete this devotional")
                .setPositiveButton(accept) { _, _ -> presenter.deleteDevotional(devotional) }
                .setNegativeButton(reject) { _, _ -> }
        val alert = ad.create()
        alert.show()
    }

    override fun showDevotionals(devotionals: ArrayList<Devotional>) {
        if (devotionals.isEmpty()) return
        isLoaded = true
        (devotionalsList.adapter as SavedDevotionalAdapter).addDevotionals(devotionals)
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

    override fun notifyItemChanged(position: Int) {
        (devotionalsList.adapter as SavedDevotionalAdapter).removeDevotional(position)
    }
}