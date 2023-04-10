package io.github.xtvj.android.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import io.github.xtvj.android.customView.LoadingDialog

abstract class BaseFragment<T : ViewDataBinding> constructor(
    @LayoutRes private val contentLayoutId: Int
) : BindingFragment<T>(contentLayoutId) {

    var loading = MutableLiveData(false)
    private var loadingDialog: LoadingDialog? = null
    var loadingMessage = "加载中……"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initLoading()
    }

    private fun initLoading() {
        loading.observe(viewLifecycleOwner) {
            if (it && (loadingDialog == null || loadingDialog?.isResumed == false)) {
                if (loadingDialog == null) {
                    loadingDialog = LoadingDialog()
                }
                loadingDialog?.setMessage(loadingMessage)
                if (loadingDialog?.isAdded != true){
                    loadingDialog?.show(childFragmentManager, "loading")
                }
            } else if (it && loadingDialog?.isResumed == true) {
                loadingDialog?.changeMessage(loadingMessage)
            }
            if (!it && loadingDialog?.isResumed == true) {
                loadingDialog?.dismiss()
                //置空，防止内存泄露
                loadingDialog = null
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingDialog = null
    }

}