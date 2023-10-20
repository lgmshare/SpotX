package com.firstpoli.spotx.ui

import android.app.ActionBar
import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.view.Gravity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.firstpoli.spotx.BP_STATES
import com.firstpoli.spotx.R
import com.firstpoli.spotx.databases.AppDatabase
import com.firstpoli.spotx.databinding.DialogConditionBinding
import com.firstpoli.spotx.databinding.DialogDateBinding
import com.firstpoli.spotx.databinding.DialogLevelBinding
import com.firstpoli.spotx.databinding.NewRecordActivityBinding
import com.firstpoli.spotx.extensions.dp2px
import com.firstpoli.spotx.extensions.toast
import com.firstpoli.spotx.models.BpData
import com.firstpoli.spotx.utils.Utils
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NewRecordActivity : BaseActivity<NewRecordActivityBinding>() {

    private var choiceDialog: Dialog? = null

    private lateinit var bpData: BpData

    override fun buildLayoutBinding(): NewRecordActivityBinding {
        return NewRecordActivityBinding.inflate(layoutInflater)
    }

    override fun initView() {
        val lastData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("bpData", BpData::class.java)
        } else {
            intent.getParcelableExtra("bpData")
        }
        if (lastData != null) {
            bpData = lastData
        } else {
            bpData = BpData()
            bpData.systolic = 100
            bpData.diastolic = 70
            bpData.pulse = 70
            bpData.datetime = System.currentTimeMillis()
            bpData.state = BP_STATES[0]
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.stageLevel.setOnClickListener {
            showLevelDialog()
        }

        binding.itemCondition.text = bpData.state
        binding.itemCondition.setOnClickListener {
            showStateDialog {
                binding.itemCondition.text = getString(R.string.state_s, it)
                bpData.state = it
            }
        }
        binding.itemDate.text = Utils.formatRecordDatetime(bpData.datetime)

        if (bpData.id == 0) {
            binding.tvTitle.setText(R.string.new_record)
            binding.itemDate.setOnClickListener {
                showDateDialog {
                    binding.itemDate.text = Utils.formatRecordDatetime(it)
                    bpData.datetime = it
                }
            }
        } else {
            binding.itemDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            binding.tvTitle.setText(R.string.alter)
        }

        binding.btnSave.setOnClickListener {
            val systolic = binding.wheelSystolic.selectedData.toInt()
            val diastolic = binding.wheelDiastolic.selectedData.toInt()
            val pulse = binding.wheelPulse.selectedData.toInt()

            bpData.systolic = systolic
            bpData.diastolic = diastolic
            bpData.pulse = pulse

            if (systolic < diastolic) {
                toast(R.string.error_tips_1)
                return@setOnClickListener
            }

            if (bpData.datetime > System.currentTimeMillis()) {
                toast(R.string.error_tips_2)
                return@setOnClickListener
            }

            lifecycleScope.launch {
                if (bpData.id > 0) {
                    AppDatabase.getInstance().alarmDao().update(bpData)
                } else {
                    AppDatabase.getInstance().alarmDao().insert(bpData)
                }
                startActivity(Intent(this@NewRecordActivity, RecordActivity::class.java).apply {
                    putExtra("bpData", bpData)
                })
                finish()
            }
        }

        val hourList = ArrayList<String>()
        for (i in 60..240) {
            hourList.add("" + i)
        }

        binding.wheelSystolic.setWheelData(hourList)
        binding.wheelSystolic.setCurrentPosition(bpData.systolic - 60)
        binding.wheelSystolic.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val systolic = binding.wheelSystolic.selectedData
                bpData.systolic = systolic.toInt()
                updateLevelView()
            }
        })

        val minuteList = ArrayList<String>()
        for (i in 40..160) {
            minuteList.add("" + i)
        }
        binding.wheelDiastolic.setWheelData(minuteList)
        binding.wheelDiastolic.setCurrentPosition(bpData.diastolic - 40)
        binding.wheelDiastolic.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val diastolic = binding.wheelDiastolic.selectedData
                bpData.diastolic = diastolic.toInt()
                updateLevelView()
            }
        })

        val secondList = ArrayList<String>()
        for (i in 30..150) {
            secondList.add("" + i)
        }
        binding.wheelPulse.setWheelData(secondList)
        binding.wheelPulse.setCurrentPosition(bpData.pulse - 30)
        binding.wheelPulse.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val pulse = binding.wheelPulse.selectedData
                bpData.pulse = pulse.toInt()
                updateLevelView()
            }
        })

        updateLevelView()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    private fun updateLevelView() {
        binding.levelView1.layoutParams = binding.levelView1.layoutParams.apply {
            height = dp2px(4f)
        }
        binding.levelView2.layoutParams = binding.levelView2.layoutParams.apply {
            height = dp2px(4f)
        }
        binding.levelView3.layoutParams = binding.levelView3.layoutParams.apply {
            height = dp2px(4f)
        }
        binding.levelView4.layoutParams = binding.levelView4.layoutParams.apply {
            height = dp2px(4f)
        }
        binding.levelView5.layoutParams = binding.levelView5.layoutParams.apply {
            height = dp2px(4f)
        }
        binding.levelView6.layoutParams = binding.levelView6.layoutParams.apply {
            height = dp2px(4f)
        }
        when (bpData.level()) {
            0 -> {
                binding.levelView1.layoutParams = binding.levelView1.layoutParams.apply {
                    height = dp2px(16f)
                }
                binding.stageLevel.setText(R.string.levle_title_1)
                binding.stageIntroduce.setText(R.string.levle_tips_1)
            }

            1 -> {
                binding.levelView2.layoutParams = binding.levelView2.layoutParams.apply {
                    height = dp2px(16f)
                }
                binding.stageLevel.setText(R.string.levle_title_2)
                binding.stageIntroduce.setText(R.string.levle_tips_2)
            }

            2 -> {
                binding.levelView3.layoutParams = binding.levelView3.layoutParams.apply {
                    height = dp2px(16f)
                }
                binding.stageLevel.setText(R.string.levle_title_3)
                binding.stageIntroduce.setText(R.string.levle_tips_3)
            }

            3 -> {
                binding.levelView4.layoutParams = binding.levelView4.layoutParams.apply {
                    height = dp2px(16f)
                }
                binding.stageLevel.setText(R.string.levle_title_4)
                binding.stageIntroduce.setText(R.string.levle_tips_4)
            }

            4 -> {
                binding.levelView5.layoutParams = binding.levelView5.layoutParams.apply {
                    height = dp2px(16f)
                }
                binding.stageLevel.setText(R.string.levle_title_5)
                binding.stageIntroduce.setText(R.string.levle_tips_5)
            }

            5 -> {
                binding.levelView6.layoutParams = binding.levelView6.layoutParams.apply {
                    height = dp2px(16f)
                }
                binding.stageLevel.setText(R.string.levle_title_6)
                binding.stageIntroduce.setText(R.string.levle_tips_6)
            }
        }
    }

    private fun showLevelDialog() {
        if (choiceDialog?.isShowing == true) {
            choiceDialog?.dismiss()
        }
        val rootBinding = DialogLevelBinding.inflate(layoutInflater)
        choiceDialog = Dialog(this).apply {
            setContentView(rootBinding.root)
        }

        rootBinding.btnConfirm.setOnClickListener {
            choiceDialog?.dismiss()
        }

        choiceDialog?.window?.also {
            it.attributes?.also { attr ->
                attr.width = ActionBar.LayoutParams.MATCH_PARENT
                attr.height = ActionBar.LayoutParams.WRAP_CONTENT
                it.attributes = attr

            }
            it.setGravity(Gravity.BOTTOM)
            it.setBackgroundDrawableResource(R.color.transparent)
        }
        choiceDialog?.show()
    }

    private fun showStateDialog(confirmCallback: ((String) -> Unit)) {
        if (choiceDialog?.isShowing == true) {
            choiceDialog?.dismiss()
        }
        val rootBinding = DialogConditionBinding.inflate(layoutInflater)
        choiceDialog = Dialog(this).apply {
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            setContentView(rootBinding.root)
        }

        val list = BP_STATES
        val adapter = StateAdapter()
        adapter.dataList.addAll(list)
        adapter.itemClickCallback = { item, position ->
            adapter.checkedIndex = position
            adapter.notifyDataSetChanged()
        }

        rootBinding.recyclerView.layoutManager = LinearLayoutManager(this@NewRecordActivity)
        rootBinding.recyclerView.adapter = adapter

        rootBinding.btnConfirm.setOnClickListener {
            choiceDialog?.dismiss()

            val name = adapter.dataList[adapter.checkedIndex]
            confirmCallback.invoke(name)
        }

        choiceDialog?.window?.also {
            it.attributes?.also { attr ->
                attr.width = ActionBar.LayoutParams.MATCH_PARENT
                attr.height = ActionBar.LayoutParams.WRAP_CONTENT
                it.attributes = attr

            }
            it.setGravity(Gravity.BOTTOM)
            it.setBackgroundDrawableResource(R.color.transparent)
        }
        choiceDialog?.show()
    }

    private fun showDateDialog(confirmCallback: ((Long) -> Unit)) {
        if (choiceDialog?.isShowing == true) {
            choiceDialog?.dismiss()
        }
        val rootBinding = DialogDateBinding.inflate(layoutInflater)
        choiceDialog = Dialog(this).apply {
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            setContentView(rootBinding.root)
        }

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = bpData.datetime
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val yearList = ArrayList<String>()
        for (i in 2020..2030) {
            yearList.add("" + i)
        }
        rootBinding.wheelYear.setWheelData(yearList)
        rootBinding.wheelYear.setCurrentPosition(year - 2020)

        val monthList = ArrayList<String>()
        for (i in 1..12) {
            if (i < 10) {
                monthList.add("0$i")
            } else {
                monthList.add("" + i)
            }
        }
        rootBinding.wheelMonth.setWheelData(monthList)
        rootBinding.wheelMonth.setCurrentPosition(month)

        val dayList = ArrayList<String>()
        for (i in 1..31) {
            if (i < 10) {
                dayList.add("0$i")
            } else {
                dayList.add("" + i)
            }
        }
        rootBinding.wheelDay.setWheelData(dayList)
        rootBinding.wheelDay.setCurrentPosition(day - 1)

        val hourList = ArrayList<String>()
        for (i in 0..23) {
            if (i < 10) {
                hourList.add("0$i")
            } else {
                hourList.add("" + i)
            }
        }
        rootBinding.wheelHour.setWheelData(hourList)
        rootBinding.wheelHour.setCurrentPosition(hour)

        val minuteList = ArrayList<String>()
        for (i in 0..59) {
            if (i < 10) {
                minuteList.add("0$i")
            } else {
                minuteList.add("" + i)
            }
        }
        rootBinding.wheelMinute.setWheelData(minuteList)
        rootBinding.wheelMinute.setCurrentPosition(minute)

        rootBinding.btnConfirm.setOnClickListener {
            choiceDialog?.dismiss()
            val year = rootBinding.wheelYear.selectedData.toInt()
            val month = rootBinding.wheelMonth.selectedData.toInt()
            val day = rootBinding.wheelDay.selectedData.toInt()
            val hour = rootBinding.wheelHour.selectedData.toInt()
            val minute = rootBinding.wheelMinute.selectedData.toInt()
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month - 1)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            confirmCallback.invoke(calendar.timeInMillis)
        }

        choiceDialog?.window?.also {
            it.attributes?.also { attr ->
                attr.width = ActionBar.LayoutParams.MATCH_PARENT
                attr.height = ActionBar.LayoutParams.WRAP_CONTENT
                it.attributes = attr

            }
            it.setGravity(Gravity.BOTTOM)
            it.setBackgroundDrawableResource(R.color.transparent)
        }
        choiceDialog?.show()
    }
}