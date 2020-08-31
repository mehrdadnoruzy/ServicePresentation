package com.home.serviceprovider.ui.main.domain

import android.content.Context
import java.util.*

interface UseCase : Observer {

    fun execute(context: Context)
}