package com.tillagewireless.ss.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.tillagewireless.ss.data.db.models.Point
import com.tillagewireless.ss.data.network.Resource
import com.tillagewireless.ss.others.Constants
import com.tillagewireless.ss.ui.auth.LoginFragment
import com.tillagewireless.ss.ui.home.HomeActivity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

val serverDateFormat: SimpleDateFormat =
    SimpleDateFormat(Constants.SERVER_DATE_TIME_FORMAT, Locale.getDefault())
val deviceDateFormat: SimpleDateFormat =
    SimpleDateFormat(Constants.DEVICE_DATE_TIME_FORMAT, Locale.getDefault())

fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}

fun Fragment.logout() = lifecycleScope.launch {
    if (activity is HomeActivity) {
        (activity as HomeActivity).performLogout()
    }
}

fun Service.coordinateJsonToListConverter(stringPoints: String?): List<Point>{
    stringPoints?.let {
        return Gson().fromJson(stringPoints, Array<Point>::class.java).toList()
    }?: return listOf()
}

fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> {
                requireView().snackbar(
                    "Please check your internet connection",
                    retry
                )
        }
        failure.errorCode == 401 -> {
            if (this is LoginFragment) {
                requireView().snackbar("${failure.errorCode}")
            } else {
                logout()
            }
        }
        else -> {
            val error = failure.errorBody?.string().toString()
            requireView().snackbar(error)
        }
    }
}

fun handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    Log.d(HomeActivity.TAG,"Response FAILED $failure")
    when {
        failure.isNetworkError -> {

        }
        failure.errorCode == 401 -> {

        }
        else -> {
            val error = failure.errorBody?.string().toString()
        }
    }
}

fun EditText.transformIntoDatePicker(context: Context, format: String, maxDate: Date? = null) {
    isFocusableInTouchMode = false
    isClickable = true
    isFocusable = false

    val myCalendar = Calendar.getInstance()
    val datePickerOnDataSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val sdf = SimpleDateFormat(format, Locale.UK)
            setText(sdf.format(myCalendar.time))
        }

    setOnClickListener {
        DatePickerDialog(
            context, android.R.style.Theme_Holo_Light_Dialog_MinWidth, datePickerOnDataSetListener, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        ).run {
            maxDate?.time?.also { datePicker.maxDate = it }
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            show()
        }
    }
}
fun View.hideKeyboard(){
    val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}

private fun serverTimeStampToMilli(ts: String): Long {
    try {
        return serverDateFormat.parse(ts).time
    } catch (ex: Exception) {
        Log.d("Exception", "Date parse error!!")
    }
    return 0
}

private fun deviceTimeStampToMilli(ts: String): Long {
    try {
        return deviceDateFormat.parse(ts).time
    } catch (ex: Exception) {
        Log.d("Exception", "Date parse error!!")
    }
    return 0
}

private fun serverMilliToTimeStamp(ms: Long): String {
    return serverDateFormat.format(Date(ms))
}

private fun deviceMilliToTimeStamp(ms: Long): String {
    return deviceDateFormat.format(Date(ms))
}
