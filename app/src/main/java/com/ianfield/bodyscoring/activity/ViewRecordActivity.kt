package com.ianfield.bodyscoring.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.ianfield.bodyscoring.R
import com.ianfield.bodyscoring.models.Record
import com.ianfield.bodyscoring.utils.ScoreScale
import com.ianfield.bodyscoring.utils.Setting
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_view_record.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Ian Field on 01/03/2016.
 */
class ViewRecordActivity : AppCompatActivity() {
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private var record: Record? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_record)
        setData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
        // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                supportFinishAfterTransition()
                return true
            }
            R.id.action_save_chart -> return checkPermissionsAndSave()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkPermissionsAndSave(): Boolean {
        val hasPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_WRITE_STORAGE)
        } else {
            if (chart.saveToGallery(record!!.name!! + System.currentTimeMillis(), 100)) {
                Snackbar.make(root, R.string.save_success, Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(root, R.string.save_failed, Snackbar.LENGTH_SHORT).show()
            }
        }
        return true
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
        super.onBackPressed()
    }

    private fun setData() {
        val realm = Realm.getDefaultInstance()
        record = realm.where(Record::class.java).equalTo("id", intent.getStringExtra(getString(R.string.extra_record_id))).findFirst()

        name.text = record?.name
        date.text = getString(R.string.recorded_label, dateFormat.format(record!!.scoringDate))
        plannedCalving.text = getString(R.string.planned_calving_label, dateFormat.format(record!!.plannedCalvingDate))

        val scoreScale = if (record?.setting == Setting.NZ) ScoreScale.NZ_SCORE_SCALE else ScoreScale.UK_SCORE_SCALE

        val scores = ArrayList<BarEntry>()
        var count = 0
        for (score in record?.scores!!) {
            count += score.count
            val entry = BarEntry(score.score.toFloat(), score.count.toFloat())
            scores.add(entry)
        }

        val set = BarDataSet(scores, "Data Set 1")

        set.color = resources.getColor(R.color.graph_bar)
        set.barBorderWidth = 1f
        set.barBorderColor = resources.getColor(R.color.primary)
        set.setValueFormatter { value, _, _, _ ->
            if (value > 0) {
                 String.format(Locale.getDefault(), "%.0f%%", (value / count.toFloat()) * 100f)
            } else {
                 ""
            }
        }
        set.valueTextSize = resources.getDimension(R.dimen.chart_value_text_size)

        set.axisDependency = YAxis.AxisDependency.LEFT
        val data = BarData(set)
        data.setDrawValues(true)
        data.barWidth = 0.4f


        setupChart(data, scoreScale)
    }

    private fun setupChart(data: BarData, scoreScale: DoubleArray) {
        chart.isAutoScaleMinMaxEnabled = true
        // no description text
        val chartOffset = resources.getDimension(R.dimen.chart_offset)
        val description = Description()
        description.text = ""
        chart.legend.isEnabled = false
        chart.setExtraOffsets(chartOffset, chartOffset, chartOffset, chartOffset)
        chart.description = description
        chart.setDrawGridBackground(false)
        chart.setTouchEnabled(true)
        chart.isDragEnabled = true
        chart.setScaleEnabled(true)

        chart.setPinchZoom(false)

        chart.setBackgroundColor(resources.getColor(R.color.graph_background))
        chart.setDrawValueAboveBar(true)

        val yAxis = chart.axisLeft
        yAxis.isEnabled = true
        yAxis.setDrawZeroLine(false)
        yAxis.setDrawGridLines(true)
        yAxis.removeAllLimitLines()
        yAxis.granularity = 1f
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        yAxis.setLabelCount(scoreScale.size, false)
        yAxis.setDrawLabels(true)
        yAxis.axisMinimum = 0f
        yAxis.textSize = 12f

        chart.axisRight.isEnabled = false

        val xAxis = chart.xAxis
        xAxis.isEnabled = true
        xAxis.setDrawGridLines(true)
        if (record?.setting == Setting.UK) {
            xAxis.labelCount = 10
        } else {
            xAxis.labelCount = 13
        }
        xAxis.isGranularityEnabled = true
        xAxis.granularity = 0.5f
        xAxis.axisMinimum = scoreScale[0].toFloat() - .5f
        xAxis.axisMaximum = scoreScale[scoreScale.size - 1].toFloat() + .5f
        xAxis.textSize = 12f
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        chart.data = data
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.view, menu)
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_WRITE_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermissionsAndSave()
                } else {
                    Snackbar.make(root, R.string.permission_denied, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        private val TAG = "ViewRecordActivity"
        private val REQUEST_WRITE_STORAGE = 112
    }
}
