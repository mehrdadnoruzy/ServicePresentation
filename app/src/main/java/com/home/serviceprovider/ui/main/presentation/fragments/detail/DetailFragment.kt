package com.home.serviceprovider.ui.main.presentation.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.home.serviceprovider.R
import com.home.serviceprovider.ui.main.presentation.fragments.base.BaseFragment
import com.home.serviceprovider.ui.main.presentation.activities.main.MainActivity
import java.util.*

class DetailFragment : BaseFragment(), Observer {

    override fun getLayoutId(): Int = R.layout.carwash_fragment
    //override fun getViewModel(): MainViewModel = (activity as? MainActivity)?.viewModel!!

    companion object {
        fun newInstance() =
            DetailFragment()
    }

    //private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.carwash_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).viewModel.getCarwashData(requireContext())
    }

    override fun update(o: Observable?, arg: Any?) {

    }


}