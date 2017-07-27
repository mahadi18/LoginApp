package com.mahadi.loginapp.Login;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import com.mahadi.loginapp.*;

/**
 * Created by Mahadi on 27-07-17.
 */

public class Userloginsession {

    public static final String IS_User_login = "isuserloggedin";
    //    {"did":"1","drivername":"arul ji","dusername":"PIKDRIVER01","logid":"79"}
    //Driver Login details
    //From DRIVER
    public static final String IS_SNO = "sno";
    public static final String IS_EMAIL = "userloginname";
    public static final String IS_USERPASSWORD = "userloginpassword";
    //
    public static final String IS_EMP_ID = "emp_id";
    //
    //From Json Driver
    public static final String IS_FIRST_NAME = "first_name";
    public static final String IS_LAST_NAME = "last_name";
    public static final String IS_IMAGE = "image";

    static SharedPreferences user_details;
    // Editor Reference for sharedpref
    SharedPreferences.Editor user_details_editor;

    public Userloginsession(final Context applicationContext) {

        // create sharedpreff file "driverSession" for DRIVERLOGINACTIVITY
        user_details = applicationContext.getSharedPreferences("usersession",0);

        //Edit pfeff file
        user_details_editor = user_details.edit();
        user_details_editor.apply();


    }
    public static boolean isuserLoggedIn() {
        return user_details.getBoolean(IS_User_login, false);
    }
    public void createuserLogin(String passwordp, String email, String SNO, final String EMP_ID, final String FIRST_NAME, final String LAST_NAME, final String Image) {
        user_details_editor.putBoolean(IS_User_login, true);
        user_details_editor.putString(IS_EMAIL, email);
        user_details_editor.putString(IS_USERPASSWORD, passwordp);
        user_details_editor.putString(IS_SNO, SNO);
        user_details_editor.putString(IS_EMP_ID, EMP_ID);
        user_details_editor.putString(IS_FIRST_NAME, FIRST_NAME);
        user_details_editor.putString(IS_LAST_NAME, LAST_NAME);
        user_details_editor.putString(IS_IMAGE, Image);
        user_details_editor.commit();
    }

    public HashMap<String, String> isGetuserDetails() {
        // Use hashmap to store user credentials
        final HashMap<String, String> userdetailsmap = new HashMap<>();

        // Driv pass
        userdetailsmap.put(IS_EMAIL, user_details.getString(IS_EMAIL, null));   // Driv Pass

        // Driver user name
        userdetailsmap.put(IS_USERPASSWORD, user_details.getString(IS_USERPASSWORD, null));

        // Driver  ID
        userdetailsmap.put(IS_SNO, user_details.getString(IS_SNO, null));

        //Driver Name
        userdetailsmap.put(IS_EMP_ID, user_details.getString(IS_EMP_ID, null));
        userdetailsmap.put(IS_FIRST_NAME, user_details.getString(IS_FIRST_NAME, null));
        userdetailsmap.put(IS_LAST_NAME, user_details.getString(IS_LAST_NAME, null));
        userdetailsmap.put(IS_IMAGE, user_details.getString(IS_IMAGE, null));

        return userdetailsmap;
    }

    public void clearAllvalues() {

        user_details_editor = user_details.edit();
        user_details_editor.clear();
        user_details_editor.apply();

    }
}
