package com.home.servicepresentation.ui.main.data.models.home

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class HomeModel(

    val promotions: ArrayList<PromotionsItem?>? = null,

    val subTitle: String? = null,

    val categories: ArrayList<CategoriesItem?>? = null,

    val title: String? = null,

    val activeServices: List<String?>? = null
) : Parcelable

@Parcelize
data class CategoriesItem(

    val image: Image? = null,

    val subTitles: SubTitles? = null,

    val description: String? = null,

    val titles: Titles? = null,

    val sort: Int? = null,

    val shortDescription: String? = null,

    val isActive: Boolean? = null,

    val title: String? = null,

    val descriptions: Descriptions? = null,

    val shortDescriptions: ShortDescriptions? = null,

    val displayType: String? = null,

    val subTitle: String? = null,

    val hasNewBadge: Boolean? = null,

    val _id: String? = null,

    val id: String? = null,

    val slug: String? = null,

    val slogans: Slogans? = null,

    val slogan: String? = null
) : Parcelable

@Parcelize
data class ShortDescriptions(

	val ar: String? = null,

	val en: String? = null
) : Parcelable

@Parcelize
data class Slogans(

	val ar: String? = null,

	val en: String? = null
) : Parcelable

@Parcelize
data class Image(

	val originalUrl4x: String? = null,

	val originalUrlSVG: String? = null,

	val originalUrl3x: String? = null,

	val originalUrlPDF: String? = null,

	val originalUrl2x: String? = null,

	val originalUrl: String? = null
) : Parcelable

@Parcelize
data class PromotionsItem(

	val image: Image? = null
) : Parcelable

@Parcelize
data class SubTitles(

	val ar: String? = null,

	val en: String? = null
) : Parcelable

@Parcelize
data class Titles(

	val ar: String? = null,

	val en: String? = null
) : Parcelable

@Parcelize
data class Descriptions(

	val ar: String? = null,

	val en: String? = null
) : Parcelable
