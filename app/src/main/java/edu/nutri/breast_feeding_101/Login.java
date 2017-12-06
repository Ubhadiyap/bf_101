package edu.nutri.breast_feeding_101;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.facebook.AccessToken;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.FacebookSdk;
//import com.facebook.appevents.AppEventsLogger;
//import com.facebook.login.LoginResult;
//import com.facebook.login.widget.LoginButton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.nutri.breast_feeding_101.R;


public class  Login extends Activity{

    UserDetails user_details = new UserDetails();

    //	MenuItem closeOption;
//    String dbase_email = "";
//    String dbse_password;
//    String dbase_username;

    String username, email;
    String password;
    private ProgressBar progressBar;
    EditText email_ed;
    EditText password_ed;

    Database database;

    boolean connecivity;

    private SQLiteDatabase newDB;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuthListener;
//    CallbackManager mCallbackManager;

    String user_id;

    @Override

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(this);
//        check_session();
        connecivity = false;

        setContentView(R.layout.login);

        Firebase.setAndroidContext(this);

        auth = FirebaseAuth.getInstance();

//		progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        database = new Database(this);


        email_ed = (EditText)findViewById(R.id.email_ed);
//        email_ed.setText("kolaak47@yahoo.com");
        password_ed = (EditText)findViewById(R.id.password_ed);
//        password_ed.setText("kolaak47");

        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Login.this, Contact_us2.class);
                startActivity(it);

//                Toast.makeText(Login.this, user_details.get_username()+ " ////////////////// "+ user_details.get_user_id() + " "+ user_details.get_email(), Toast.LENGTH_SHORT).show();
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    get_user_details(auth.getCurrentUser().getUid());

                } else {

                }
            }
        };

        Check_firebase();

    }

    private void get_user_details(String uid) {

        Database database = new Database(this);
        Cursor cursor = database.getWritableDatabase().rawQuery(database.SELECT_ALL, null);

        if (cursor.getCount() != 0 ) {
            if(cursor.moveToFirst()){
                do {
                    if(uid.equals(cursor.getString(cursor.getColumnIndex("user_id")))){

                        String email = cursor.getString(cursor.getColumnIndex("email"));
                        String user_id = cursor.getString(cursor.getColumnIndex("user_id"));
                        String username = cursor.getString(cursor.getColumnIndex("username"));


                        UserDetails user_details = new UserDetails(username, email, user_id);

//                        user_details.set_email(email);
//                        user_details.set_user_id(user_id);
//                        user_details.set_username(username);
//                        username = cursor.getString(cursor.getColumnIndex("username"));
//                        email = cursor.getString(cursor.getColumnIndex("email"));
//                        user_details.set_user_id( );

//                        Toast.makeText(Login.this, username+ " ------------- "+ user_id + " "+ email, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(Login.this, user_details.get_username()+ " ////////////////// "+ user_details.get_user_id() + " "+ user_details.get_email(), Toast.LENGTH_SHORT).show();

                        Intent it = new Intent(Login.this, Slider.class);
                        startActivity(it);
                        finish();
                    }

                }while (cursor.moveToNext());
            }}



    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            auth.removeAuthStateListener(mAuthListener);
        }
    }

    public void login(View v) {

        email = email_ed.getText().toString();
        password = password_ed.getText().toString();

        if (email.equals("")) {
            email_ed.setError("Enter your Email");
        } else if (!email.contains("@")) {
            email_ed.setError("Invalid email");
        } else if (password.equals("") || password.length() < 6) {
            password_ed.setError(getString(R.string.minimum_password));
        } else {

            if (connecivity == false) {
                Toast.makeText(this, "No Connection Found", Toast.LENGTH_SHORT).show();
            } else {

                final ProgressDialog alertDialog = new ProgressDialog(this);
                alertDialog.setMessage("Please wait.....");
                alertDialog.show();

                auth.signInWithEmailAndPassword(email_ed.getText().toString().trim(), password_ed.getText().toString())
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                alertDialog.dismiss();

                                if (!task.isSuccessful()) {
                                    show_snackbar("Check your email and password or sign up");
                                    check_hint();
                                } else {
                                    user_id = auth.getCurrentUser().getUid();

                                    Database Database = new Database(Login.this);
                                    SQLiteDatabase newDB = Database.getWritableDatabase();
                                    Cursor c = newDB.rawQuery(Database.SELECT_ALL, null);

                                    int x = 0;
                                    if (c.getCount() != 0) {

                                        if (c.moveToFirst()) {
                                            do {
                                                if (c.getString(c.getColumnIndex("user_id")).equals(user_id)) {
                                                    x++;
                                                }
                                            } while (c.moveToNext());
                                        }
                                    }
                                    if (x == 0) {

                                        get_username();

                                        Database DBase = new Database(getApplicationContext());

                                        ContentValues values = new ContentValues();



                                        values.put("username", username);
                                        values.put("email", email_ed.getText().toString().trim());
                                        values.put("user_id", user_id);

//                                        UserDetails user_details = new UserDetails(username, email, user_id);

                                        DBase.getWritableDatabase().insert(Database.login_database, null, values);
                                    }
                                    user_details.set_user_id(auth.getCurrentUser().getUid());

                                    Intent it = new Intent(Login.this, Slider.class);
                                    startActivity(it);
                                    finish();
                                }
                            }
                        });
            }

        }
    }

    private void get_username() {

        final ProgressDialog alertDialog = new ProgressDialog(this);
        alertDialog.setMessage("Please wait.....");
        alertDialog.show();



    }

    private void check_hint() {

        String email = email_ed.getText().toString().trim();

        String query = "SELECT * FROM login_database WHERE email='" + email +"'";

        Database DBase = new Database(this);
        Cursor  cursor = DBase.getWritableDatabase().rawQuery(query,null);

        if (cursor != null) {
            if (cursor.moveToFirst()){
                String hint ="Passoword hint: "+ cursor.getString(cursor.getColumnIndex("hint"));

                show_snackbar(hint);
            }
        }

    }


    public void signup(View v){
        startActivity(new Intent(this, Sign_up.class));
        finish();
    }

    public void reset_password(View v){
        startActivity(new Intent(this, Reset_password.class));
        finish();
    }

    private void Check_firebase() {
        Firebase connectedRef = new Firebase(UserDetails.database_url + ".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    connecivity = true;
//                    Toast.makeText(Login.this, "connected", Toast.LENGTH_SHORT).show();
                } else {
                    connecivity = false;
//                    Toast.makeText(Login.this, "not connected", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(FirebaseError error) {
                System.err.println("Listener was cancelled");
            }
        });
    }

    public void show_snackbar(String text){

        RelativeLayout RelativeLayout1 = (RelativeLayout)findViewById(R.id.RelativeLayout1);

        Snackbar snackbar = Snackbar.make(RelativeLayout1, text, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.parseColor("#d9ffaa"));

        snackbar.show();
    }

    @Override
    public void onBackPressed(){

    }

}