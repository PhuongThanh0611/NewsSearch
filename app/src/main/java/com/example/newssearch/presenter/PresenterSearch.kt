package com.example.newssearch.presenter

import android.util.Log
import com.example.newssearch.RepositorySearch
import com.example.newssearch.service.apiSearch
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PresenterSearch(private var callback: PresenterToView) {

    fun requestSearch(page: Int,sortOrder:String?,beginDate:String?,fq:String?, q:String?) {
        RepositorySearch.createService(apiSearch::class.java)
            .getSearch(page,sortOrder,beginDate,fq, q).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                //request thành công
                Log.d("arrmovie", result.toString())
                callback.CallApiSuccess(result.response)
            },
                callback::CallApiFailed
            )
    }

}