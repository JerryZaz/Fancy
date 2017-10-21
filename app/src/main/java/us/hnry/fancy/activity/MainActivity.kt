package us.hnry.fancy.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import us.hnry.fancy.R
import us.hnry.fancy.fragments.MainFragment
import us.hnry.fancy.utils.Utility

/**
 * @author Henry
 * 10/20/2017
 */
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val drawer by lazy { findViewById<DrawerLayout>(R.id.drawer_layout) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val searchFab = findViewById<FloatingActionButton>(R.id.search_fab)
        searchFab?.setOnClickListener({
            startActivity(Intent(this, SearchActivity::class.java).putExtra(Utility.SEARCH_INTENT, Utility.SYMBOL_SEARCH))
        })

        val fragmentTag = "fragment_main_list"
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction().add(R.id.content_main_list_container, MainFragment(), fragmentTag).commit()
        }
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_search_symbol -> startActivity(Intent(this, SearchActivity::class.java).putExtra(Utility.SEARCH_INTENT, Utility.SYMBOL_SEARCH))
            R.id.nav_search_thor -> startActivity(Intent(this, SearchActivity::class.java).putExtra(Utility.SEARCH_INTENT, Utility.THOR_SEARCH))
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}