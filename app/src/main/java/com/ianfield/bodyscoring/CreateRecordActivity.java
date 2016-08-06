package com.ianfield.bodyscoring;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.ianfield.bodyscoring.managers.RecordManager;
import com.ianfield.bodyscoring.models.Record;
import com.ianfield.bodyscoring.utils.DateUtils;
import com.ianfield.bodyscoring.utils.Setting;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateRecordActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static final String PLANNED_CALVING_DATE_PICKER = "planned_calving_date_picker";
    public static final String SCORING_DATE_PICKER = "scoring_date_picker";

    @BindView(R.id.scoringDate) TextView scoringDate;

    @BindView(R.id.expectedCalvingDate) TextView expectedCalvingDate;

    @BindView(R.id.name) EditText name;

    Record record = new Record();

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_record);
        ButterKnife.bind(this);
        Date today = new Date();

        @Setting.Country String setting = PreferenceManager
                .getDefaultSharedPreferences(this)
                .getString(
                        getString(R.string.pref_Locality),
                        getString(R.string.pref_localities_default)
                );
        record.setSetting(setting);
        record.setPlannedCalvingDate(today);
        record.setScoringDate(today);
        scoringDate.setText(DateUtils.dateToString(new Date()));
        expectedCalvingDate.setText(DateUtils.dateToString(new Date()));
    }

    Calendar todaysDatePickerCaldendar = Calendar.getInstance();
    DatePickerDialog todaysDatePickerDialog = DatePickerDialog.newInstance(
            this,
            todaysDatePickerCaldendar.get(Calendar.YEAR),
            todaysDatePickerCaldendar.get(Calendar.MONTH),
            todaysDatePickerCaldendar.get(Calendar.DAY_OF_MONTH)
    );

    Calendar expectedDatePickerCaldendar = Calendar.getInstance();
    DatePickerDialog expectedDatePickerDialog = DatePickerDialog.newInstance(
            this,
            expectedDatePickerCaldendar.get(Calendar.YEAR),
            expectedDatePickerCaldendar.get(Calendar.MONTH),
            expectedDatePickerCaldendar.get(Calendar.DAY_OF_MONTH)
    );

    @OnClick(R.id.scoringDate) public void clickSetDate() {
        todaysDatePickerDialog.show(getFragmentManager(), SCORING_DATE_PICKER);
    }

    @OnClick(R.id.expectedCalvingDate) public void clickSetExpectedDate() {
        expectedDatePickerDialog.show(getFragmentManager(), PLANNED_CALVING_DATE_PICKER);
    }

    @OnClick(R.id.next) public void clickNext() {
        record.setName(name.getText().toString());
        if (record.isValidRecord()) {
            saveRecordAndLaunchScoring();
        } else {
            final Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content), R.string.snackbar_missing_name, Snackbar.LENGTH_LONG);

            snackBar.setAction(R.string.snackbar_dismiss, v -> {
                snackBar.dismiss();
            });
            snackBar.show();
        }
    }

    private void saveRecordAndLaunchScoring() {
        record = RecordManager.createRecord(record);
        Intent intent = new Intent(this, ScoringActivity.class);
        intent.putExtra(getString(R.string.extra_record_id), record.getId());
        startActivity(intent);
        finish();
    }

    @Override public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth)  {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, monthOfYear);
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        if (view == todaysDatePickerDialog) {
            record.setScoringDate(date.getTime());
            scoringDate.setText(DateUtils.dateToString(date.getTime()));
        } else {
            record.setPlannedCalvingDate(date.getTime());
            expectedCalvingDate.setText(DateUtils.dateToString(date.getTime()));
        }
    }
}
