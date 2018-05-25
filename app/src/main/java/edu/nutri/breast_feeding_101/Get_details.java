package edu.nutri.breast_feeding_101;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * Created by Akano on 1/3/2017.
 */

public class Get_details extends Fragment {

    public Get_details(){ }

    private static String file_url;
    ProgressDialog pd;

    String user_id, email,  username, url, url2;
    Firebase reference1, Course_Stat_ref;

    TextView tv1 , tv2;
    ImageView iv;
    Button bt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        url = UserDetails.database_url + "Users/";

        reference1 = new Firebase(url);

        user_id = UserDetails.user_id;
        email = UserDetails.email;
        username = UserDetails.username;

        View rootView = inflater.inflate(R.layout.get_details, container, false);

        tv1 = (TextView) rootView.findViewById(R.id.textView10);
        tv2 = (TextView) rootView.findViewById(R.id.textView24);
        iv = (ImageView)rootView.findViewById(R.id.imageView4);
        bt = (Button)rootView.findViewById(R.id.button12);

        tv1.setVisibility(View.INVISIBLE);
        tv2.setVisibility(View.INVISIBLE);
        iv.setVisibility(View.INVISIBLE);
        bt.setVisibility(View.INVISIBLE);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retry();
            }
        });

        pd = new ProgressDialog(getActivity());

        tv1.setVisibility(View.INVISIBLE);
        tv2.setVisibility(View.INVISIBLE);
        iv.setVisibility(View.INVISIBLE);
        bt.setVisibility(View.INVISIBLE);

        pd.setMessage("Loading details ....");
        pd.show();

        check_database();
        return rootView;
    }

    public void retry(){

        check_database();
    }

    private void check_database() {

        reference1.child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.getValue()==null){
                    pd.dismiss();

                    Intent it = new Intent(getActivity(), Update_details.class);

                    startActivity(it);
                }
                else {
                    pd.dismiss();
                    Map map = snapshot.getValue(Map.class);

                    String user_id = map.get("User_id").toString();
                    String email = map.get("Email").toString();
                    String username = map.get("Name").toString();
                    String age = map.get("Age").toString();
                    String gender = map.get("Gender").toString();
                    String marital_status = map.get("Marital_Status").toString();
                    String level_of_education = map.get("Level_of_Education").toString();
                    String pregnancy_Status = map.get("Pregnancy_Status").toString();
                    String religion = map.get("Religion").toString();
                    String no_of_children = map.get("No_of_Children").toString();


    Add_to_database(user_id, email, username, age, gender, marital_status, level_of_education, pregnancy_Status, religion, no_of_children);

                }
            }
            @Override public void onCancelled(FirebaseError error) {
                pd.dismiss();
                Show_error();
            }
        });
    }

    private void Add_to_database(String user_id, String email, String username, String age, String gender, String marital_status, String level_of_education, String pregnancy_Status, String religion, String no_of_children) {
        Details_database DBase = new Details_database(getActivity());

        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("user_id", user_id);
        values.put("age", age);
        values.put("marital_status", marital_status);
        values.put("children", no_of_children);
        values.put("education", level_of_education);
        values.put("religion", religion);
        values.put("pregnant", pregnancy_Status);
        values.put("gender", gender);
        values.put("email", email);
        values.put("unique_id", user_id);

        DBase.getWritableDatabase()
                .insert(Details_database.login_database_2, age, values);

        Database database = new Database(getActivity());
        SQLiteDatabase db = database.getWritableDatabase();

        ContentValues values2 = new ContentValues();
        values2.put("username", username);
        values2.put("email", email);
        values2.put("user_id", user_id);

        db.update("login_database", values2, "user_id" + " = ?",
                new String[] { user_id });

        pd.dismiss();

        download_image();
    }

    private void download_image() {
        startActivity(new Intent(getActivity(), Download_image.class));
    }

    public void Show_error(){
        tv1.setVisibility(View.VISIBLE);
        tv2.setVisibility(View.VISIBLE);
        iv.setVisibility(View.VISIBLE);
        bt.setVisibility(View.VISIBLE);

    }
}

