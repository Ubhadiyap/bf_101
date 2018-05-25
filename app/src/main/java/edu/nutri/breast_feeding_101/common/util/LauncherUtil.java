package edu.nutri.breast_feeding_101.common.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by ribads on 4/2/18.
 */

public class LauncherUtil {

    public static void launchClass(Context context, Class aClass) {
        launchClass(context, aClass, null, false);
    }
    public static void launchClass(Context context, Class aClass, Bundle bundle) {
        launchClass(context, aClass, bundle, false);
    }

    public static void launchClass(Context context, Class aClass, Bundle bundle, boolean isNewTask){
        Intent intent = new Intent(context, aClass);
        if (bundle != null)
            intent.putExtras(bundle);
        if (isNewTask)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent, bundle);
    }

    public static void launchFragment(FragmentManager supportFragmentManager, int frame, Fragment fragment, boolean replace){

        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
//        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        if(replace)
            fragmentTransaction.replace(frame, fragment);
        else
            fragmentTransaction.add(frame, fragment);
        fragmentTransaction.commit();
    }
}
