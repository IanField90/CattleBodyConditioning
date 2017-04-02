package com.ianfield.bodyscoring.utils;

import android.support.annotation.StringDef;

/**
 * Created by Ian Field on 05/08/2016.
 */
public class Setting {
    public static final String UK = "UK";
    public static final String NZ = "NZ";

    @StringDef({
            UK,
            NZ,
    })
    public @interface Country {
    }
}
