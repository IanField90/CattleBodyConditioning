package com.ianfield.bodyscoring.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ianfield.bodyscoring.R;
import com.ianfield.bodyscoring.managers.RecordManager;
import com.ianfield.bodyscoring.models.Record;
import com.ianfield.bodyscoring.widgets.ScoreAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScoringActivity extends AppCompatActivity {
    private static final String TAG = "ScoringActivity";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    Record record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring);
        ButterKnife.bind(this);

        retrieveRecord();

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ScoreAdapter adapter = new ScoreAdapter(record.getScores());
        recyclerView.setAdapter(adapter);

        Log.d(TAG, "onCreate: " + adapter.getItemCount());
    }

    private void retrieveRecord() {
        record = RecordManager.getRecordById(
                getIntent().getExtras().getString(
                    getString(R.string.extra_record_id)
                )
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.scoring, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                done();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void done() {
        finish();
    }
}
