package edu.nutri.breast_feeding_101;

import java.util.ArrayList;

public class UserDetails {


    static ArrayList admin_email_list = new ArrayList();

    private static String pre_ass_score;
    private static String quiz_score;
    private static String username;
    private static String email ;
    private static  String user_id ;
    static Boolean admin = false;
    static Boolean checked_update = false;
    static Boolean show_ads = false;
    static String database_url = "https://bf101-5e3c5.firebaseio.com/";

    public UserDetails() {


    }

    public UserDetails(String username, String  email, String user_id) {

        this.username = username;
        this.email = email;
        this.user_id = user_id;

    }


    public void set_username(String username2){
        this.username = username2;
    }

    public String get_username(){
        return this.username;
    }

    public void set_email(String email){
        this.email = email;
    }

    public String get_email(){
        return this.email;
    }

    public void set_user_id(String user_id){
        this.user_id = user_id;
    }

    public String get_user_id(){
        return this.user_id;
    }

    public void set_pre_ass_score(String pre_ass_score){
        this.pre_ass_score = pre_ass_score;
    }

    public String get_pre_ass_score(){
        return this.pre_ass_score;
    }

    public void set_quiz_score(String quiz_score){
        this.quiz_score = quiz_score;
    }

    public String get_quiz_score(){
        return this.quiz_score;
    }

    public void set_checked_update(boolean checked_update){
        this.checked_update = checked_update;
    }

    public boolean get_checked_update(){
        return checked_update;
    }

    public void set_show_ads(boolean show_ads){
        this.show_ads = show_ads;
    }

    public boolean get_show_ads(){
        return show_ads;
    }

    public void set_admin(boolean admin){
        this.admin = admin;
    }

    public boolean get_admin(){
        return admin;
    }

    public void set_admin_email_list(String email) {
        admin_email_list.add(email);
    }

    public ArrayList get_admin_email_list(){

        return admin_email_list;
    }
}




