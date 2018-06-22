package com.ianfield.bodyscoring.activity

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.ianfield.bodyscoring.R
import com.ianfield.bodyscoring.managers.RecordManager
import com.ianfield.bodyscoring.models.Record
import com.ianfield.bodyscoring.utils.DateUtils
import com.ianfield.bodyscoring.utils.Setting
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_create_record.*
import java.util.*

class CreateRecordActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private var record: Record? = Record()
    private val todaysDatePickerCalendar = Calendar.getInstance()
    private var todaysDatePickerDialog = DatePickerDialog.newInstance(
            this,
            todaysDatePickerCalendar.get(Calendar.YEAR),
            todaysDatePickerCalendar.get(Calendar.MONTH),
            todaysDatePickerCalendar.get(Calendar.DAY_OF_MONTH)
    )
    private val expectedDatePickerCalendar = Calendar.getInstance()
    private var expectedDatePickerDialog = DatePickerDialog.newInstance(
            this,
            expectedDatePickerCalendar.get(Calendar.YEAR),
            expectedDatePickerCalendar.get(Calendar.MONTH),
            expectedDatePickerCalendar.get(Calendar.DAY_OF_MONTH)
    )
    private var existing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_record)
        val id = intent.getStringExtra("record_id")
        if (id != null) {
            record = Realm.getDefaultInstance().where(Record::class.java).equalTo("id", id).findFirst()
            existing = true
            setTitle(R.string.edit_record)
        }

        val today = Date()

        next.setOnClickListener { clickNext() }
        expectedCalvingDate.setOnClickListener { clickSetExpectedDate() }
        scoringDate.setOnClickListener { clickSetDate() }

        @Setting.Country val setting = PreferenceManager
                .getDefaultSharedPreferences(this)
                .getString(
                        getString(R.string.pref_locality),
                        getString(R.string.pref_localities_default)
                )
        if (!existing) {
            record!!.setting = setting
            record!!.plannedCalvingDate = today
            record!!.scoringDate = today
            scoringDate!!.text = DateUtils.dateToString(Date())
            expectedCalvingDate!!.text = DateUtils.dateToString(Date())
        } else {
            name!!.setText(record!!.name)
            name!!.setSelection(record!!.name!!.length)
            scoringDate!!.text = DateUtils.dateToString(record!!.scoringDate!!)
            expectedCalvingDate!!.text = DateUtils.dateToString(record!!.plannedCalvingDate!!)

            todaysDatePickerCalendar.time = record!!.scoringDate
            todaysDatePickerDialog = DatePickerDialog.newInstance(
                    this,
                    todaysDatePickerCalendar.get(Calendar.YEAR),
                    todaysDatePickerCalendar.get(Calendar.MONTH),
                    todaysDatePickerCalendar.get(Calendar.DAY_OF_MONTH)
            )

            expectedDatePickerCalendar.time = record!!.plannedCalvingDate
            expectedDatePickerDialog = DatePickerDialog.newInstance(
                    this,
                    expectedDatePickerCalendar.get(Calendar.YEAR),
                    expectedDatePickerCalendar.get(Calendar.MONTH),
                    expectedDatePickerCalendar.get(Calendar.DAY_OF_MONTH)
            )

            next!!.setText(R.string.next_continue)
        }

    }

    fun clickSetDate() {
        todaysDatePickerDialog.show(fragmentManager, SCORING_DATE_PICKER)
    }

    fun clickSetExpectedDate() {
        expectedDatePickerDialog.show(fragmentManager, PLANNED_CALVING_DATE_PICKER)
    }

    fun clickNext() {
        if (existing) {
            Realm.getDefaultInstance().beginTransaction()
        }
        record!!.name = name!!.text.toString()
        if (existing) {
            Realm.getDefaultInstance().commitTransaction()
        }
        if (record!!.isValidRecord) {
            saveRecordAndLaunchScoring()
        } else {
            val snackBar = Snackbar.make(findViewById<View>(android.R.id.content), R.string.snackbar_missing_name, Snackbar.LENGTH_LONG)

            //            snackBar.setAction(R.string.snackbar_dismiss, v -> snackBar.dismiss());
            snackBar.show()
        }
    }

    private fun saveRecordAndLaunchScoring() {
        if (!existing) {
            record = RecordManager.createRecord(record!!)
        }
        val intent = Intent(this, ScoringActivity::class.java)
        intent.putExtra(getString(R.string.extra_record_id), record?.id)
        startActivity(intent)
        finish()
    }

    override fun onDateSet(view: DatePickerDialog, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val date = Calendar.getInstance()
        date.set(Calendar.YEAR, year)
        date.set(Calendar.MONTH, monthOfYear)
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        if (view === todaysDatePickerDialog) {
            if (existing) {
                Realm.getDefaultInstance().beginTransaction()
            }
            record!!.scoringDate = date.time
            if (existing) {
                Realm.getDefaultInstance().commitTransaction()
            }
            scoringDate!!.text = DateUtils.dateToString(date.time)
        } else {
            record!!.plannedCalvingDate = date.time
            expectedCalvingDate!!.text = DateUtils.dateToString(date.time)
        }
    }

    companion object {
        private val PLANNED_CALVING_DATE_PICKER = "planned_calving_date_picker"
        private val SCORING_DATE_PICKER = "scoring_date_picker"
    }
}
