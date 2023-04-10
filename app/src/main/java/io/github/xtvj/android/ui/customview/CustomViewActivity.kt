package io.github.xtvj.android.ui.customview

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import io.github.xtvj.android.R
import io.github.xtvj.android.base.BaseActivity
import io.github.xtvj.android.databinding.ActivityCustomViewBinding
import io.github.xtvj.android.interfaces.OnClickHandle
import io.github.xtvj.android.utils.ToastUtils.toast

@AndroidEntryPoint
class CustomViewActivity : BaseActivity<ActivityCustomViewBinding>(R.layout.activity_custom_view), OnClickHandle {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView(){
        binding.onClickHandle = this
    }

    override fun onClick(view: View) {
        when(view){
            binding.ccvCustomView ->{
                applicationContext.toast("自定义View")
            }
        }
    }

}