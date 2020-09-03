package com.home.servicepresentation.data.models.detail

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
data class DetailModel(

	val image: Image? = null,

	val data: ArrayList<DataItem?>? = null,

	val description: String? = null,

	val title: String? = null,

	val slogan: String? = null,

	val checkout: Checkout? = null,

	val slug: String? = null
) : Parcelable, Observable()

@Parcelize
data class ShortDescriptions(

	val ar: String? = null,

	val en: String? = null
) : Parcelable

@Parcelize
data class Titles(

	val ar: String? = null,

	val en: String? = null
) : Parcelable

@Parcelize
data class AsapTitles(

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
data class DataItem(

	val image: Image? = null,

	val subTitles: SubTitles? = null,

	val hasDiscount: Boolean? = null,

	val titles: Titles? = null,

	val sort: Int? = null,

	val shortDescription: String? = null,

	val isActive: Boolean? = null,

	val title: String? = null,

	val shortDescriptions: ShortDescriptions? = null,

	val discountPercentage: Int? = null,

	val subTitle: String? = null,

	val isSpecial: Boolean? = null,

	val coverImageId: String? = null,

	val coverImage: CoverImage? = null,

	val listBasePrice: Int? = null,

	val _id: String? = null,

	val id: String? = null,

	val slug: String? = null,

	val categoryId: String? = null,

	val basePrice: Int? = null
) : Parcelable

@Parcelize
data class ExtraNoteField(

	val _id: String? = null,

	val id: String? = null,

	val isEnable: Boolean? = null
) : Parcelable

@Parcelize
data class SubTitles(

	val ar: String? = null,

	val en: String? = null
) : Parcelable

@Parcelize
data class ScheduleTitles(

	val ar: String? = null,

	val en: String? = null
) : Parcelable

@Parcelize
data class Thumbnails(

	val has512x512: Boolean? = null,

	val has128x128: Boolean? = null,

	val has256x256: Boolean? = null
) : Parcelable

@Parcelize
data class CoverImage(

	val path: String? = null,

	val createdAt: String? = null,

	val fileName: String? = null,

	val V: Int? = null,

	val _id: String? = null,

	val originalUrl: String? = null,

	val id: String? = null,

	val type: String? = null,

	val thumbnails: Thumbnails? = null,

	val serviceId: String? = null,

	val updatedAt: String? = null
) : Parcelable

@Parcelize
data class Scheduling(

	val scheduleTitle: String? = null,

	val scheduleTitles: ScheduleTitles? = null,

	val hasAsapFeature: Boolean? = null,

	val asapTitle: String? = null,

	val _id: String? = null,

	val id: String? = null,

	val hasReturnDate: Boolean? = null,

	val hasScheduleFeature: Boolean? = null,

	val asapTitles: AsapTitles? = null
) : Parcelable

@Parcelize
data class Checkout(

	val extraNoteField: ExtraNoteField? = null,

	val paymentMethods: List<String?>? = null,

	val scheduling: Scheduling? = null
) : Parcelable
