package edu.nutri.breast_feeding_101.auth.view;

import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.database.FirebaseDatabase;

import edu.nutri.breast_feeding_101.MainActivity;
import edu.nutri.breast_feeding_101.R;
import edu.nutri.breast_feeding_101.common.util.LauncherUtil;
import edu.nutri.breast_feeding_101.common.view.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            // TODO: 5/25/18 Check for update
                if (firebaseUser == null)
                LauncherUtil.launchClass(getApplicationContext(), AuthActivity.class, null, true);
                else
                    LauncherUtil.launchClass(getApplicationContext(), MainActivity.class, null, true);
//                LauncherUtil.launchClass(getApplicationContext(), MainActivity.class, null, true);
        }, 1000 );
    }
}
