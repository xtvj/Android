package io.github.xtvj.android.ui.customView2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import dagger.hilt.android.AndroidEntryPoint
import io.github.xtvj.android.R
import io.github.xtvj.android.databinding.ActivityCustomView2Binding
import io.github.xtvj.common.base.BaseActivity
import io.github.xtvj.common.base.BindingActivity

@AndroidEntryPoint
class CustomView2Activity : BaseActivity<ActivityCustomView2Binding>(R.layout.activity_custom_view2) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


}