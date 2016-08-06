package com.ianfield.bodyscoring;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.MetadataChangeSet;
import com.ianfield.bodyscoring.models.Record;
import com.ianfield.bodyscoring.models.Score;
import com.ianfield.bodyscoring.utils.ScoreScale;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Ian Field on 01/03/2016.
 */
public class ViewRecordActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "ViewRecordActivity";
    private static final int REQUEST_WRITE_STORAGE = 112;

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.planned_calving)
    TextView plannedCalving;

    @BindView(R.id.date)
    TextView date;

    @BindView(R.id.chart)
    LineChart chart;

    @BindView(R.id.root)
    LinearLayout root;

    GoogleApiClient googleApiClient;
    public static final int RESOLVE_CONNECTION_REQUEST_CODE = 1;
    private static final int REQUEST_CODE_CREATOR = 2;
    private static final int REQUEST_CODE_RESOLUTION = 3;

    Record record;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
        ButterKnife.bind(this);
        name.setText(getIntent().getStringExtra("name"));
        plannedCalving.setText(getIntent().getStringExtra("planned_calving_date"));
        date.setText(getIntent().getStringExtra("date"));

        setData();

        if (googleApiClient == null) {
            // Create the API client and bind it to an instance variable.
            // We use this instance as the callback for connection and connection
            // failures.
            // Since no account name is passed, the user is prompted to choose.
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Drive.API)
                    .addScope(Drive.SCOPE_FILE)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
            case R.id.action_save_chart:
                return checkPermissionsAndSave();
            case R.id.action_upload:
                googleApiClient.connect();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkPermissionsAndSave() {
        boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        } else {
            if (chart.saveToGallery(record.getName() + System.currentTimeMillis(), 100)) {
                Snackbar.make(root, R.string.save_success, Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(root, R.string.save_failed, Snackbar.LENGTH_SHORT).show();
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
        super.onBackPressed();
    }

    private void setData() {
        Realm realm = Realm.getDefaultInstance();
        record = realm.where(Record.class).equalTo("id", getIntent().getStringExtra(getString(R.string.extra_record_id))).findFirst();

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
        set.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> String.format(Locale.getDefault(), "%d", (int)value));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view, menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermissionsAndSave();
                } else{
                    Snackbar.make(root, R.string.permission_denied, Snackbar.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // save the file
        Log.i(TAG, "GoogleApiClient connected, uploading");

        Drive.DriveApi.newDriveContents(googleApiClient)
                .setResultCallback(contentsCallback);

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended: ");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // Called whenever the API client fails to connect.
        Log.i(TAG, "GoogleApiClient connection failed: " + connectionResult.toString());
        if (!connectionResult.hasResolution()) {
            // show the localized error dialog.
            GoogleApiAvailability.getInstance().getErrorDialog(this, connectionResult.getErrorCode(), 0).show();
            return;
        }
        // The failure has a resolution. Resolve it.
        // Called typically when the app is not yet authorized, and an authorization
        // dialog is displayed to the user.
        try {
            connectionResult.startResolutionForResult(this, REQUEST_CODE_RESOLUTION);
        } catch (IntentSender.SendIntentException e) {
            Log.e(TAG, "Exception while starting resolution activity", e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESOLVE_CONNECTION_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    googleApiClient.connect();
                }
                break;
        }
    }


    final private ResultCallback<DriveApi.DriveContentsResult> contentsCallback =
            new ResultCallback<DriveApi.DriveContentsResult>() {
                @Override
                public void onResult(@NonNull DriveApi.DriveContentsResult result) {
                    if (!result.getStatus().isSuccess()) {
                        Snackbar.make(root, R.string.error_drive_upload, Snackbar.LENGTH_SHORT).show();
                        return;
                    }

                    // Get an output stream for the contents.
                    OutputStream outputStream = result.getDriveContents().getOutputStream();
                    try {
                        outputStream.write(record.toString().getBytes());
                    } catch (IOException e) {
                        Log.e(TAG, "Unable to write file contents.", e);
                    }
                    // Create the initial metadata - MIME type and title.
                    // Note that the user will be able to change the title later.
                    MetadataChangeSet metadataChangeSet = new MetadataChangeSet.Builder()
                            .setMimeType("text/plain")
                            .setTitle(record.getName() + System.currentTimeMillis() +  ".txt")
                            .build();
                    // Create an intent for the file chooser, and start it.
                    IntentSender intentSender = Drive.DriveApi
                            .newCreateFileActivityBuilder()
                            .setInitialMetadata(metadataChangeSet)
                            .setInitialDriveContents(result.getDriveContents())
                            .build(googleApiClient);
                    try {
                        startIntentSenderForResult(
                                intentSender, REQUEST_CODE_CREATOR, null, 0, 0, 0);
                    } catch (IntentSender.SendIntentException e) {
                        Log.e(TAG, "Failed to launch file chooser.", e);
                    }
                }
            };
}
