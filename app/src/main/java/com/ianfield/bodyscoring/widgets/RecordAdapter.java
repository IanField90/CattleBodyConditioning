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

import java.util.ArrayList;

/**
 * Created by Ian Field on 1/20/16.
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {
    ArrayList<Record> records;
    OnRecordActionListener listener;
    public RecordAdapter(ArrayList<Record> records, @Nullable OnRecordActionListener listener) {
        this.records = records;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Record record = records.get(position);
        holder.name.setText(record.getName());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onView(holder.getAdapterPosition(), holder.name);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView name;
        public Button view;
        public Button edit;
        public Button delete;
        public ViewHolder(View container) {
            super(container);
            cardView = (CardView) container;
            name = (TextView) cardView.findViewById(R.id.name);
            view = (Button) cardView.findViewById(R.id.view);
            edit = (Button) cardView.findViewById(R.id.edit);
            delete = (Button) cardView.findViewById(R.id.delete);
        }
    }

    public interface OnRecordActionListener {
        void onView(int position, TextView name);

        void onEdit(int position);

        void onDelete(int position);
    }
}
