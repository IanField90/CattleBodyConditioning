package com.ianfield.bodyscoring.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout

import com.ianfield.bodyscoring.R
import com.ianfield.bodyscoring.models.Score


import io.realm.Realm
import kotlinx.android.synthetic.main.score_view.view.*

/**
 * Created by Ian Field on 13/01/2016.
 */
class ScoreView : LinearLayout {

    private var score: Score? = null

    constructor(context: Context) : super(context) {
        initializeViews(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initializeViews(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initializeViews(context)
    }

    /**
     * Inflates the views in the layout.
     *
     * @param context the current context for the view.
     */
    private fun initializeViews(context: Context) {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.score_view, this)
    }

    private fun setCount(count: Int) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        score?.count = count
        realm.commitTransaction()
        countText!!.text = score!!.count.toString()
        if (count == 0) {
            subtractButton!!.isEnabled = false
        } else if (count > 0) {
            subtractButton!!.isEnabled = true
        }
    }

    fun setScore(score: Score) {
        this.score = score
        scoreText?.text = score.score.toString()
        countText?.text = score.count.toString()

        if (score.count == 0) {
            subtractButton!!.isEnabled = false
        }
        addButton?.setOnClickListener { setCount(this@ScoreView.score!!.count + 1) }

        subtractButton?.setOnClickListener {
            if (this@ScoreView.score!!.count > 0) {
                setCount(this@ScoreView.score!!.count - 1)
            }
        }
    }
}
