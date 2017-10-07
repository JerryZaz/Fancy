package us.hnry.fancy.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author Henry
 *         10/6/2017
 */

public class SystemUtil {
    private SystemUtil() {
        // default empty constructor
    }

    public static void hideSoftKeyboard(@NonNull Context context, @Nullable View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
