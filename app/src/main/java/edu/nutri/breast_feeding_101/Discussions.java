package edu.nutri.breast_feeding_101;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Akano on 3/8/2017.
 */

public class Discussions extends Activity{


    UserDetails user_details = new UserDetails();

    TextView noUsersText;
    int totalUsers = 0;
    ProgressDialog pd;
    EditText editText;
    LinearLayout main_lay;
    String message, name, time, email;
    Firebase reference1, comment_ref;
    ScrollView scrollView;
    String url, comment_url;

    String discussion_name;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discussion);

        Bundle b = getIntent().getExtras();

        discussion_name = b.getString("discussion_name");

        scrollView = (ScrollView)findViewById(R.id.scrollView);
        Firebase.setAndroidContext(this);

        main_lay = (LinearLayout) findViewById(R.id.main_lay);

        editText = (EditText) findViewById(R.id.editText);
        noUsersText = (TextView) findViewById(R.id.noUsersText);

        email = user_details.get_email();
        message = "this is a sample message used to test community function and chat of bf101";
        name = "adekola";
        time = "12:30 20/2/2017";

        pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();

//        url = "https://nutrition-64795.firebaseio.com/within_6";

        url = UserDetails.database_url + "Discussions/"+discussion_name;
        reference1 = new Firebase(url);

        comment_url = "https://nutrition-64795.firebaseio.com/Discussions/comments";
        comment_ref = new Firebase(comment_url);

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);

                String message = map.get("message").toString();
                String userName = map.get("name").toString();
                String time = map.get("date").toString();
                String comment = map.get("comment").toString();
//                String email = map.get("email").toString();

                set_layout(totalUsers, message, userName, time, comment);

                totalUsers++;
                pd.dismiss();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    private void set_layout(int id, String message, String name, String time, final String comment) {

        TextView tv = new TextView(this);
        tv.setTextSize(18);
        tv.setText(message);

        TextView tv2 = new TextView(this);
        tv2.setText("Asked by: " + name +"  ");
        tv2.setTextSize(13);

        TextView tv3 = new TextView(this);
        tv3.setTextSize(13);
        tv3.setText("  at "+ time);

        ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.comment);

        ImageView iv2 = new ImageView(this);
        iv2.setImageResource(R.drawable.like4);

        ImageView iv3 = new ImageView(this);
        iv3.setImageResource(R.drawable.view);

        LinearLayout ll2 = new LinearLayout(this);
        ll2.setOrientation(LinearLayout.HORIZONTAL);

        ll2.addView(tv2);
        ll2.addView(tv3);

        LinearLayout ll3 = new LinearLayout(this);
        ll3.setOrientation(LinearLayout.HORIZONTAL);

        final TextView tv4 = new TextView(this);
        tv4.setText("0");

        String  comment_url = "https://nutrition-64795.firebaseio.com/Discussions/comments";
        Firebase comment_ref = new Firebase(comment_url);

        comment_ref.child(comment).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.getValue()==null){
                }
                else {
                    pd.dismiss();
                    Map map = snapshot.getValue(Map.class);
                    String comm = map.get("Comment").toString();
                    tv4.setText(comm);

                }
            }
            @Override public void onCancelled(FirebaseError error) {
            }
        });

        TextView bl = new TextView(this);
        bl.setText("   ");

        TextView b2 = new TextView(this);
        b2.setText("   ");

        TextView tv5 = new TextView(this);
        tv5.setText("0");

        TextView tv6 = new TextView(this);
        tv6.setText("0");

        ll3.addView(iv);
        ll3.addView(tv4);

        ll3.addView(bl);

        ll3.addView(iv2);
        ll3.addView(tv5);

        ll3.addView(b2);

        ll3.addView(iv3);
        ll3.addView(tv6);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setId(id);
        ll.setBackgroundResource(R.drawable.rounded_edittext6);
        ll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url_id = discussion_name+"_" + v.getId();

                Intent it = new Intent(getApplicationContext(), Chat.class);
                it.putExtra("url_id", url_id);
                it.putExtra("comment_code", comment);
                startActivity(it);
            }
        });

        TextView blank = new TextView(this);
        blank.setText(" ");

        ll.addView(tv);
        ll.addView(ll2);
        ll.addView(ll3);

        main_lay.addView(ll, 0);
        main_lay.addView(blank, 0);

    }

    public void add(View v){

        if(!editText.equals("")){
            String hour = String.valueOf(Calendar.getInstance().get(Calendar.HOUR));
            String minute = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));

            String second = String.valueOf(Calendar.getInstance().get(Calendar.SECOND));
            String millisec = String.valueOf(Calendar.getInstance().get(Calendar.MILLISECOND));

            String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
            String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1);
            String day = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

            String Time = hour+":"+minute+" "+day+"/"+month+"/"+year;

            String comment_code = day+""+month+""+year+hour+""+minute+""+second+millisec;

            HashMap<String,Object> map = new HashMap<String,Object>();
//            Map<String, String> map = new HashMap<String, String>();

            map.put("message", editText.getText().toString());
            map.put("date", Time);
            //TODO username
            map.put("name", user_details.get_username());
            map.put("email", user_details.get_email());
            map.put("comment", comment_code);
            reference1.push().setValue(map);



            HashMap<String,Object> map2 = new HashMap<String,Object>();

            map2.put("Comment", 0);

            comment_ref.child(comment_code).setValue(map2);


            editText.setText("");
        }

    }

}