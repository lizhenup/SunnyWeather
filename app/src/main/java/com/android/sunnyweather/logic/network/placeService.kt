package com.android.sunnyweather.logic.network

import com.android.sunnyweather.SunnyWeatherApplication
import com.android.sunnyweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {

    //访问网络数据
    @GET("v2/place?token${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>

}