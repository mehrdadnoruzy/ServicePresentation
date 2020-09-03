package com.home.servicepresentation.data

import android.content.Context
import com.google.gson.JsonSyntaxException
import com.home.servicepresentation.data.models.base.BaseModel
import com.home.servicepresentation.data.models.detail.DetailModel
import com.home.servicepresentation.data.models.home.HomeModel
import com.home.servicepresentation.data.network.CheckNetwork
import com.home.servicepresentation.data.network.Network
import com.nhaarman.mockitokotlin2.mock
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.BufferedInputStream
import java.io.FileNotFoundException
import java.net.MalformedURLException

class NetworkTest {

    private val trueUrlHome = "https://api-dot-rafiji-staging.appspot.com/customer/v2/home"
    private val falseUrlHome = "https://api-dot-rafiji-staging.appspot.com/customer/v2/false"
    private val trueUrlDetail = "https://api-dot-rafiji-staging.appspot.com/customer/v2/categories/carwash/services"
    private val falseUrlDetail = "https://api-dot-rafiji-staging.appspot.com/customer/v2/categories/carwash/false"

    private lateinit var network: Network

    @Before
    fun setup(){
        network = Network.getInstance(CheckNetwork.getInstance(mock<Context>()))
    }

    @Test
    fun getApiDataOf_Home_Equal_To_Json() {

        val result = network.getApiDataOf(trueUrlHome)
        assertEquals(homeModelJson,result)

    }

    @Test
    fun getApiDataOf_Not_Null_With_True_Url() {

        val result = network.getApiDataOf(trueUrlHome)
        assertNotNull(result)

    }

    @Test
    fun getApiDataOf_Not_Null_With_False_Url() {

        val result = network.getApiDataOf(falseUrlHome)
        assertNotNull(result)

    }

    @Test
    fun getApiDataOf_Not_Null_With_Empty_Url() {

        val result = network.getApiDataOf("")
        assertNotNull(result)

    }

    @Test(expected = MalformedURLException::class)
    fun openConnection_Must_Throws_Exception() {

        network.openConnection("")

    }

    @Test
    fun openConnection_Must_Not_Found_Url() {

        val result = network.openConnection(falseUrlHome)
        assertEquals(404, result.responseCode)

    }

    @Test
    fun readStream_Must_Not_Null_With_True_Url(){

        val result = network.readStream(BufferedInputStream(network.openConnection(trueUrlHome).inputStream))
        assertNotNull(result)

    }

    @Test(expected = FileNotFoundException::class)
    fun readStream_Must_Throws_Exception_With_False_Url(){

        network.readStream(BufferedInputStream(network.openConnection(falseUrlHome).inputStream))

    }

    @Test(expected = MalformedURLException::class)
    fun readStream_Must_Throws_Exception_When_Url_Is_Empty(){

        network.readStream(BufferedInputStream(network.openConnection("").inputStream))

    }

    @Test
    fun createHomeModel_With_True_Json_Not_Null(){

        val model = network.createHomeModel(homeModelJson)
        assertNotNull(model)

    }

    @Test
    fun createDetailModel_With_True_Json_Not_Null(){

        val model = network.createDetailModel(detailModelJson)
        assertNotNull(model)

    }

    @Test
    fun createHomeModel_With_True_Json_Check_Type_Base() {

        val model = network.createHomeModel(homeModelJson)
        assertThat(model, instanceOf(BaseModel::class.java))

    }

    @Test
    fun createDetailModel_With_True_Json_Check_Type_Base() {

        val model = network.createDetailModel(detailModelJson)
        assertThat(model, instanceOf(BaseModel::class.java))

    }

    @Test
    fun createHomeModel_With_True_Json_Check_Type() {

        val model = network.createHomeModel(homeModelJson)
        assertThat(model?.data, instanceOf(HomeModel::class.java))

    }

    @Test
    fun createDetailModel_With_True_Json_Check_Type() {

        val model = network.createDetailModel(detailModelJson)
        assertThat(model?.data, instanceOf(DetailModel::class.java))

    }

    @Test(expected = JsonSyntaxException::class)
    fun createHomeModel_With_False_Json(){

        network.createHomeModel("{:}")

    }

    @Test(expected = JsonSyntaxException::class)
    fun createDetailModel_With_False_Json(){

        network.createDetailModel("{:}")

    }

    @Test
    fun createHomeModel_Error_Type(){
        val result = network.createHomeModel("ERROR:000:message")
        assertNull(result?.data)
    }

    @Test
    fun createDetailModel_Error_Type(){
        val result = network.createDetailModel("ERROR:000:message")
        assertNull(result?.data)
    }

    val homeModelJson = "{\"title\":\"Book Now!\",\"subTitle\":\"You can book the desired service and get the best quality!\",\"activeServices\":[],\"categories\":[{\"titles\":{\"en\":\"Carwash\",\"ar\":\"Carwash\"},\"subTitles\":{\"en\":\"4 Packages\",\"ar\":\"4 Packages\"},\"descriptions\":{\"en\":\"Our services are what you want!Book one of them.\",\"ar\":\"Our services are what you want!Book one of them.\"},\"shortDescriptions\":{\"en\":\"Basic | Eco | Pro | VIP\",\"ar\":\"Basic | Eco | Pro | VIP\"},\"slogans\":{\"en\":\"Like Day One!\",\"ar\":\"Like Day One!\"},\"isActive\":true,\"sort\":0,\"hasNewBadge\":false,\"_id\":\"5e7a40aed2dd69acb979d605\",\"slug\":\"carwash\",\"displayType\":\"subServices\",\"title\":\"Carwash\",\"subTitle\":\"4 Packages\",\"description\":\"Our services are what you want!Book one of them.\",\"shortDescription\":\"Basic | Eco | Pro | VIP\",\"slogan\":\"Like Day One!\",\"image\":{\"originalUrl\":\"https://storage.googleapis.com/rafiji-staging/images/categoryImage/carwash.png\",\"originalUrl@2x\":\"https://storage.googleapis.com/rafiji-staging/images/categoryImage/carwash@2x.png\",\"originalUrl@3x\":\"https://storage.googleapis.com/rafiji-staging/images/categoryImage/carwash@3x.png\",\"originalUrl@4x\":\"https://storage.googleapis.com/rafiji-staging/images/categoryImage/carwash@4x.png\",\"originalUrlPDF\":\"https://storage.googleapis.com/rafiji-staging/images/categoryImage/carwash.pdf\",\"originalUrlSVG\":\"https://storage.googleapis.com/rafiji-staging/images/categoryImage/carwash.svg\"},\"id\":\"5e7a40aed2dd69acb979d605\"},{\"titles\":{\"en\":\"Laundry\",\"ar\":\"Laundry\"},\"subTitles\":{\"en\":\"Hight Qulity\",\"ar\":\"Hight Qulity\"},\"descriptions\":{\"en\":\"Clothes, Sheets, etc\",\"ar\":\"Clothes, Sheets, etc\"},\"shortDescriptions\":{\"en\":\"Clothes, Sheets, etc\",\"ar\":\"Clothes, Sheets, etc\"},\"isActive\":true,\"sort\":1,\"hasNewBadge\":true,\"_id\":\"5ee00230230c2d12f2903aa7\",\"slug\":\"laundry\",\"displayType\":\"priceList\",\"title\":\"Laundry\",\"subTitle\":\"Hight Qulity\",\"description\":\"Clothes, Sheets, etc\",\"shortDescription\":\"Clothes, Sheets, etc\",\"image\":{\"originalUrl\":\"https://storage.googleapis.com/rafiji-staging/images/categoryImage/laundry.png\",\"originalUrl@2x\":\"https://storage.googleapis.com/rafiji-staging/images/categoryImage/laundry@2x.png\",\"originalUrl@3x\":\"https://storage.googleapis.com/rafiji-staging/images/categoryImage/laundry@3x.png\",\"originalUrl@4x\":\"https://storage.googleapis.com/rafiji-staging/images/categoryImage/laundry@4x.png\",\"originalUrlPDF\":\"https://storage.googleapis.com/rafiji-staging/images/categoryImage/laundry.pdf\",\"originalUrlSVG\":\"https://storage.googleapis.com/rafiji-staging/images/categoryImage/laundry.svg\"},\"id\":\"5ee00230230c2d12f2903aa7\"}],\"promotions\":[{\"image\":{\"originalUrl\":\"https://storage.googleapis.com/rafiji-staging/images/promotions/travel.png\"}},{\"image\":{\"originalUrl\":\"https://storage.googleapis.com/rafiji-staging/images/promotions/movie.png\"}}]}"
    val detailModelJson = "{\"slug\":\"carwash\",\"title\":\"Carwash\",\"image\":{\"originalUrl\":\"https://storage.googleapis.com/rafiji-staging/images/categoryImage/carwash.png\",\"originalUrl@2x\":\"https://storage.googleapis.com/rafiji-staging/images/categoryImage/carwash@2x.png\",\"originalUrl@3x\":\"https://storage.googleapis.com/rafiji-staging/images/categoryImage/carwash@3x.png\",\"originalUrl@4x\":\"https://storage.googleapis.com/rafiji-staging/images/categoryImage/carwash@4x.png\",\"originalUrlPDF\":\"https://storage.googleapis.com/rafiji-staging/images/categoryImage/carwash.pdf\",\"originalUrlSVG\":\"https://storage.googleapis.com/rafiji-staging/images/categoryImage/carwash.svg\"},\"slogan\":\"Like Day One!\",\"description\":\"Our services are what you want!Book one of them.\",\"checkout\":{\"paymentMethods\":[\"cash\",\"pos\"],\"scheduling\":{\"asapTitles\":{\"en\":\"As soon as possible!\",\"ar\":\"As soon as possible!\"},\"scheduleTitles\":{\"en\":\"Schedule a Time\",\"ar\":\"Schedule a Time\"},\"hasAsapFeature\":true,\"hasScheduleFeature\":true,\"hasReturnDate\":false,\"_id\":\"5f5107848684d1000a3a66a6\",\"asapTitle\":\"As soon as possible!\",\"scheduleTitle\":\"Schedule a Time\",\"id\":\"5f5107848684d1000a3a66a6\"},\"extraNoteField\":{\"isEnable\":false,\"_id\":\"5f5107848684d1000a3a66a7\",\"id\":\"5f5107848684d1000a3a66a7\"}},\"data\":[{\"titles\":{\"en\":\"Basic\",\"ar\":\"Basic\"},\"subTitles\":{\"en\":\"Basic\",\"ar\":\"Basic\"},\"shortDescriptions\":{\"en\":\"Exterior Wash\",\"ar\":\"Exterior Wash\"},\"isActive\":true,\"sort\":1,\"isSpecial\":false,\"_id\":\"5e7a43bcd2dd69acb979d608\",\"slug\":\"basic-carwash\",\"coverImageId\":\"5e7a42d2d2dd69acb979d607\",\"categoryId\":\"5e7a40aed2dd69acb979d605\",\"basePrice\":29,\"listBasePrice\":29,\"coverImage\":{\"_id\":\"5e7a42d2d2dd69acb979d607\",\"path\":\"images/serviceCoverImage\",\"fileName\":\"15527a73-e489-4a65-8748-6db61ffc8958.jpg\",\"type\":\"serviceCoverImage\",\"thumbnails\":{\"has128x128\":false,\"has256x256\":false,\"has512x512\":false},\"createdAt\":\"2020-03-24T17:26:42.298Z\",\"updatedAt\":\"2020-03-24T17:30:38.013Z\",\"__v\":0,\"serviceId\":\"5e7a43bcd2dd69acb979d608\",\"originalUrl\":\"https://storage.googleapis.com/rafiji-staging/images/serviceCoverImage/original_15527a73-e489-4a65-8748-6db61ffc8958.jpg\",\"id\":\"5e7a42d2d2dd69acb979d607\"},\"image\":{\"originalUrl\":\"https://storage.googleapis.com/rafiji-staging/images/serviceImage/basic-carwash.png\",\"originalUrl@2x\":\"https://storage.googleapis.com/rafiji-staging/images/serviceImage/basic-carwash@2x.png\",\"originalUrl@3x\":\"https://storage.googleapis.com/rafiji-staging/images/serviceImage/basic-carwash@3x.png\",\"originalUrl@4x\":\"https://storage.googleapis.com/rafiji-staging/images/serviceImage/basic-carwash@4x.png\",\"originalUrlPDF\":\"https://storage.googleapis.com/rafiji-staging/images/serviceImage/basic-carwash.pdf\",\"originalUrlSVG\":\"https://storage.googleapis.com/rafiji-staging/images/serviceImage/basic-carwash.svg\"},\"title\":\"Basic\",\"subTitle\":\"Basic\",\"shortDescription\":\"Exterior Wash\",\"hasDiscount\":false,\"discountPercentage\":0,\"id\":\"5e7a43bcd2dd69acb979d608\"},{\"titles\":{\"en\":\"Eco\",\"ar\":\"Eco\"},\"subTitles\":{\"en\":\"Eco\",\"ar\":\"Eco\"},\"shortDescriptions\":{\"en\":\"Quick exterior steam wash\",\"ar\":\"Quick exterior steam wash\"},\"isActive\":true,\"sort\":2,\"isSpecial\":false,\"_id\":\"5e7a4677d2dd69acb979d610\",\"slug\":\"eco-carwash\",\"coverImageId\":\"5e7a453fd2dd69acb979d60f\",\"categoryId\":\"5e7a40aed2dd69acb979d605\",\"basePrice\":39,\"listBasePrice\":39,\"coverImage\":{\"_id\":\"5e7a453fd2dd69acb979d60f\",\"path\":\"images/serviceCoverImage\",\"fileName\":\"28004b7d-4860-4133-bbcb-01a648dd5cf7.jpg\",\"type\":\"serviceCoverImage\",\"thumbnails\":{\"has128x128\":false,\"has256x256\":false,\"has512x512\":false},\"createdAt\":\"2020-03-24T17:37:03.637Z\",\"updatedAt\":\"2020-03-24T17:42:21.009Z\",\"__v\":0,\"serviceId\":\"5e7a4677d2dd69acb979d610\",\"originalUrl\":\"https://storage.googleapis.com/rafiji-staging/images/serviceCoverImage/original_28004b7d-4860-4133-bbcb-01a648dd5cf7.jpg\",\"id\":\"5e7a453fd2dd69acb979d60f\"},\"image\":{\"originalUrl\":\"https://storage.googleapis.com/rafiji-staging/images/serviceImage/eco-carwash.png\",\"originalUrl@2x\":\"https://storage.googleapis.com/rafiji-staging/images/serviceImage/eco-carwash@2x.png\",\"originalUrl@3x\":\"https://storage.googleapis.com/rafiji-staging/images/serviceImage/eco-carwash@3x.png\",\"originalUrl@4x\":\"https://storage.googleapis.com/rafiji-staging/images/serviceImage/eco-carwash@4x.png\",\"originalUrlPDF\":\"https://storage.googleapis.com/rafiji-staging/images/serviceImage/eco-carwash.pdf\",\"originalUrlSVG\":\"https://storage.googleapis.com/rafiji-staging/images/serviceImage/eco-carwash.svg\"},\"title\":\"Eco\",\"subTitle\":\"Eco\",\"shortDescription\":\"Quick exterior steam wash\",\"hasDiscount\":false,\"discountPercentage\":0,\"id\":\"5e7a4677d2dd69acb979d610\"},{\"titles\":{\"en\":\"Pro\",\"ar\":\"Pro\"},\"subTitles\":{\"en\":\"Pro\",\"ar\":\"Pro\"},\"shortDescriptions\":{\"en\":\"Interior, ring, tire, etc\",\"ar\":\"Interior, ring, tire, etc\"},\"isActive\":true,\"sort\":3,\"isSpecial\":false,\"_id\":\"5e7a488bd2dd69acb979d61a\",\"slug\":\"pro-carwash\",\"coverImageId\":\"5e7a47dfd2dd69acb979d619\",\"categoryId\":\"5e7a40aed2dd69acb979d605\",\"basePrice\":69,\"listBasePrice\":69,\"coverImage\":{\"_id\":\"5e7a47dfd2dd69acb979d619\",\"path\":\"images/serviceCoverImage\",\"fileName\":\"600f1e25-7084-4317-be3a-26b943f7a7e8.jpg\",\"type\":\"serviceCoverImage\",\"thumbnails\":{\"has128x128\":false,\"has256x256\":false,\"has512x512\":false},\"createdAt\":\"2020-03-24T17:48:15.282Z\",\"updatedAt\":\"2020-03-24T17:51:10.209Z\",\"__v\":0,\"serviceId\":\"5e7a488bd2dd69acb979d61a\",\"originalUrl\":\"https://storage.googleapis.com/rafiji-staging/images/serviceCoverImage/original_600f1e25-7084-4317-be3a-26b943f7a7e8.jpg\",\"id\":\"5e7a47dfd2dd69acb979d619\"},\"image\":{\"originalUrl\":\"https://storage.googleapis.com/rafiji-staging/images/serviceImage/pro-carwash.png\",\"originalUrl@2x\":\"https://storage.googleapis.com/rafiji-staging/images/serviceImage/pro-carwash@2x.png\",\"originalUrl@3x\":\"https://storage.googleapis.com/rafiji-staging/images/serviceImage/pro-carwash@3x.png\",\"originalUrl@4x\":\"https://storage.googleapis.com/rafiji-staging/images/serviceImage/pro-carwash@4x.png\",\"originalUrlPDF\":\"https://storage.googleapis.com/rafiji-staging/images/serviceImage/pro-carwash.pdf\",\"originalUrlSVG\":\"https://storage.googleapis.com/rafiji-staging/images/serviceImage/pro-carwash.svg\"},\"title\":\"Pro\",\"subTitle\":\"Pro\",\"shortDescription\":\"Interior, ring, tire, etc\",\"hasDiscount\":false,\"discountPercentage\":0,\"id\":\"5e7a488bd2dd69acb979d61a\"},{\"titles\":{\"en\":\"VIP\",\"ar\":\"VIP\"},\"subTitles\":{\"en\":\"VIP\",\"ar\":\"VIP\"},\"shortDescriptions\":{\"en\":\"Interior & Exterior\",\"ar\":\"Interior & Exterior\"},\"isActive\":true,\"sort\":4,\"isSpecial\":true,\"_id\":\"5e7a499cd2dd69acb979d628\",\"slug\":\"vip-carwash\",\"coverImageId\":\"5e7a48f3d2dd69acb979d627\",\"categoryId\":\"5e7a40aed2dd69acb979d605\",\"basePrice\":149,\"listBasePrice\":149,\"coverImage\":{\"_id\":\"5e7a48f3d2dd69acb979d627\",\"path\":\"images/serviceCoverImage\",\"fileName\":\"5e9af05b-6faa-4dd1-90c5-b8ea9639bbe9.jpg\",\"type\":\"serviceCoverImage\",\"thumbnails\":{\"has128x128\":false,\"has256x256\":false,\"has512x512\":false},\"createdAt\":\"2020-03-24T17:52:51.496Z\",\"updatedAt\":\"2020-03-24T17:55:43.310Z\",\"__v\":0,\"serviceId\":\"5e7a499cd2dd69acb979d628\",\"originalUrl\":\"https://storage.googleapis.com/rafiji-staging/images/serviceCoverImage/original_5e9af05b-6faa-4dd1-90c5-b8ea9639bbe9.jpg\",\"id\":\"5e7a48f3d2dd69acb979d627\"},\"image\":{\"originalUrl\":\"https://storage.googleapis.com/rafiji-staging/images/serviceImage/vip-carwash.png\",\"originalUrl@2x\":\"https://storage.googleapis.com/rafiji-staging/images/serviceImage/vip-carwash@2x.png\",\"originalUrl@3x\":\"https://storage.googleapis.com/rafiji-staging/images/serviceImage/vip-carwash@3x.png\",\"originalUrl@4x\":\"https://storage.googleapis.com/rafiji-staging/images/serviceImage/vip-carwash@4x.png\",\"originalUrlPDF\":\"https://storage.googleapis.com/rafiji-staging/images/serviceImage/vip-carwash.pdf\",\"originalUrlSVG\":\"https://storage.googleapis.com/rafiji-staging/images/serviceImage/vip-carwash.svg\"},\"title\":\"VIP\",\"subTitle\":\"VIP\",\"shortDescription\":\"Interior & Exterior\",\"hasDiscount\":false,\"discountPercentage\":0,\"id\":\"5e7a499cd2dd69acb979d628\"}]}"
}