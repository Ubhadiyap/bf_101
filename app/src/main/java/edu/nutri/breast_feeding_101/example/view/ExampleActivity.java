package edu.nutri.breast_feeding_101.example.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import edu.nutri.breast_feeding_101.R;
import edu.nutri.breast_feeding_101.UserDetails;
import edu.nutri.breast_feeding_101.common.view.BaseActivity;
import edu.nutri.breast_feeding_101.database.models.User;

public class ExampleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
    }

    public void add(View view) {

        Firebase reference1 = new Firebase(UserDetails.database_url + "UserTest/").child("user22");
        reference1.keepSynced(true);

        Map<String, String> map = new HashMap<String, String>();

        String name = "Test Name";
        String email = "TestEmail@mail.com";
        String userid = "TestID";

        map.put("Name", "dsc");
        map.put("Email", "dscsc");
        map.put("User_id", "csdc");
        map.put("Gender", "csdcr");

        User user = new User();
        user.setUserName(name);
        user.setEmail(email);
        user.setUserId(userid);

//        firebaseAuth.createUserWithEmailAndPassword("TestEmail@mail.com", "password");

//        reference1.updateChildren(user.toMap());
        reference1.setValue(user);
//        reference1.child("userName").setValue("nnnnnnnnn");


//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("UserTest2");
//        myRef.setValue(user);

        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    User user1 = dataSnapshot.getValue(User.class);
                    Toast.makeText(ExampleActivity.this, firebaseUser.getEmail() + "", Toast.LENGTH_SHORT).show();
                    ((TextView)findViewById(R.id.text)).setText(user1.getEmail()+" "+user1.getUserName()+" "+user1.getUserId());
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

//        reference1.child("user22").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//
//                if (snapshot.getValue()==null){
//
//                }
//                else {
//                    User user1 = snapshot.getValue(User.class);
//                    Toast.makeText(ExampleActivity.this, user1.getEmail()+"", Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override public void onCancelled(FirebaseError error) {
//            }
//        });

    }
}
