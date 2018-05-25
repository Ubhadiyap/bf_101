package edu.nutri.breast_feeding_101.database.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ribads on 4/11/18.
 */
@IgnoreExtraProperties
public class User {

    public void user(){

    }

    private static String USER_ID;
//    private static String FIRST_NAME;
//    private static String LAST_NAME;
    private static String USERNAME;
    private static String EMAIL;
    private static String GENDER;
    private static String NO_OF_CHILDREN;
    private static String LEVEL_OF_EDUCATION;
    private static String DATE_OF_BIRTH;
    private static boolean ADMIN;
    private static String PROFILE_PHOTO;

    public void setUserId(String userId){
        USER_ID = userId;
    }

    public String getUserId(){
        return USER_ID;
    }

    public void setUserName(String userName){
        USERNAME = userName;
    }

    public String getUserName(){
        return USERNAME;
    }

    public void setAdmin(boolean isAdmin){
        ADMIN = isAdmin;
    }

    public boolean getAdmin(){
        return ADMIN;
    }

//    public void setFirstName(String firstName){
//        FIRST_NAME = firstName;
//    }
//
//    public String getFirstName(){
//        return FIRST_NAME;
//    }
//
//    public void setLastName(String lastName){
//        LAST_NAME = lastName;
//    }
//
//    public String getLastName(){
//        return LAST_NAME;
//    }

    public void setEmail(String email){
        EMAIL = email;
    }

    public String getEmail(){
        return EMAIL;
    }

    public void setGender(String gender){
        GENDER = gender;
    }

    public String getGender(){
        return GENDER;
    }

    public void setNoOfChildren(String noOfChildren){
        NO_OF_CHILDREN = noOfChildren;
    }

    public String getNoOfChildren(){
        return NO_OF_CHILDREN;
    }

    public void setLevelOfEducation(String levelOfEducation){
        LEVEL_OF_EDUCATION = levelOfEducation;
    }

    public String getLevelOfEducation(){
        return LEVEL_OF_EDUCATION;
    }

    public void setDateOfBirth(String  dateOfBirth){
        DATE_OF_BIRTH = dateOfBirth;
    }

    public String getDateOfBirth(){
        return DATE_OF_BIRTH;
    }

    public void setProfilePhoto(String profilePhoto){
        PROFILE_PHOTO = profilePhoto;
    }

    public String getProfilePhoto(){
        return PROFILE_PHOTO;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", USER_ID);
        result.put("userName", USERNAME);
        result.put("email", EMAIL);

        return result;
    }
}
