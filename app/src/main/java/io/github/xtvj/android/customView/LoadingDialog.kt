package io.github.xtvj.android.customView

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import io.github.xtvj.android.R
import io.github.xtvj.android.databinding.DialogLoadingBinding


class LoadingDialog : DialogFragment() {


    private var bindingComponent: DataBindingComponent? = DataBindingUtil.getDefaultComponent()

    private var _binding: DialogLoadingBinding? = null

    private val binding: DialogLoadingBinding
        get() = checkNotNull(_binding) {
            "Fragment $this binding cannot be accessed before onCreateView() or after onDestroyView()"
        }

    private var message: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.alertDialog)
        isCancelable = false
    }

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_loading, container, false, bindingComponent)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        message?.let {
            binding.tvLoading.text = it
        }

    }

    //设置弹窗的宽度为屏幕宽度的65%
    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val dm = Resources.getSystem().displayMetrics
            dialog.window?.setLayout((dm.widthPixels * 0.65).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    fun setMessage(message: String) {
        this.message = message
    }

    //loadingDialog?.isResumed == true 时调用
    fun changeMessage(message: String){
        if (isResumed){
            binding.tvLoading.text = message
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding?.unbind()
        _binding = null
    }
}