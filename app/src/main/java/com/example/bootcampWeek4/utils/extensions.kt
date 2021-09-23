package com.example.bootcampWeek4.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

inline fun <reified T : AppCompatActivity> Context.startActivity(block: Intent.() -> Unit = {}) {

    val intent = Intent(this, T::class.java)
    startActivity(
        intent.also {
            block.invoke(it)
        }
    )
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}


fun Fragment.toast(messageToShow: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(requireContext(), messageToShow, duration).show()
}

fun AppCompatActivity.toast(messageToShow: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, messageToShow, duration).show()
}

fun EditText.getString(): String {

    return this.text.toString()

}

fun Fragment.saveDataAsString(key: String, value: String) {
    val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences(
        "MySharedPreferences",
        Context.MODE_PRIVATE
    )

    val myEdit = sharedPreferences.edit()

    myEdit.putString(key, value)

    myEdit.apply()
}