package com.digzdigital.divinitytoday.ui.reader

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.digzdigital.divinitytoday.DivinityTodayApp
import com.digzdigital.divinitytoday.R
import com.digzdigital.divinitytoday.data.model.Devotional
import com.digzdigital.divinitytoday.data.model.getCleanedContent
import com.digzdigital.divinitytoday.data.model.getCleanedTitle
import com.digzdigital.divinitytoday.data.model.getFormattedDate
import kotlinx.android.synthetic.main.activity_reader.*
import xyz.digzdigital.keddit.commons.extensions.inflate
import javax.inject.Inject


class ReaderFragment : Fragment(), ReaderContract.View {

    lateinit private var devotional: Devotional
    private var isOnline = false

    @Inject
    lateinit var presenter:ReaderContract.Presenter

    companion object Factory {
        val ARG_PARAM1 = "param1"
        val ARG_PARAM2 = "param2"

        fun newInstance(devotional: Devotional, isOnline: Boolean): Fragment {
            val fragment = ReaderFragment()
            val args = Bundle()
            args.putParcelable(ARG_PARAM1, devotional)
            args.putBoolean(ARG_PARAM2, isOnline)
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            devotional = arguments.getParcelable(ARG_PARAM1)
            isOnline = arguments.getBoolean(ARG_PARAM2)
        }
        (activity.application as DivinityTodayApp).appComponent.inject(this)
        presenter.onAttach(this)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.activity_reader)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        readerTitle.text = devotional.getCleanedTitle()
        readerDate.text = devotional.getFormattedDate()
        readerContent.text = devotional.getCleanedContent()
        if(!isOnline){
            readerSave.visibility = View.GONE
            return
        }
        readerSave.setOnClickListener { presenter.saveDevotional(devotional) }
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun disableSaveButton() {
        readerSave.visibility = View.GONE
    }
}