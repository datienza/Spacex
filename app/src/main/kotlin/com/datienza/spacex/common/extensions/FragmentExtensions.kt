package com.datienza.spacex.common.extensions

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.datienza.spacex.common.animations.DefaultTransitionAnimation
import com.datienza.spacex.common.animations.TransitionAnimation

inline fun <reified ActivityT : Activity> Fragment.startActivity(buildIntent: Intent.() -> Unit = { }) {
    val intent = Intent(activity, ActivityT::class.java)
    intent.buildIntent()

    startActivity(intent)
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
inline fun <reified ActivityT : Activity> Fragment.launchActivity(setupActivity: ActivityBuilder.() -> Unit = { }) {
    val intent = Intent(context, ActivityT::class.java)
    val activityBuilder = ActivityBuilder(intent)

    activityBuilder.setupActivity()

    if (activityBuilder.requestCode == ActivityBuilder.REQUEST_CODE_NO_RESULT) {
        startActivity(intent)
    } else {
        startActivityForResult(intent, activityBuilder.requestCode, activityBuilder.options)
    }
}

fun Fragment.popBackStack() {
    hideKeyboard()
    childFragmentManager.popBackStack()
}

fun Fragment.popBackStackInclusive() {
    hideKeyboard()
    if (childFragmentManager.backStackEntryCount > 0)
        childFragmentManager.popBackStack(childFragmentManager.getBackStackEntryAt(0).id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
}

fun Fragment.replaceFragment(fragment: Fragment,
                             frameId: Int,
                             addToStack: Boolean = true,
                             clearBackStack: Boolean = false,
                             animation: TransitionAnimation? = DefaultTransitionAnimation()
) =
        childFragmentManager.replaceFragment(
                fragment,
                frameId,
                addToStack,
                clearBackStack,
                animation
        )

fun Fragment.addFragment(fragment: Fragment,
                         frameId: Int, addToStack: Boolean = false,
                         animation: TransitionAnimation? = DefaultTransitionAnimation()
) =
        childFragmentManager.addFragment(
                fragment,
                frameId,
                addToStack,
                animation
        )


fun Fragment.getCurrentFragment(): Fragment? =
        childFragmentManager.getCurrentFragment()

inline fun <T: Fragment> T.withArgs(
        argsBuilder: Bundle.() -> Unit): T =
        this.apply {
            arguments = Bundle().apply(argsBuilder)
        }


