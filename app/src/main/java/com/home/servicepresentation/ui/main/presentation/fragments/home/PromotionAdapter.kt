package com.home.servicepresentation.ui.main.presentation.fragments.home

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.home.servicepresentation.R
import com.home.servicepresentation.ui.main.data.models.home.PromotionsItem
import com.home.servicepresentation.ui.main.utils.imageDownloadTask
import kotlinx.android.synthetic.main.home_item_service.view.*

class PromotionAdapter(
    private val promotions: ArrayList<PromotionsItem?>?
) : RecyclerView.Adapter<PromotionAdapter.ViewHolder>() {
    var liveDataIMG: MutableLiveData<Bitmap> = MutableLiveData()
    var liveDataMSG: MutableLiveData<String> = MutableLiveData()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_item_promotion, parent, false)
        )

    override fun getItemCount(): Int = promotions?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        imageDownloadTask(
            holder.itemView.image,
            promotions?.get(position)?.image?.originalUrl,
            liveDataMSG,
            liveDataIMG
        )
    }

    fun addPromotions(newPromotions: ArrayList<PromotionsItem?>?) {
        promotions?.addAll(newPromotions!!)
    }
}