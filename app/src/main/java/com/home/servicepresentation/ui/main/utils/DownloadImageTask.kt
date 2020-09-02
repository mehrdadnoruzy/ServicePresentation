package com.home.servicepresentation.ui.main.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import java.io.IOException
import java.io.InputStream
import java.net.URL

@Deprecated("Operation of this class was slow")
class DownloadImageTask(image: ImageView, listener: MessagesListener) :
    AsyncTask<String?, Void?, Bitmap?>() {
    var liveDataMSG: MutableLiveData<String> = MutableLiveData()
    var listener: MessagesListener
    var image: ImageView

    init {
        this.image = image
        this.listener = listener
    }

    fun momomo(image: ImageView){

        // url of image to download
        val urlImage:URL = URL("https://images345.pexels.com/photos/730344/" +
                "pexels-photo-730344.jpeg?" +
                "auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260")

            // async task to get / download bitmap from url
            val result: Deferred<Bitmap?> = GlobalScope.async {
                urlImage.toBitmap()
            }

            GlobalScope.launch(Dispatchers.Main) {
                // get the downloaded bitmap
                val bitmap : Bitmap? = result.await()
                bitmap?.apply {
                    image.setImageBitmap(this)
                }
            }
    }

    fun URL.toBitmap(): Bitmap?{
        return try {
            BitmapFactory.decodeStream(openStream())
        }catch (e: IOException){
            liveDataMSG.value = e.message
            null
        }
    }

    override fun doInBackground(vararg urls: String?): Bitmap? {
        val urldisplay = urls[0]
        var mIcon: Bitmap? = null
        try {
            val `in`: InputStream = URL(urldisplay).openStream()
            mIcon = BitmapFactory.decodeStream(`in`)
        } catch (e: Exception) {
            //listener.showMessage(e.message ?: "There was a problem loading the image.")
        }
        return mIcon
    }

    override fun onPostExecute(result: Bitmap?) {
        image.setImageBitmap(result)
    }
    interface MessagesListener {

        fun showMessage(msg: String)
    }
}