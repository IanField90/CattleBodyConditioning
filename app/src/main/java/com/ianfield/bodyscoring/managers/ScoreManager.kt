package com.ianfield.bodyscoring.managers

import com.ianfield.bodyscoring.models.Score

import io.realm.Realm

/**
 * Created by Ian Field on 14/01/2016.
 */
internal object ScoreManager {

    private val TAG = "ScoreManager"

    fun createScore(score: Double): Score {
        val scoreRecord = Score()
        scoreRecord.score = score
        scoreRecord.count = 0
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.copyToRealm(scoreRecord)
        realm.commitTransaction()
        return scoreRecord
    }
}
