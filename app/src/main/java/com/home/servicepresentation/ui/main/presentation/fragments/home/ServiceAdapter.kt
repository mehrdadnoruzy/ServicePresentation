package com.home.servicepresentation.ui.main.presentation.fragments.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.home.servicepresentation.R
import com.home.servicepresentation.ui.main.data.models.home.CategoriesItem
import com.home.servicepresentation.ui.main.presentation.fragments.base.MessagesListener
import com.home.servicepresentation.ui.main.utils.DownloadImageTask
import kotlinx.android.synthetic.main.home_item_service.view.*

class ServiceAdapter(
    private val categories: ArrayList<CategoriesItem?>?,
    private val itemClickListener: HomeAdapterItemClickListener,
    private val messenger: MessagesListener
) : RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            categoriesItem: CategoriesItem?,
            itemClickListener: HomeAdapterItemClickListener,
            messenger: MessagesListener
        ) {
            itemView.setOnClickListener {
                if (categoriesItem?.title?.toLowerCase()?.trim()
                        .equals("carwash")
                ) itemClickListener.itemServiceClicked()
            }
            DownloadImageTask(itemView.image, messenger).execute(categoriesItem?.image?.originalUrl)
            itemView.title.text = categoriesItem?.title
            itemView.subtitle.text = categoriesItem?.subTitle
            itemView.short_description.text = categoriesItem?.shortDescription
            if (categoriesItem?.hasNewBadge == true)
                itemView.has_new_badge.visibility = View.VISIBLE
            if (categoriesItem?.isActive != true) {
                (itemView as CardView).setCardBackgroundColor(Color.parseColor("#C1C1C1"))
                (itemView as CardView).isEnabled = false
                (itemView as CardView).isClickable = false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_item_service, parent, false)
        )

    override fun getItemCount(): Int = categories?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(categories?.get(position), itemClickListener, messenger)

    fun addCategories(newCategories: ArrayList<CategoriesItem?>?) {
        categories?.addAll(newCategories!!)
    }
}