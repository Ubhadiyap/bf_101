package edu.nutri.breast_feeding_101;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Akano on 12/12/2016.
 */
public class Quiz_score extends Activity {

    int width;
    String user_id, email, username, score;
    int high_scores[];
    String name[];
    int highest_score;
    TextView textView29, textView30, textView31;
    LinearLayout score_lay;
    ProgressBar progressBar2;
    Button button9;

    int un_uploaded, attempts, no;
    int complete;

    String url;
    Firebase reference;

    Boolean conectivity;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_score);

        conectivity = false;

        no = 11;
        url = UserDetails.database_url + "Leaderboard/";

        Firebase.setAndroidContext(this);
        reference = new Firebase(url);

        button9 = (Button)findViewById(R.id.button9);
        progressBar2 = (ProgressBar)findViewById(R.id.progressBar2);

        attempts = 0;

        Bundle b = getIntent().getExtras();

        get_username_email();

        score = b.getString("score");
        user_id= b.getString("user_id");

        TextView textView22 = (TextView)findViewById(R.id.textView22);
        textView22.setText(score);

        score_lay = (LinearLayout)findViewById(R.id.Score_lay);

        textView29 = (TextView)findViewById(R.id.textView29);
        textView30 = (TextView)findViewById(R.id.textView30);
        textView31 = (TextView)findViewById(R.id.textView31);

        score_lay.removeAllViews();

        get_unuploaded();

//        Highest_score_text = (TextView)findViewById(R.id.textView28);

//        add_score_lay();

//        check_all_scores();

//        get_leaderboard();
//        test();

    }

    private void create_leaderboard_table() {

        no = 11;
        add_header();

        Firebase ref = new Firebase(UserDetails.database_url + "Leaderboard/");

        Query queryRef = ref.orderByChild("Score").limitToLast(10);

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                no--;
                Map map = snapshot.getValue(Map.class);
//                Toast.makeText(getApplicationContext(), map.get("Score").toString()+"", Toast.LENGTH_SHORT).show();

                String user_id = map.get("User_id").toString();
                String name = map.get("Name").toString();
                String score = map.get("Score").toString();

                create_table(user_id, name, score, no);

//                Toast.makeText(Quiz_score.this, user_id + name + score+ "", Toast.LENGTH_SHORT).show();

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
            // ....
        });

    }

    private void get_unuploaded() {

        Firebase connectedRef = new Firebase(UserDetails.database_url + ".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    conectivity = true;

                    un_uploaded = 0;
                    complete = 0;

                    Database db = new Database(getApplicationContext());
                    Cursor c = db.getWritableDatabase().rawQuery(db.all_scores, null);
                    String uploaded;
                    if (c.getCount()!=0){
                        if (c.moveToFirst()){
                            do {
                                uploaded = c.getString(c.getColumnIndex("uploaded"));
                                if (uploaded.equals("0")){
                                    un_uploaded++;
                                }
                            }while (c.moveToNext());
                        }
                    }

//        Toast.makeText(this, un_uploaded+"", Toast.LENGTH_SHORT).show();
                    Query_database();
                } else {
//                    Toast.makeText(getApplicationContext(), "not connected", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(FirebaseError error) {
//                Toast.makeText(getApplicationContext(), "Listener was cancelled", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Query_database() {

        Database db = new Database(this);
        Cursor c = db.getWritableDatabase().rawQuery(db.all_scores, null);

        String username, email, user_id, uploaded, score, column_id;
        if (c.getCount()!=0){
            if (c.moveToFirst()){
                do {
                    username = c.getString(c.getColumnIndex("username"));
                    email = c.getString(c.getColumnIndex("email"));
                    user_id = c.getString(c.getColumnIndex("user_id"));
                    score = c.getString(c.getColumnIndex("score"));
                    uploaded = c.getString(c.getColumnIndex("uploaded"));
                    column_id = c.getString(c.getColumnIndex("_id"));

                    if (uploaded.equals("0")){
                        upload_quiz_score(username, user_id, email, score, column_id);
                    }

                }while (c.moveToNext());
            }
        }
        confirm_database();
    }

    private void confirm_database() {

        un_uploaded = 0;
        complete = 0;

        Database db = new Database(this);
        Cursor c = db.getWritableDatabase().rawQuery(db.all_scores, null);
        String uploaded;
        if (c.getCount()!=0){
            if (c.moveToFirst()){
                do {
                    uploaded = c.getString(c.getColumnIndex("uploaded"));
                    if (uploaded.equals("0")){
                        un_uploaded++;
                    }
                }while (c.moveToNext());
            }
        }

        if (un_uploaded==0){
//            Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show();
            score_lay.removeAllViews();
//            add_score_lay();
            create_leaderboard_table();
        }
        else{
            attempts++;
//            Toast.makeText(this, "no", Toast.LENGTH_SHORT).show();
            if (attempts<1) {
                get_unuploaded();
            }else {
                error();
            }
        }

    }

    private void error() {
        score_lay.removeAllViews();
        score_lay.addView(textView31);
        score_lay.addView(textView30);
        score_lay.addView(textView29);
        score_lay.addView(button9);
    }

    private void upload_quiz_score(String username, String user_id, String email, String score, final String column_id) {

        String hour = String.valueOf(Calendar.getInstance().get(Calendar.HOUR));
        String minute = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1);
        String day = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        String Time = hour+":"+minute+" "+day+"/"+month+"/"+year;

        HashMap<String,Object> map = new HashMap<String,Object>();

        map.put("Name", username);
        map.put("User_id", user_id);
        map.put("Email", email);
        map.put("Score", Integer.parseInt(score));
        map.put("Time", Time);

        reference.push().setValue(map, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {

                } else {
//                    Toast.makeText(Quiz_score.this, "yesmm", Toast.LENGTH_SHORT).show();
                    Database db = new Database(getApplicationContext());
                    db.update_uploaded(column_id);
                }
            }
        });



    }
    private void get_username_email() {

        username = UserDetails.username;
        email = UserDetails.email;
    }

    public void create_table(String user_id, String name2, String score2, int no){


        LinearLayout sl = new LinearLayout(this);
        sl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        sl.setBackgroundResource(R.drawable.rounded_edittext);
        sl.setOrientation(LinearLayout.HORIZONTAL);

        final LinearLayout sl2 = new LinearLayout(this);

        sl2.setBackgroundResource(R.drawable.rounded_edittext);
        sl2.setOrientation(LinearLayout.HORIZONTAL);

        final LinearLayout s3 = new LinearLayout(this);

        s3.setBackgroundResource(R.drawable.rounded_edittext);
        s3.setOrientation(LinearLayout.HORIZONTAL);

        final LinearLayout s4 = new LinearLayout(this);

        s4.setBackgroundResource(R.drawable.rounded_edittext);
        s4.setOrientation(LinearLayout.HORIZONTAL);

        final LinearLayout relativeLayout1 = (LinearLayout) findViewById(R.id.main_lay);
        ViewTreeObserver observer = relativeLayout1.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                width = relativeLayout1.getWidth();
                relativeLayout1.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                sl2.setLayoutParams(new LinearLayout.LayoutParams(width / 5, LinearLayout.LayoutParams.WRAP_CONTENT));

                s3.setLayoutParams(new LinearLayout.LayoutParams((width / 5) * 2, LinearLayout.LayoutParams.WRAP_CONTENT));

                s4.setLayoutParams(new LinearLayout.LayoutParams((width / 5) * 2, LinearLayout.LayoutParams.WRAP_CONTENT));

            }
        });
        TextView tv = new TextView(this);
        tv.setTextSize(18);
        tv.setText(no+"");

        TextView tv2 = new TextView(this);
        tv2.setTextSize(18);
        tv2.setText(score2);

        TextView tv3 = new TextView(this);
        tv3.setTextSize(18);
        if (user_id.equals(UserDetails.user_id)){
            tv3.setText("You");
            tv3.setTextColor(Color.parseColor("#00ff00"));

        }
        else {
            tv3.setText(name2);
        }


        sl2.addView(tv);
        s3.addView(tv2);
        s4.addView(tv3);

        sl.addView(sl2);
        sl.addView(s4);
        sl.addView(s3);

        score_lay.addView(sl, 1);
    }

    private void add_header() {
        LinearLayout slb = new LinearLayout(this);
        slb.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        slb.setBackgroundResource(R.drawable.rounded_edittext);
        slb.setOrientation(LinearLayout.HORIZONTAL);

        final LinearLayout sl2b = new LinearLayout(this);

        sl2b.setBackgroundResource(R.drawable.rounded_edittext);
        sl2b.setOrientation(LinearLayout.HORIZONTAL);

        final LinearLayout s3b = new LinearLayout(this);

        s3b.setBackgroundResource(R.drawable.rounded_edittext);
        s3b.setOrientation(LinearLayout.HORIZONTAL);

        final LinearLayout s4b = new LinearLayout(this);

        s4b.setBackgroundResource(R.drawable.rounded_edittext);
        s4b.setOrientation(LinearLayout.HORIZONTAL);

        final LinearLayout relativeLayout1b = (LinearLayout) findViewById(R.id.main_lay);
        ViewTreeObserver observer2 = relativeLayout1b.getViewTreeObserver();
        observer2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                width = relativeLayout1b.getWidth();
                relativeLayout1b.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                sl2b.setLayoutParams(new LinearLayout.LayoutParams(width / 5, LinearLayout.LayoutParams.WRAP_CONTENT));

                s3b.setLayoutParams(new LinearLayout.LayoutParams((width / 5) * 2, LinearLayout.LayoutParams.WRAP_CONTENT));

                s4b.setLayoutParams(new LinearLayout.LayoutParams((width / 5) * 2, LinearLayout.LayoutParams.WRAP_CONTENT));

            }
        });

        TextView tvb = new TextView(this);
        tvb.setTextSize(23);
        tvb.setTextColor(Color.parseColor("#e35e4c"));
        tvb.setText("Rank");

        TextView tv2b = new TextView(this);
        tv2b.setTextSize(23);
        tv2b.setTextColor(Color.parseColor("#e35e4c"));
        tv2b.setText("Score");

        TextView tv3b = new TextView(this);
        tv3b.setTextSize(23);
        tv3b.setTextColor(Color.parseColor("#e35e4c"));
        tv3b.setText("Name");

        sl2b.addView(tvb);
        s3b.addView(tv2b);
        s4b.addView(tv3b);

        slb.addView(sl2b);
        slb.addView(s4b);
        slb.addView(s3b);

        score_lay.addView(slb);
    }

    public void try_again(View v) {
        Intent it = new Intent(this, Quiz.class);
        it.putExtra("user_id", user_id);
        startActivity(it);
    }
    public void home(View v){
        Intent it = new Intent(this, MainActivity.class);
        it.putExtra("user_id", user_id);
        startActivity(it);
    }
    public void retry(View v){

        if(conectivity==true) {
            get_unuploaded();
        }
    }
    @Override
    public void onBackPressed(){

    }
}