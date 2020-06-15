package com.example.newssearch.adapter

import com.example.newssearch.model.entity.Doc

interface DocListener {
    fun onClickItem(item: Doc)
    fun onLoadMore()
}