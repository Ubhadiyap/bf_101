package edu.nutri.breast_feeding_101.auth.presenter;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.nutri.breast_feeding_101.R;
import edu.nutri.breast_feeding_101.common.util.ValidatorUtil;

/**
 * Created by Akano on 1/20/2018.
 */

public class LoginPresenter {

    EditText emailEt, passwordEt;
    Button loginBtn;
    TextView signUpTv, forgotPasswordTv;
    TextInputLayout passwordLay;

    public LoginPresenter(View view) {

        emailEt = view.findViewById(R.id.emailEt);
        emailEt.setText("kolaak47@yahoo.com");
        passwordEt = view.findViewById(R.id.passwordEt);
        passwordEt.setText("muideen");
        loginBtn = view.findViewById(R.id.loginBtn);
        signUpTv = view.findViewById(R.id.signUpTv);
        forgotPasswordTv = view.findViewById(R.id.forgotPasswordTv);
        passwordLay = view.findViewById(R.id.passwordLay);
        passwordLay.setPasswordVisibilityToggleEnabled(true);

    }

    public boolean isValid(){

        return ValidatorUtil.isEmailValid(emailEt) && ValidatorUtil.isPasswordValid(passwordEt);
    }

    public String getEmail(){
        return  emailEt.getText().toString().trim();
    }

    public String getPassword(){
        return passwordEt.getText().toString().trim();
    }

    public Button getLoginButton() {
        return loginBtn;
    }

    public TextView getForgotPasswordTv() {
        return forgotPasswordTv;
    }

    public TextView getSignUpTv() {
        return signUpTv;
    }
}
