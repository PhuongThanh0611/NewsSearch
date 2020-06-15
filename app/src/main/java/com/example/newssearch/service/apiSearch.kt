package com.example.newssearch.service

import com.example.newssearch.constant.const.Companion.BASE_API
import com.example.newssearch.model.entity.ResultStory
import retrofit2.http.GET
import retrofit2.http.Query
import io.reactivex.Observable

interface apiSearch {
        @GET("articlesearch.json")
        fun getSettings(
                @Query("page") page:Int,
                @Query("sort") sort:String? =null,
                @Query("begin_date") begin_date:String?=null,
                @Query("fq") fq:String?=null,
                @Query("q") q: String?,
                @Query("api-key") apikey:String = BASE_API

        ):Observable<ResultStory>
        @GET("articlesearch.json")
        fun getSearch(
                @Query("page") page:Int,
                @Query("q") q: String?,
                @Query("api-key") apikey:String = BASE_API
        ):Observable<ResultStory>


}