package com.firstpoli.spotx.ui

import android.content.Intent
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.firstpoli.spotx.R
import com.firstpoli.spotx.databases.AppDatabase
import com.firstpoli.spotx.databinding.FragmentBpBinding
import com.firstpoli.spotx.models.BpCountData
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BpFragment : BaseFragment<FragmentBpBinding>() {

    private var loadJob: Job? = null

    private var countType = 1
    private val lastData = BpCountData()
    private val averageData = BpCountData()
    private val average24Data = BpCountData()
    private val average72Data = BpCountData()
    private val averageWeekData = BpCountData()

    override fun buildLayoutBinding(): FragmentBpBinding {
        return FragmentBpBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.run {
            btnAdd.setOnClickListener {
                startActivity(Intent(requireActivity(), NewRecordActivity::class.java))
            }

            btnStartRecord.setOnClickListener {
                startActivity(Intent(requireActivity(), NewRecordActivity::class.java))
            }

            btnHistory.setOnClickListener {
                startActivity(Intent(requireActivity(), BpHistoryActivity::class.java))
            }

            btnLast.setOnClickListener {
                countType--
                if (countType < 0) {
                    countType = 4
                }
                updateCountView()
            }

            btnNext.setOnClickListener {
                countType++
                if (countType > 4) {
                    countType = 0
                }
                updateCountView()
            }

        }
    }

    override fun onResume() {
        super.onResume()
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
                    binding.layoutEmpty.isVisible = false
                    binding.layoutRecord.isVisible = true

                    lastData.clean()
                    averageData.clean()
                    average24Data.clean()
                    average72Data.clean()
                    averageWeekData.clean()

                    lastData.totalSystolic = data[0].systolic
                    lastData.totalDiastolic = data[0].diastolic
                    lastData.totalPulse = data[0].pulse

                    val currentTime = System.currentTimeMillis()
                    data.forEach {
                        averageData.totalSystolic += it.systolic
                        averageData.totalDiastolic += it.diastolic
                        averageData.totalPulse += it.pulse
                        averageData.count += 1

                        //24小时
                        if (it.datetime >= currentTime - 24 * 60 * 60 * 1000) {
                            average24Data.totalSystolic += it.systolic
                            average24Data.totalDiastolic += it.diastolic
                            average24Data.totalPulse += it.pulse
                            average24Data.count += 1
                        }
                        //72小时
                        if (it.datetime >= currentTime - 72 * 60 * 60 * 1000) {
                            average72Data.totalSystolic += it.systolic
                            average72Data.totalDiastolic += it.diastolic
                            average72Data.totalPulse += it.pulse
                            average72Data.count += 1
                        }
                        //一周
                        if (it.datetime >= currentTime - 7 * 24 * 60 * 60 * 1000) {
                            averageWeekData.totalSystolic += it.systolic
                            averageWeekData.totalDiastolic += it.diastolic
                            averageWeekData.totalPulse += it.pulse
                            averageWeekData.count += 1
                        }
                    }

                    updateCountView()
                } else {
                    binding.layoutEmpty.isVisible = true
                    binding.layoutRecord.isVisible = false
                }
            }.onFailure {

            }
        }
    }

    private fun updateCountView() {
        when (countType) {
            0 -> {
                binding.tvRecordType.setText(R.string.average)
                binding.tvSystolic.text = (averageData.averageCountSystolic()).toString()
                binding.tvDiastolic.text = (averageData.averageCountDiastolic()).toString()
                binding.tvPulse.text = (averageData.averageCountPulse()).toString()
            }

            1 -> {
                binding.tvRecordType.setText(R.string.latest_record)
                binding.tvSystolic.text = (lastData.totalSystolic).toString()
                binding.tvDiastolic.text = (lastData.totalDiastolic).toString()
                binding.tvPulse.text = (lastData.totalPulse).toString()
            }

            2 -> {
                binding.tvRecordType.setText(R.string.hour_72_average)
                binding.tvSystolic.text = (average72Data.averageCountSystolic()).toString()
                binding.tvDiastolic.text = (average72Data.averageCountDiastolic()).toString()
                binding.tvPulse.text = (average72Data.averageCountPulse()).toString()
            }

            3 -> {
                binding.tvRecordType.setText(R.string.one_week_average)
                binding.tvSystolic.text = (averageWeekData.averageCountSystolic()).toString()
                binding.tvDiastolic.text = (averageWeekData.averageCountDiastolic()).toString()
                binding.tvPulse.text = (averageWeekData.averageCountPulse()).toString()
            }

            4 -> {
                binding.tvRecordType.setText(R.string.hour_24_average)
                binding.tvSystolic.text = (average24Data.averageCountSystolic()).toString()
                binding.tvDiastolic.text = (average24Data.averageCountDiastolic()).toString()
                binding.tvPulse.text = (average24Data.averageCountPulse()).toString()
            }
        }
    }
}