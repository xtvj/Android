package io.github.xtvj.android.ui.thirdPartDemo

import android.os.Bundle
import android.view.View
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import dagger.hilt.android.AndroidEntryPoint
import io.github.xtvj.android.R
import io.github.xtvj.android.databinding.ActivityThirdPartDemoBinding
import io.github.xtvj.android.interfaces.OnClickHandle
import io.github.xtvj.common.base.BaseActivity


@AndroidEntryPoint
class ThirdPartDemoActivity :
    BaseActivity<ActivityThirdPartDemoBinding>(R.layout.activity_third_part_demo),
    OnClickHandle {

    private val balloon by lazy {
        Balloon.Builder(this)
            .setLayout(R.layout.custom_popup)
            .setArrowSize(10)
            .setArrowOrientation(ArrowOrientation.BOTTOM)
            .setArrowPosition(0.5f)
            .setArrowColorResource(R.color.teal_700)
//            .setWidthRatio(0.55f)
//            .setHeight(250)
            .setCornerRadius(4f)
//            .setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            .setBalloonAnimation(BalloonAnimation.NONE)
            .setLifecycleOwner(this)
            .build()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.onClickHandle = this
    }

    override fun onClick(view: View) {
        when (view) {
            binding.mbBalloon -> {
                if (balloon.isShowing){
                    balloon.dismiss()
                }else{
                    balloon.showAlignTop(binding.mbBalloon)
                }
            }
        }
    }
}