package io.github.xtvj.common.utils

import android.text.TextUtils
import android.webkit.MimeTypeMap

object FileUtils {



    /**
     * 获取MIME类型
     * @param url file path or whatever suitable URL you want.
     * @return
     */
    fun getMimeType(url: String?): String? {
        if (!TextUtils.isEmpty(url)) {
            val extension = MimeTypeMap.getFileExtensionFromUrl(url)
            if (extension != null) {
                return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
            }
        }
        return null
    }

}