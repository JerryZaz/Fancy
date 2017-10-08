package us.hnry.fancy.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * @author Henry
 * 10/7/2017
 */
object SystemUtil {

    fun hideSoftKeyboard(context: Context, view: View?) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}