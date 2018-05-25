package edu.nutri.breast_feeding_101.common.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Debug;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.nutri.breast_feeding_101.BuildConfig;
import edu.nutri.breast_feeding_101.R;
import edu.nutri.breast_feeding_101.UserDetails;
import edu.nutri.breast_feeding_101.auth.view.AuthActivity;
import edu.nutri.breast_feeding_101.common.util.LauncherUtil;

/**
 * Created by ribads on 4/2/18.
 */

public class BaseActivity extends AppCompatActivity {

    boolean connectivity = false;
    FirebaseDatabase database;
    public FirebaseUser firebaseUser;
    public FirebaseAuth firebaseAuth;
    public DatabaseReference databaseReference;
    public String email, userId;
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initBar("", true);
        firebaseInstances();
    }

    public void firebaseInstances() {
        database = FirebaseDatabase.getInstance();
        Firebase.setAndroidContext(this);
        if (BuildConfig.DEBUG)
            databaseReference = database.getReference().child("Test");
        else
            databaseReference = database.getReference().child("Live");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        email = firebaseUser.getEmail();
        userId = firebaseUser.getUid();
        Check_firebase();
    }

    public void setContentView(int layoutResID, String titleBarText, boolean back) {
        super.setContentView(layoutResID);
        initBar(titleBarText, back);
    }

    public void setContentView(int layoutResID, String titleBarText) {
        super.setContentView(layoutResID);
        initBar(titleBarText, true);
    }

    private void initBar(String titleBarText, boolean back) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null && !isTaskRoot()) {
            actionBar.setDisplayHomeAsUpEnabled(back);
            actionBar.setTitle(titleBarText);
            actionBar.setElevation(1);
        }
    }

    public void logOut(Context context){
        FirebaseAuth.getInstance().signOut();
        LauncherUtil.launchClass(context, AuthActivity.class);
    }

    public void showProgress(String message){
        showProgress(message, false);
    }

    public void showProgress(String message, Boolean cancelable){
        final ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView();
        final View progressView = LayoutInflater.from(this).inflate(R.layout.progress_view, null, true);
        progressView.setTag(780);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        viewGroup.addView(progressView, params);
        progressView.setVisibility(View.VISIBLE);
        TextView textView = progressView.findViewById(R.id.message);
        if (message != null && textView != null) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(message);
        }
    }

    public void hideProgress(){
        final ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView();
        View progressView = viewGroup.findViewWithTag(780);
        if (progressView != null) {
            progressView.setVisibility(View.GONE);
            viewGroup.removeView(progressView);
        }
    }

    public void Check_firebase() {
        Firebase connectedRef = new Firebase(UserDetails.database_url + ".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected)
                    connectivity = true;
                else
                    connectivity = false;
            }
            @Override
            public void onCancelled(FirebaseError error) {
//                System.err.println("Listener was cancelled");
            }
        });
    }
}
