package com.ianfield.bodyscoring.models

import java.util.Locale

import io.realm.RealmObject

/**
 * Created by Ian on 14/01/2016.
 */
open class Score : RealmObject() {

    var score: Double = 0.toDouble()

    var count = 0

    override fun toString(): String {
        return String.format(Locale.getDefault(), "Score: %.1f, Count: %d", score, count)
    }
}
