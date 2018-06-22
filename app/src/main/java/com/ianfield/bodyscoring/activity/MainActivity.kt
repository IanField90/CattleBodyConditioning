package com.ianfield.bodyscoring.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat.makeSceneTransitionAnimation
import android.support.v4.util.Pair
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.ianfield.bodyscoring.R
import com.ianfield.bodyscoring.managers.RecordManager
import com.ianfield.bodyscoring.models.Record
import com.ianfield.bodyscoring.widgets.RecordAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var recordAdapter: RecordAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fabNew.setOnClickListener { fabNewClick() }
        savedList?.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        savedList?.layoutManager = layoutManager
    }

    override fun onResume() {
        super.onResume()

        val records = RecordManager.allRecords
        setupPromptForSize(records.size)

        recordAdapter = RecordAdapter(records, object : RecordAdapter.OnRecordActionListener {
            override fun onView(recordId: String?, name: TextView?, recordedDate: TextView?, dueDate: TextView?) {
                val intent = Intent(this@MainActivity, ViewRecordActivity::class.java)
                intent.putExtra(getString(R.string.extra_record_id), recordId)
                val options = makeSceneTransitionAnimation(
                        this@MainActivity,
                        Pair.create(name, "name"),
                        Pair.create(recordedDate, "date"),
                        Pair.create(dueDate, "planned_calving_date")
                )
                startActivity(intent, options.toBundle())
            }

            override fun onEdit(recordId: String?) {
                val intent = Intent(this@MainActivity, CreateRecordActivity::class.java)
                intent.putExtra(getString(R.string.extra_record_id), recordId)
                startActivity(intent)
            }

            override fun onDelete(record: Record?, position: Int) {
                AlertDialog.Builder(this@MainActivity)
                        .setTitle(R.string.delete)
                        .setMessage(R.string.are_you_sure)
                        .setPositiveButton(R.string.yes) { _, _ ->
                            RecordManager.deleteRecord(record!!)
                            recordAdapter?.notifyItemRemoved(position)
                            recordAdapter?.notifyItemRangeChanged(position, recordAdapter!!.records!!.size)
                            setupPromptForSize(recordAdapter!!.records!!.size)

                        }
                        .setNegativeButton(R.string.no, null)
                        .show()
            }
        })
        savedList!!.adapter = recordAdapter

    }

    private fun fabNewClick() {
        startActivity(Intent(this, CreateRecordActivity::class.java))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }
            R.id.action_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    private fun setupPromptForSize(size: Int) {
        if (size == 0) {
            createRecordPrompt.visibility = View.VISIBLE
            savedList.visibility = View.GONE
        } else {
            createRecordPrompt.visibility = View.GONE
            savedList.visibility = View.VISIBLE
        }
    }

    companion object {
        private val TAG = "MainActivity"
    }
}
