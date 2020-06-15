package com.example.newssearch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.newssearch.R
import com.example.newssearch.constant.const.Companion.BASE_IMG
import com.example.newssearch.main.MainActivity
import com.example.newssearch.model.entity.Doc
import kotlinx.android.synthetic.main.layout_item.view.*

class DocsAdapter(private val listDocs: ArrayList<Doc>, private var listener: MainActivity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false)
        return ViewHolderDoc(view)
    }

    override fun getItemCount(): Int {
        return listDocs.count()
    }

    fun AddList(listDocs: List<Doc>,page :Int) {
        if(page ==1){
            this.listDocs.clear()

        }
        this.listDocs.apply {
            addAll(listDocs)
        }
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == listDocs.size - 2) listener.onLoadMore()
        (holder as ViewHolderDoc).bind(listDocs[position], listener)
    }

    class ViewHolderDoc(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(doc: Doc, itemClick: MainActivity) {
            if (doc.multimedia.isNotEmpty()) {
                Glide.with(view.context)
                    .load(BASE_IMG + doc.multimedia.first().url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(7)))
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .into(itemView.img)

            }
            itemView.tvInfo.text = doc.snippet
            itemView.setOnClickListener {
                itemClick.onClickItem(doc)
            }
        }
    }

}