package edu.nutri.breast_feeding_101.auth.fragment;


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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.nutri.breast_feeding_101.MainActivity;
import edu.nutri.breast_feeding_101.R;
import edu.nutri.breast_feeding_101.auth.interactor.LoginInteractor;
import edu.nutri.breast_feeding_101.common.util.LauncherUtil;
import edu.nutri.breast_feeding_101.common.util.NotificationUtil;
import edu.nutri.breast_feeding_101.common.view.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment implements OnCompleteListener{

    LoginInteractor loginInteractor;
    Button loginBtn;
    TextView signUpTv, forgotPasswordTv;
    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = getFragmentView(inflater, R.layout.fragment_login, container);
//        inflater.inflate(R.layout.fragment_login, container, false);

        return view;
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginInteractor = new LoginInteractor(view, getActivity(), this );
        initViews();

    }

    private void initViews() {
        signUpTv = loginInteractor.getSignUpTv();
        signUpTv.setOnClickListener(v ->
                LauncherUtil.launchFragment(getFragmentManager(), R.id.frame, new SignUpFragment(), true));

        forgotPasswordTv = loginInteractor.getForgotPasswordTv();
        forgotPasswordTv.setOnClickListener(v ->
                LauncherUtil.launchFragment(getFragmentManager(), R.id.frame, new ForgotPasswordFragment(), true));

        loginBtn = loginInteractor.getLoginButton();
        loginBtn.setOnClickListener(v -> login());
    }

    public void login(){

        if (loginInteractor.isValid()) {
             showProgress("Loging in...");
             loginInteractor.login(firebaseAuth);
        }
    }


    @Override
    public void onComplete(@NonNull Task task) {
        hideProgress();
        if (task.isSuccessful()) {
            firebaseUser = firebaseAuth.getCurrentUser();
            LauncherUtil.launchClass(getActivity(), MainActivity.class);

        }
        else {
            NotificationUtil.showFailureDialog(getFragmentManager(), "ERROR", "Login Failed");
        }
    }
}
