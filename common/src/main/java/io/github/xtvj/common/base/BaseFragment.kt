package io.github.xtvj.common.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import io.github.xtvj.common.R
import io.github.xtvj.common.customView.LoadingAlertDialog
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

    lateinit var loadingMessage: MutableStateFlow<String>
    private var job: Job? = null
    private fun initLoading() {
        loadingMessage = MutableStateFlow(resources.getString(R.string.loading))
        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                combine(loading, loadingMessage) { _, _ ->
                }.collectLatest {
                    job?.cancel()
                    job = launch(Dispatchers.Main) {
                        if (loading.value) {
                            loadingDialog.changeMessage(loadingMessage.value)
                            if (!isRemoving) {
                                loadingDialog.show()
                            }
                        } else {
                            if (loadingDialog.isShowing && !isRemoving) {
                                loadingDialog.dismiss()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
    }

}