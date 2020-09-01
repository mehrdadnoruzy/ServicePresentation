package com.home.servicepresentation.ui.main.presentation.fragments.detail

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.home.servicepresentation.R
import com.home.servicepresentation.ui.main.data.models.detail.DataItem
import com.home.servicepresentation.ui.main.data.models.detail.DetailModel
import com.home.servicepresentation.ui.main.data.models.detail.DetailObservable
import com.home.servicepresentation.ui.main.presentation.activities.main.MainActivity
import com.home.servicepresentation.ui.main.presentation.fragments.base.BaseFragment
import com.home.servicepresentation.ui.main.presentation.fragments.base.MessagesListener
import com.home.servicepresentation.ui.main.utils.DownloadImageTask
import kotlinx.android.synthetic.main.detail_fragment.*
import java.util.*
import java.util.concurrent.TimeUnit


class DetailFragment : BaseFragment(), Observer,
    GridAdapterItemClickListener,
    MessagesListener {

    private lateinit var task: AsyncTask<*,*,*>
    override fun getLayoutId(): Int = R.layout.detail_fragment
    private lateinit var adapterGrid: GridAdapter
    companion object {
        fun newInstance() =
            DetailFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            //loadingViewShow()
            setupGridSegment()
            (activity as MainActivity).viewModel.detailObservable.addObserver(this)
            (activity as MainActivity).viewModel.getDetailData(requireContext())
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
        //GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        //recyclerViewGrid.setLayoutManager(GridLayoutManager(requireContext(), 2))
        adapterGrid = GridAdapter(arrayListOf(), this, this)
        recyclerViewGrid.adapter = adapterGrid
        //LinearSnapHelper().attachToRecyclerView(recyclerViewGrid)
    }

    override fun update(o: Observable?, arg: Any?) {
        when(o){
            is DetailObservable -> {
                if(arg is DetailModel){
                    updateUI(arg)
                }
            }
        }
    }

    fun updateUI(detailModel: DetailModel){
        //loadingViewHide()
        if (this.isAdded) {
            task = DownloadImageTask(image, this).execute(detailModel?.image?.originalUrl4x)
            task.get(3000, TimeUnit.MILLISECONDS)
            title.text = detailModel.title
            slogan.text = detailModel.slogan
            description.text = detailModel.description
            renderListOfGrid(detailModel.data)
        }
    }

    private fun renderListOfGrid(data: ArrayList<DataItem?>?) {
        adapterGrid.addData(data)
        adapterGrid.notifyDataSetChanged()
    }

    override fun showMessage(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun itemGridClicked() {
    }

    fun loadingViewShow(){
        //loading_view.show()
    }
    fun loadingViewHide(){
        //loading_view.hide()
    }

    override fun onStop() {
        //check the state of the task
        if (task != null && task.status === AsyncTask.Status.RUNNING) task.cancel(true)
        super.onStop()
    }
}