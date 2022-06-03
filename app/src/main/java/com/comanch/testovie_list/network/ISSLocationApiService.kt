package com.comanch.testovie_list.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL =
    "https://api.nasa.gov/planetary/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ISSLocationApiService {


    @GET("apod")
    suspend fun getImage(
        @Query("api_key") api_key: String = "DEMO_KEY",
        @Query("count") count: Int = 1,
    ) : List<ImageFromCosmos>
}

object ISSLocationApi {
    val retrofitService: ISSLocationApiService by lazy { retrofit.create(ISSLocationApiService::class.java) }
}