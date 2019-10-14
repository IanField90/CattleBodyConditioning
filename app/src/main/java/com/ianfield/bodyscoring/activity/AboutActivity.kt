package com.ianfield.bodyscoring.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.ianfield.bodyscoring.R


import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        webview.loadUrl("file:///android_asset/about.html")
    }
}
