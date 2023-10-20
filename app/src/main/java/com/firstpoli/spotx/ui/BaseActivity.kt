package com.firstpoli.spotx.ui

import android.app.ActionBar
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.firstpoli.spotx.databinding.DialogClearBinding
import com.firstpoli.spotx.databinding.DialogSimpleBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    lateinit var binding: VB

    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = buildLayoutBinding()
        setContentView(binding.root)
        initView()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    abstract fun buildLayoutBinding(): VB

    abstract fun initView()

    fun showClearDialog(confirmCallback: (() -> Unit)) {
        if (dialog?.isShowing == true) {
            dialog?.dismiss()
        }

        val rootBinding = DialogClearBinding.inflate(layoutInflater)
        dialog = Dialog(this).apply {
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            setContentView(rootBinding.root)
        }

        rootBinding.btnConfirm.setOnClickListener {
            dialog?.dismiss()
            confirmCallback.invoke()
        }
        rootBinding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }

        dialog?.window?.also {
            it.attributes?.also { attr ->
                attr.width = ActionBar.LayoutParams.MATCH_PARENT
                attr.height = ActionBar.LayoutParams.WRAP_CONTENT
                it.attributes = attr
            }
        }
        dialog?.show()
    }

    fun showTipsDialog(confirmCallback: (() -> Unit)) {
        if (dialog?.isShowing == true) {
            dialog?.dismiss()
        }
        val rootBinding = DialogSimpleBinding.inflate(layoutInflater)
        dialog = Dialog(this).apply {
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            setContentView(rootBinding.root)
        }

        rootBinding.btnConfirm.setOnClickListener {
            dialog?.dismiss()
            confirmCallback.invoke()
        }
        rootBinding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }
        dialog?.window?.also {
            it.attributes?.also { attr ->
                attr.width = ActionBar.LayoutParams.MATCH_PARENT
                attr.height = ActionBar.LayoutParams.WRAP_CONTENT
                it.attributes = attr
            }
        }
        dialog?.show()
    }

    override fun onPause() {
        super.onPause()
        if (dialog?.isShowing == true) {
            dialog?.dismiss()
        }
        dialog = null
    }



}