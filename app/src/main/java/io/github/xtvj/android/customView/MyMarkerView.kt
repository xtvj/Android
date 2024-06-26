package io.github.xtvj.android.customView

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.DecimalFormat
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils
import io.github.xtvj.android.R

/**
 * Custom implementation of the MarkerView.
 */
@SuppressLint("ViewConstructor")
class MyMarkerView(context: Context?, layoutResource: Int) : MarkerView(context, layoutResource) {
    private val tvContent: TextView = findViewById(R.id.tvContent)
    private val tvMarkerTime: TextView = findViewById(R.id.tvMarkerTime)

    // runs every time the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @SuppressLint("SetTextI18n")
    override fun refreshContent(e: Entry, highlight: Highlight) {
        if (e is CandleEntry) {
            tvContent.text = String.format(resources.getString(R.string.unit_format), Utils.formatNumber(e.high, 0, true))
        } else {
            tvContent.text = String.format(resources.getString(R.string.unit_format), Utils.formatNumber(e.y, 0, true))
        }
        tvMarkerTime.text = DecimalFormat("#").format(e.x) + ":00"
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(-(width / 2).toFloat(), -height.toFloat())
    }
}