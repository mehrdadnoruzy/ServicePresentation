package com.home.servicepresentation.ui.main.presentation.fragments.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.home.servicepresentation.R
import com.home.servicepresentation.ui.main.data.models.home.PromotionsItem
import kotlinx.android.synthetic.main.home_item_service.view.*
import java.io.InputStream
import java.net.URL


class PromotionAdapter(private val promotions: ArrayList<PromotionsItem?>?, private val listener: AdapterListener) : RecyclerView.Adapter<PromotionAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(promotionsItem: PromotionsItem?, listener: AdapterListener){
            //itemView.image.setImageBitmap(BitmapFactory.decodeStream(URL(categoriesItem?.image?.originalUrlSVG).openConnection().getInputStream()))
            DownloadImageTask(itemView.image, listener).execute(promotionsItem?.image?.originalUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_item_promotion, parent,false))

    override fun getItemCount(): Int = promotions?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(promotions?.get(position), listener)

    fun addPromotions(newPromotions: ArrayList<PromotionsItem?>?){
        promotions?.addAll(newPromotions!!)
    }

    private class DownloadImageTask(bmImage: ImageView, listener: AdapterListener) :
        AsyncTask<String?, Void?, Bitmap?>() {
        var listen = listener
        var bmImage: ImageView
        override fun doInBackground(vararg urls: String?): Bitmap? {
            val urldisplay = urls[0]
            var mIcon: Bitmap? = null
            try {
                val `in`: InputStream = URL(urldisplay).openStream()
                mIcon = BitmapFactory.decodeStream(`in`)
            } catch (e: Exception) {
                listen.showMessage(e.message ?: "بارگزاری تصویر با مشکل مواجه شد")
            }
            return mIcon
        }

        override fun onPostExecute(result: Bitmap?) {
            bmImage.setImageBitmap(result)
        }

        init {
            this.bmImage = bmImage
        }
    }
}