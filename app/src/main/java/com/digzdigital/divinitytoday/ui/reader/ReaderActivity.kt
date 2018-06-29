package com.digzdigital.divinitytoday.ui.reader

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import android.widget.Toast
import com.digzdigital.divinitytoday.DivinityTodayApp
import com.digzdigital.divinitytoday.R
import com.digzdigital.divinitytoday.data.model.Devotional
import com.digzdigital.divinitytoday.ui.reader.di.ReaderPresenterModule
import kotlinx.android.synthetic.main.activity_reader.*
import javax.inject.Inject


class ReaderActivity : AppCompatActivity(), ReaderContract.View {

    @Inject
    lateinit var presenter: ReaderPresenter
    private var isSaved = false

    companion object {
        const val DEVOTIONAL_ID = "devotional"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reader)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val devotionalId = intent.getStringExtra(DEVOTIONAL_ID)

        if (devotionalId.isNullOrEmpty()) {
            onBackPressed()
            return
        }

        (application as DivinityTodayApp)
                .appComponent
                .plus(ReaderPresenterModule(this))
                .inject(this)

        presenter.onAttach(this)
        presenter.loadDevotional(devotionalId)

        setClickListenerForAnimation()
    }

    private fun setClickListenerForAnimation() {
        val scaleAnimation = ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f)
        scaleAnimation.duration = 500
        val bounceInterpolator = BounceInterpolator()
        scaleAnimation.interpolator = bounceInterpolator
        readerSave.setOnCheckedChangeListener { buttonView, _ ->
            buttonView.startAnimation(scaleAnimation)
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showDevotional(devotional: Devotional) {
        readerTitle.text = devotional.title
        readerDate.text = devotional.date
        readerContent.text = devotional.content
        readerSave.isChecked = devotional.bookmarked
        isSaved = devotional.bookmarked
        readerSave.setOnClickListener {
            if (isSaved)
                presenter.removeDevotionalFromBookmarks(devotional)
            else
                presenter.bookmarkDevotional(devotional)
        }
    }

    override fun setDevotionalSavedState(isSaved: Boolean) {
        readerSave.isChecked = isSaved
        this.isSaved = isSaved
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}