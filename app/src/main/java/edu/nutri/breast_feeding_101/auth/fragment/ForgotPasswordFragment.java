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

import edu.nutri.breast_feeding_101.R;
import edu.nutri.breast_feeding_101.auth.interactor.ForgotPasswordInteractor;
import edu.nutri.breast_feeding_101.common.util.LauncherUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPasswordFragment extends Fragment implements OnCompleteListener{


    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    ForgotPasswordInteractor forgotPasswordInteractor;
    Button resetPasswordBtn;
    TextView loginTv, signUpTv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        forgotPasswordInteractor = new ForgotPasswordInteractor(getActivity().findViewById(android.R.id.content), this);

        initViews();

    }

    private void initViews() {
        resetPasswordBtn = forgotPasswordInteractor.getResetPasswordBtn();
        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    resetPassword();
            }
        });

        loginTv = forgotPasswordInteractor.getLoginTv();
        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LauncherUtil.launchFragment(getFragmentManager(), R.id.frame, new LoginFragment(), true);
            }
        });

        signUpTv = forgotPasswordInteractor.getSignUpTv();
        signUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LauncherUtil.launchFragment(getFragmentManager(), R.id.frame, new SignUpFragment(), true);
            }
        });
    }

    private void resetPassword() {
        if (forgotPasswordInteractor.isValid()) forgotPasswordInteractor.resetPassword();
    }

    @Override
    public void onComplete(@NonNull Task task) {

        if (task.isComplete()){
            Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
        }
        else {

        }
    }
}
