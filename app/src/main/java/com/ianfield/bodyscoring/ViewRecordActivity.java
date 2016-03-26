package com.ianfield.bodyscoring;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ianfield on 01/03/2016.
 */
public class ViewRecordActivity extends AppCompatActivity {
    @Bind(R.id.name) TextView name;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
        ButterKnife.bind(this);
        name.setText(getIntent().getStringExtra("name"));
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public void onBackPressed() {
        supportFinishAfterTransition();
        super.onBackPressed();
    }
}
