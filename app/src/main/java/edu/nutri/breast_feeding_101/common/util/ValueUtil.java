package edu.nutri.breast_feeding_101.common.util;

import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ribads on 4/2/18.
 */

public class ValueUtil {

    public static String getText(TextView textView){
        return textView.getText().toString();
    }

    public static void initTextView(TextView mTextView, String value){
        if (mTextView != null && value != null){
            mTextView.setText(value);
        }
    }

    public static Calendar getCalendar(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

}
