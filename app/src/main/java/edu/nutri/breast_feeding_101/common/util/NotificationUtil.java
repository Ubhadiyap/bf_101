package edu.nutri.breast_feeding_101.common.util;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;

import edu.nutri.breast_feeding_101.common.widget.PopupDialogFragment;

/**
 * Created by ribads on 4/2/18.
 */

public class NotificationUtil {

    public static void showSuccessDialog(FragmentManager manager, String message){
        showDialog(manager, true, "SUCCESS", message, null, null);
    }

    public static void showSuccessDialog(FragmentManager manager, String title, String message){
        showDialog(manager, true, title, message, null, null);
    }

    public static void showSuccessDialog(FragmentManager manager, String title, String message,
                                  View.OnClickListener listener1){
        showDialog(manager, true, title, message, listener1, null);
    }

    public static void showFailureDialog(FragmentManager manager,  String message){
        showDialog(manager, false, "ERROR", message, null, null);
    }

    public static void showFailureDialog(FragmentManager manager,  String title, String message){
        showDialog(manager, false, title, message, null, null);
    }

    public static void showFailureDialog(FragmentManager manager, String title, String message,
                                  View.OnClickListener listener1){
        showDialog(manager, false, title, message, listener1, null);
    }

    public static void showDialog(FragmentManager manager, Boolean status, String title, String message,
                                  View.OnClickListener listener1, View.OnClickListener listener2){

        PopupDialogFragment dialogFragment = PopupDialogFragment.newInstance(status, title, message);
        dialogFragment.attachListener(listener1, listener2);
        dialogFragment.show(manager, null);

    }

    public static void showSnackBar(){

//        Snackbar.make()
    }

    public static void showToast(Context context, String message){
        showToast(context, message, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, String message, int lenght){
        Toast.makeText(context, message, lenght).show();
    }

}
