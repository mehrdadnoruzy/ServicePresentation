package com.home.servicepresentation.ui.main.presentation.fragments.error

import android.os.Bundle
import android.view.View
import com.home.servicepresentation.R
import com.home.servicepresentation.ui.main.presentation.fragments.base.BaseFragment
import com.home.servicepresentation.ui.main.presentation.fragments.home.HomeFragment
import kotlinx.android.synthetic.main.error_fragment.*

class ErrorFragment : BaseFragment(){

    override fun getLayoutId(): Int = R.layout.error_fragment
    companion object {
        fun newInstance() =
            ErrorFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_try_again.setOnClickListener { beginTransaction() }
    }

    private fun beginTransaction(){
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container,
                HomeFragment.newInstance()
            )?.addToBackStack(HomeFragment.javaClass.name)
            ?.commit()
    }
}