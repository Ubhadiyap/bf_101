package edu.nutri.breast_feeding_101.auth.interactor;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.nutri.breast_feeding_101.auth.fragment.LoginFragment;
import edu.nutri.breast_feeding_101.auth.presenter.LoginPresenter;

/**
 * Created by Akano on 1/20/2018.
 */

public class LoginInteractor {

    LoginPresenter loginPresenter;
    Context context;

    OnCompleteListener onCompleteListener;

    public LoginInteractor(View view, FragmentActivity loginFragment, OnCompleteListener onCompleteListener) {

        context = loginFragment;
        loginPresenter = new LoginPresenter(view);
        this.onCompleteListener = onCompleteListener;
    }

    public boolean isValid(){
        return loginPresenter.isValid();
    }

    public void login(FirebaseAuth firebaseAuth) {

        String email = loginPresenter.getEmail();
        String password = loginPresenter.getPassword();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(onCompleteListener);

    }

    public Button getLoginButton() {
        return loginPresenter.getLoginButton();
    }

    public TextView getSignUpTv() {
        return  loginPresenter.getSignUpTv();
    }

    public TextView getForgotPasswordTv() {
        return loginPresenter.getForgotPasswordTv();
    }
}
