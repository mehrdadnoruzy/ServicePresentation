package com.home.serviceprovider.ui.main.presentation.activities.main

import android.content.Context
import androidx.lifecycle.ViewModel
import com.home.serviceprovider.ui.main.data.models.detail.CarwashModel
import com.home.serviceprovider.ui.main.data.models.home.HomeModel
import com.home.serviceprovider.ui.main.data.models.home.HomeObservable
import com.home.serviceprovider.ui.main.domain.DetailUsecase
import com.home.serviceprovider.ui.main.domain.HomeUsecase
import java.util.*

class MainViewModel(private val homeUsecase: HomeUsecase, private val detailUsecase: DetailUsecase) : ViewModel(), Observer {

    var homeObservable = HomeObservable()

    init {
        homeUsecase.homeObservable.addObserver(this)
    }

    fun getHomeData(context: Context){
        homeUsecase.execute(context)
    }

    fun getCarwashData(context: Context){
        detailUsecase.execute(context)
    }

    override fun update(o: Observable?, arg: Any?) {
        when(o){
            is HomeObservable -> {
                if(arg is HomeModel){
                    homeObservable.addModel(arg)
                }
            }
            is CarwashModel -> {}
        }
    }
}