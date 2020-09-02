package com.home.servicepresentation.ui.main.presentation.fragments.detail

import android.graphics.Bitmap
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.home.servicepresentation.R
import com.home.servicepresentation.ui.main.data.models.detail.DataItem
import com.home.servicepresentation.ui.main.utils.imageDownloadTask
import kotlinx.android.synthetic.main.detaile_item_grid.view.*

class DetailAdapter(
    private val data: ArrayList<DataItem?>?
) : RecyclerView.Adapter<DetailAdapter.ViewHolder>() {
    var liveDataIMG: MutableLiveData<Bitmap> = MutableLiveData()
    var liveDataMSG: MutableLiveData<String> = MutableLiveData()
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            dataItem: DataItem?
        ) {
            //DownloadImageTask(itemView.image, messenger).execute(dataItem?.image?.originalUrl)
            itemView.title.text = dataItem?.title
            itemView.subtitle.text = dataItem?.subTitle
            itemView.short_description.text = dataItem?.shortDescription
            itemView.price.text = dataItem?.basePrice.toString() + " QAR"
            if (dataItem?.hasDiscount == true) {
                itemView.has_discount.visibility = View.VISIBLE
                itemView.discount_percentage.text = dataItem?.discountPercentage.toString()
                itemView.discount_price.visibility = View.VISIBLE
                itemView.discount_price.text = dataItem?.listBasePrice.toString() + " QAR"
            }
            if (dataItem?.isSpecial == true) {
                (itemView as CardView).setCardBackgroundColor(
                    Color.parseColor("#103AC1")
                )
                itemView.title.setTextColor(Color.parseColor("#FFFFFF"))
                itemView.subtitle.setTextColor(Color.parseColor("#FFFFFF"))
                itemView.short_description.setTextColor(Color.parseColor("#FFFFFF"))

            }
            if (dataItem?.isActive != true) {
                (itemView as CardView).setCardBackgroundColor(Color.parseColor("#C1C1C1"))
                itemView.isEnabled = false
                itemView.isClickable = false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.detaile_item_grid, parent, false)
        )

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data?.get(position))
        imageDownloadTask(
            holder.itemView.image,
            data?.get(position)?.image?.originalUrl,
            liveDataMSG, liveDataIMG
        )
    }

    fun addData(newData: java.util.ArrayList<DataItem?>?) {
        data?.addAll(newData!!)
    }
}