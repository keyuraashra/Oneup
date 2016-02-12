package com.oneup.app.constants;

import com.oneup.app.R;

/**
 * Created by Scorpion on 11-02-2016.
 */
public class Constants {
    public static final String PREFERENCE_NAME = "com.oneup.app.preference_";
    public static final String IS_REGISTERED = PREFERENCE_NAME + "is_register";

    // User details
    public static final String NAME = PREFERENCE_NAME + "name";
    public static final String EMAIL = PREFERENCE_NAME + "email";
    public static final String PASSWORD = PREFERENCE_NAME + "password";

    // User card details
    public static final String CARD_FIRST_NAME = PREFERENCE_NAME + "card_fname";
    public static final String CARD_LAST_NAME = PREFERENCE_NAME + "card_lname";
    public static final String CARD_NUMBER = PREFERENCE_NAME + "card_number";
    public static final String CARD_EXPIRY = PREFERENCE_NAME + "card_expiry";
    public static final String CARD_ADD_1 = PREFERENCE_NAME + "card_add_1";
    public static final String CARD_ADD_2 = PREFERENCE_NAME + "card_add_2";

    //Not A Look Image ID Array
    public static Integer[] ImageIds = {
            R.drawable.ic_choose_first, R.drawable.ic_choose_second,
            R.drawable.ic_choose_third, R.drawable.ic_choose_fourth,
            R.drawable.ic_choose_fifth, R.drawable.ic_choose_sixth,
            R.drawable.ic_choose_eighth, R.drawable.ic_choose_nine,
            R.drawable.ic_choose_ten, R.drawable.ic_choose_eleven,
            R.drawable.ic_choose_twelve, R.drawable.ic_choose_thirteen,
            R.drawable.ic_choose_fourteen, R.drawable.ic_choose_fifteen,
            R.drawable.ic_choose_sixteen, R.drawable.ic_choose_seventeen,
    };
}
