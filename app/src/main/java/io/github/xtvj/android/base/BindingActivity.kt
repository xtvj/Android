package io.github.xtvj.android.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BindingActivity<T : ViewDataBinding> constructor(
    @LayoutRes private val contentLayoutId: Int
) : AppCompatActivity() {

    private var bindingComponent: DataBindingComponent? = DataBindingUtil.getDefaultComponent()

    protected val binding: T by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        DataBindingUtil.setContentView(this, contentLayoutId, bindingComponent)
    }

    protected inline fun binding(block: T.() -> Unit): T {
        return binding.apply(block)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    init {
        addOnContextAvailableListener {
            binding.notifyChange()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}
