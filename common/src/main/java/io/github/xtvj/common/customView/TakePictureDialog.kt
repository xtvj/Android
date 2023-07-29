package io.github.xtvj.common.customView

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import io.github.xtvj.common.R
import io.github.xtvj.common.base.BindingSheetDialogFragment
import io.github.xtvj.common.databinding.FragmentTakePictureDialogBinding

@AndroidEntryPoint
class TakePictureDialog constructor(
    private val title1: String = "",
    private val title2: String = "",
    private val cancel: String = ""
) : BindingSheetDialogFragment<FragmentTakePictureDialogBinding>(R.layout.fragment_take_picture_dialog) {


    enum class TakePictureButton {
        TAKE_PHOTO, SYSTEM_ALBUM, CANCEL
    }

    override fun onStart() {
        super.onStart()
        //设置默认也是展开的，以免横屏时需要上拉才能显示按钮。
        (dialog as BottomSheetDialog).behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun getTheme(): Int {
        return R.style.bottomSheetDialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    var onItemClick: ((TakePictureButton) -> Unit)? = null


    private fun initView() {
        binding.btTakePicture.text = title1.ifBlank {
            resources.getString(R.string.take_photo)
        }
        binding.btSysAlbum.text = title2.ifBlank {
            resources.getString(R.string.system_album)
        }
        binding.btCancel.text = cancel.ifBlank {
            resources.getString(R.string.cancel)
        }

        binding.btTakePicture.setOnClickListener {
            onItemClick?.invoke(TakePictureButton.TAKE_PHOTO)
            dismiss()
        }
        binding.btSysAlbum.setOnClickListener {
            onItemClick?.invoke(TakePictureButton.SYSTEM_ALBUM)
            dismiss()
        }
        binding.btCancel.setOnClickListener {
            onItemClick = null
            dismiss()
        }
    }


}