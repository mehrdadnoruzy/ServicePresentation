package com.home.servicepresentation.ui.main.presentation.activities.main

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.home.servicepresentation.R
import com.home.servicepresentation.ui.main.data.network.Network
import com.home.servicepresentation.ui.main.data.repository.Repository
import com.home.servicepresentation.ui.main.domain.DetailUsecase
import com.home.servicepresentation.ui.main.domain.HomeUsecase
import com.home.servicepresentation.ui.main.presentation.fragments.home.HomeFragment

class MainActivity : AppCompatActivity() {

/*    val viewModel: MyViewModel by lazy {
        ViewModelProvider(
            this,
            MyViewModelFactory()
        ).get(MyViewModel::class.java)
    }*/

    val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModelProviderFactory(MainViewModel(HomeUsecase(Repository(Network())), DetailUsecase(Repository(Network()))))
        ).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null)
            beginTransaction()
    }

    fun beginTransaction() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                HomeFragment.newInstance()
            )?.addToBackStack(HomeFragment.javaClass.name)
            .commit()
    }

    override fun onBackPressed() {
        val fm: FragmentManager = supportFragmentManager
        if (fm.getBackStackEntryCount() > 0) {
            if ((fm.findFragmentById(R.id.container) is HomeFragment)) showExitDialog()
            else fm.popBackStack()
        } else super.onBackPressed()
    }

    fun showExitDialog() {
        AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Exit")
            .setMessage("Are you sure you want to close this application?")
            .setPositiveButton("Yes") { dialog, which -> finish() }
            .setNegativeButton("No", null)
            .show()
    }
}