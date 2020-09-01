package com.home.servicepresentation.ui.main.presentation.fragments.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearSnapHelper
import com.home.servicepresentation.R
import com.home.servicepresentation.ui.main.data.models.base.BaseModel
import com.home.servicepresentation.ui.main.data.models.detail.DetailObservable
import com.home.servicepresentation.ui.main.data.models.home.CategoriesItem
import com.home.servicepresentation.ui.main.data.models.home.HomeModel
import com.home.servicepresentation.ui.main.data.models.home.HomeObservable
import com.home.servicepresentation.ui.main.data.models.home.PromotionsItem
import com.home.servicepresentation.ui.main.presentation.activities.main.MainActivity
import com.home.servicepresentation.ui.main.presentation.fragments.base.BaseFragment
import com.home.servicepresentation.ui.main.presentation.fragments.base.MessagesListener
import com.home.servicepresentation.ui.main.presentation.fragments.detail.DetailFragment
import com.home.servicepresentation.ui.main.presentation.fragments.error.ErrorFragment
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_middle.*
import java.util.*

class HomeFragment : BaseFragment(), Observer,
    HomeAdapterItemClickListener,
    MessagesListener {

    override fun getLayoutId(): Int = R.layout.home_fragment
    private lateinit var adapterService: ServiceAdapter
    private lateinit var adapterPromotion: PromotionAdapter

    companion object {
        fun newInstance() =
            HomeFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            loadingViewShow()
            setupServiceSegment()
            setupPromotionSegment()
            (activity as MainActivity).viewModel.baseObservable.addObserver(this)
            (activity as MainActivity).viewModel.homeObservable.addObserver(this)
            (activity as MainActivity).viewModel.getHomeData(requireContext())
        }
    }

    private fun setupServiceSegment() {
        recyclerViewService.setHasFixedSize(true)
        adapterService = ServiceAdapter(arrayListOf(), this, this)
        recyclerViewService.adapter = adapterService
        LinearSnapHelper().attachToRecyclerView(recyclerViewService)
    }

    private fun setupPromotionSegment() {
        recyclerViewPromotion.setHasFixedSize(true)
        adapterPromotion = PromotionAdapter(arrayListOf(), this, this)
        recyclerViewPromotion.adapter = adapterPromotion
        LinearSnapHelper().attachToRecyclerView(recyclerViewPromotion)
    }

    override fun update(o: Observable?, arg: Any?) {
        when (o) {
            is HomeObservable -> updateUI(arg as HomeModel)
            is DetailObservable -> {
            }
            else -> showProblem(arg as BaseModel<*>)
        }
    }

    fun updateUI(homeModel: HomeModel) {
        if (isAdded) {
            loadingViewHide()
            tv_title.text = homeModel.title
            tv_subtitle.text = homeModel.subTitle
            renderListOfServices(homeModel.categories)
            renderListOfPromotions(homeModel.promotions)
        }
    }

    fun showProblem(problemModel: BaseModel<*>) {
        if (isAdded) {
            loadingViewHide()
            var problem = StringBuilder()
            if (problemModel.code != -1) problem.append(problemModel.code.toString() + ":")
            if (problemModel.msg != "") problem.append(problemModel.msg)
            Toast.makeText(requireContext(), problem, Toast.LENGTH_LONG).show()
            beginTransaction()
        }
    }

    fun beginTransaction() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(
                R.id.container,
                ErrorFragment.newInstance()
            )?.addToBackStack(ErrorFragment.javaClass.name)
            ?.commit()
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
        if (isAdded)
            loading_view.show()
    }

    private fun loadingViewHide() {
        if (isAdded)
            loading_view.hide()
    }

    override fun showMessage(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun itemServiceClicked() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(
                R.id.container,
                DetailFragment.newInstance()
            )?.addToBackStack(DetailFragment.javaClass.name)
            ?.commit()
    }

    override fun itemPromotionClicked() {

    }
}