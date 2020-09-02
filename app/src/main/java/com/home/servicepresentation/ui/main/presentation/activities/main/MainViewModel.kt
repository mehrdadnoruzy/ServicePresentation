package com.home.servicepresentation.ui.main.presentation.activities.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.home.servicepresentation.ui.main.data.models.base.BaseModel
import com.home.servicepresentation.ui.main.data.models.detail.DetailModel
import com.home.servicepresentation.ui.main.data.models.home.HomeModel
import com.home.servicepresentation.ui.main.domain.DetailUsecase
import com.home.servicepresentation.ui.main.domain.HomeUsecase
import kotlinx.coroutines.*

class MainViewModel(private val homeUsecase: HomeUsecase,
                    private val detailUsecase: DetailUsecase
): ViewModel() {

    var liveDataHome: MutableLiveData<BaseModel<HomeModel>?> = MutableLiveData()
    var liveDataDetail: MutableLiveData<BaseModel<DetailModel>?> = MutableLiveData()

    fun getHomeData(context: Context) {
        GlobalScope.launch(Dispatchers.Main) {
            val result : BaseModel<HomeModel>? = homeUsecase.executeAsync(context)
            result?.apply {
                liveDataHome.value = result
            }
        }
    }

    fun getDetailData(context: Context) {
        GlobalScope.launch(Dispatchers.Main) {
            val result : BaseModel<DetailModel>? = detailUsecase.executeAsync(context)
            result?.apply {
                liveDataDetail.value = this
            }
        }
    }
}