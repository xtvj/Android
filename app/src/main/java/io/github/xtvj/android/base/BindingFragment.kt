package io.github.xtvj.android.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import io.github.xtvj.android.utils.LogUtils.logs

abstract class BindingFragment<T : ViewDataBinding> constructor(
    @LayoutRes private val contentLayoutId: Int
) : Fragment() {

    private var bindingComponent: DataBindingComponent? = DataBindingUtil.getDefaultComponent()

    private var _binding: T? = null

    protected val binding: T
        get() = checkNotNull(_binding) {
            "Fragment $this binding cannot be accessed before onCreateView() or after onDestroyView()"
        }

    protected inline fun binding(block: T.() -> Unit): T {
        return binding.apply(block)
    }

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, contentLayoutId, container, false, bindingComponent)
        logs(javaClass.name + "---onCreateView")
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding?.unbind()
        _binding = null
        logs(javaClass.name + "---onDestroyView")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logs(javaClass.name + "---onCreate")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logs(javaClass.name + "---onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        logs(javaClass.name + "---onStart")
    }

    override fun onResume() {
        super.onResume()
        logs(javaClass.name + "---onResume")
    }

    override fun onPause() {
        super.onPause()
        logs(javaClass.name + "---onPause")
    }

    override fun onStop() {
        super.onStop()
        logs(javaClass.name + "---onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        logs(javaClass.name + "---onDestroy")
    }
}
