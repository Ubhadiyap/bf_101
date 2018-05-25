package edu.nutri.breast_feeding_101;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Akano on 10/26/2016.
 */

public class Reset_password extends Activity {

    private FirebaseAuth auth;
    String username;
    EditText username_ed;
    boolean connecivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        connecivity = false;

        Firebase.setAndroidContext(this);

        auth = FirebaseAuth.getInstance();

        username_ed = (EditText)findViewById(R.id.username_ed);

        username = username_ed.getText().toString();

        Button button1 = (Button)findViewById(R.id.button1);


        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Reset_password.this, Contact_us2.class);
                startActivity(it);
            }
        });

    button1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String email = username_ed.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                return;
            } else {

                if (connecivity == false) {
//                    Toast.makeText(getApplicationContext(), "No Connection Found", Toast.LENGTH_SHORT).show();
                }
                else {
                    ProgressBar prog = new ProgressBar(Reset_password.this);

                    final AlertDialog.Builder alert = new AlertDialog.Builder(Reset_password.this);
                    alert.setMessage("Please wait...");
                    alert.setView(prog);
                    prog.setVisibility(View.VISIBLE);

                    final AlertDialog alertDialog = alert.show();

                    auth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Reset_password.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Reset_password.this, Login.class));
                                    } else {
                                        Toast.makeText(Reset_password.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                    }

                                    alertDialog.dismiss();
                                }
                            });
                }
            }
        }
    });
        Check_firebase();
}
//
//    public void signup(View v){
//        startActivity(new Intent(this, Sign_up.class));
//        finish();
//    }

    public void login(View v){
        startActivity(new Intent(this, Sign_up.class));
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
//                    Toast.makeText(Reset_password.this, "connected", Toast.LENGTH_SHORT).show();
                } else {
                    connecivity = false;
//                    Toast.makeText(Reset_password.this, "not connected", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(FirebaseError error) {
//                System.err.println("Listener was cancelled");
            }
        });
    }


}
