
package com.amikom.kulinerjogja.utils;

import android.util.Log;

public class LogManager {

    public static void print(String message) {
        boolean isDebug = true;
        if (isDebug) {
            Log.d("Kuliner Jogja", message);
        }
    }
}
