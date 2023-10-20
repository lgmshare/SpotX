package com.firstpoli.spotx.ui

import android.content.Intent
import android.os.Build
import com.firstpoli.spotx.BP_STATES
import com.firstpoli.spotx.R
import com.firstpoli.spotx.databinding.RecordActivityBinding
import com.firstpoli.spotx.extensions.dp2px
import com.firstpoli.spotx.models.Article
import com.firstpoli.spotx.models.BpData
import com.firstpoli.spotx.utils.Utils
import java.text.SimpleDateFormat
import java.util.Locale

class RecordActivity : BaseActivity<RecordActivityBinding>() {

    private lateinit var bpData: BpData

    override fun buildLayoutBinding(): RecordActivityBinding {
        return RecordActivityBinding.inflate(layoutInflater)
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

        updateLevelView()
        binding.itemCondition.text = bpData.state
        binding.itemDate.text = Utils.formatRecordDatetime(bpData.datetime)

        binding.tvSystolic.text = bpData.systolic.toString()
        binding.tvDiastolic.text = bpData.diastolic.toString()
        binding.tvPulse.text = bpData.pulse.toString()

        val list = mutableListOf<Article>().apply {
            add(Article(0, getString(R.string.article_title_1), getString(R.string.article_content_1)))
            add(Article(1, getString(R.string.article_title_2), getString(R.string.article_content_2)))
            add(Article(2, getString(R.string.article_title_3), getString(R.string.article_content_3)))
            add(Article(3, getString(R.string.article_title_4), getString(R.string.article_content_4)))
        }
        val article1 = list.random()
        list.remove(article1)
        val article2 = list.random()

        binding.article1.text = article1.title
        binding.article2.text = article2.title

        binding.btnRead1.setOnClickListener {
            startActivity(Intent(this, ArticleDetailActivity::class.java).apply {
                putExtra("article", article1)
            })
        }

        binding.btnRead2.setOnClickListener {
            startActivity(Intent(this, ArticleDetailActivity::class.java).apply {
                putExtra("article", article2)
            })
        }

        binding.btnSave.setOnClickListener {
            finish()
        }

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

}