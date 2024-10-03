package com.example.trabajopractico1

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.trabajopractico1.databinding.ActivityHomeBinding
import com.example.trabajopractico1.fragments.FirstFragment
import com.example.trabajopractico1.fragments.SecondFragment
import com.example.trabajopractico1.fragments.ThirdFragment
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityHomeBinding

    private lateinit var drawerLayout: DrawerLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        drawerLayout = binding.drawerLayout

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_home_open, R.string.nav_drawer_home_close)
        drawerLayout.addDrawerListener(toggle)

        val preferences = getSharedPreferences(RegisterActivity.CREDENTIALS, MODE_PRIVATE)

        //binding.btnSingOut.setOnClickListener {
            //preferences.edit().clear().apply()
            //goToMainActivity()
        //}
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)


        binding.navigationView.setNavigationItemSelectedListener(this)


        if (savedInstanceState == null) {
            replaceFragment(FirstFragment())
            binding.navigationView.setCheckedItem(R.id.nav_item_one)
        }

    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_item_one -> {
                replaceFragment(FirstFragment())
            }

            R.id.nav_item_two -> {
                replaceFragment(SecondFragment())
            }

            R.id.nav_item_three -> {
                replaceFragment(ThirdFragment())
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            //finish()
            //super.onBackPressed()
            //onBackPressedDispatcher.onBackPressed()
        }
    }
}