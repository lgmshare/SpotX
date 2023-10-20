package com.firstpoli.spotx.ui

import android.content.Intent
import com.firstpoli.spotx.databinding.FragmentSettingBinding

class SettingFragment : BaseFragment<FragmentSettingBinding>() {
    override fun buildLayoutBinding(): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(layoutInflater)
    }

    override fun initView() {

        binding.btnMore.setOnClickListener {
            startActivity(Intent(requireActivity(), PrivacyActivity::class.java))
        }

    }
}