package com.home.servicepresentation.ui.main.presentation.fragments.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.home.servicepresentation.R
import com.home.servicepresentation.data.models.home.CategoriesItem
import com.home.servicepresentation.ui.main.utils.imageDownloadTask
import com.home.servicepresentation.ui.main.utils.listenToClick
import kotlinx.android.synthetic.main.home_item_service.view.*
import java.util.*

class ServiceAdapter(
    private val categories: ArrayList<CategoriesItem?>?
) : RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {

    var liveDataIMG: MutableLiveData<View> = MutableLiveData()
    var liveDataMSG: MutableLiveData<String> = MutableLiveData()
    var liveDataClicked: MutableLiveData<Boolean> = MutableLiveData()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            categoriesItem: CategoriesItem?
        ) {
            itemView.title.text = categoriesItem?.title ?: "-"
            itemView.subtitle.text = categoriesItem?.subTitle ?: "-"
            itemView.short_description.text = categoriesItem?.shortDescription ?: "-"
            if (categoriesItem?.hasNewBadge == true)
                itemView.has_new_badge.visibility = View.VISIBLE
            if (categoriesItem?.isActive != true) {
                (itemView as CardView).setCardBackgroundColor(Color.parseColor("#C1C1C1"))
                itemView.isEnabled = false
                itemView.isClickable = false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        liveDataIMG.observe(parent.context as LifecycleOwner, {
            it?.loading_image?.visibility = View.GONE
        })
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_item_service, parent, false)
        )
            .listenToClick { pos, type, title ->
                //val item = categories?.get(pos)
                if (title.toLowerCase(Locale.ROOT).trim().equals("carwash"))
                    liveDataClicked.value = true
            }
    }

    override fun getItemCount(): Int = categories?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories?.get(position))
        loadImage(holder, position)
    }

    private fun loadImage(holder: ViewHolder, position: Int) {
        imageDownloadTask(
            holder.itemView.image,
            categories?.get(position)?.image?.originalUrl,
            liveDataMSG,
            liveDataIMG,
            holder.itemView
        )
        imageDownloadTask(
            holder.itemView.image,
            categories?.get(position)?.image?.originalUrl2x,
            liveDataMSG,
            liveDataIMG,
            holder.itemView
        )
        imageDownloadTask(
            holder.itemView.image,
            categories?.get(position)?.image?.originalUrl3x,
            liveDataMSG,
            liveDataIMG,
            holder.itemView
        )
        imageDownloadTask(
            holder.itemView.image,
            categories?.get(position)?.image?.originalUrl4x,
            liveDataMSG,
            liveDataIMG,
            holder.itemView
        )
    }

    fun addCategories(newCategories: ArrayList<CategoriesItem?>?) {
        categories?.addAll(newCategories!!)
    }
}