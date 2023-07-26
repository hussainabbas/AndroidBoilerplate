package com.company.myapplication.helper.utils

import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionInflater
import android.view.Gravity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.company.myapplication.R


fun loadFragment(fragment: Fragment, activity: FragmentActivity, bundle: Bundle? = null) {
    try {
        val slideTransitionEnter = Slide(Gravity.BOTTOM)
        val slideTransitionExit = Slide(Gravity.TOP)
        slideTransitionEnter.duration = 700
        slideTransitionExit.duration = 350
        fragment.enterTransition = slideTransitionEnter
        fragment.exitTransition = slideTransitionExit
        val changeBoundsTransition =
            TransitionInflater.from(activity.applicationContext)
                .inflateTransition(R.transition.activity_slide)
        fragment.sharedElementEnterTransition = changeBoundsTransition
        fragment.arguments = bundle
        fragment.allowEnterTransitionOverlap = false
        fragment.allowReturnTransitionOverlap = false
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment, fragment.javaClass.name)
            .addToBackStack(fragment.javaClass.name)
            .commit()
    } catch (e: Exception) {
    }
}

fun loadDialogFragment(activity: FragmentActivity, dialogFragment: DialogFragment) {
    try {
        val newFragment: DialogFragment = dialogFragment
        val slideTransitionEnter = Slide(Gravity.BOTTOM)
        val slideTransitionExit = Slide(Gravity.TOP)
        slideTransitionEnter.duration = 700
        slideTransitionExit.duration = 350
        newFragment.enterTransition = slideTransitionEnter
        newFragment.exitTransition = slideTransitionExit
        val changeBoundsTransition =
            TransitionInflater.from(activity.applicationContext)
                .inflateTransition(R.transition.activity_slide)
        newFragment.sharedElementEnterTransition = changeBoundsTransition
        newFragment.allowEnterTransitionOverlap = false
        newFragment.allowReturnTransitionOverlap = false
        newFragment.show(activity.supportFragmentManager, dialogFragment.javaClass.name)
    } catch (e: Exception) {
    }
}