package com.home.servicepresentation.ui.main.data.models.detail

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
data class CarwashModel(

	@field:SerializedName("image")
	val image: Image? = null,

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("slogan")
	val slogan: String? = null,

	@field:SerializedName("checkout")
	val checkout: Checkout? = null,

	@field:SerializedName("slug")
	val slug: String? = null
) : Parcelable, Observable()

@Parcelize
data class ShortDescriptions(

	@field:SerializedName("ar")
	val ar: String? = null,

	@field:SerializedName("en")
	val en: String? = null
) : Parcelable

@Parcelize
data class Titles(

	@field:SerializedName("ar")
	val ar: String? = null,

	@field:SerializedName("en")
	val en: String? = null
) : Parcelable

@Parcelize
data class AsapTitles(

	@field:SerializedName("ar")
	val ar: String? = null,

	@field:SerializedName("en")
	val en: String? = null
) : Parcelable

@Parcelize
data class Image(

	@field:SerializedName("originalUrl@4x")
	val originalUrl4x: String? = null,

	@field:SerializedName("originalUrlSVG")
	val originalUrlSVG: String? = null,

	@field:SerializedName("originalUrl@3x")
	val originalUrl3x: String? = null,

	@field:SerializedName("originalUrlPDF")
	val originalUrlPDF: String? = null,

	@field:SerializedName("originalUrl@2x")
	val originalUrl2x: String? = null,

	@field:SerializedName("originalUrl")
	val originalUrl: String? = null
) : Parcelable

@Parcelize
data class DataItem(

	@field:SerializedName("image")
	val image: Image? = null,

	@field:SerializedName("subTitles")
	val subTitles: SubTitles? = null,

	@field:SerializedName("hasDiscount")
	val hasDiscount: Boolean? = null,

	@field:SerializedName("titles")
	val titles: Titles? = null,

	@field:SerializedName("sort")
	val sort: Int? = null,

	@field:SerializedName("shortDescription")
	val shortDescription: String? = null,

	@field:SerializedName("isActive")
	val isActive: Boolean? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("shortDescriptions")
	val shortDescriptions: ShortDescriptions? = null,

	@field:SerializedName("discountPercentage")
	val discountPercentage: Int? = null,

	@field:SerializedName("subTitle")
	val subTitle: String? = null,

	@field:SerializedName("isSpecial")
	val isSpecial: Boolean? = null,

	@field:SerializedName("coverImageId")
	val coverImageId: String? = null,

	@field:SerializedName("coverImage")
	val coverImage: CoverImage? = null,

	@field:SerializedName("listBasePrice")
	val listBasePrice: Int? = null,

	@field:SerializedName("_id")
	val _id: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("categoryId")
	val categoryId: String? = null,

	@field:SerializedName("basePrice")
	val basePrice: Int? = null
) : Parcelable

@Parcelize
data class ExtraNoteField(

	@field:SerializedName("_id")
	val _id: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("isEnable")
	val isEnable: Boolean? = null
) : Parcelable

@Parcelize
data class SubTitles(

	@field:SerializedName("ar")
	val ar: String? = null,

	@field:SerializedName("en")
	val en: String? = null
) : Parcelable

@Parcelize
data class ScheduleTitles(

	@field:SerializedName("ar")
	val ar: String? = null,

	@field:SerializedName("en")
	val en: String? = null
) : Parcelable

@Parcelize
data class Thumbnails(

	@field:SerializedName("has512x512")
	val has512x512: Boolean? = null,

	@field:SerializedName("has128x128")
	val has128x128: Boolean? = null,

	@field:SerializedName("has256x256")
	val has256x256: Boolean? = null
) : Parcelable

@Parcelize
data class CoverImage(

	@field:SerializedName("path")
	val path: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("fileName")
	val fileName: String? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("_id")
	val _id: String? = null,

	@field:SerializedName("originalUrl")
	val originalUrl: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("thumbnails")
	val thumbnails: Thumbnails? = null,

	@field:SerializedName("serviceId")
	val serviceId: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
) : Parcelable

@Parcelize
data class Scheduling(

	@field:SerializedName("scheduleTitle")
	val scheduleTitle: String? = null,

	@field:SerializedName("scheduleTitles")
	val scheduleTitles: ScheduleTitles? = null,

	@field:SerializedName("hasAsapFeature")
	val hasAsapFeature: Boolean? = null,

	@field:SerializedName("asapTitle")
	val asapTitle: String? = null,

	@field:SerializedName("_id")
	val _id: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("hasReturnDate")
	val hasReturnDate: Boolean? = null,

	@field:SerializedName("hasScheduleFeature")
	val hasScheduleFeature: Boolean? = null,

	@field:SerializedName("asapTitles")
	val asapTitles: AsapTitles? = null
) : Parcelable

@Parcelize
data class Checkout(

	@field:SerializedName("extraNoteField")
	val extraNoteField: ExtraNoteField? = null,

	@field:SerializedName("paymentMethods")
	val paymentMethods: List<String?>? = null,

	@field:SerializedName("scheduling")
	val scheduling: Scheduling? = null
) : Parcelable
