package io.github.xtvj.android.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.github.xtvj.android.R
import io.github.xtvj.android.databinding.ActivityMainBinding
import io.github.xtvj.android.ui.animatedVectorDrawable.AnimatedVectorDrawableActivity
import io.github.xtvj.android.ui.blueTooth.scan.ScanBlueToothActivity
import io.github.xtvj.android.ui.customView2.CustomView2Activity
import io.github.xtvj.android.ui.customview.CustomViewActivity
import io.github.xtvj.android.ui.navigationView.NavigationViewActivity
import io.github.xtvj.android.ui.thirdPartDemo.ThirdPartDemoActivity
import io.github.xtvj.common.base.BaseActivity
import io.github.xtvj.common.utils.GridSpaceItemDecoration
import io.github.xtvj.common.utils.ScreenUtils.dpToPx
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val adapter by lazy {
        MainAdapter()
    }

    private val list = listOf("Animated\nDrawable", "自定义\nView", "自定义\nView2", "导航", "蓝牙", "第三方库示例")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        lifecycleScope.launch {
            initData()
        }
    }

    private fun initView() {
        binding.rvMain.layoutManager = GridLayoutManager(this, 3)
        binding.rvMain.addItemDecoration(
            GridSpaceItemDecoration(
                3,
                (16).dpToPx.toInt(),
                (16).dpToPx.toInt()
            )
        )
        binding.rvMain.adapter = adapter
        adapter.onItemClick = { _, position ->
            when (position) {
                0 -> {
                    startActivity(Intent(this, AnimatedVectorDrawableActivity::class.java))
                }

                1 -> {
                    startActivity(Intent(this, CustomViewActivity::class.java))
                }

                2 -> {
                    startActivity(Intent(this, CustomView2Activity::class.java))
                }

                3 -> {
                    startActivity(Intent(this, NavigationViewActivity::class.java))
                }

                4 -> {
                    startActivity(Intent(this, ScanBlueToothActivity::class.java))
                }

                5 -> {
                    startActivity(Intent(this, ThirdPartDemoActivity::class.java))
                }
            }
        }
    }

    private suspend fun initData() {
        adapter.submitData(PagingData.from(list))
    }

}