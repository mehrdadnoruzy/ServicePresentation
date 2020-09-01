package com.home.servicepresentation.ui.main.presentation.activities.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.home.servicepresentation.R
import com.home.servicepresentation.ui.main.presentation.fragments.home.HomeFragment

class MainActivity : AppCompatActivity() {
    val viewModel: MainViewModel by lazy { ViewModelProvider(this,
        MyViewModelFactory()
    ).get(MainViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container,
                        HomeFragment.newInstance()
                    )?.addToBackStack(HomeFragment.javaClass.name)
                    .commit()
        }
    }

    override fun onBackPressed() {
        val fm: FragmentManager = supportFragmentManager
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack")
            fm.popBackStack()
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super")
            super.onBackPressed()
        }
    }
}