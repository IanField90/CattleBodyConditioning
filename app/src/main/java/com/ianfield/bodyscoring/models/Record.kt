package com.ianfield.bodyscoring.models

import android.text.TextUtils

import com.ianfield.bodyscoring.utils.Setting

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.Sort
import io.realm.annotations.PrimaryKey

/**
 * Created by Ian Field on 09/01/2016.
 *
 *
 * Store the scoring numbers for a herd, UK scale and NZ scale
 * 1. Drying off ( 7–8 weeks pre-calving)
 * 2. Pre-calving ( 3 weeks pre-calving)
 * 3. Pre-service
 */
open class Record : RealmObject() {
    var id: String? = null
    var name: String? = null
    var plannedCalvingDate: Date? = null
    var scoringDate: Date? = null
    @get:Setting.Country
    var setting: String? = null
    var scores: RealmList<Score>? = null

    val isValidRecord: Boolean
        get() = plannedCalvingDate != null && scoringDate != null && !TextUtils.isEmpty(name)

//    init {
//        this.id = UUID.randomUUID().toString()
//    }

    fun toExport(): String {
        val df = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        var export = String.format("%s\n", name)
        export += "Recorded on: ${df.format(scoringDate)}\n"
        export += "Planned start of calving: ${df.format(plannedCalvingDate)}\n\n"

        scores?.forEach {
            export += "${it.score}: ${it.count}\n"
        }
        return export
    }
}
