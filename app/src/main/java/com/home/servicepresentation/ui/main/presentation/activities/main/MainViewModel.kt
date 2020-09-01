package com.home.servicepresentation.ui.main.presentation.activities.main

import android.content.Context
import androidx.lifecycle.ViewModel
import com.home.servicepresentation.ui.main.data.models.detail.DetailModel
import com.home.servicepresentation.ui.main.data.models.detail.DetailObservable
import com.home.servicepresentation.ui.main.data.models.home.HomeModel
import com.home.servicepresentation.ui.main.data.models.home.HomeObservable
import com.home.servicepresentation.ui.main.domain.DetailUsecase
import com.home.servicepresentation.ui.main.domain.HomeUsecase
import java.util.*

class MainViewModel(private val homeUsecase: HomeUsecase, private val detailUsecase: DetailUsecase) : ViewModel(), Observer {

    var homeObservable = HomeObservable()
    var detailObservable = DetailObservable()

    init {
        homeUsecase.homeObservable.addObserver(this)
        detailUsecase.detailObservable.addObserver(this)
    }

    fun getHomeData(context: Context){
        homeUsecase.execute(context)
    }

    fun getDetailData(context: Context){
        detailUsecase.execute(context)
    }

    override fun update(o: Observable?, arg: Any?) {
        when(o){
            is HomeObservable -> {
                if(arg is HomeModel){
                    homeObservable.addModel(arg)
                }
            }
            is DetailObservable -> {
                if (arg is DetailModel){
                    detailObservable.addModel(arg)
                }
            }
        }
    }
}