package com.ianfield.bodyscoring.widgets;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ianfield.bodyscoring.R;
import com.ianfield.bodyscoring.models.Score;

import java.util.ArrayList;

/**
 * Created by Ian Field on 1/20/16.
 * TODO score change callback to update DB
 */
public class ScoreAdapter extends  RecyclerView.Adapter<ScoreAdapter.ViewHolder> {
    private ArrayList<Score> mDataset;

    public ScoreAdapter(ArrayList<Score> dataset) {
        this.mDataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_container, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Score dataItem = mDataset.get(position);
        holder.mScoreView.setCount(dataItem.getCount());
        holder.mScoreView.setScore(dataItem.getScore());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public ScoreView mScoreView;
        public ViewHolder(View container) {
            super(container);
            mCardView = (CardView) container.findViewById(R.id.cardView);
            mScoreView = (ScoreView) mCardView.findViewById(R.id.scoreView);
        }
    }


}
