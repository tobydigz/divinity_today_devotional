package com.digzdigital.divinitytoday.ui.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import com.digzdigital.divinitytoday.DivinityTodayApp
import com.digzdigital.divinitytoday.R
import com.digzdigital.divinitytoday.data.db.MigrationToRealm
import com.digzdigital.divinitytoday.data.session.SessionManager
import com.digzdigital.divinitytoday.ui.home.MainActivity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()
    @Inject
    lateinit var sessionManager: SessionManager
    @Inject
    lateinit var migration: MigrationToRealm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        (application as DivinityTodayApp).appComponent.inject(this)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val animation = AnimationUtils.loadAnimation(this, R.anim.transition)
        splash_image.startAnimation(animation)
    }

    override fun onResume() {
        super.onResume()
        val isRealmInUse = sessionManager.shouldDoMigration()
        if (isRealmInUse) doCountdown()
        else doMigration()
    }

    private fun doCountdown() {
        val disposable = Observable.just(1)
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeBy(
                        onComplete = {
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)
                            finish()
                        })
        compositeDisposable.add(disposable)
    }


    private fun doMigration() {
        val disposable = migration.doMigration()
                .doOnSubscribe {
                    splash_image.visibility = View.GONE
                    operation_layout.visibility = View.VISIBLE
                }
                .subscribeBy(
                        onSuccess = {
                            sessionManager.setMigrationAsDone()
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)
                            finish()
                        },
                        onError = {
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)
                            finish()
                        })
        compositeDisposable.add(disposable)
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }
}
