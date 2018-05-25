package edu.nutri.breast_feeding_101.common.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
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
 * Created by ribads on 4/12/18.
 */

public class BaseFragment extends Fragment{

    boolean connectivity = false;
    FirebaseDatabase database;
    public FirebaseUser firebaseUser;
    public FirebaseAuth firebaseAuth;
    public DatabaseReference databaseReference;


    public View getFragmentView(LayoutInflater inflater, int layoutResID, ViewGroup container){
        firebaseInstances();
        return inflater.inflate(layoutResID, container, false);
    }

    public void firebaseInstances() {
        database = FirebaseDatabase.getInstance();
        Firebase.setAndroidContext(getActivity());
        if (BuildConfig.DEBUG)
            databaseReference = database.getReference().child("Test");
        else
            databaseReference = database.getReference().child("Live");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        Check_firebase();
    }

    public void logOut(Context context){
        FirebaseAuth.getInstance().signOut();
        LauncherUtil.launchClass(context, AuthActivity.class);
    }

    public void showProgress(String message){
        showProgress(message, false);
    }

    public void showProgress(String message, Boolean cancelable){
        final ViewGroup viewGroup = (ViewGroup) getActivity().getWindow().getDecorView();
        final View progressView = LayoutInflater.from(getActivity()).inflate(R.layout.progress_view, null, true);
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
        final ViewGroup viewGroup = (ViewGroup) getActivity().getWindow().getDecorView();
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
