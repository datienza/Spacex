package com.datienza.spacex.common.extensions

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.datienza.spacex.common.animations.DefaultTransitionAnimation
import com.datienza.spacex.common.animations.TransitionAnimation

inline fun <reified ActivityT : Activity> Activity.startActivity(crossinline buildIntent: Intent.() -> Unit = { }) {
    launchActivity<ActivityT> {
        intent {
            buildIntent()
        }
    }
}

/**
 * Example usage:
 * ```
 * launchActivity<PlanSummaryActivity> {
 *     requestCode = requestCodePlanSummary
 *     options = Bundle()
 *     intent {
 *
 *     }
 *     options {
 *
 *     }
 * }
 * ```
 */
inline fun <reified ActivityT : Activity> Activity.launchActivity(setupActivity: ActivityBuilder.() -> Unit = { }) {
    val intent = Intent(this, ActivityT::class.java)
    val activityBuilder = ActivityBuilder(intent)
    activityBuilder.setupActivity()
    if (activityBuilder.requestCode == ActivityBuilder.REQUEST_CODE_NO_RESULT) {
        startActivity(intent)
    } else {
        startActivityForResult(intent, activityBuilder.requestCode, activityBuilder.options)
    }
}

class ActivityBuilder(private val intent: Intent) {
    companion object {
        const val REQUEST_CODE_NO_RESULT = Int.MIN_VALUE
    }

    var requestCode: Int = REQUEST_CODE_NO_RESULT

    /**
     * Set the options [Bundle], used for startActivityForResult. Make sure you call this method
     * before you use the [options] block so you don't overwrite it.
     */
    var options: Bundle? = null

    /**
     * This will create a new [Bundle] if it hasn't been set yet. If you set the [Bundle] by using
     * [options], make sure you call it before you call this block.
     */
    fun options(setupBundle: Bundle.() -> Unit) {
        if (options == null) {
            options = Bundle()
        }
        options?.setupBundle()
    }

    fun intent(setupIntent: Intent.() -> Unit) {
        intent.setupIntent()
    }
}

fun FragmentActivity.popBackStack() {
    hideKeyboard()
    supportFragmentManager.popBackStack()
}

fun FragmentActivity.popBackOrExit() {
    hideKeyboard()
    if (supportFragmentManager.backStackEntryCount > 0) {
        supportFragmentManager.popBackStack()
    } else {
        finish()
    }
}

fun FragmentActivity.popBackStackInclusive() {
    hideKeyboard()
    if (supportFragmentManager.backStackEntryCount > 0) {
        supportFragmentManager.popBackStack(supportFragmentManager.getBackStackEntryAt(0).id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}

fun FragmentActivity.replaceFragment(fragment: Fragment,
                                     frameId: Int,
                                     addToStack: Boolean = true,
                                     clearBackStack: Boolean = false,
                                     animation: TransitionAnimation? = DefaultTransitionAnimation()
) =
        supportFragmentManager.replaceFragment(
                fragment,
                frameId,
                addToStack,
                clearBackStack,
                animation
        )

fun FragmentActivity.addFragment(fragment: Fragment,
                                 frameId: Int,
                                 addToStack: Boolean = false,
                                 animation: TransitionAnimation? = DefaultTransitionAnimation()
) =
        supportFragmentManager.addFragment(
                fragment,
                frameId,
                addToStack,
                animation
        )

fun FragmentActivity.getCurrentFragment(): Fragment? =
        supportFragmentManager.getCurrentFragment()