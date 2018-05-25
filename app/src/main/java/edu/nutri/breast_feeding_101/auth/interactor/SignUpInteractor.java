package edu.nutri.breast_feeding_101.auth.interactor;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import edu.nutri.breast_feeding_101.Database;
import edu.nutri.breast_feeding_101.MainActivity;
import edu.nutri.breast_feeding_101.UserDetails;
import edu.nutri.breast_feeding_101.auth.fragment.SignUpFragment;
import edu.nutri.breast_feeding_101.auth.presenter.SignUpPresenter;
import edu.nutri.breast_feeding_101.database.models.User;

/**
 * Created by Akano on 1/20/2018.
 */
public class SignUpInteractor {

    SignUpPresenter signUpPresenter;
    Context context;
    OnCompleteListener onCompleteListener;
    String userName, email, password;

    public SignUpInteractor(View view, FragmentActivity loginFragment, OnCompleteListener onCompleteListener) {

        signUpPresenter = new SignUpPresenter(view);
        context = loginFragment;
        this.onCompleteListener = onCompleteListener;

    }

    public void signUp(FirebaseAuth firebaseAuth){
        userName = signUpPresenter.getFirstNamee();
        email = signUpPresenter.getEmail();
        password = signUpPresenter.getPassword();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(onCompleteListener);
    }

    public boolean isValid() {
        return signUpPresenter.isValid();
    }

    public Button getSignUpButton() {
        return signUpPresenter.getSignUpButton();
    }

    public TextView getLoginTv() {
        return  signUpPresenter.getLoginTv();
    }

    public TextView getForgotPasswordTv() {
        return signUpPresenter.getForgotPasswordTv();
    }

    public void saveUsersData(FirebaseUser firebaseUser, DatabaseReference databaseReference, OnSuccessListener listener) {

        String email = firebaseUser.getEmail();
        String userid = firebaseUser.getUid();

        User user = new User();
        user.setUserId(userid);
        user.setEmail(email);
        user.setUserName(userName);
        user.setAdmin(false);

        databaseReference.child("Users").child(userid).setValue(user).addOnSuccessListener(listener);
    }

//    public void add_to_database(FirebaseUser user) {
//
//        String user_id = user.getUid();
//        String email = user.getEmail();
//        String username = signUpPresenter.getFirstNamee();
//
//        Database DBase = new Database(context);
//
//        ContentValues values = new ContentValues();
//
//        values.put("username", username);
//        values.put("email", email);
//        values.put("user_id", user_id);
//
//        DBase.getWritableDatabase().insert(DBase.login_database, null, values);
//
//        Intent it = new Intent(context, MainActivity.class);
//        context.startActivity(it);
//
//    }
}
