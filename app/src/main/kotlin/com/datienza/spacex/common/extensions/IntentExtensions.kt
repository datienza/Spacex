package com.datienza.spacex.common.extensions

import android.content.Context
import android.content.Intent

fun Intent.canBeExecuted(context: Context?): Boolean {
    val queryIntentActivities = context?.packageManager?.queryIntentActivities(this, 0)
    return if (queryIntentActivities != null) {
        queryIntentActivities.size > 0
    } else {
        false
    }

}