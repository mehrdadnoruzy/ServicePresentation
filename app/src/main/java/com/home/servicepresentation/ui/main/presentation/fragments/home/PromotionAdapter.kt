package com.home.servicepresentation.ui.main.presentation.fragments.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.home.servicepresentation.R
import com.home.servicepresentation.ui.main.data.models.home.PromotionsItem
import com.home.servicepresentation.ui.main.utils.MessagesListener
import com.home.servicepresentation.ui.main.utils.DownloadImageTask
import kotlinx.android.synthetic.main.home_item_service.view.*


class PromotionAdapter(private val promotions: ArrayList<PromotionsItem?>?, private val itemClickListener: HomeAdapterItemClickListener, private val messenger: MessagesListener) : RecyclerView.Adapter<PromotionAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(promotionsItem: PromotionsItem?, itemClickListener: HomeAdapterItemClickListener, messenger: MessagesListener){
            itemView.setOnClickListener { itemClickListener.itemPromotionClicked() }
            DownloadImageTask(itemView.image, messenger).execute(promotionsItem?.image?.originalUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_item_promotion, parent,false))

    override fun getItemCount(): Int = promotions?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(promotions?.get(position), itemClickListener, messenger)

    fun addPromotions(newPromotions: ArrayList<PromotionsItem?>?){
        promotions?.addAll(newPromotions!!)
    }
}