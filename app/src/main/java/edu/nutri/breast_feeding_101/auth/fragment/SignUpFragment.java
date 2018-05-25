package edu.nutri.breast_feeding_101.auth.fragment;


import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.nutri.breast_feeding_101.Database;
import edu.nutri.breast_feeding_101.MainActivity;
import edu.nutri.breast_feeding_101.R;
import edu.nutri.breast_feeding_101.auth.interactor.SignUpInteractor;
import edu.nutri.breast_feeding_101.common.util.LauncherUtil;
import edu.nutri.breast_feeding_101.common.util.NotificationUtil;
import edu.nutri.breast_feeding_101.common.view.BaseFragment;
import edu.nutri.breast_feeding_101.database.models.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends BaseFragment implements OnCompleteListener, OnSuccessListener {

    public SignUpFragment() {
    }

    SignUpInteractor signUpInteractor;
    Button signUpBtn;
    TextView loginTv, forgotPasswordTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = getFragmentView(inflater,R.layout.fragment_sign_up, container);

        return view;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signUpInteractor = new SignUpInteractor(view, getActivity(), this );

        initViews();

    }

    private void initViews() {
        loginTv = signUpInteractor.getLoginTv();
        loginTv.setOnClickListener(v ->
                LauncherUtil.launchFragment(getFragmentManager(), R.id.frame, new LoginFragment(), true));

        forgotPasswordTv = signUpInteractor.getForgotPasswordTv();
        forgotPasswordTv.setOnClickListener(v ->
                LauncherUtil.launchFragment(getFragmentManager(), R.id.frame, new ForgotPasswordFragment(), true));

        signUpBtn = signUpInteractor.getSignUpButton();
        signUpBtn.setOnClickListener(v ->
                signUp());
    }

    public void signUp(){
        if (signUpInteractor.isValid()){
            showProgress("Signing up...");
            signUpInteractor.signUp(firebaseAuth);
        }
    }


    @Override
    public void onComplete(@NonNull Task task) {

        if (task.isSuccessful()) {
            firebaseUser = firebaseAuth.getCurrentUser();
            signUpInteractor.saveUsersData(firebaseUser, databaseReference, this);
        }
        else {
            hideProgress();
            NotificationUtil.showFailureDialog(getFragmentManager(), "ERROR", "SignUp Failed");
        }
    }

    @Override
    public void onSuccess(Object o) {
        hideProgress();
        LauncherUtil.launchClass(getActivity(), MainActivity.class);
    }
}
