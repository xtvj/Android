package io.github.xtvj.android.ui.animatedVectorDrawable

import android.annotation.SuppressLint
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import io.github.xtvj.android.R
import io.github.xtvj.android.base.BaseActivity
import io.github.xtvj.android.databinding.ActivityAnimatedVectorDrawableBinding
import io.github.xtvj.android.interfaces.OnClickHandle
import kotlin.random.Random

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
                val rightDrawable = resources.getDrawable(R.drawable.avd_right_arrow_to_down_arrow, null)
                val downDrawable = resources.getDrawable(R.drawable.avd_down_arrow_to_right_arrow, null)
//                val right = binding.mbAnimated.icon == rightDrawable
                if (right) {
                    binding.mbAnimated.icon = rightDrawable
                    if (rightDrawable is Animatable) {
                        with(rightDrawable as Animatable) {
                            if (isRunning) {
                                stop()
                            }
                            start()
                        }
                    }
                } else {
                    binding.mbAnimated.icon = downDrawable
                    if (downDrawable is Animatable) {
                        with(downDrawable as Animatable) {
                            if (isRunning) {
                                stop()
                            }
                            start()
                        }
                    }
                }
                right = !right
            }
        }
    }

}