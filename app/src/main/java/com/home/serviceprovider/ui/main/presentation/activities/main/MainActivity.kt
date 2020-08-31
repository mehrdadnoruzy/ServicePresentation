package com.home.serviceprovider.ui.main.presentation.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.home.serviceprovider.R
import com.home.serviceprovider.ui.main.presentation.fragments.home.HomeFragment
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {
    val viewModel: MainViewModel by lazy { ViewModelProvider(this,
        MyViewModelFactory()
    ).get(MainViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        //loading_view.show()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container,
                        HomeFragment.newInstance()
                    )
                    .commitNow()
        }
    }
}