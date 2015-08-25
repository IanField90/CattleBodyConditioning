package com.ianfield.bodyscoring;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@EActivity(R.layout.activity_create_record)
public class CreateRecordActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    @ViewById(R.id.txtDate)
    TextView mDateText;

    @ViewById(R.id.txtExpectedDate)
    TextView mExpectedDate;

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

    @Click(R.id.btnSetDate)
    void clickSetDate() {
        todaysDatePickerDialog.show(getFragmentManager(), "todaysDatePickerDialog");
    }

    @Click(R.id.btnSetExpectedDate)
    void clickSetExpectedDate() {
        expectedDatePickerDialog.show(getFragmentManager(), "expectedDatePickerDialog");
    }

    @Click(R.id.btnNext)
    void clickNext() {
        // TODO validation
        ScoringActivity_.intent(this).start();
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
