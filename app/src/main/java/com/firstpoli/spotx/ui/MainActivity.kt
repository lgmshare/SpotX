package com.firstpoli.spotx.ui

import android.view.View
import android.widget.TextView
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.firstpoli.spotx.R
import com.firstpoli.spotx.databinding.MainActivityBinding
import com.firstpoli.spotx.extensions.dp2px
import com.firstpoli.spotx.extensions.getStatusBarHeight
import com.firstpoli.spotx.extensions.setMargin
import com.lgmshare.commons.extensions.onTabSelectionChanged

class MainActivity : BaseActivity<MainActivityBinding>() {

    private val fragments = HashMap<Int, Fragment>()

    override fun buildLayoutBinding(): MainActivityBinding {
        return MainActivityBinding.inflate(layoutInflater)
    }

    override fun initView() {
        val top = getStatusBarHeight() + dp2px(24f)
        val se = dp2px(42f)
        binding.tabLayout.setMargin(se, top, se, 0)


        fragments[0] = BpFragment()
        fragments[1] = ArticleListFragment()
        fragments[2] = SettingFragment()

        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.adapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
            override fun getItemCount(): Int = fragments.size

            override fun createFragment(position: Int): Fragment {
                return fragments[position] ?: throw RuntimeException("Trying to fetch unknown fragment id $position")
            }
        }
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.getTabAt(position)?.select()
            }
        })

        binding.viewPager.offscreenPageLimit = 3
        binding.viewPager.setCurrentItem(0, false)

        setupTabs()
        setupTabColors()
    }

    private fun setupTabs() {
        binding.tabLayout.removeAllTabs()
        val tabLabels = arrayOf(R.string.bp, R.string.info, R.string.settings)

        tabLabels.forEachIndexed { i, labelId ->
            binding.tabLayout.newTab().setCustomView(R.layout.main_tablayout_item).apply {
                customView?.findViewById<TextView>(R.id.tab_item_label)?.setText(labelId)
                binding.tabLayout.addTab(this)
            }
        }

        binding.tabLayout.onTabSelectionChanged(
            tabUnselectedAction = {
                updateBottomTabItemColors(it.customView, false)
            },
            tabSelectedAction = {
                binding.viewPager.setCurrentItem(it.position, false)
                updateBottomTabItemColors(it.customView, true)
            }
        )
    }

    private fun setupTabColors() {
        val activeView = binding.tabLayout.getTabAt(binding.viewPager.currentItem)?.customView
        updateBottomTabItemColors(activeView, true)

        getInactiveTabIndexes(binding.viewPager.currentItem).forEach { index ->
            val inactiveView = binding.tabLayout.getTabAt(index)?.customView
            updateBottomTabItemColors(inactiveView, false)
        }

        binding.tabLayout.getTabAt(binding.viewPager.currentItem)?.select()
    }

    private fun getInactiveTabIndexes(activeIndex: Int) = arrayListOf(0, 1, 2).filter { it != activeIndex }

    private fun updateBottomTabItemColors(view: View?, isActive: Boolean) {
        if (isActive) {
            val color = resources.getColor(R.color.white, theme)
            view?.findViewById<TextView>(R.id.tab_item_label)?.setTextColor(color)
            view?.findViewById<TextView>(R.id.tab_item_label)?.setBackgroundResource(R.drawable.shape_6ca3ff_to_257bff_r24)
        } else {
            val color = resources.getColor(R.color.c_333333, theme)
            view?.findViewById<TextView>(R.id.tab_item_label)?.setTextColor(color)
            view?.findViewById<TextView>(R.id.tab_item_label)?.setBackgroundResource(R.drawable.shape_ffffff_r24)
        }
    }
}