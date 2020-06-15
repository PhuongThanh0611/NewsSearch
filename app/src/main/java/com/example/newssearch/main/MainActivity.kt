package com.example.newssearch.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.newssearch.R
import com.example.newssearch.adapter.DocListener
import com.example.newssearch.adapter.DocsAdapter
import com.example.newssearch.model.entity.Doc
import com.example.newssearch.model.entity.Response
import com.example.newssearch.presenter.PresenterSearch
import com.example.newssearch.presenter.PresenterToView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), PresenterToView, DocListener {
    private var presenter: PresenterSearch? = null
    private lateinit var docsAdapter: DocsAdapter
    private var currentPage = 1
    private var progressBar: ProgressBar? = null

    // các biến trả về bên màn hình setting
    private var beginDate: String? = null
    private var sortOrder: String? = null
    private var news: String? = null
    private var search: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
        presenter = PresenterSearch(this)
        presenter?.requestSettings(currentPage, sortOrder, beginDate, news, search)
        setEvent()

    }

    // xử lý button search
    private fun setEvent() {

        btnSearch.setOnClickListener {
            search = etSearch.text.toString()
            currentPage=1
            //currentPage = progressBar!!.progress
            simpleProgressBar.visibility=(View.VISIBLE)
            presenter?.requestSettings(currentPage, null, null, null, search)

        }
    }

    //khoi tao recyclerview
    private fun initRecyclerView() {
        docsAdapter = DocsAdapter(arrayListOf(), this)
        rvList.adapter = this.docsAdapter
        rvList.setHasFixedSize(true)
    }

    //gọi menubar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // xử lý item của menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.setting) {
            val intent = Intent(this, MainSettings::class.java)
            startActivityForResult(intent, 999)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun CallApiSuccess(result: Response) {
        docsAdapter.AddList(result.docs,currentPage)
        simpleProgressBar.visibility=View.GONE
    }
    override fun CallApiFailed(error: Throwable) {
        Log.d("bbb", "Error ${error.localizedMessage}")
        Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
    }

    override fun onClickItem(item: Doc) {

        val url: String = item.webUrl
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)

    }

    override fun onLoadMore() {
        currentPage++
        presenter?.requestSettings(currentPage, sortOrder, beginDate, news, search)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 999) {
            //presenter = PresenterSearch(this)
            if (data != null) {
                beginDate = data.getStringExtra("beginDate")
                sortOrder = data.getStringExtra("sortOrder")
                news = data.getStringExtra("news")
                }
            currentPage=1
            simpleProgressBar.visibility=(View.VISIBLE)
            presenter?.requestSettings(currentPage, sortOrder, beginDate, news, null)
        }

    }
}
