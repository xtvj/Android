package io.github.xtvj.android.ui.animatedVectorDrawable

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import io.github.xtvj.android.R
import io.github.xtvj.android.databinding.ActivityAnimatedVectorDrawableBinding
import io.github.xtvj.android.interfaces.OnClickHandle
import io.github.xtvj.android.utils.MyDrawableUtils
import io.github.xtvj.common.base.BaseActivity

@AndroidEntryPoint
class AnimatedVectorDrawableActivity : BaseActivity<ActivityAnimatedVectorDrawableBinding>(R.layout.activity_animated_vector_drawable), OnClickHandle {

    private var right = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.onClickHandle = this
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onClick(view: View) {
        when (view) {
            binding.mbAnimated -> {
                val drawable = if (right) {
                    resources.getDrawable(R.drawable.avd_right_arrow_to_down_arrow, null)
                } else {
                    resources.getDrawable(R.drawable.avd_down_arrow_to_right_arrow, null)
                }
                binding.mbAnimated.icon = drawable
                MyDrawableUtils.startAnimate(drawable)
                right = !right
            }
            binding.mbAnimatedRefresh ->{
                val drawable = binding.mbAnimatedRefresh.icon
                MyDrawableUtils.startAnimate(drawable)
            }
        }
    }

}