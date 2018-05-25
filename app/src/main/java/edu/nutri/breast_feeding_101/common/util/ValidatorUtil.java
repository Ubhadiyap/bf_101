package edu.nutri.breast_feeding_101.common.util;

import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static edu.nutri.breast_feeding_101.common.util.Constants.PASSWORD_MIN_LENGTH;
import static edu.nutri.breast_feeding_101.common.util.Constants.USERNAME_MIN_LENGTH;

/**
 * Created by Akano on 1/21/2018.
 */

public class ValidatorUtil {

    public static boolean isEmailValid(EditText emailEt){
        String email = emailEt.getText().toString().trim();

        String expression = "^[\\w\\.]+@([\\w]+\\.)+[A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        boolean valid = matcher.matches();

        if (!valid) emailEt.setError("Invalid email");

        return valid;
    }

    public static boolean isPasswordValid(EditText passwordEt){
        String password = passwordEt.getText().toString().trim();

        boolean valid = password.length() >= PASSWORD_MIN_LENGTH ;

        if (!valid) passwordEt.setError("Password too short");

        return valid;
    }

    public static boolean isNameValid(EditText editText){

        boolean valid = editText.length() >= USERNAME_MIN_LENGTH;

        if (!valid) editText.setError("length too short");

        return valid;
    }

    public static boolean isNotEmpty(EditText editText){

        boolean valid = editText.getText().toString().trim().length() > 0;

        if (!valid) editText.setError("field can not be empty");

        return valid;

    }

}
