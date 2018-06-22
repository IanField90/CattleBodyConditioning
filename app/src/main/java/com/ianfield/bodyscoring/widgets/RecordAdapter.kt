package com.ianfield.bodyscoring.widgets

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ianfield.bodyscoring.R
import com.ianfield.bodyscoring.models.Record
import io.realm.RealmResults
import kotlinx.android.synthetic.main.record_view.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Ian Field on 1/20/16.
 */
class RecordAdapter(val records: RealmResults<Record>?, private val listener: OnRecordActionListener?) : RecyclerView.Adapter<RecordAdapter.ViewHolder>() {
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.UK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.record_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = records!![position]
        val context = holder.itemView.context
        holder.itemView.name.text = record?.name
        holder.itemView.date.text = context.getString(R.string.recorded_label,
                dateFormat.format(record?.scoringDate))
        holder.itemView.plannedCalving.text = context.getString(R.string.planned_calving_label,
                dateFormat.format(record?.plannedCalvingDate))
        if (listener != null) {
            holder.itemView.view.setOnClickListener { listener.onView(record?.id, holder.itemView.name, holder.itemView.date, holder.itemView.plannedCalving) }
            holder.itemView.edit.setOnClickListener { listener.onEdit(record?.id) }
            holder.itemView.delete.setOnClickListener { listener.onDelete(record, holder.adapterPosition) }
        }
    }

    override fun getItemCount(): Int {
        return records?.size ?: 0
    }

    interface OnRecordActionListener {
        fun onView(id: String?, name: TextView?, date: TextView?, plannedCalving: TextView?)

        fun onEdit(id: String?)

        fun onDelete(record: Record?, position: Int)
    }

    class ViewHolder(container: View) : RecyclerView.ViewHolder(container)
}
