package com.firstpoli.spotx.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.firstpoli.spotx.App
import com.firstpoli.spotx.R
import com.firstpoli.spotx.extensions.dp2px
import com.firstpoli.spotx.extensions.getScreenWidth
import com.firstpoli.spotx.extensions.setMargin
import com.firstpoli.spotx.models.Article

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ItemViewHolder>() {

    var dataList: ArrayList<Article> = arrayListOf()

    inner class ItemViewHolder : RecyclerView.ViewHolder {

        val article_item: ConstraintLayout
        val article_bg: ImageView
        val article_title: TextView

        constructor(item: View) : super(item) {
            article_item = item.findViewById(R.id.article_item)
            article_bg = item.findViewById(R.id.article_bg)
            article_title = item.findViewById(R.id.article_title)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataList[position]
        val widthSize = App.INSTANCE.getScreenWidth() - App.INSTANCE.dp2px(40f)
        holder.article_item.layoutParams = holder.article_item.layoutParams.apply {
            width = widthSize
        }
        holder.article_bg.layoutParams = holder.article_bg.layoutParams.apply {
            width = widthSize
            height = (widthSize / 2.67).toInt()
        }
        holder.article_title.setMargin((widthSize / 2.96).toInt(), App.INSTANCE.dp2px(20f), App.INSTANCE.dp2px(12f), App.INSTANCE.dp2px(20f))

        holder.itemView.setOnClickListener {
            itemClickCallback?.invoke(item, position)
        }
        when (item.id) {
            0 -> {
                holder.article_bg.setImageResource(R.mipmap.at_1)
            }

            1 -> {
                holder.article_bg.setImageResource(R.mipmap.at_2)
            }

            2 -> {
                holder.article_bg.setImageResource(R.mipmap.at_3)
            }

            3 -> {
                holder.article_bg.setImageResource(R.mipmap.at_4)
            }
        }
        holder.article_title.text = item.title
    }

    var itemClickCallback: ((Article, Int) -> Unit)? = null

}