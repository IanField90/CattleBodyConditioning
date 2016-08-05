package com.ianfield.bodyscoring.widgets;

import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ianfield.bodyscoring.R;
import com.ianfield.bodyscoring.models.Record;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by Ian Field on 1/20/16.
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {
    RealmResults<Record> records;
    OnRecordActionListener listener;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public RecordAdapter(@Nullable RealmResults<Record> records, @Nullable OnRecordActionListener listener) {
        this.records = records;
        this.listener = listener;
    }

    public RealmResults<Record> getRecords() {
        return records;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Record record = records.get(position);
        holder.name.setText(record.getName());
        holder.date.setText("Recorded: " + dateFormat.format(record.getScoringDate()));
        holder.plannedCalving.setText("Planned: " + dateFormat.format(record.getPlannedCalvingDate()));
        if (listener != null) {
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onView(record.getId(), holder.name, holder.date, holder.plannedCalving);
                }
            });
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEdit(record.getId());
                }
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDelete(record, holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return records == null ? 0 : records.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        @BindView(R.id.name) public TextView name;
        @BindView(R.id.view) public Button view;
        @BindView(R.id.edit) public Button edit;
        @BindView(R.id.delete) public Button delete;
        @BindView(R.id.date) public TextView date;
        @BindView(R.id.planned_calving) public TextView plannedCalving;
        public ViewHolder(View container) {
            super(container);
            cardView = (CardView) container;
            ButterKnife.bind(this, container);
        }
    }

    public interface OnRecordActionListener {
        void onView(String id, TextView name, TextView date, TextView plannedCalving);

        void onEdit(String id);

        void onDelete(Record record, int position);
    }
}
