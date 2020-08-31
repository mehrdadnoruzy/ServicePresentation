package com.home.serviceprovider.ui.main.presentation.fragments.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.home.serviceprovider.R
import com.home.serviceprovider.ui.main.data.models.home.CategoriesItem
import kotlinx.android.synthetic.main.home_item_service.view.*
import java.io.InputStream
import java.net.URL


class HomeAdapter(private val categories: ArrayList<CategoriesItem?>?) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(categoriesItem: CategoriesItem?){
            //itemView.image.setImageBitmap(BitmapFactory.decodeStream(URL(categoriesItem?.image?.originalUrlSVG).openConnection().getInputStream()))
            DownloadImageTask(itemView.image).execute(categoriesItem?.image?.originalUrl)
            itemView.title.text = categoriesItem?.title
            itemView.subtitle.text = categoriesItem?.subTitle
            itemView.short_description.text = categoriesItem?.shortDescription
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_item_service, parent,false))

    override fun getItemCount(): Int = categories?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(categories?.get(position))

    fun addCategories(newCategories: ArrayList<CategoriesItem?>?){
        categories?.addAll(newCategories!!)
    }

    private class DownloadImageTask(bmImage: ImageView) :
        AsyncTask<String?, Void?, Bitmap?>() {
        var bmImage: ImageView
        override fun doInBackground(vararg urls: String?): Bitmap? {
            val urldisplay = urls[0]
            var mIcon: Bitmap? = null
            try {
                val `in`: InputStream = URL(urldisplay).openStream()
                mIcon = BitmapFactory.decodeStream(`in`)
            } catch (e: Exception) {
                Log.e("Error", e.message)
                e.printStackTrace()
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