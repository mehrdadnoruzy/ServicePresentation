package com.home.servicepresentation.ui.main.presentation.fragments.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearSnapHelper
import com.home.servicepresentation.R
import com.home.servicepresentation.data.models.base.BaseModel
import com.home.servicepresentation.data.models.home.CategoriesItem
import com.home.servicepresentation.data.models.home.HomeModel
import com.home.servicepresentation.data.models.home.PromotionsItem
import com.home.servicepresentation.ui.main.presentation.activities.main.MainActivity
import com.home.servicepresentation.ui.main.presentation.fragments.base.BaseFragment
import com.home.servicepresentation.ui.main.presentation.fragments.detail.DetailFragment
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_middle.*
import kotlinx.android.synthetic.main.problem.*
import java.util.*

class HomeFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.home_fragment
    private lateinit var adapterService: ServiceAdapter
    private lateinit var adapterPromotion: PromotionAdapter

    companion object {
        fun newInstance() =
            HomeFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try_again.setOnClickListener {
            include_problem.visibility = View.GONE
            callApi()
        }
        if (savedInstanceState == null) {
            setupServiceSegment()
            setupPromotionSegment()
            (activity as MainActivity).viewModel.liveDataHome.observe(viewLifecycleOwner, {
                loadingViewHide()
                if (it?.code == "200")
                    updateUI(it.data!!)
                else analyzeProblem(it)
            })
            callApi()
        }
    }

    private fun callApi() {
        if ((activity as MainActivity).viewModel.liveDataHome.value?.data == null) {
            loadingViewShow()
            (activity as MainActivity).viewModel.getHomeData(requireContext())
        } else {
            analyzeProblem((activity as MainActivity).viewModel.liveDataHome.value)
        }
    }

    private fun setupServiceSegment() {
        recyclerViewService.setHasFixedSize(true)
        adapterService = ServiceAdapter(arrayListOf())
        recyclerViewService.adapter = adapterService
        LinearSnapHelper().attachToRecyclerView(recyclerViewService)
        adapterService.liveDataMSG.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
        adapterService.liveDataClicked.observe(viewLifecycleOwner, {
            itemServiceClicked()
        })
    }

    private fun setupPromotionSegment() {
        recyclerViewPromotion.setHasFixedSize(true)
        adapterPromotion = PromotionAdapter(arrayListOf())
        recyclerViewPromotion.adapter = adapterPromotion
        LinearSnapHelper().attachToRecyclerView(recyclerViewPromotion)
        adapterPromotion.liveDataMSG.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun updateUI(homeModel: HomeModel) {
        if (isAdded) {
            tv_title.text = homeModel.title
            tv_subtitle.text = homeModel.subTitle
            renderListOfServices(homeModel.categories)
            renderListOfPromotions(homeModel.promotions)
            include_middle.visibility = View.VISIBLE
        }
    }

    private fun analyzeProblem(problem: BaseModel<HomeModel>?) {
        include_middle.visibility = View.GONE
        when (problem?.code) {
            "200" -> {
            }
            else -> {
                Toast.makeText(
                    requireContext(),
                    problem?.code + "-" + problem?.msg,
                    Toast.LENGTH_LONG
                ).show()
                include_problem.visibility = View.VISIBLE
            }
        }
    }

    private fun renderListOfServices(categories: ArrayList<CategoriesItem?>?) {
        adapterService.addCategories(categories)
        adapterService.notifyDataSetChanged()
    }

    private fun renderListOfPromotions(promotions: ArrayList<PromotionsItem?>?) {
        adapterPromotion.addPromotions(promotions)
        adapterPromotion.notifyDataSetChanged()
    }

    private fun loadingViewShow() {
        if (isAdded) {
            include_middle.visibility = View.GONE
            loading_view.show()
        }
    }

    private fun loadingViewHide() {
        if (isAdded) {
            include_middle.visibility = View.VISIBLE
            loading_view.hide()
        }
    }

    private fun itemServiceClicked() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(
                R.id.container,
                DetailFragment.newInstance()
            )?.addToBackStack(DetailFragment.javaClass.name)
            ?.commit()
    }
}