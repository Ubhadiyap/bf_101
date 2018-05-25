package edu.nutri.breast_feeding_101.auth.presenter;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.nutri.breast_feeding_101.R;
import edu.nutri.breast_feeding_101.common.util.ValidatorUtil;

/**
 * Created by Akano on 1/20/2018.
 */

public class ForgotPasswordPresenter {

    EditText emailEt;
    TextView loginTv, signUpTv;
    Button resetPsswordBtn;
    public ForgotPasswordPresenter(View view) {

        emailEt =  view.findViewById(R.id.emailEt);
        loginTv = view.findViewById(R.id.loginTv);
        signUpTv = view.findViewById(R.id.signUpTv);
        resetPsswordBtn = view.findViewById(R.id.resetPasswordBtn);
    }

    public boolean isValid() {
        return ValidatorUtil.isEmailValid(emailEt);
    }

    public Button getResetPasswordBtn() {
        return resetPsswordBtn;
    }

    public TextView getLoginTv() {
        return loginTv;
    }

    public TextView getSignUpTv() {
        return signUpTv;
    }

    public String getEmail(){
        return emailEt.getText().toString().trim();
    }

}
