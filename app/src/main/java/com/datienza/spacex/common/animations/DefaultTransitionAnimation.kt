package com.datienza.spacex.common.animations

import com.test.emptyapplication.R

class DefaultTransitionAnimation : TransitionAnimation {
    override val enter: Int = R.anim.slide_in_right
    override val exit: Int = R.anim.slide_out_left
    override val popEnter: Int = android.R.anim.slide_in_left
    override val popExit: Int = android.R.anim.slide_out_right
}