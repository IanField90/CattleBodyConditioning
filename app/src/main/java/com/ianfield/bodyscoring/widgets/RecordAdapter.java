package com.ianfield.bodyscoring.widgets;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ianfield.bodyscoring.R;
import com.ianfield.bodyscoring.models.Record;

import java.util.ArrayList;

/**
 * Created by Ian Field on 1/20/16.
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {
    ArrayList<Record> mRecords;

    public RecordAdapter(ArrayList<Record> dataSet) {
        this.mRecords = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Record record = mRecords.get(position);
        holder.mName.setText(record.getName());
    }

    @Override
    public int getItemCount() {
        return mRecords.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mName;
        public ViewHolder(View container) {
            super(container);
            mCardView = (CardView) container;
            mName = (TextView) mCardView.findViewById(R.id.name);
        }
    }
}
