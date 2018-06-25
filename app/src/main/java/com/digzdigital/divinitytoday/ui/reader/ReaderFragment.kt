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
import kotlinx.android.synthetic.main.activity_reader.*
import xyz.digzdigital.keddit.commons.extensions.inflate
import javax.inject.Inject


class ReaderFragment : Fragment(), ReaderContract.View {

    @Inject
    lateinit var presenter: ReaderPresenter
    var devotionalId = ""

    companion object {
        const val ARG_PARAM1 = "devotional"


        fun newInstance(devotionalId: String): Fragment {
            val fragment = ReaderFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, devotionalId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments == null) {

            return
        }

        (activity!!.application as DivinityTodayApp).appComponent.inject(this)

        devotionalId = arguments!!.getString(ARG_PARAM1)

        presenter.onAttach(this)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.activity_reader)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showDevotional(devotional: Devotional) {
        readerTitle.text = devotional.title
        readerDate.text = devotional.date
        readerContent.text = devotional.content
        readerSave.isChecked = devotional.bookmarked
        readerSave.setOnClickListener {
            if (readerSave.isChecked)
                presenter.removeDevotionalFromBookmarks(devotional)
            else
                presenter.bookmarkDevotional(devotional)
        }
    }

    override fun setDevotionalSavedState(isSaved: Boolean) {
        readerSave.isChecked = isSaved
    }
}