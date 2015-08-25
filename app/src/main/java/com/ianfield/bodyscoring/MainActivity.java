package com.ianfield.bodyscoring;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends AppCompatActivity {

    @OptionsItem(R.id.action_settings)
    void actionSettingsClicked() {

    }

    @Click(R.id.fabNew)
    void fabNewClick() {
        CreateRecordActivity_.intent(this).start();
    }
}
