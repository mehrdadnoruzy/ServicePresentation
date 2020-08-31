package com.home.serviceprovider.ui.main.presentation.fragments.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import com.home.serviceprovider.R
import com.home.serviceprovider.ui.main.data.models.home.CategoriesItem
import com.home.serviceprovider.ui.main.data.models.home.HomeModel
import com.home.serviceprovider.ui.main.data.models.home.HomeObservable
import com.home.serviceprovider.ui.main.presentation.fragments.base.BaseFragment
import com.home.serviceprovider.ui.main.presentation.activities.main.MainActivity
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_middle.*
import java.util.*


class HomeFragment : BaseFragment(), Observer {

    override fun getLayoutId(): Int = R.layout.home_fragment
    private lateinit var adapter: HomeAdapter
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
        //recyclerViewService.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerViewService.setHasFixedSize(true)
        //PagerSnapHelper().attachToRecyclerView(recyclerViewService)
        adapter = HomeAdapter(arrayListOf())
        recyclerViewService.adapter = adapter
        LinearSnapHelper().attachToRecyclerView(recyclerViewService)
    }
    private fun setupPromotionSegment() {

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
        renderListOfPromotions()
        //val url = URL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464")
        //val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        //imageView.setImageBitmap(bmp)


    }

    private fun renderListOfServices(categories: ArrayList<CategoriesItem?>?) {
        adapter.addCategories(categories)
        adapter.notifyDataSetChanged()
    }

    private fun renderListOfPromotions() {

    }

    fun loadingViewShow(){
        loading_view.show()
    }
    fun loadingViewHide(){
        loading_view.hide()
    }
}