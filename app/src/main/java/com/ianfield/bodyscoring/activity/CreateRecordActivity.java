package com.ianfield.bodyscoring.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.ianfield.bodyscoring.R;
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
import io.realm.Realm;

public class CreateRecordActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final String PLANNED_CALVING_DATE_PICKER = "planned_calving_date_picker";
    private static final String SCORING_DATE_PICKER = "scoring_date_picker";

    @BindView(R.id.scoringDate)
    TextView scoringDate;

    @BindView(R.id.expectedCalvingDate)
    TextView expectedCalvingDate;

    @BindView(R.id.name)
    TextInputEditText name;

    @BindView(R.id.next)
    Button next;

    private Record record = new Record();
    private final Calendar todaysDatePickerCalendar = Calendar.getInstance();
    private DatePickerDialog todaysDatePickerDialog = DatePickerDialog.newInstance(
            this,
            todaysDatePickerCalendar.get(Calendar.YEAR),
            todaysDatePickerCalendar.get(Calendar.MONTH),
            todaysDatePickerCalendar.get(Calendar.DAY_OF_MONTH)
    );
    private final Calendar expectedDatePickerCalendar = Calendar.getInstance();
    private DatePickerDialog expectedDatePickerDialog = DatePickerDialog.newInstance(
            this,
            expectedDatePickerCalendar.get(Calendar.YEAR),
            expectedDatePickerCalendar.get(Calendar.MONTH),
            expectedDatePickerCalendar.get(Calendar.DAY_OF_MONTH)
    );
    private boolean existing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_record);
        String id = getIntent().getStringExtra("record_id");
        if (id != null) {
            record = Realm.getDefaultInstance().where(Record.class).equalTo("id", id).findFirst();
            existing = true;
            setTitle(R.string.edit_record);
        }

        ButterKnife.bind(this);
        Date today = new Date();

        @Setting.Country String setting = PreferenceManager
                .getDefaultSharedPreferences(this)
                .getString(
                        getString(R.string.pref_locality),
                        getString(R.string.pref_localities_default)
                );
        if (!existing) {
            record.setSetting(setting);
            record.setPlannedCalvingDate(today);
            record.setScoringDate(today);
            scoringDate.setText(DateUtils.INSTANCE.dateToString(new Date()));
            expectedCalvingDate.setText(DateUtils.INSTANCE.dateToString(new Date()));
        } else {
            name.setText(record.getName());
            name.setSelection(record.getName().length());
            scoringDate.setText(DateUtils.INSTANCE.dateToString(record.getScoringDate()));
            expectedCalvingDate.setText(DateUtils.INSTANCE.dateToString(record.getPlannedCalvingDate()));

            todaysDatePickerCalendar.setTime(record.getScoringDate());
            todaysDatePickerDialog = DatePickerDialog.newInstance(
                    this,
                    todaysDatePickerCalendar.get(Calendar.YEAR),
                    todaysDatePickerCalendar.get(Calendar.MONTH),
                    todaysDatePickerCalendar.get(Calendar.DAY_OF_MONTH)
            );

            expectedDatePickerCalendar.setTime(record.getPlannedCalvingDate());
            expectedDatePickerDialog = DatePickerDialog.newInstance(
                    this,
                    expectedDatePickerCalendar.get(Calendar.YEAR),
                    expectedDatePickerCalendar.get(Calendar.MONTH),
                    expectedDatePickerCalendar.get(Calendar.DAY_OF_MONTH)
            );

            next.setText(R.string.next_continue);
        }

    }


    @OnClick(R.id.scoringDate)
    public void clickSetDate() {
        todaysDatePickerDialog.show(getFragmentManager(), SCORING_DATE_PICKER);
    }

    @OnClick(R.id.expectedCalvingDate)
    public void clickSetExpectedDate() {
        expectedDatePickerDialog.show(getFragmentManager(), PLANNED_CALVING_DATE_PICKER);
    }

    @OnClick(R.id.next)
    public void clickNext() {
        if (existing) {
            Realm.getDefaultInstance().beginTransaction();
        }
        record.setName(name.getText().toString());
        if (existing) {
            Realm.getDefaultInstance().commitTransaction();
        }
        if (record.isValidRecord()) {
            saveRecordAndLaunchScoring();
        } else {
            final Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content), R.string.snackbar_missing_name, Snackbar.LENGTH_LONG);

//            snackBar.setAction(R.string.snackbar_dismiss, v -> snackBar.dismiss());
            snackBar.show();
        }
    }

    private void saveRecordAndLaunchScoring() {
        if (!existing) {
            record = RecordManager.INSTANCE.createRecord(record);
        }
        Intent intent = new Intent(this, ScoringActivity.class);
        intent.putExtra(getString(R.string.extra_record_id), record.getId());
        startActivity(intent);
        finish();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, monthOfYear);
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        if (view == todaysDatePickerDialog) {
            if (existing) {
                Realm.getDefaultInstance().beginTransaction();
            }
            record.setScoringDate(date.getTime());
            if (existing) {
                Realm.getDefaultInstance().commitTransaction();
            }
            scoringDate.setText(DateUtils.INSTANCE.dateToString(date.getTime()));
        } else {
            record.setPlannedCalvingDate(date.getTime());
            expectedCalvingDate.setText(DateUtils.INSTANCE.dateToString(date.getTime()));
        }
    }
}
