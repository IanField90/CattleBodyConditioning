package com.ianfield.bodyscoring;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.ianfield.bodyscoring.models.Record;
import com.ianfield.bodyscoring.models.Score;
import com.ianfield.bodyscoring.utils.ScoreScale;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Ian Field on 01/03/2016.
 */
public class ViewRecordActivity extends AppCompatActivity {
    @BindView(R.id.name) TextView name;
    @BindView(R.id.planned_calving) TextView plannedCalving;
    @BindView(R.id.date) TextView date;
    @BindView(R.id.chart) LineChart chart;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
        ButterKnife.bind(this);
        name.setText(getIntent().getStringExtra("name"));
        plannedCalving.setText(getIntent().getStringExtra("planned_calving_date"));
        date.setText(getIntent().getStringExtra("date"));

        setData();
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
            case R.id.action_save_chart:
                return checkPermissionsAndSave();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkPermissionsAndSave() {
        // TODO actually check permissions
        if (chart.saveToGallery("title" + System.currentTimeMillis(), 100)) {
            Toast.makeText(getApplicationContext(), "Saving SUCCESSFUL!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Saving FAILED!", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override public void onBackPressed() {
        supportFinishAfterTransition();
        super.onBackPressed();
    }

    private void setData() {
        Realm realm = Realm.getDefaultInstance();
        Record record = realm.where(Record.class).equalTo("id", getIntent().getStringExtra(getString(R.string.extra_record_id))).findFirst();

        ArrayList<Entry> scores = new ArrayList<>();
        for (Score score : record.getScores()) {
            Entry entry = new Entry((float) score.getScore(), score.getCount());
            scores.add(entry);
        }

        LineDataSet set = new LineDataSet(scores, "Data Set 1");
        set.setCircleColorHole(Color.WHITE);
        set.setCircleColor(getResources().getColor(R.color.accent));
        set.setCircleRadius(7f);
        set.setCircleHoleRadius(3f);
        set.setDrawCircleHole(true);
        set.setDrawValues(true);
        set.setColor(getResources().getColor(R.color.primary));
        set.setLineWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
        set.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return String.format(Locale.getDefault(), "%d", (int)value);
            }
        });
        set.setValueTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 5, getResources().getDisplayMetrics()));
        set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        LineData data = new LineData(set);
        data.setDrawValues(true);


        setupChart(data);
    }

    private void setupChart(LineData data) {
        chart.setAutoScaleMinMaxEnabled(true);
        // no description text
        chart.setDescription("");
        chart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable / disable grid background
        chart.setDrawGridBackground(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setBackgroundColor(getResources().getColor(R.color.graph_background));

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setEnabled(true);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawGridLines(true);
        leftAxis.removeAllLimitLines();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setLabelCount(ScoreScale.UK_SCORE_SCALE.length, false);
        leftAxis.setDrawLabels(true);
        leftAxis.setAxisMinValue(0);

        chart.getAxisRight().setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(true);
        xAxis.setAxisMinValue(0);
        xAxis.setAxisMaxValue((float) ScoreScale.UK_SCORE_SCALE[ScoreScale.UK_SCORE_SCALE.length - 1] + 1);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        chart.setData(data);
//        chart.animateX(200);

        // getting the legend is only possible after setting data
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view, menu);
        return true;
    }
}
