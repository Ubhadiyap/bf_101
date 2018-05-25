package edu.nutri.breast_feeding_101;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Course_quiz extends Activity implements View.OnClickListener, Animation.AnimationListener {

    int attempt;
    int success;
    int failed;
    int total_score;

    String new_attempt;
    String new_success;
    String new_failed;
    String new_total_score;
    String score_string;
    Random ran;

    CountDownTimer cdt;

    Animation shake;
    Animation blink;

    String username, email;

    int question_no;
    int random;
    int score;

    TextView question, title_text, textView3;

    Button Button1, Button2, Button3, Button4;

    RelativeLayout qlay, rel2, rel4, rel3;

    String user_id, course, database;
    String average_score;
    String pre_ass_score;
    List<Integer> rand_int;

    TextView score_text, time_text, textView33;

    String[] questions;
    String[] option_1;
    String[] option_2;
    String[] option_3;
    String[] option_4;
    String[] answer;

    Course_stats_db Course_stats_db;
    Course_score_db Course_score_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        Bundle b = getIntent().getExtras();
        user_id = b.getString("user_id");
        pre_ass_score = b.getString("score_string");
        course = b.getString("course");

        Course_stats_db = new Course_stats_db(this.getApplicationContext());
        Course_score_db = new Course_score_db(this.getApplicationContext());

        get_username_email();

        textView3 = (TextView) findViewById(R.id.textView3);
        textView3.setVisibility(View.GONE);

        textView33 = (TextView) findViewById(R.id.textView33);
        rel3 = (RelativeLayout) findViewById(R.id.rel3);
        rel2 = (RelativeLayout) findViewById(R.id.rel2);
        rel4 = (RelativeLayout) findViewById(R.id.rel4);

        textView33.setVisibility(View.INVISIBLE);
        rel2.setVisibility(View.INVISIBLE);
        rel4.setVisibility(View.INVISIBLE);

        title_text = (TextView) findViewById(R.id.title_text);
        title_text.setText("Quiz");
        score_text = (TextView) findViewById(R.id.main_title);
        time_text = (TextView) findViewById(R.id.time_text);
        time_text.setTextSize(20);
        qlay = (RelativeLayout) findViewById(R.id.qlay);

        questions = getResources().getStringArray(R.array.Questions);
        option_1 = getResources().getStringArray(R.array.option_1);
        option_2 = getResources().getStringArray(R.array.option_2);
        option_3 = getResources().getStringArray(R.array.option_3);
        option_4 = getResources().getStringArray(R.array.option_4);
        answer = getResources().getStringArray(R.array.answers);

        blink = AnimationUtils.loadAnimation(this, R.anim.blink);
        shake = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        shake.setAnimationListener(this);

        rand_int = new ArrayList<Integer>();

        question_no = 0;
        score = 0;

        Button1 = (Button) findViewById(R.id.Button1);
        Button2 = (Button) findViewById(R.id.Button2);
        Button3 = (Button) findViewById(R.id.Button3);
        Button4 = (Button) findViewById(R.id.Button4);

        question = (TextView) findViewById(R.id.textView2);

        Button1.setOnClickListener(this);
        Button2.setOnClickListener(this);
        Button3.setOnClickListener(this);
        Button4.setOnClickListener(this);

        cdt = new CountDownTimer(26000, 1000) {

            public void onTick(long millisUntilFinished) {
                if ((millisUntilFinished / 1000) <= 6) {
                    time_text.setTextSize(20);
                    time_text.startAnimation(blink);
                }
                time_text.setText("00:" + ((millisUntilFinished / 1000) - 1));

                if ((millisUntilFinished / 1000) == 1) {
                    qlay.startAnimation(shake);
                    time_text.setTextSize(20);
                }
            }

            public void onFinish() {
            }
        };
        show_question();

        switch (course) {
            case "1":
                database = "course1_database";
                break;
            case "2":
                database = "course2_database";
                break;
            case "3":
                database = "course3_database";
                break;
            case "4":
                database = "course4_database";
                break;
            case "5":
                database = "course5_database";
                break;
            case "6":
                database = "course6_database";
                break;
        }
    }

    private void get_username_email() {

        Database database = new Database(this);
        Cursor c = database.getWritableDatabase().rawQuery(database.SELECT_ALL, null);

        if (c.getCount() != 0) {
            if (c.moveToFirst()) {
                do {
                    if (c.getString(c.getColumnIndex("user_id")).equals(user_id)) {
                        username = c.getString(c.getColumnIndex("username"));
                        email = c.getString(c.getColumnIndex("email"));

                    }
                } while (c.moveToNext());
            }
        }
    }

    @Override
    public void onClick(View v) {

        cdt.cancel();

        Drawable drawableNormal = v.getBackground();

        Drawable drawablePressed = v.getBackground().getConstantState().newDrawable();
        drawablePressed.mutate();
        drawablePressed.setColorFilter(Color.parseColor("#ff0000"), PorterDuff.Mode.SRC_ATOP);

        StateListDrawable listDrawable = new StateListDrawable();
        listDrawable.addState(new int[]{android.R.attr.state_pressed}, drawablePressed);
        listDrawable.addState(new int[]{}, drawableNormal);
        v.setBackground(listDrawable);

        int id = v.getId();
        String selected_answer = null;

        switch (id) {
            case R.id.Button1:
                selected_answer = Button1.getText().toString();
                break;
            case R.id.Button2:
                selected_answer = Button2.getText().toString();
                break;
            case R.id.Button3:
                selected_answer = Button3.getText().toString();
                break;
            case R.id.Button4:
                selected_answer = Button4.getText().toString();
                break;
        }

        if (selected_answer.equals(answer[random])) {
            score++;
        } else {
        }

        qlay.startAnimation(shake);
    }

    private void show_question() {
        random();
        change_question();
    }

    private void random() {
        ran = new Random();

        switch (course) {
            case "1":
                random = ran.nextInt(9);
                break;
            case "2":
                random = ran.nextInt((19 + 1) - 10) + 10;
                break;
            case "3":
                random = ran.nextInt((27 + 1) - 20) + 20;
                break;
            case "4":
                random = ran.nextInt((37 + 1) - 28) + 28;
                break;
            case "5":
                random = ran.nextInt((47 + 1) - 38) + 38;
                break;
            case "6":
                random = ran.nextInt((57 + 1) - 48) + 48;
                break;
        }

        if (rand_int.size() == 57) {
            rand_int.clear();
        }
        if (rand_int.contains(random)) {
            random();
        } else {
            if (question_no == 5) {

                add_to_Course_stats_database();
                add_to_Course_score_database();

                Intent i = new Intent(this, Reset_password.Score_page.class);

                i.putExtra("user_id", user_id);
                i.putExtra("score", score * 20);
                startActivity(i);

                cdt.cancel();
                qlay.clearAnimation();
                time_text.clearAnimation();

            } else {

                cdt.start();
                time_text.clearAnimation();
                question_no++;

                score_string = String.valueOf(score);
                change_question();

                rand_int.add(random);
            }
        }
    }

    private void change_question() {

        question.setText(question_no + ") " + questions[random]);
        Button1.setText(option_1[random]);
        Button2.setText(option_2[random]);
        Button3.setText(option_3[random]);
        Button4.setText(option_4[random]);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onAnimationStart(Animation animation) {
        random();
    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }


    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    private void add_to_Course_stats_database() {

        SQLiteDatabase newDB;

        newDB = Course_stats_db.getWritableDatabase();
        Cursor c = newDB.rawQuery(Course_stats_db.select_all(), null);

        if (c.getCount() != 0) {
            if (c.moveToFirst()) {
                int x = 0;
                do {
                    if (user_id.equals(c.getString(c.getColumnIndex("user_id"))) && course.equals(c.getString(c.getColumnIndex("course_no")))) {

                        attempt = Integer.parseInt(c.getString(c.getColumnIndex("attempts")));
                        success = Integer.parseInt(c.getString(c.getColumnIndex("success")));
                        failed = Integer.parseInt(c.getString(c.getColumnIndex("failed")));
                        total_score = Integer.parseInt(c.getString(c.getColumnIndex("total_score")));

                        x++;
                    }
                } while (c.moveToNext());

                if (x == 0) {
//                    add
                    add_to_Course_stats_database2();
                } else {
//                update
                    add_to_Course_stats_database3();
                }
            }
        } else {
//            add
            add_to_Course_stats_database2();
        }

    }

    private void add_to_Course_stats_database3() {
        new_attempt = String.valueOf(attempt + 1);
        if (score >= 4) {
            new_success = String.valueOf(success + 1);
            new_failed = String.valueOf(failed + 0);
        } else {
            new_success = String.valueOf(success + 0);
            new_failed = String.valueOf(failed + 1);
        }

        new_total_score = String.valueOf((score * 20) + total_score);

        average_score = String.valueOf(((score * 20) + total_score) / (attempt + 1));

        Course_stats_db.update_database(username, user_id, email, course, new_attempt, new_success, new_failed, new_total_score, average_score, "0");


    }

    private void add_to_Course_stats_database2() {
        new_attempt = "1";
        if (score >= 4) {
            new_success = "1";
            new_failed = "0";
        } else {
            new_success = "0";
            new_failed = "1";
        }

        new_total_score = String.valueOf(score * 20);

        Course_stats_db.add_to_database(username, user_id, email, course, new_attempt, new_success, new_failed, new_total_score, new_total_score, "0");
    }

    private void add_to_Course_score_database() {

        String status;
        if (score >= 4) {
            status = "pass";
        } else {
            status = "fail";
        }

        String new_score = String.valueOf((score * 20));

        Course_score_db.add_to_database(username, user_id, email, course, pre_ass_score, new_score, status, "0");
    }
}