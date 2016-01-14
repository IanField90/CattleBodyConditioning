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
 * Created by Ian on 13/01/2016.
 */
public class ScoreView extends LinearLayout {

    private Button mSubtractButton;
    private Button mAddButton;
    private TextView mCountTextView;
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

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mSubtractButton = (Button) this.findViewById(R.id.subtract_button);
        mAddButton = (Button) this.findViewById(R.id.add_button);
        mCountTextView = (TextView) this.findViewById(R.id.count_text);

        if (count == 0) {
            mSubtractButton.setEnabled(false);
        }
        mAddButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                mCountTextView.setText(String.valueOf(count));

                if (count > 0) {
                    mSubtractButton.setEnabled(true);
                }
            }
        });

        mSubtractButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 0) {
                    count--;
                    mCountTextView.setText(String.valueOf(count));

                    if (count == 0) {
                        mSubtractButton.setEnabled(false);
                    }
                }
            }
        });
    }

}