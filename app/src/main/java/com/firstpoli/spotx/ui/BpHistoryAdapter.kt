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
import com.firstpoli.spotx.models.BpData
import com.firstpoli.spotx.utils.Utils

class BpHistoryAdapter : RecyclerView.Adapter<BpHistoryAdapter.ItemViewHolder>() {

    var dataList: ArrayList<BpData> = arrayListOf()

    inner class ItemViewHolder : RecyclerView.ViewHolder {

        val stage_level: View
        val stage_title: TextView
        val tv_systolic: TextView
        val tv_diastolic: TextView
        val tv_pulse: TextView
        val tv_datetime: TextView
        val btn_edit: View

        constructor(item: View) : super(item) {
            stage_level = item.findViewById(R.id.stage_level)
            stage_title = item.findViewById(R.id.stage_title)
            tv_systolic = item.findViewById(R.id.tv_systolic)
            tv_diastolic = item.findViewById(R.id.tv_diastolic)
            tv_pulse = item.findViewById(R.id.tv_pulse)
            tv_datetime = item.findViewById(R.id.tv_datetime)
            btn_edit = item.findViewById(R.id.btn_edit)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bp_history, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataList[position]

        when (item.level()) {
            0 -> {
                holder.stage_level.setBackgroundColor(holder.itemView.context.getColor(R.color.c_7dff58))
                holder.stage_title.setText(R.string.levle_title_1)
            }

            1 -> {
                holder.stage_level.setBackgroundColor(holder.itemView.context.getColor(R.color.c_5294ff))
                holder.stage_title.setText(R.string.levle_title_2)
            }

            2 -> {
                holder.stage_level.setBackgroundColor(holder.itemView.context.getColor(R.color.c_ffe14c))
                holder.stage_title.setText(R.string.levle_title_3)
            }

            3 -> {
                holder.stage_level.setBackgroundColor(holder.itemView.context.getColor(R.color.c_f3a336))
                holder.stage_title.setText(R.string.levle_title_4)
            }

            4 -> {
                holder.stage_level.setBackgroundColor(holder.itemView.context.getColor(R.color.c_ff7b2d))
                holder.stage_title.setText(R.string.levle_title_5)
            }

            5 -> {
                holder.stage_level.setBackgroundColor(holder.itemView.context.getColor(R.color.c_fa2f23))
                holder.stage_title.setText(R.string.levle_title_6)
            }
        }

        holder.tv_systolic.text = item.systolic.toString()
        holder.tv_diastolic.text = item.diastolic.toString()
        holder.tv_pulse.text = item.pulse.toString()
        holder.tv_datetime.text = Utils.formatRecordDatetime(item.datetime)

        holder.itemView.setOnClickListener {
            itemClickCallback?.invoke(item, position)
        }

    }

    var itemClickCallback: ((BpData, Int) -> Unit)? = null

}