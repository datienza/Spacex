package com.datienza.spacex.common.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.datienza.spacex.common.animations.TransitionAnimation

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun FragmentManager.replaceFragment(fragment: Fragment,
                                    frameId: Int,
                                    addToStack: Boolean = true,
                                    clearBackStack: Boolean = false,
                                    animation: TransitionAnimation?) {
    inTransaction {
        if (clearBackStack && backStackEntryCount > 0) {
            val first = getBackStackEntryAt(0)
            popBackStack(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        animation?.let {
            setCustomAnimations(animation.enter, animation.exit, animation.popEnter, animation.popExit)
        }

        if (addToStack) {
            replace(frameId, fragment, fragment.javaClass.simpleName)
                    .addToBackStack(fragment.javaClass.simpleName)
        } else {
            replace(frameId, fragment, fragment.javaClass.simpleName)
        }
    }
}

fun FragmentManager.addFragment(fragment: Fragment,
                                frameId: Int,
                                addToStack: Boolean = false,
                                animation: TransitionAnimation?) {
    inTransaction {
        animation?.let {
            setCustomAnimations(animation.enter, animation.exit, animation.popEnter, animation.popExit)
        }
        if (addToStack) {
            add(frameId, fragment, fragment.javaClass.simpleName)
                    .addToBackStack(fragment.javaClass.simpleName)
        } else {
            add(frameId, fragment)
        }
    }
}

fun FragmentManager.getCurrentFragment(): Fragment? {
    var fragmentTag: String? = ""

    if (backStackEntryCount > 0) {
        fragmentTag = getBackStackEntryAt(backStackEntryCount - 1).name

    }
    return findFragmentByTag(fragmentTag)
}