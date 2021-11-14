package com.example.secondhiltapp.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import com.example.secondhiltapp.R
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

fun Context.snackBar(msg: String,activity: Activity, save: Boolean = true){
    Snackbar.make(activity.findViewById(R.id.bottom_nav), msg, Snackbar.LENGTH_LONG).apply {
        if (save){
            this.setAction(R.string.ok){}
        }
    }.show()
}

fun getLocale(res: Resources): Locale {
    val config = res.configuration
    return if (Build.VERSION.SDK_INT >= 24) config.locales.get(0) else config.locale
}

fun Context.goTo3WE(){
    val openURL = Intent(Intent.ACTION_VIEW)
    openURL.data = Uri.parse(URL_3WE)
    this.startActivity(openURL, null)
}

fun convertToCustomFormat(dateStr: String?): String {
    val utc = TimeZone.getTimeZone("UTC")
    val sourceFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssss")//SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy")
    val destFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm aa")
    sourceFormat.timeZone = utc
    val convertedDate = sourceFormat.parse(dateStr)
    return destFormat.format(convertedDate)
}

fun getResourceString(id: Int): String = getResourceString(id)


val <T> T.exhaustive: T
    get() = this