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

public class SignUpPresenter{

    EditText firstNameEt, emailEt, passwordEt;
    TextInputLayout passwordLay;
    TextView loginTv, forgotPasswordTv;
    Button signUpBtn;

    public SignUpPresenter(View view) {

        firstNameEt = view.findViewById(R.id.firstNameEt);
        firstNameEt.setText("name");
        emailEt = view.findViewById(R.id.emailEt);
        emailEt.setText("kolaak47@yahoo.com");
        passwordEt = view.findViewById(R.id.passwordEt);
        passwordEt.setText("muideen");
        passwordLay = view.findViewById(R.id.passwordLay);
        loginTv = view.findViewById(R.id.loginTv);
        forgotPasswordTv = view.findViewById(R.id.forgot_password);
        signUpBtn = view.findViewById(R.id.signUpBtn);

        passwordLay.setPasswordVisibilityToggleEnabled(true);

    }

    public boolean isValid() {
        return ValidatorUtil.isNameValid(firstNameEt) && ValidatorUtil.isEmailValid(emailEt) && ValidatorUtil.isPasswordValid(passwordEt);
    }

    public TextView getLoginTv() {
        return loginTv;
    }

    public Button getSignUpButton() {
        return signUpBtn;
    }

    public TextView getForgotPasswordTv() {
        return forgotPasswordTv;
    }

    public String getFirstNamee() {
        return firstNameEt.getText().toString().trim();
    }

    public String getPassword() {
        return passwordEt.getText().toString().trim();
    }

    public String getEmail() {
        return emailEt.getText().toString().trim();
    }

}
