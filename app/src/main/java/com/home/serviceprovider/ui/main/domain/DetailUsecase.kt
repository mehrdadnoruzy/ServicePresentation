package com.home.serviceprovider.ui.main.domain

import android.content.Context
import com.home.serviceprovider.ui.main.data.repository.Repository
import java.util.*

class DetailUsecase(private val repository: Repository): UseCase {
    override fun execute(context: Context) {
        repository.getCarwashData()
    }

    override fun update(o: Observable?, arg: Any?) {
        //carwashObservable.addModel(arg)
    }
}