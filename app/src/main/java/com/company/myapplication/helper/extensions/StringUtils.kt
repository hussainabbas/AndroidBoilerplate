package com.company.myapplication.helper.extensions

import java.text.SimpleDateFormat
import java.util.*

fun String.formatToDate(): String? {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.sss'Z'", Locale.getDefault())
    val date = inputFormat.parse(this)
    val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return date?.let { outputFormat.format(it) }
}

fun String.initials(): String {
    return this.substring(0, 1)
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
}

fun String.firstNameInitials(): String {
    return this.substring(0, 2)
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
}