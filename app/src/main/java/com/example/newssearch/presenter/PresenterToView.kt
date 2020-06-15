package com.example.newssearch.presenter

import com.example.newssearch.model.entity.Response

interface PresenterToView {
    fun CallApiSuccess(result: Response)
    fun CallApiFailed(error: Throwable)
}