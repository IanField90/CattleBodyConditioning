package com.ianfield.bodyscoring;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateRecordActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static final String EXPECTED_DATE_PICKER_DIALOG = "expectedDatePickerDialog";
    public static final String TODAYS_DATE_PICKER_DIALOG = "todaysDatePickerDialog";

    @Bind(R.id.txtDate)
    TextView mDateText;

    @Bind(R.id.txtExpectedDate)
    TextView mExpectedDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_record);
        ButterKnife.bind(this);
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

    @OnClick(R.id.btnSetDate)
    public void clickSetDate() {
        todaysDatePickerDialog.show(getFragmentManager(), TODAYS_DATE_PICKER_DIALOG);
    }

    @OnClick(R.id.btnSetExpectedDate)
    public void clickSetExpectedDate() {
        expectedDatePickerDialog.show(getFragmentManager(), EXPECTED_DATE_PICKER_DIALOG);
    }

    @OnClick(R.id.btnNext)
    public void clickNext() {
        // TODO validation
        startActivity(new Intent(this, ScoringActivity.class));
        finish();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth)  {
        DateFormat df = SimpleDateFormat.getDateInstance();
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, monthOfYear);
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        if (view == todaysDatePickerDialog) {
            mDateText.setText(df.format(date.getTime()));
        } else {
            mExpectedDate.setText(df.format(date.getTime()));
        }
    }
}
