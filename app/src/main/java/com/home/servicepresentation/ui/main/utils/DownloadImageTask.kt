package com.home.servicepresentation.ui.main.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import java.io.InputStream
import java.net.URL

class DownloadImageTask(image: ImageView, listener: MessagesListener) : AsyncTask<String?, Void?, Bitmap?>() {
    var listener: MessagesListener
    var image: ImageView

    init {
        this.image = image
        this.listener = listener
    }

    override fun doInBackground(vararg urls: String?): Bitmap? {
        val urldisplay = urls[0]
        var mIcon: Bitmap? = null
        try {
            val `in`: InputStream = URL(urldisplay).openStream()
            mIcon = BitmapFactory.decodeStream(`in`)
        } catch (e: Exception) {
            listener.showMessage(e.message ?: "بارگزاری تصویر با مشکل مواجه شد")
        }
        return mIcon
    }

    override fun onPostExecute(result: Bitmap?) {
        image.setImageBitmap(result)
    }

}