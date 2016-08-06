package com.ianfield.bodyscoring.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ianfield.bodyscoring.R;
import com.ianfield.bodyscoring.models.Score;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

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

    private void setCount(int count) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        score.setCount(count);
        realm.commitTransaction();
        countText.setText(String.valueOf(score.getCount()));
        if (count == 0) {
            subtractButton.setEnabled(false);
        } else if (count > 0) {
            subtractButton.setEnabled(true);
        }
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void setScore(Score score) {
        this.score = score;
        scoreText.setText(String.valueOf(score.getScore()));
        countText.setText(String.valueOf(score.getCount()));

        if (score.getCount() == 0) {
            subtractButton.setEnabled(false);
        }
        addButton.setOnClickListener(v -> setCount(ScoreView.this.score.getCount() + 1));

        subtractButton.setOnClickListener(v -> {
            if (ScoreView.this.score.getCount() > 0) {
                setCount(ScoreView.this.score.getCount() - 1);
            }
        });
    }
}
