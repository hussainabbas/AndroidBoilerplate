package com.company.myapplication.helper.extensions

import androidx.fragment.app.FragmentManager

fun FragmentManager.clearBackStackFragments() {
    this.popBackStack(
        null,
        FragmentManager.POP_BACK_STACK_INCLUSIVE
    )
}