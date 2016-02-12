package com.oneup.app.utils;

import java.text.SimpleDateFormat;

/**
 * Created by Scorpion on 12-02-2016.
 */
public class DateTimeUtils {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String CARD_EXPIRY_FORMAT = "dd - MMM yyyy";

    public static SimpleDateFormat getCardExpiryFormat() {
        return new SimpleDateFormat(CARD_EXPIRY_FORMAT);
    }

    public static SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat(DATE_FORMAT);
    }
}
