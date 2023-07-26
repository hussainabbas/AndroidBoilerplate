package com.company.myapplication.helper.extensions

import android.animation.Animator
import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.content.Context
import android.widget.EditText
import android.widget.TextView
import com.company.myapplication.R

@SuppressLint("ResourceType")
fun TextView.setErrorValue(value: String, editText: EditText, context: Context) {
    visible()
    text = value
    editText.setBackgroundResource(R.drawable.ic_ed_bg_red)
    editText.requestFocus()
    val animator = AnimatorInflater.loadAnimator(context, R.anim.bounce)
    animator.setTarget(editText)
    animator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(p0: Animator) {
        }

        override fun onAnimationEnd(p0: Animator) {
            editText.translationX = 0.1f // reset the translationY property
        }

        override fun onAnimationCancel(p0: Animator) {
        }

        override fun onAnimationRepeat(p0: Animator) {
        }
    })

    animator.start()
    //editText.startAnimation(animation)
}

fun TextView.clearErrorValue(editText: EditText) {
    gone()
    editText.setBackgroundResource(R.drawable.ic_ed_bg)
}
