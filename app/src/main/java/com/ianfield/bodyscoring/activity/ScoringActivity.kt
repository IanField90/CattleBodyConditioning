package com.ianfield.bodyscoring.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.ianfield.bodyscoring.R
import com.ianfield.bodyscoring.managers.RecordManager
import com.ianfield.bodyscoring.models.Record
import com.ianfield.bodyscoring.widgets.DividerColorItemDecoration
import com.ianfield.bodyscoring.widgets.ScoreAdapter
import kotlinx.android.synthetic.main.activity_scoring.*

class ScoringActivity : AppCompatActivity() {

    private var record: Record? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoring)

        retrieveRecord()

        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val adapter = ScoreAdapter(record!!.scores!!)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerColorItemDecoration(this, DividerItemDecoration.VERTICAL))

        Log.d(TAG, "onCreate: " + adapter.itemCount)
    }

    private fun retrieveRecord() {
        record = RecordManager.getRecordById(
                intent.extras?.getString(getString(R.string.extra_record_id))!!
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.scoring, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_done -> {
                done()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun done() {
        val intent = Intent(this, ViewRecordActivity::class.java)
        intent.putExtra(getString(R.string.extra_record_id), record?.id)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    companion object {
        private val TAG = "ScoringActivity"
    }
}
