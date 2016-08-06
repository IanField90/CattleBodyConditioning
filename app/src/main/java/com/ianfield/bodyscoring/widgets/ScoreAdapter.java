package com.ianfield.bodyscoring.widgets;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ianfield.bodyscoring.R;
import com.ianfield.bodyscoring.models.Score;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;

/**
 * Created by Ian Field on 1/20/16.
 */
public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {
    private RealmList<Score> dataset;

    public ScoreAdapter(RealmList<Score> dataset) {
        this.dataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_container, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.scoreView.setScore(dataset.get(position));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cardView)
        public CardView cardView;

        @BindView(R.id.scoreView)
        public ScoreView scoreView;

        public ViewHolder(View container) {
            super(container);
            ButterKnife.bind(this, container);
        }
    }


}
