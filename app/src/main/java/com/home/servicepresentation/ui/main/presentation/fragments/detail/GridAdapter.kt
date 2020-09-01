package com.home.servicepresentation.ui.main.presentation.fragments.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.home.servicepresentation.R
import com.home.servicepresentation.ui.main.data.models.detail.DataItem
import com.home.servicepresentation.ui.main.utils.MessagesListener
import com.home.servicepresentation.ui.main.utils.DownloadImageTask
import com.home.servicepresentation.ui.main.utils.MyColors
import kotlinx.android.synthetic.main.detaile_item_grid.view.*


class GridAdapter(private val data: ArrayList<DataItem?>?, private val itemClickListener: GridAdapterItemClickListener, private val messenger: MessagesListener) : RecyclerView.Adapter<GridAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dataItem: DataItem?, itemClickListener: GridAdapterItemClickListener, messenger: MessagesListener){
            itemView.setOnClickListener { itemClickListener.itemGridClicked() }
            DownloadImageTask(itemView.image, messenger).execute(dataItem?.image?.originalUrl)
            itemView.title.text = dataItem?.title
            itemView.subtitle.text = dataItem?.subTitle
            itemView.short_description.text = dataItem?.shortDescription
            itemView.price.text = dataItem?.basePrice.toString()
            if (dataItem?.hasDiscount == true) {
                itemView.has_discount.visibility = View.VISIBLE
                itemView.discount_percentage.text = dataItem?.discountPercentage.toString()
                itemView.discount_price.visibility = View.VISIBLE
                itemView.discount_price.text = dataItem?.listBasePrice.toString()
            }
            if (dataItem?.isSpecial == true)
                (itemView as CardView).setCardBackgroundColor(MyColors.BLUE)
            if (dataItem?.isActive != true) {
                (itemView as CardView).setCardBackgroundColor(MyColors.GRAY)
                (itemView as CardView).isEnabled = false
                (itemView as CardView).isClickable = false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.detaile_item_grid, parent,false))

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(data?.get(position), itemClickListener, messenger)

    fun addData(newData: java.util.ArrayList<DataItem?>?){
        data?.addAll(newData!!)
    }
}