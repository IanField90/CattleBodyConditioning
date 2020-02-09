package com.ianfield.bodyscoring.managers

import com.ianfield.bodyscoring.models.Record
import com.ianfield.bodyscoring.models.Score
import com.ianfield.bodyscoring.utils.ScoreScale
import com.ianfield.bodyscoring.utils.Setting

import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import java.util.*

/**
 * Created by Ian on 14/01/2016.
 */
object RecordManager {

    val allRecords: RealmResults<Record>
        get() = Realm.getDefaultInstance()
                .where(Record::class.java)
                .sort("scoringDate", Sort.DESCENDING)
                .findAll()

    fun createRecord(record: Record): Record {
        record.id = UUID.randomUUID().toString()
        var scoreScale = ScoreScale.UK_SCORE_SCALE
        when (record.setting) {
            Setting.NZ -> {
                record.setting = Setting.NZ
                scoreScale = ScoreScale.NZ_SCORE_SCALE
            }
            Setting.UK -> {
                record.setting = Setting.UK
                scoreScale = ScoreScale.UK_SCORE_SCALE
            }
        }

        val scores = ArrayList<Score>()

        for (score in scoreScale) {
            scores.add(ScoreManager.createScore(score))
        }

        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val realmRecord = realm.copyToRealm(record)
        for (score in scores) {
            realm.copyToRealm(score)
            realmRecord.scores?.add(score)
        }
        realm.commitTransaction()

        return realmRecord
    }

    fun updateRecord(record: Record): Record {
        return Realm.getDefaultInstance().copyToRealmOrUpdate(record)
    }

    fun getRecordById(id: String): Record? {
        val realm = Realm.getDefaultInstance()
        val record = realm
                .where(Record::class.java)
                .equalTo("id", id)
                .findFirst()!!
        if (record.setting == Setting.UK && (record.scores!!.size < ScoreScale.UK_SCORE_SCALE.size)) {
            val missingScores = ScoreScale.UK_SCORE_SCALE.toList().minus(record.scores?.map { it.score }!!)
            val scores = mutableListOf<Score>()
            missingScores.forEach {
                scores.add(ScoreManager.createScore(it))
            }
            realm.beginTransaction()
            scores.forEach { record.scores?.add(it) }
            realm.commitTransaction()
        }

        val orderedScores = record.scores?.sortedBy { it.score }
        realm.beginTransaction()
        record.scores?.clear()
        orderedScores?.forEach { record.scores?.add(it) }
        realm.commitTransaction()

        return record
    }

    fun deleteRecord(record: Record) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        record.deleteFromRealm()
        realm.commitTransaction()
    }
}
