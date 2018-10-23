package com.saladinid.blonjoan.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.saladinid.blonjoan.R
import com.saladinid.blonjoan.fragment.*
import com.saladinid.blonjoan.other.CircleTransform

class MainActivity: AppCompatActivity() {

    private
    var navigationView: NavigationView ? = null
    private
    var drawer: DrawerLayout ? = null
    private
    var navHeader: View ? = null
    private
    var imgNavHeaderBg: ImageView ? = null
    private
    var imgProfile: ImageView ? = null
    private
    var txtName: TextView ? = null
    private
    var txtWebsite: TextView ? = null
    private
    var toolbar: Toolbar ? = null
    private
    var fab: FloatingActionButton ? = null

    // toolbar titles respected to selected nav menu item
    private
    var activityTitles: Array < String > ? = null

    // flag to load home fragment when user presses back key
    private val shouldLoadHomeFragOnBackPress = true
    private
    var mHandler: Handler ? = null

    val homeFragment: Fragment
        get() {
            when(navItemIndex) {
                1 -> {
                    return HomeFragment()
                }
                0 -> {
                    return PhotosFragment()
                }
                2 -> {
                    val moviesFragment = MoviesFragment()
                    return NotificationsFragment()
                }
                3 -> {
                    return NotificationsFragment()
                }

                4 -> {
                    return SettingsFragment()
                }
                else ->
                    return HomeFragment()
            }
        }


    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById < View > (R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        mHandler = Handler()

        drawer = findViewById < View > (R.id.drawer_layout) as DrawerLayout
        navigationView = findViewById < View > (R.id.nav_view) as NavigationView
        fab = findViewById < View > (R.id.fab) as FloatingActionButton

        // Navigation view header
        navHeader = navigationView!!.getHeaderView(0)
        txtName = navHeader!!.findViewById < View > (R.id.name) as TextView
        txtWebsite = navHeader!!.findViewById < View > (R.id.website) as TextView
        imgNavHeaderBg = navHeader!!.findViewById < View > (R.id.img_header_bg) as ImageView
        imgProfile = navHeader!!.findViewById < View > (R.id.img_profile) as ImageView

        // load toolbar titles from string resources
        activityTitles = resources.getStringArray(R.array.nav_item_activity_titles)

        fab!!.setOnClickListener {
            view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        // load nav menu header data
        loadNavHeader()

        // initializing navigation menu
        setUpNavigationView()

        if (savedInstanceState == null) {
            navItemIndex = 0
            CURRENT_TAG = TAG_HOME
            loadHomeFragment()
        }
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private fun loadNavHeader() {
        // name, website
        txtName!!.text = "Ravi Tamada"
        txtWebsite!!.text = "www.androidhive.info"

        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg!!)

        // Loading profile image
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile!!)

        // showing dot next to notifications label
        // navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private fun loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu()

        // set toolbar title
        setToolbarTitle()

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (supportFragmentManager.findFragmentByTag(CURRENT_TAG) != null) {
            drawer!!.closeDrawers()

            // show or hide the fab button
            toggleFab()
            return
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        val mPendingRunnable = Runnable {
            // update the main content by replacing fragments
            val fragment = homeFragment
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out)
            fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG)
            fragmentTransaction.commitAllowingStateLoss()
        }

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler!!.post(mPendingRunnable)
        }

        // show or hide the fab button
        toggleFab()

        //Closing drawer on item click
        drawer!!.closeDrawers()

        // refresh toolbar menu
        invalidateOptionsMenu()
    }

    private fun setToolbarTitle() {
        supportActionBar!!.title = activityTitles!![navItemIndex]
    }

    private fun selectNavMenu() {
        navigationView!!.menu.getItem(navItemIndex).isChecked = true
    }

    private fun setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView!!.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener {
            menuItem ->
            // This method will trigger on item Click of navigation menu
            // Check to see which item was being clicked and perform appropriate action
            when(menuItem.itemId) {
                // Replacing the main content with ContentFragment Which is our Inbox View;
                R.id.nav_home -> {
                    navItemIndex = 0
                    CURRENT_TAG = TAG_HOME
                }
                R.id.nav_photos -> {
                    navItemIndex = 1
                    CURRENT_TAG = TAG_PHOTOS
                }
                R.id.nav_about_us -> {
                    // launch new intent instead of loading fragment
                    startActivity(Intent(this@MainActivity, AboutUsActivity::class.java))
                    drawer!!.closeDrawers()
                    return@OnNavigationItemSelectedListener true
                }
                else -> navItemIndex = 0
            }

            //Checking if the item is in checked state or not, if not make it in checked state
            if (menuItem.isChecked) {
                menuItem.isChecked = false
            } else {
                menuItem.isChecked = true
            }
            menuItem.isChecked = true

            loadHomeFragment()

            true
        })


        val actionBarDrawerToggle = object: ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            override fun onDrawerClosed(drawerView: View) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView)
            }

            override fun onDrawerOpened(drawerView: View) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView)
            }
        }

        //Setting the actionbarToggle to drawer layout
        drawer!!.setDrawerListener(actionBarDrawerToggle)

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState()
    }

    override fun onBackPressed() {
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer!!.closeDrawers()
            return
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0
                CURRENT_TAG = TAG_HOME
                loadHomeFragment()
                return
            }
        }

        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        if (navItemIndex == 1) {
            menuInflater.inflate(R.menu.main, menu)
        }

        // when fragment is notifications, load the menu created for notifications
        if (navItemIndex == 3) {
            menuInflater.inflate(R.menu.notifications, menu)
        }
        return true
    }

    @SuppressLint("ResourceType")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_logout) {
            Toast.makeText(applicationContext, "Logout user!", Toast.LENGTH_LONG).show()
            return true
        } else if (id == R.id.action_category) {
            val ft = supportFragmentManager.beginTransaction()
            val dialogFragment = MoviesFragment()
            dialogFragment.show(ft, "dialog")
            return true
        }

        // user is in notifications fragment
        // and selected 'Mark all as Read'
        if (id == R.id.action_mark_all_read) {
            Toast.makeText(applicationContext, "All notifications marked as read!", Toast.LENGTH_LONG).show()
        }

        // user is in notifications fragment
        // and selected 'Clear All'
        if (id == R.id.action_clear_notifications) {
            Toast.makeText(applicationContext, "Clear all notifications!", Toast.LENGTH_LONG).show()
        }

        return super.onOptionsItemSelected(item)
    }

    // show or hide the fab
    private fun toggleFab() {
        if (navItemIndex == 0)
            fab!!.show()
        else
            fab!!.hide()
    }

    companion object {

        // urls to load navigation header background image
        // and profile image
        // private static final String urlNavHeaderBg = "https://api.androidhive.info/images/nav-menu-header-bg.jpg";
        // private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg";
        private val urlNavHeaderBg = "https://api.androidhive.info/images/nav-menu-header-bg.jpg"
        private val urlProfileImg = "https://scontent-sit4-1.cdninstagram.com/vp/dffa36387e0f8d64358ad4daab601ec2/5C15355E/t51.2885-15/sh0.08/e35/p640x640/37166642_971266433056684_3263580605522116608_n.jpg"

        // index to identify current nav menu item
        var navItemIndex = 0

        // tags used to attach the fragments
        private val TAG_HOME = "home"
        private val TAG_PHOTOS = "photos"
        private val TAG_MOVIES = "movies"
        private val TAG_NOTIFICATIONS = "notifications"
        private val TAG_SETTINGS = "settings"
        var CURRENT_TAG = TAG_HOME
    }

}
