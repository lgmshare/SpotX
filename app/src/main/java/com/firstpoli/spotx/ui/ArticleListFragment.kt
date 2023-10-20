package com.firstpoli.spotx.ui

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.firstpoli.spotx.R
import com.firstpoli.spotx.databinding.ArticleListFragmentBinding
import com.firstpoli.spotx.extensions.dp2px
import com.firstpoli.spotx.extensions.getStatusBarHeight
import com.firstpoli.spotx.extensions.setMargin
import com.firstpoli.spotx.models.Article

class ArticleListFragment : BaseFragment<ArticleListFragmentBinding>() {

    private val adapter by lazy {
        ArticleAdapter()
    }

    override fun buildLayoutBinding(): ArticleListFragmentBinding {
        return ArticleListFragmentBinding.inflate(layoutInflater)
    }

    override fun initView() {
        val top = requireActivity().getStatusBarHeight() + requireActivity().dp2px(88f)
        binding.recyclerView.setMargin(0, top, 0, 0)

        val list = mutableListOf<Article>().apply {
            add(Article(0, getString(R.string.article_title_1), getString(R.string.article_content_1)))
            add(Article(1, getString(R.string.article_title_2), getString(R.string.article_content_2)))
            add(Article(2, getString(R.string.article_title_3), getString(R.string.article_content_3)))
            add(Article(3, getString(R.string.article_title_4), getString(R.string.article_content_4)))
        }
        adapter.dataList.addAll(list)
        adapter.itemClickCallback = { item, position ->
            startActivity(Intent(requireActivity(), ArticleDetailActivity::class.java).apply {
                putExtra("article", item)
            })
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

    }
}