package edu.nutri.breast_feeding_101;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Akano on 3/29/2017.
 */

public class Admin extends Activity {

    Firebase reference1;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        url = UserDetails.database_url + "Admins/";

        Firebase.setAndroidContext(this);
        reference1 = new Firebase(url);

    }

    public void add_admin(View v){

        final EditText ed = new EditText(this);
        ed.setHint("Email");

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Add an Admin");
        alert.setView(ed);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String email = ed.getText().toString().toLowerCase();

                add_admin_email(email);

            }
        });
        final AlertDialog alertDialog = alert.show();

    }

    private void add_admin_email(String email) {

        Map<String, String> map = new HashMap<String, String>();

        map.put("Email", email);
        reference1.push().setValue(map);

    }

    public void get_details(View v){

        RadioGroup group = new RadioGroup(this);
        group.setOrientation(RadioGroup.VERTICAL);

        final RadioButton r1 = new RadioButton(this);
        r1.setText("Get all users");

        final RadioButton r2 = new RadioButton(this);
        r2.setText("Get all Course Scores");

        final RadioButton r3 = new RadioButton(this);
        r3.setText("Get all Course Statistics");

        group.addView(r1);
        group.addView(r2);
        group.addView(r3);

        AlertDialog.Builder alert2 = new AlertDialog.Builder(this);
        alert2.setTitle("Add an Admin");
        alert2.setView(group);

        alert2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent it = new Intent(getApplicationContext(), Export_details.class);

                if (r1.isChecked()==true){
//                    Toast.makeText(Admin.this, "1", Toast.LENGTH_SHORT).show();
                    it.putExtra("query", "Users");
                }
                if (r2.isChecked()==true){
//                    Toast.makeText(Admin.this, "2", Toast.LENGTH_SHORT).show();
                    it.putExtra("query", "Course_Scores");
                }
                if (r3.isChecked()==true){
//                    Toast.makeText(Admin.this, "3", Toast.LENGTH_SHORT).show();
                    it.putExtra("query", "Course_Stats");
                }
                startActivity(it);
            }
        });

        alert2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        final AlertDialog alertDialog2 = alert2.show();
        }



    }


