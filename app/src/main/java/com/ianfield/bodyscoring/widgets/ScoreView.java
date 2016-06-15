package com.ianfield.bodyscoring.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ianfield.bodyscoring.R;
import com.ianfield.bodyscoring.managers.ScoreManager;
import com.ianfield.bodyscoring.models.Score;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Field on 13/01/2016.
 */
public class ScoreView extends LinearLayout {

    @BindView(R.id.subtract_button) Button subtractButton;
    @BindView(R.id.add_button) Button addButton;
    @BindView(R.id.count_text) TextView countText;
    @BindView(R.id.score) TextView scoreText;
    private Score score;

    public ScoreView(Context context) {
        super(context);
        initializeViews(context);
    }

    public ScoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public ScoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    /**
     * Inflates the views in the layout.
     *
     * @param context the current context for the view.
     */
    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.score_view, this);
    }

    private void setCount(int count, boolean update) {
        score.setCount(count);
        countText.setText(String.valueOf(score.getCount()));
        if (count == 0) {
            subtractButton.setEnabled(false);
        } else if (count > 0) {
            subtractButton.setEnabled(true);
        }
        if (update) {
            ScoreManager.updateCount(getContext(), score);
        }
    }

    private void setScore(double score) {
        this.score.setScore(score);
        scoreText.setText(String.valueOf(this.score.getScore()));
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void setScore(Score score) {
        this.score = score;
        setScore(score.getScore());
        setCount(score.getCount(), false);
        scoreText.setText(String.valueOf(score.getScore()));

        if (score.getCount() == 0) {
            subtractButton.setEnabled(false);
        }
        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setCount(ScoreView.this.score.getCount() + 1, true);
            }
        });

        subtractButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScoreView.this.score.getCount() > 0) {
                    setCount(ScoreView.this.score.getCount() - 1, true);
                }
            }
        });
    }
}
