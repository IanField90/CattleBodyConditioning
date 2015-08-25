package com.ianfield.bodyscoring;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;

@EActivity(R.layout.activity_scoring)
@OptionsMenu(R.menu.menu_scoring)
public class ScoringActivity extends AppCompatActivity {


    @OptionsItem(R.id.action_done)
    void done() {
        finish();
    }
}
