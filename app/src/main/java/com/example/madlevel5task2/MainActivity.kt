package com.example.madlevel5task2

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var deleteButton: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        navController = findNavController(R.id.nav_host_fragment)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (menu != null) {
            deleteButton = menu.findItem(R.id.btn_nav_icon_delete)
            deleteButton.isVisible = true
        }
        toggleNavIcon()
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.btn_nav_icon_delete -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportActionBar?.setHomeButtonEnabled(false)
            supportFragmentManager.popBackStack()
        }
        return true
    }

    private fun toggleNavIcon() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in arrayOf(R.id.AddGameFragment)) { // history fragment
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setTitle(R.string.AddGame)
                deleteButton.isVisible = false
            } else { // home fragment
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.setTitle(R.string.GameBacklog)
            }
        }
    }
}