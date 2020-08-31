package com.home.servicepresentation.ui.main.domain

import android.content.Context
import com.home.servicepresentation.ui.main.data.repository.Repository
import java.util.*

class DetailUsecase(private val repository: Repository): UseCase {
    override fun execute(context: Context) {
        repository.getCarwashData()
    }

    override fun update(o: Observable?, arg: Any?) {
        //carwashObservable.addModel(arg)
    }
}