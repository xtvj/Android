package io.github.xtvj.android.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import io.github.xtvj.android.R
import io.github.xtvj.android.customView.LoadingAlertDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

abstract class BaseFragment<T : ViewDataBinding> constructor(
    @LayoutRes private val contentLayoutId: Int
) : BindingFragment<T>(contentLayoutId) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initLoading()
    }

    var loading = MutableStateFlow(false)
    private val loadingDialog by lazy {
        LoadingAlertDialog(requireContext())
    }

    var loadingMessage = MutableStateFlow("加载中……")
    private var job: Job? = null
    private fun initLoading() {
        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                combine(loading, loadingMessage) { l, m ->
                }.collectLatest {
                    job?.cancel()
                    job = launch(Dispatchers.Main) {
                        if (loading.value) {
                            loadingDialog.changeMessage(loadingMessage.value)
                            loadingDialog.show()
                        } else {
                            loadingDialog.dismiss()
                        }
                    }
                }
            }
        }
    }

}