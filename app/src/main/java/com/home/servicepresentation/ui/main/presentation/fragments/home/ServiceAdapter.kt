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
import com.home.servicepresentation.ui.main.data.models.home.CategoriesItem
import kotlinx.android.synthetic.main.home_item_service.view.*
import java.io.InputStream
import java.net.URL


class ServiceAdapter(private val categories: ArrayList<CategoriesItem?>?, private val listener: AdapterListener) : RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(categoriesItem: CategoriesItem?, listener: AdapterListener){
            //itemView.image.setImageBitmap(BitmapFactory.decodeStream(URL(categoriesItem?.image?.originalUrlSVG).openConnection().getInputStream()))
            DownloadImageTask(itemView.image, listener).execute(categoriesItem?.image?.originalUrl)
            itemView.title.text = categoriesItem?.title
            itemView.subtitle.text = categoriesItem?.subTitle
            itemView.short_description.text = categoriesItem?.shortDescription
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_item_service, parent,false))

    override fun getItemCount(): Int = categories?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(categories?.get(position), listener)

    fun addCategories(newCategories: ArrayList<CategoriesItem?>?){
        categories?.addAll(newCategories!!)
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