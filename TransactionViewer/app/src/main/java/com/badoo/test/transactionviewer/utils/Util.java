package com.badoo.test.transactionviewer.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

/**
 * Created by yaozhong on 02/11/2016.
 */

public final class Util {

    // Load local json file
    public static String LoadJsonFile(String filename, Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    // Round float to 2 decimal
    public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    // Match corresponding currency symbol
    public static String getCurrencySymbol(String currency) {
        switch (currency) {
            case "EUR":
                return "€";
            case "CAD":
                return "CA$";
            case "USD":
                return "$";
            case "GBP":
                return "£";
            case "AUD":
                return "A$";
        }
        return "@";
    }

}
