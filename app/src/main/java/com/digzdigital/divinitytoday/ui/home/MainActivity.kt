package com.digzdigital.divinitytoday.ui.home

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.digzdigital.divinitytoday.R
import com.digzdigital.divinitytoday.commons.OnDevotionalSelectedListener
import com.digzdigital.divinitytoday.data.model.Devotional
import com.digzdigital.divinitytoday.ui.devlist.DevotionalsFragment
import com.digzdigital.divinitytoday.ui.reader.ReaderFragment
import com.digzdigital.divinitytoday.ui.saveddevlist.SavedDevotionalFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnDevotionalSelectedListener {

    lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        fragmentManager = supportFragmentManager

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        switchFragment(DevotionalsFragment())
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    fun switchFragment(fragment: Fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_online) {
            switchFragment(DevotionalsFragment())
        } else if (id == R.id.nav_saved) {
            switchFragment(SavedDevotionalFragment())
        }
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onDevotionalSelected(devotional: Devotional, isOnline:Boolean) {
//        Toast.makeText(this, "${devotional.title} is selected", Toast.LENGTH_SHORT).show()
         val fragment = ReaderFragment.newInstance(devotional, isOnline)
         fragmentManager.beginTransaction()
                 .replace(R.id.content_frame, fragment)
                 .addToBackStack(null)
                 .commit()
    }
}
