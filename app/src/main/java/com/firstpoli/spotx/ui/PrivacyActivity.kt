package com.firstpoli.spotx.ui

import com.firstpoli.spotx.databinding.PrivacyActivityBinding

class PrivacyActivity : BaseActivity<PrivacyActivityBinding>() {

    override fun buildLayoutBinding(): PrivacyActivityBinding {
        return PrivacyActivityBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }
}