package io.github.xtvj.android.ui.customview

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.xtvj.android.customView.SleepStatusView
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class CustomViewViewModel @Inject constructor(
) : ViewModel() {

    fun getStatusData(): MutableList<SleepStatusView.SleepStatusList> {
        val list = mutableListOf<SleepStatusView.SleepStatusList>()
        //列表计算元素属性总和
        while ((list.sumOf {
                it.lengthPercent.toDouble()
            }) < 1.0) {
            list.add(
                //枚举类随机值
                SleepStatusView.SleepStatusList(
                    SleepStatusView.SleepStatus.values().random(),
                    Random.nextDouble(0.0, 0.2).toFloat()
                )
            )
        }
        return list
    }

    //charts data
    //name:heartbeat---90---90---88---83---89---90---88---85---82---87---83---86---86---84---83---0---0---0---0---0---0---73---0---,
    //name:breath---17---16---16---16---16---16---16---16---16---16---16---17---17---15---16---0---0---0---0---0---0---15---0---

}