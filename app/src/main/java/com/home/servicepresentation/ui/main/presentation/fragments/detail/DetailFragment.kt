package com.home.servicepresentation.ui.main.presentation.fragments.detail

import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.home.servicepresentation.R
import com.home.servicepresentation.ui.main.data.models.base.BaseModel
import com.home.servicepresentation.ui.main.data.models.detail.DataItem
import com.home.servicepresentation.ui.main.data.models.detail.DetailModel
import com.home.servicepresentation.ui.main.presentation.activities.main.MainActivity
import com.home.servicepresentation.ui.main.presentation.fragments.base.BaseFragment
import com.home.servicepresentation.ui.main.utils.imageDownloadTask
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.android.synthetic.main.detail_fragment.include_problem
import kotlinx.android.synthetic.main.detail_fragment.loading_view
import kotlinx.android.synthetic.main.detail_fragment.main
import kotlinx.android.synthetic.main.problem.*
import java.util.*

class DetailFragment : BaseFragment() {
    var liveDataIMG: MutableLiveData<Bitmap> = MutableLiveData()
    var liveDataMSG: MutableLiveData<String> = MutableLiveData()
    private var task: AsyncTask<*, *, *>? = null
    override fun getLayoutId(): Int = R.layout.detail_fragment
    private lateinit var adapterGrid: DetailAdapter

    companion object {
        fun newInstance() =
            DetailFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try_again.setOnClickListener {
            include_problem.visibility = View.GONE
            callApi()
        }
        if (savedInstanceState == null) {
            setupGridSegment()
            liveDataMSG.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            })
            (activity as MainActivity).viewModel.liveDataDetail.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                loadingViewHide()
                if (it?.code == "200")
                    updateUI(it.data!!)
                else analyzeProblem(it)
            })
            callApi()
        }
    }

    private fun callApi(){
        if ((activity as MainActivity).viewModel.liveDataDetail.value?.data == null) {
            loadingViewShow()
            (activity as MainActivity).viewModel.getDetailData(requireContext())
        }
        else {
            analyzeProblem((activity as MainActivity).viewModel.liveDataDetail.value)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        back.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    private fun setupGridSegment() {
        recyclerViewGrid.setHasFixedSize(true)
        adapterGrid = DetailAdapter(arrayListOf())
        recyclerViewGrid.adapter = adapterGrid
        adapterGrid.liveDataMSG.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }

    fun updateUI(detailModel: DetailModel) {
        if (this.isAdded) {
            imageDownloadTask(image, detailModel?.image?.originalUrl4x, liveDataMSG, liveDataIMG)
            title.text = detailModel.title
            slogan.text = detailModel.slogan
            description.text = detailModel.description
            renderListOfGrid(detailModel.data)
            main.visibility=View.VISIBLE
        }
    }

    fun analyzeProblem(problem: BaseModel<DetailModel>?) {
        main.visibility=View.GONE
        when(problem?.code){
            "200" -> {}//updateUI(problem.data!!)
            "403" -> {
                Toast.makeText(requireContext(), problem.msg, Toast.LENGTH_SHORT).show()
                include_problem.visibility = View.VISIBLE
            }
            "600" -> {
                Toast.makeText(requireContext(), problem.msg, Toast.LENGTH_SHORT).show()
                include_problem.visibility = View.VISIBLE
            }
            else -> Toast.makeText(requireContext(), problem?.code+"-"+problem?.msg, Toast.LENGTH_LONG).show()
        }
    }

    private fun renderListOfGrid(data: ArrayList<DataItem?>?) {
        adapterGrid.addData(data)
        adapterGrid.notifyDataSetChanged()
    }

    private fun loadingViewShow() {
        if (isAdded) {
            main.visibility=View.GONE
            loading_view.show()
        }
    }

    private fun loadingViewHide() {
        if (isAdded) {
            main.visibility=View.VISIBLE
            loading_view.hide()
        }
    }
}