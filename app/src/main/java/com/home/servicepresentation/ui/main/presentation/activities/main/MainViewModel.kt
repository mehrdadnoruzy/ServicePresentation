package com.home.servicepresentation.ui.main.presentation.activities.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.home.servicepresentation.data.models.base.BaseModel
import com.home.servicepresentation.data.models.detail.DetailModel
import com.home.servicepresentation.data.models.home.HomeModel
import com.home.servicepresentation.domain.DetailUsecase
import com.home.servicepresentation.domain.HomeUsecase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel(
    private val homeUsecase: HomeUsecase,
    private val detailUsecase: DetailUsecase
) : ViewModel() {

    var liveDataHome: MutableLiveData<BaseModel<HomeModel>?> = MutableLiveData()
    var liveDataDetail: MutableLiveData<BaseModel<DetailModel>?> = MutableLiveData()

    fun getHomeData() {
        GlobalScope.launch(Dispatchers.Main) {
            val result: BaseModel<HomeModel>? = homeUsecase.executeAsync()
            result?.apply {
                liveDataHome.value = result
            }
        }
    }

    fun getDetailData() {
        GlobalScope.launch(Dispatchers.Main) {
            val result: BaseModel<DetailModel>? = detailUsecase.executeAsync()
            result?.apply {
                liveDataDetail.value = this
            }
        }
    }
}