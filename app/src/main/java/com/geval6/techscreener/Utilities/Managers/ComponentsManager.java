package com.geval6.techscreener.Utilities.Managers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.HashMap;
import java.util.regex.Pattern;

public class ComponentsManager {

    public static String stringByReplacingValues(String string, HashMap<String, String> hashMap) {

        for (HashMap.Entry<String, String> val : hashMap.entrySet()) {
            string = string.replace("{" + val.getKey() + "}", val.getValue());
        }
        return string;

    }

    public static boolean isEmail(String email) {
        return Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(email).matches();
    }
    public static boolean getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return true;
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return true;
        }
        return false;
    }
}
