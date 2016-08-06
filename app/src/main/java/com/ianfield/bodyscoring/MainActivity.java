package com.ianfield.bodyscoring;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import com.ianfield.bodyscoring.managers.RecordManager;
import com.ianfield.bodyscoring.models.Record;
import com.ianfield.bodyscoring.widgets.RecordAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v4.app.ActivityOptionsCompat.makeSceneTransitionAnimation;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
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
            @Override
            public void onView(String recordId, TextView name, TextView recordedDate, TextView dueDate) {
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

            @Override
            public void onEdit(String recordId) {
                Intent intent = new Intent(MainActivity.this, ScoringActivity.class);
                intent.putExtra(getString(R.string.extra_record_id), recordId);
                startActivity(intent);
            }

            @Override
            public void onDelete(final Record record, final int position) {
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

    }

    @OnClick(R.id.fabNew) public void fabNewClick() {
        startActivity(new Intent(this, CreateRecordActivity.class));
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
}
