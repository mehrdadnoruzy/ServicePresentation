package com.home.servicepresentation.ui.main.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.home_item_service.view.*
import kotlinx.coroutines.*
import java.io.IOException
import java.net.URL

fun <T : RecyclerView.ViewHolder> T.listenToClick(event: (position: Int, type: Int, title: String) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(getAdapterPosition(), getItemViewType(), it.title.text.toString())
    }
    return this
}

fun imageDownloadTask(image: ImageView, url: String?, liveDataMSG: MutableLiveData<String>, liveDataIMG: MutableLiveData<Bitmap>){
    if (liveDataIMG.value==null) {
        val urlImage: URL = URL(url)
        val result: Deferred<Bitmap?> = GlobalScope.async {
            urlImage.toBitmap(liveDataMSG)
        }
        GlobalScope.launch(Dispatchers.Main) {
            val bitmap: Bitmap? = result.await()
            liveDataIMG.value = bitmap
            liveDataIMG.value?.apply {
                image.setImageBitmap(this)
            }
        }
    }
}

fun URL.toBitmap(liveDataMSG: MutableLiveData<String>): Bitmap?{
    return try {
        BitmapFactory.decodeStream(openStream())
    }catch (e: IOException){
        liveDataMSG.postValue(e.message)
        null
    }
}