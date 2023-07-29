package io.github.xtvj.common.customView

import android.content.Context
import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import io.github.xtvj.common.R
import io.github.xtvj.common.databinding.DialogLoadingBinding

class LoadingAlertDialog(
    context: Context,
    var message: String = context.getString(R.string.loading),
    @StyleRes themeResId: Int = R.style.dialog
) : AlertDialog(context, themeResId) {

    private lateinit var binding: DialogLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCancelable(false)
        initView()
    }

    private fun initView() {
        binding.tvLoading.text = message
    }

    fun changeMessage(message: String) {
        this.message = message
        if (isShowing) {
            binding.tvLoading.text = message
        }
    }

}