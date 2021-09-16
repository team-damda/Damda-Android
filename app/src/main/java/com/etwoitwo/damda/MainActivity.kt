package com.etwoitwo.damda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
//    lateinit var mSocket: Socket
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(MainStockFragment())

        bottomNavigationView = findViewById(R.id.bottomNavBar)
        initNavigationBar()
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

    private fun initNavigationBar() {
        bottomNavigationView.setOnItemSelectedListener {
            return@setOnItemSelectedListener when (it.itemId) {
                R.id.item_bottomnav_main -> {
                    replaceFragment(MainStockFragment())
                    true
                }
                R.id.item_bottomnav_search -> {
                    replaceFragment(SearchFragment())
                    true
                }
                R.id.item_bottomnav_wallet -> {
                    replaceFragment(WalletFragment())
                    true
                }
                R.id.item_bottomnav_more -> {
                    replaceFragment(MoreFragment())
                    true
                }
                else -> false
            }
        }

    }


}