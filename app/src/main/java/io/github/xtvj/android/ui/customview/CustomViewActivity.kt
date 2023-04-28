package io.github.xtvj.android.ui.customview

import android.graphics.Color
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IFillFormatter
import dagger.hilt.android.AndroidEntryPoint
import io.github.xtvj.android.R
import io.github.xtvj.android.base.BaseActivity
import io.github.xtvj.android.customView.MyMarkerView
import io.github.xtvj.android.databinding.ActivityCustomViewBinding
import io.github.xtvj.android.interfaces.OnClickHandle
import io.github.xtvj.android.utils.LogUtils.logs
import io.github.xtvj.android.utils.ToastUtils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CustomViewActivity : BaseActivity<ActivityCustomViewBinding>(R.layout.activity_custom_view), OnClickHandle {

    private val viewModel by viewModels<CustomViewViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        lifecycleScope.launch(Dispatchers.Main) {
            initData()
        }
    }

    private fun initView() {
        binding.onClickHandle = this

        initLineChart()
    }

    override fun onClick(view: View) {
        when (view) {
            binding.ccvCustomView -> {
                applicationContext.toast("自定义View")
            }
        }
    }

    private fun initData() {
        binding.sleepStatusView.list = viewModel.getStatusData()
        setData(listOf(90, 90, 88, 83, 89, 90, 88, 85, 82, 87, 83, 86, 86, 84, 83, 0, 24, 71, 65, 43, 52, 73, 43))
    }

    //初始化chart
    private fun initLineChart() {
        binding.lineChart.description.isEnabled = false
        binding.lineChart.setTouchEnabled(true)
        binding.lineChart.setPinchZoom(true)
        binding.lineChart.isDragEnabled = true
        binding.lineChart.setScaleEnabled(true)
        binding.lineChart.setDrawGridBackground(false)

        val mv = MyMarkerView(this, R.layout.custom_marker_view)
        mv.chartView = binding.lineChart // For bounds control

        binding.lineChart.marker = mv // Set the marker to the chart

        //颜色标注设置
        binding.lineChart.legend.isEnabled = false

        //X轴设置
        val xAxis: XAxis = binding.lineChart.xAxis
//        xAxis.setCenterAxisLabels(true)
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        val format = DecimalFormat("#")
        xAxis.valueFormatter = IAxisValueFormatter { value, axis ->
            "${format.format(value)}:00"
        }
        xAxis.axisLineColor = Color.WHITE //X轴颜色
        xAxis.axisLineWidth = 0.5f //X轴粗细
        xAxis.position = XAxis.XAxisPosition.BOTTOM //X轴所在位置   默认为上面
        xAxis.axisMaximum = 22f //X轴最大数值
        xAxis.axisMinimum = 0f //X轴最小数值
        xAxis.xOffset = 0f
        //X轴坐标的个数    第二个参数一般填false     true表示强制设置标签数 可能会导致X轴坐标显示不全等问题
        xAxis.setLabelCount(5, false)

        //Y轴设置
        val y: YAxis = binding.lineChart.axisLeft
        y.setLabelCount(5, false)
        y.textColor = Color.BLUE
        y.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        y.axisLineColor = Color.rgb(204, 204, 204)
        y.setDrawAxisLine(false)
        y.setDrawGridLines(true)
        y.axisMinimum = 0f
        y.yOffset = 0f
        y.axisMaximum = 160f
        y.axisLineColor = Color.BLUE

        binding.lineChart.axisRight.isEnabled = false

        binding.lineChart.animateXY(1000, 1000)

        binding.lineChart.invalidate()
    }

    private fun setData(list: List<Int>) {
        val values = list.mapIndexed { index, i ->
            Entry(index.toFloat(), i.toFloat())
        }
        logs(values.joinToString {
            "${it.x} --- ${it.y}"
        })
        val set1 = LineDataSet(values, "DataSet 1")
        set1.mode = LineDataSet.Mode.CUBIC_BEZIER
        set1.cubicIntensity = 0.2f
        set1.setDrawFilled(true)
        set1.setDrawCircles(false)
        set1.lineWidth = 1.8f
        set1.highLightColor = Color.rgb(244, 117, 117)
        set1.color = Color.WHITE
        set1.fillColor = Color.rgb(39, 22, 38)
        set1.fillAlpha = 100
        set1.setDrawHorizontalHighlightIndicator(false)
        set1.fillFormatter = IFillFormatter { dataSet, dataProvider ->
            binding.lineChart.axisLeft.axisMinimum
        }
        // create a data object with the data sets
        val data = LineData(set1)
        data.setDrawValues(false)

        // set data
        binding.lineChart.data = data
        binding.lineChart.invalidate()
    }

}