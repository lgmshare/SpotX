package com.firstpoli.spotx.ui

import android.os.Build
import androidx.annotation.RequiresApi
import com.firstpoli.spotx.App
import com.firstpoli.spotx.databinding.ArticleDetailActivityBinding
import com.firstpoli.spotx.extensions.dp2px
import com.firstpoli.spotx.extensions.getScreenWidth
import com.firstpoli.spotx.extensions.setMargin
import com.firstpoli.spotx.models.Article

class ArticleDetailActivity : BaseActivity<ArticleDetailActivityBinding>() {

    override fun buildLayoutBinding(): ArticleDetailActivityBinding {
        return ArticleDetailActivityBinding.inflate(layoutInflater)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun initView() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        val widthSize = App.INSTANCE.getScreenWidth() - App.INSTANCE.dp2px(40f)
        binding.articleItem.layoutParams = binding.articleItem.layoutParams.apply {
            width = widthSize
        }
        binding.articleBg.layoutParams = binding.articleBg.layoutParams.apply {
            width = widthSize
            height = (widthSize / 2.67).toInt()
        }
        binding.articleTitle.setMargin((widthSize / 2.96).toInt(), App.INSTANCE.dp2px(20f), App.INSTANCE.dp2px(12f), App.INSTANCE.dp2px(20f))

        val article = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("article", Article::class.java)
        } else {
            intent.getParcelableExtra("article")
        }
        article?.also {
            binding.articleTitle.text = it.title
            binding.articleContent.text = it.content
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }
}