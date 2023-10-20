package com.firstpoli.spotx.ui

import android.animation.ValueAnimator
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.firstpoli.spotx.extensions.progressAnimation
import com.firstpoli.spotx.firebase.FirebaseEventUtil
import com.firstpoli.spotx.utils.SharePrefUtils
import com.firstpoli.spotx.utils.Utils
import com.firstpoli.spotx.AppActivityManager
import com.firstpoli.spotx.databinding.SplashActivityBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import java.util.Locale

class SplashActivity : BaseActivity<SplashActivityBinding>() {

    private var job: Job? = null

    private var progressAnimator: ValueAnimator? = null

    override fun buildLayoutBinding(): SplashActivityBinding {
        return SplashActivityBinding.inflate(layoutInflater)
    }

    override fun initView() {
        if (SharePrefUtils.isFirstLaunch) {
//            FirebaseEventUtil.event("sweet_first")
//            SharePrefUtils.isFirstLaunch = false
//            SharePrefUtils.country = Locale.getDefault().country
        }
    }

    override fun onStart() {
        super.onStart()
//        if (!AppActivityManager.isHasActivityStack(MainActivity::class.java)) {
//            FirebaseEventUtil.event("sweet_cold")
//        } else {
//            FirebaseEventUtil.event("sweet_hot")
//        }
//        Utils.log("firebase属性:country=${SharePrefUtils.country}")
//        FirebaseEventUtil.setProperty(SharePrefUtils.country)

        binding.progressbar.progress = 0
        job = lifecycleScope.launch {
            kotlin.runCatching {
                withTimeoutOrNull(3000) {
                    startProgressAnimation(2500)
                    launch {
                        delay(2500)
                    }
                }
            }.onSuccess {
                if (!AppActivityManager.isHasActivityStack(MainActivity::class.java)) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                }
                finish()
            }.onFailure {
            }
        }
    }

    override fun onStop() {
        super.onStop()
        stopProgressAnimation()
        job?.cancel()
    }

    override fun onBackPressed() {
    }

    private fun startProgressAnimation(duration: Long) {
        progressAnimator?.cancel()
        progressAnimator = binding.progressbar.progressAnimation(duration, binding.progressbar.progress)
    }

    private fun stopProgressAnimation() {
        progressAnimator?.cancel()
        progressAnimator = null
    }
}
