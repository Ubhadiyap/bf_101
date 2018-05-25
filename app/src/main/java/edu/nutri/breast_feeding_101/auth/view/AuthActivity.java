package edu.nutri.breast_feeding_101.auth.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.nutri.breast_feeding_101.R;
import edu.nutri.breast_feeding_101.auth.fragment.LoginFragment;
import edu.nutri.breast_feeding_101.common.util.LauncherUtil;
import edu.nutri.breast_feeding_101.common.view.BaseActivity;
import edu.nutri.breast_feeding_101.database.models.User;

public class AuthActivity extends BaseActivity {

    private CallbackManager mCallbackManager;

//    String name, email, userId, imageUrl;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        setDefaultFragment();
//
        initSocialAuth();
    }

    private void initSocialAuth() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

//        SignInButton signInButton = findViewById(R.id.sign_in_button);
//        signInButton.setSize(SignInButton.SIZE_STANDARD);
//        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                google_signIn();
//            }
//        });
//
        mCallbackManager = CallbackManager.Factory.create();
//        LoginButton loginButton = findViewById(R.id.login_button);
//        loginButton.setReadPermissions("email", "public_profile");
//        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                handleFacebookAccessToken(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//            }
//        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 914) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            firebaseAuthWithGoogle(account);
        } catch (ApiException e) {
            Toast.makeText(this, "signInResult:failed code=" + e.getStatusCode(), Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                        User user = new User();
                        user.setUserName(firebaseUser.getDisplayName());
                        user.setEmail(firebaseUser.getEmail());
                        user.setUserId(firebaseUser.getUid());
                        user.setProfilePhoto(firebaseUser.getPhotoUrl().toString());

                        databaseReference.child("user").child(firebaseUser.getUid()).setValue(user);

                        if (firebaseUser.getPhotoUrl()!=null){
                        }else {
                        }

                    } else {
                        Log.w("google", "signInWithCredential:failure", task.getException());
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                        User user = new User();
                        user.setUserName(firebaseUser.getDisplayName());
                        user.setEmail(firebaseUser.getEmail());
                        user.setUserId(firebaseUser.getUid());
                        user.setProfilePhoto(firebaseUser.getPhotoUrl().toString());

                        databaseReference.child("user").child(firebaseUser.getUid()).setValue(user);


                    } else {
                        Log.w("FacebookLogin", "signInWithCredential:failure", task.getException());
                    }
                });
    }

    private void setDefaultFragment() {

        LauncherUtil.launchFragment(getSupportFragmentManager(), R.id.frame, new LoginFragment(), false);

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .add(R.id.frame, new LoginFragment())
//                .commit();
    }

    public void onGoogleClick(View view) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 914);
    }

    public void onFacebookClick(View view) {
    }
}