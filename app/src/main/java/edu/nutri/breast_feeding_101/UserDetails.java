package edu.nutri.breast_feeding_101;

import android.app.Activity;

import java.util.ArrayList;

public class UserDetails {

    static ArrayList admin_email = new ArrayList();

    static String username = "";
    static String email = "";
    static String user_id = "";
    static Boolean admin = false;
    static Boolean checked_update = false;
    static Boolean show_ads = false;
    public static String database_url = "https://bf101-5e3c5.firebaseio.com/";

    public static  void add_admin(String email) {
        admin_email.add(email);
    }
}


