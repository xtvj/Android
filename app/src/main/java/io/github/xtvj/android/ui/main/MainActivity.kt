package io.github.xtvj.android.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.github.xtvj.android.R
import io.github.xtvj.android.base.BaseActivity
import io.github.xtvj.android.databinding.ActivityMainBinding
import io.github.xtvj.android.ui.animatedVectorDrawable.AnimatedVectorDrawableActivity
import io.github.xtvj.android.utils.GridSpaceItemDecoration
import io.github.xtvj.android.utils.ScreenUtils.toPx
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val adapter by lazy {
        MainAdapter()
    }

    private val list = listOf("Animated\nDrawable", "测试二", "测试三", "测试四", "测试五")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        lifecycleScope.launch {
            initData()
        }
    }

    private fun initView() {
        binding.rvMain.layoutManager = GridLayoutManager(this, 3)
        binding.rvMain.addItemDecoration(GridSpaceItemDecoration(3, (16).toPx, (16).toPx))
        binding.rvMain.adapter = adapter
        adapter.onItemClick = { _, position ->
            when (position) {
                0 -> {
                    startActivity(Intent(this, AnimatedVectorDrawableActivity::class.java))
                }
                1 -> {

                }
                2 -> {

                }
                3 -> {

                }
                4 -> {

                }
            }
        }
    }

    private suspend fun initData() {
        adapter.submitData(PagingData.from(list))
    }

}