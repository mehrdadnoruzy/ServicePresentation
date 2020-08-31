package com.home.servicepresentation.ui.main.data.models.home

import java.util.*

class HomeObservable : Observable() {

    fun addModel(homeModel: HomeModel){
        setChanged()
        notifyObservers(homeModel)
    }
}