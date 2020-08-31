package com.home.serviceprovider.ui.main.presentation.fragments.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.home.serviceprovider.ui.main.presentation.activities.main.MainActivity
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.main_activity.*

abstract class BaseFragment: Fragment() { //<V:ViewModel>

    abstract fun getLayoutId(): Int
    //abstract fun getViewModel(): V
    //lateinit var mViewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //mViewModel = getViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }


}