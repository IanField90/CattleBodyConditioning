package com.ianfield.bodyscoring.widgets

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration

import com.ianfield.bodyscoring.R

/**
 * Created by ianfield on 20/12/2016.
 */

class DividerColorItemDecoration(context: Context, orientation: Int) : DividerItemDecoration(context, orientation) {

    init {
        setDrawable(ContextCompat.getDrawable(context, R.drawable.line_divider)!!)
    }
}