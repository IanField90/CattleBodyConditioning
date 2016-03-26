package com.ianfield.bodyscoring.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ianfield.bodyscoring.R;

/**
 * Created by Ian Field on 13/01/2016.
 */
public class ScoreView extends LinearLayout {

    private Button subtractButton;
    private Button addButton;
    private TextView countText;
    private TextView scoreText;
    private double score = 0;
    private int count = 0;

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
     * @param context
     *           the current context for the view.
     */
    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.score_view, this);
    }

    public void setCount(int count) {
        this.count = count;
        countText.setText(String.valueOf(this.count));
    }

    public void setScore(double score) {
        this.score = score;
        scoreText.setText(String.valueOf(this.score));
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        subtractButton = (Button) this.findViewById(R.id.subtract_button);
        addButton = (Button) this.findViewById(R.id.add_button);
        countText = (TextView) this.findViewById(R.id.count_text);
        scoreText = (TextView) this.findViewById(R.id.score);
        scoreText.setText(String.valueOf(score));

        if (count == 0) {
            subtractButton.setEnabled(false);
        }
        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                countText.setText(String.valueOf(count));

                if (count > 0) {
                    subtractButton.setEnabled(true);
                }
            }
        });

        subtractButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 0) {
                    count--;
                    countText.setText(String.valueOf(count));

                    if (count == 0) {
                        subtractButton.setEnabled(false);
                    }
                }
            }
        });
    }

}
