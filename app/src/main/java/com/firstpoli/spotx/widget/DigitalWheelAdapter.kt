package com.firstpoli.spotx.widget

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.firstpoli.spotx.R
import com.firstpoli.spotx.extensions.dp2px
import com.firstpoli.spotx.extensions.sp2px

class DigitalWheelAdapter : BaseQuickAdapter<String, DigitalWheelAdapter.ItemViewHolder>() {

    private var padding = 0

    override fun onBindViewHolder(viewHolder: ItemViewHolder, i: Int, item: String?) {
        viewHolder.textView.text = item
        padding = context.dp2px(12f)
    }

    override fun onCreateViewHolder(context: Context, viewGroup: ViewGroup, i: Int): ItemViewHolder {
        val appCompatTextView = AppCompatTextView(viewGroup.context)
        appCompatTextView.setTextColor(context.getColor(R.color.black))
        appCompatTextView.includeFontPadding = false
        appCompatTextView.gravity = Gravity.CENTER
        appCompatTextView.setBackgroundColor(0)
        appCompatTextView.paint.isFakeBoldText = true
        appCompatTextView.setPadding(padding, padding, padding, padding)
        TextViewCompat.setAutoSizeTextTypeWithDefaults(appCompatTextView, 1)
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(appCompatTextView, context.sp2px(12f), context.sp2px(24f), 1, 0)
        appCompatTextView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return ItemViewHolder(appCompatTextView)
    }

    inner class ItemViewHolder : RecyclerView.ViewHolder {

        val textView: TextView

        constructor(item: TextView) : super(item) {
            textView = item
        }
    }
}