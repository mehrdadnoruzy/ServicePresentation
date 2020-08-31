package com.home.serviceprovider.ui.main.presentation.activities.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.home.serviceprovider.ui.main.data.network.Network
import com.home.serviceprovider.ui.main.data.repository.Repository
import com.home.serviceprovider.ui.main.domain.DetailUsecase
import com.home.serviceprovider.ui.main.domain.HomeUsecase

class MyViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(
            HomeUsecase(Repository(Network())),
            DetailUsecase(Repository(Network()))
        ) as T
    }
}