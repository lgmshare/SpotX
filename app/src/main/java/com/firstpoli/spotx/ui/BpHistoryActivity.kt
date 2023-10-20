package com.firstpoli.spotx.ui

import android.content.Intent
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.firstpoli.spotx.databases.AppDatabase
import com.firstpoli.spotx.databinding.BpHistoryActivityBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BpHistoryActivity : BaseActivity<BpHistoryActivityBinding>() {

    private var loadJob: Job? = null

    private val adapter by lazy {
        BpHistoryAdapter()
    }

    override fun buildLayoutBinding(): BpHistoryActivityBinding {
        return BpHistoryActivityBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        adapter.itemClickCallback = { item, position ->
            startActivity(Intent(this@BpHistoryActivity, NewRecordActivity::class.java).apply {
                putExtra("bpData", item)
            })
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    override fun onStop() {
        super.onStop()
        loadJob?.cancel()
    }

    private fun loadData() {
        loadJob = lifecycleScope.launch {
            kotlin.runCatching {
                val data = AppDatabase.getInstance().alarmDao().queryAll()
                val list = data.reversed()
                list
            }.onSuccess { data ->
                if (data.isNotEmpty()) {
                    adapter.dataList.clear()
                    adapter.dataList.addAll(data)
                    adapter.notifyDataSetChanged()
                }
            }.onFailure {

            }
        }
    }
}