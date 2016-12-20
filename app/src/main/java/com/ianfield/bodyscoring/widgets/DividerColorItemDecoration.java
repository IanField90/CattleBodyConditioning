package com.ianfield.bodyscoring.widgets;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;

import com.ianfield.bodyscoring.R;

/**
 * Created by ianfield on 20/12/2016.
 */

public class DividerColorItemDecoration extends DividerItemDecoration {

    public DividerColorItemDecoration(Context context, int orientation) {
        super(context, orientation);
        setDrawable(ContextCompat.getDrawable(context, R.drawable.line_divider));
    }
}