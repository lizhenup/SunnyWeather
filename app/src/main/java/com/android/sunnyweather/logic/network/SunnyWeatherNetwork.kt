package com.android.sunnyweather.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object SunnyWeatherNetwork {

    private val placeService = ServiceCreator.create<PlaceService>() //创建动态代理对象

    private val weatherService = ServiceCreator.create(WeatherService::class.java)

    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    suspend fun getDailyWeather(lng: String, lat: String) =
            weatherService.getDailyWeather(lng, lat).await()

    suspend fun getRealtimeWeather(lng: String, lat: String) =
            weatherService.getRealtimeWeather(lng, lat).await()

    private suspend fun <T> Call<T>.await(): T {//挂起函数 Call的扩展函数
        return suspendCoroutine { continuation -> //立即挂起当前协程，在一个普通线程中执行Lambda表达式
            enqueue(object : Callback<T> { //enqueue 发起网络请求
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body) //body不为空恢复协程的运行
                    else continuation.resumeWithException(
                            RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            })
        }

    }
}