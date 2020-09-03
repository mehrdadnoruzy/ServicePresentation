package com.home.servicepresentation.ui.main.presentation.fragments.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.home.servicepresentation.R
import com.home.servicepresentation.data.models.home.PromotionsItem
import com.home.servicepresentation.ui.main.utils.imageDownloadTask
import kotlinx.android.synthetic.main.home_item_service.view.*

class PromotionAdapter(
    private val promotions: ArrayList<PromotionsItem?>?
) : RecyclerView.Adapter<PromotionAdapter.ViewHolder>() {

    var liveDataIMG: MutableLiveData<View> = MutableLiveData()
    var liveDataMSG: MutableLiveData<String> = MutableLiveData()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        liveDataIMG.observe(parent.context as LifecycleOwner, {
            it?.loading_image?.visibility = View.GONE
        })
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_item_promotion, parent, false)
        )
    }

    override fun getItemCount(): Int = promotions?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        loadImage(holder, position)
    }

    private fun loadImage(holder: ViewHolder, position: Int) {
        imageDownloadTask(
            holder.itemView.image,
            promotions?.get(position)?.image?.originalUrl,
            liveDataMSG,
            liveDataIMG,
            holder.itemView
        )
        imageDownloadTask(
            holder.itemView.image,
            promotions?.get(position)?.image?.originalUrl2x,
            liveDataMSG,
            liveDataIMG,
            holder.itemView
        )
        imageDownloadTask(
            holder.itemView.image,
            promotions?.get(position)?.image?.originalUrl3x,
            liveDataMSG,
            liveDataIMG,
            holder.itemView
        )
        imageDownloadTask(
            holder.itemView.image,
            promotions?.get(position)?.image?.originalUrl4x,
            liveDataMSG,
            liveDataIMG,
            holder.itemView
        )
    }

    fun addPromotions(newPromotions: ArrayList<PromotionsItem?>?) {
        promotions?.addAll(newPromotions!!)
    }
}