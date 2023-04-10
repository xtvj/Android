package io.github.xtvj.android.ui.welcome

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.github.xtvj.android.R
import io.github.xtvj.android.base.BaseActivity
import io.github.xtvj.android.databinding.ActivityWelcomeBinding
import io.github.xtvj.android.ui.main.MainActivity
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>(R.layout.activity_welcome){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
            finish()
        }
    }
}