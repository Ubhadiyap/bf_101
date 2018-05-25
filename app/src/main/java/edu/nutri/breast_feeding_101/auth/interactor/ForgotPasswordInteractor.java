package edu.nutri.breast_feeding_101.auth.interactor;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

import edu.nutri.breast_feeding_101.auth.presenter.ForgotPasswordPresenter;
import edu.nutri.breast_feeding_101.common.util.ValidatorUtil;

/**
 * Created by Akano on 1/20/2018.
 */

public class ForgotPasswordInteractor {

    ForgotPasswordPresenter forgotPasswordPresenter;
    FirebaseAuth firebaseAuth;
    OnCompleteListener onCompleteListener;
    public ForgotPasswordInteractor(View view, OnCompleteListener onCompleteListener) {

        forgotPasswordPresenter = new ForgotPasswordPresenter(view);
        firebaseAuth = FirebaseAuth.getInstance();
        this.onCompleteListener = onCompleteListener;

    }

    public void resetPassword() {
        String email = forgotPasswordPresenter.getEmail();

        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(onCompleteListener);
    }

    public boolean isValid(){
        return forgotPasswordPresenter.isValid();
    }

    public Button getResetPasswordBtn() {
        return forgotPasswordPresenter.getResetPasswordBtn();
    }

    public TextView getLoginTv() {
        return forgotPasswordPresenter.getLoginTv();
    }

    public TextView getSignUpTv() {
        return forgotPasswordPresenter.getSignUpTv();
    }

}
