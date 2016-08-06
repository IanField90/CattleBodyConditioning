package com.ianfield.bodyscoring;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.ianfield.bodyscoring.managers.RecordManager;
import com.ianfield.bodyscoring.models.Record;
import com.ianfield.bodyscoring.widgets.RecordAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v4.app.ActivityOptionsCompat.*;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "MainActivity";
    GoogleApiClient googleApiClient;
    public static final int RESOLVE_CONNECTION_REQUEST_CODE = 1;
    private static final int REQUEST_CODE_CREATOR = 2;
    private static final int REQUEST_CODE_RESOLUTION = 3;

    @BindView(R.id.savedList) RecyclerView recyclerView;

    private RecordAdapter recordAdapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override protected void onResume() {
        super.onResume();
        recordAdapter = new RecordAdapter(RecordManager.getAllRecords(), new RecordAdapter.OnRecordActionListener() {
            @Override public void onView(String recordId, TextView name, TextView recordedDate, TextView dueDate) {
                Intent intent = new Intent(MainActivity.this, ViewRecordActivity.class);
                intent.putExtra(getString(R.string.extra_record_id), recordId);
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("planned_calving_date", dueDate.getText().toString());
                intent.putExtra("date", recordedDate.getText().toString());
                ActivityOptionsCompat options = makeSceneTransitionAnimation(
                        MainActivity.this,
                        Pair.create((View) name, "name"),
                        Pair.create((View) recordedDate, "date"),
                        Pair.create((View) dueDate, "planned_calving_date")
                );
                startActivity(intent, options.toBundle());
            }

            @Override public void onEdit(String recordId) {
                Intent intent = new Intent(MainActivity.this, ScoringActivity.class);
                intent.putExtra(getString(R.string.extra_record_id), recordId);
                startActivity(intent);
            }

            @Override public void onDelete(final Record record, final int position) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.delete)
                        .setMessage(R.string.are_you_sure)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                RecordManager.deleteRecord(record);
                                recordAdapter.notifyItemRemoved(position);
                                recordAdapter.notifyItemRangeChanged(position, recordAdapter.getRecords().size());
                            }
                        })
                        .setNegativeButton(R.string.no, null)
                        .show();
            }
        });
        recyclerView.setAdapter(recordAdapter);
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
//        googleApiClient.connect();
    }

    @Override protected void onPause() {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
        super.onPause();
    }


    @OnClick(R.id.fabNew) public void fabNewClick() {
        startActivity(new Intent(this, CreateRecordActivity.class));
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                actionSettingsClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void actionSettingsClicked() {
        // Open settings screen to toggle mode (scoring resolution/values)
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @Override public void onConnected(Bundle bundle) {
        // save the file
    }

    @Override public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended: ");
    }

    @Override public void onConnectionFailed(@NonNull ConnectionResult result) {
        // Called whenever the API client fails to connect.
        Log.i(TAG, "GoogleApiClient connection failed: " + result.toString());
        if (!result.hasResolution()) {
            // show the localized error dialog.
            GoogleApiAvailability.getInstance().getErrorDialog(this, result.getErrorCode(), 0).show();
            return;
        }
        // The failure has a resolution. Resolve it.
        // Called typically when the app is not yet authorized, and an authorization
        // dialog is displayed to the user.
        try {
            result.startResolutionForResult(this, REQUEST_CODE_RESOLUTION);
        } catch (IntentSender.SendIntentException e) {
            Log.e(TAG, "Exception while starting resolution activity", e);
        }
    }

    @Override protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
//        switch (requestCode) {
//            case RESOLVE_CONNECTION_REQUEST_CODE:
//                if (resultCode == RESULT_OK) {
//                    googleApiClient.connect();
//                }
//                break;
//        }
    }
}
