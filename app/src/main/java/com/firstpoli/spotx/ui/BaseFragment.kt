package com.firstpoli.spotx.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.firstpoli.spotx.extensions.toast

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    protected open lateinit var binding: VB

    private var lazyVisible = true //数据懒加载

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        buildLayoutBinding().let { binding = it }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    protected abstract fun buildLayoutBinding(): VB

    /**
     * 初始化View
     * use with [onViewCreated]
     */
    protected abstract fun initView()


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        onLazyVisible()
    }

    override fun onDetach() {
        super.onDetach()
    }

    /**
     * 是否需要懒加载
     */
    private fun onLazyVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && lazyVisible) {
            view?.post {
                lazyVisible = false
                onLazyLoad()
            }
        }
    }

    /**
     * 懒加载
     */
    protected open fun onLazyLoad() {}

    fun showToast(@StringRes msgResId: Int) {
        if (!isDetached) {
            activity?.also {
                if (!it.isDestroyed) {
                    it.runOnUiThread {
                        it.toast(msgResId)
                    }
                }
            }
        }
    }

    fun showToast(msg: String?) {
        if (!isDetached && !msg.isNullOrEmpty()) {
            activity?.also {
                if (!it.isDestroyed) {
                    it.runOnUiThread {
                        it.toast(msg)
                    }
                }
            }
        }
    }
}