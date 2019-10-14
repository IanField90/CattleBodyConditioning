package com.ianfield.bodyscoring.utils

import androidx.annotation.StringDef

/**
 * Created by Ian Field on 05/08/2016.
 */
class Setting {

    companion object {
        const val UK = "UK"
        const val NZ = "NZ"
    }


    @StringDef(UK, NZ)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Country
}
