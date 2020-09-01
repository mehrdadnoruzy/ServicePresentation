package com.home.servicepresentation.ui.main.presentation.fragments.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearSnapHelper
import com.home.servicepresentation.R
import com.home.servicepresentation.ui.main.data.models.home.CategoriesItem
import com.home.servicepresentation.ui.main.data.models.home.HomeModel
import com.home.servicepresentation.ui.main.data.models.home.HomeObservable
import com.home.servicepresentation.ui.main.data.models.home.PromotionsItem
import com.home.servicepresentation.ui.main.presentation.fragments.base.BaseFragment
import com.home.servicepresentation.ui.main.presentation.activities.main.MainActivity
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_middle.*
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : BaseFragment(), Observer, AdapterListener {

    override fun getLayoutId(): Int = R.layout.home_fragment
    private lateinit var adapterService: ServiceAdapter
    private lateinit var adapterPromotion: PromotionAdapter
    companion object {
        fun newInstance() =
            HomeFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingViewShow()
        setupServiceSegment()
        setupPromotionSegment()
        (activity as MainActivity).viewModel.homeObservable.addObserver(this)
        (activity as MainActivity).viewModel.getHomeData(requireContext())
    }

    private fun setupServiceSegment() {
        recyclerViewService.setHasFixedSize(true)
        adapterService = ServiceAdapter(arrayListOf(), this)
        recyclerViewService.adapter = adapterService
        LinearSnapHelper().attachToRecyclerView(recyclerViewService)
    }
    private fun setupPromotionSegment() {
        recyclerViewPromotion.setHasFixedSize(true)
        adapterPromotion = PromotionAdapter(arrayListOf(), this)
        recyclerViewPromotion.adapter = adapterPromotion
        LinearSnapHelper().attachToRecyclerView(recyclerViewPromotion)
    }

    override fun update(o: Observable?, arg: Any?) {
        when(o){
            is HomeObservable -> {
                if(arg is HomeModel){
                    updateUI(arg)
                }
            }
        }
    }

    fun updateUI(homeModel: HomeModel){
        loadingViewHide()
        tv_title.text = homeModel.title
        tv_subtitle.text = homeModel.subTitle
        renderListOfServices(homeModel.categories)
        renderListOfPromotions(homeModel.promotions)
    }

    private fun renderListOfServices(categories: ArrayList<CategoriesItem?>?) {
        adapterService.addCategories(categories)
        adapterService.notifyDataSetChanged()
    }

    private fun renderListOfPromotions(promotions: ArrayList<PromotionsItem?>?) {
        adapterPromotion.addPromotions(promotions)
        adapterPromotion.notifyDataSetChanged()
    }

    fun loadingViewShow(){
        loading_view.show()
    }
    fun loadingViewHide(){
        loading_view.hide()
    }

    override fun showMessage(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}