package com.ianfield.bodyscoring.widgets

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ianfield.bodyscoring.R
import com.ianfield.bodyscoring.models.Score
import io.realm.RealmList
import kotlinx.android.synthetic.main.score_container.view.*

/**
 * Created by Ian Field on 1/20/16.
 */
class ScoreAdapter(private val dataset: RealmList<Score>) : RecyclerView.Adapter<ScoreAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.score_container, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.scoreView.setScore(dataset[position]!!)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    class ViewHolder(container: View) : RecyclerView.ViewHolder(container)

}
