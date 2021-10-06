package com.etwoitwo.damda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.etwoitwo.damda.feature.more.MoreFragment
import com.etwoitwo.damda.feature.wallet.WalletFragment
import com.etwoitwo.damda.feature.search.SearchFragment
import com.etwoitwo.damda.feature.main.MainStockFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

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