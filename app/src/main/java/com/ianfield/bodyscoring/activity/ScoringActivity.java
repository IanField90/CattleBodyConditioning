package com.ianfield.bodyscoring.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ianfield.bodyscoring.R;
import com.ianfield.bodyscoring.managers.RecordManager;
import com.ianfield.bodyscoring.models.Record;
import com.ianfield.bodyscoring.widgets.DividerColorItemDecoration;
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
        recyclerView.addItemDecoration(new DividerColorItemDecoration(this, DividerItemDecoration.VERTICAL));

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
        Intent intent = new Intent(this, ViewRecordActivity.class);
        intent.putExtra(getString(R.string.extra_record_id), record.getId());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
