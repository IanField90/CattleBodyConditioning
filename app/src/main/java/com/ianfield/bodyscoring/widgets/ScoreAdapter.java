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
public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {
    private ArrayList<Score> dataset;

    public ScoreAdapter(ArrayList<Score> dataset) {
        this.dataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_container, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Score dataItem = dataset.get(position);
        holder.scoreView.setCount(dataItem.getCount());
        holder.scoreView.setScore(dataItem.getScore());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ScoreView scoreView;
        public ViewHolder(View container) {
            super(container);
            cardView = (CardView) container.findViewById(R.id.cardView);
            scoreView = (ScoreView) cardView.findViewById(R.id.scoreView);
        }
    }


}