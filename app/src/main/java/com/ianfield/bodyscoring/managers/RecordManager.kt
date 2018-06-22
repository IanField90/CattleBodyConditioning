package com.ianfield.bodyscoring.managers

import com.ianfield.bodyscoring.models.Record
import com.ianfield.bodyscoring.models.Score
import com.ianfield.bodyscoring.utils.ScoreScale
import com.ianfield.bodyscoring.utils.Setting

import io.realm.Realm
import io.realm.RealmResults
import java.util.*

/**
 * Created by Ian on 14/01/2016.
 */
object RecordManager {

    val allRecords: RealmResults<Record>
        get() = Realm.getDefaultInstance()
                .where(Record::class.java)
                .findAll()

    fun createRecord(record: Record): Record {
        var record = record
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
        record = realm.copyToRealm(record)
        for (score in scores) {
            realm.copyToRealm(score)
            record.scores?.add(score)
        }
        realm.commitTransaction()

        return record
    }

    fun updateRecord(record: Record): Record {
        return Realm.getDefaultInstance().copyToRealmOrUpdate(record)
    }

    fun getRecordById(id: String): Record? {
        return Realm.getDefaultInstance()
                .where(Record::class.java)
                .equalTo("id", id)
                .findFirst()
    }

    fun deleteRecord(record: Record) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        record.deleteFromRealm()
        realm.commitTransaction()
    }
}
