package com.xmlparse.utils;

/**
 * Created by guoxinggen on 10/11/16.
 *
 * The text tool class.
 */
public class TextUtils {


    public static final boolean isEmpty(String string) {

        if (string == null && string.equals("")) {
            return true;
        }
        return false;
    }

}
