package com.firstpoli.spotx.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
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

class StateAdapter : RecyclerView.Adapter<StateAdapter.ItemViewHolder>() {

    var dataList: ArrayList<String> = arrayListOf()

    var checkedIndex = 0

    inner class ItemViewHolder : RecyclerView.ViewHolder {

        val state_title: TextView
        val state_checkbox: CheckBox

        constructor(item: View) : super(item) {
            state_title = item.findViewById(R.id.state_title)
            state_checkbox = item.findViewById(R.id.state_checkbox)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_record_condition, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataList[position]
        holder.state_title.text = item

        if (checkedIndex == position) {
            holder.state_checkbox.setButtonDrawable(R.mipmap.ic_checked)
        } else {
            holder.state_checkbox.setButtonDrawable(R.mipmap.ic_unchecked)
        }

        holder.itemView.setOnClickListener {
            itemClickCallback?.invoke(item, position)
        }
    }

    var itemClickCallback: ((String, Int) -> Unit)? = null

}