package edu.nutri.breast_feeding_101;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by Akano on 12/8/2016.
 */

public class Quiz extends Activity implements View.OnClickListener{

    UserDetails user_details = new UserDetails();

    TextView textView33;
    RelativeLayout rel3;
    ImageView live_1, live_2,live_3;

    int lives;
    String score_string;
    Random ran;

    CountDownTimer cdt;

    Animation shake, shake2, shake3;
    Animation blink;
    Animation fade_out;

    int done_rand[] = new int[10];

    String username, email;

    int question_no;
    int random;
    int score;

    TextView question, title_text;

    Button Button1, Button2, Button3, Button4;

    RelativeLayout qlay;

    String user_id;
    String pre_ass_score;
    List<Integer> rand_int;

    TextView score_text, time_text;

    String [] questions;
    String [] option_1;
    String [] option_2;
    String [] option_3;
    String [] option_4;
    String [] answer;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        user_id = user_details.get_user_id();
        username = user_details.get_username();
        email = user_details.get_email();

        textView33 = (TextView)findViewById(R.id.textView33);
        rel3 = (RelativeLayout)findViewById(R.id.rel3);

        textView33.setVisibility(View.INVISIBLE);

        title_text = (TextView)findViewById(R.id.title_text);
        title_text.setText("Quiz");
        score_text = (TextView)findViewById(R.id.main_title);
        time_text = (TextView)findViewById(R.id.time_text);

        qlay = (RelativeLayout)findViewById(R.id.qlay);
        lives = 3;

        live_1 = (ImageView)findViewById(R.id.live_1);
        live_1.setImageResource(R.drawable.lives3);

        live_2 = (ImageView)findViewById(R.id.live_2);
        live_2.setImageResource(R.drawable.lives3);

        live_3 = (ImageView)findViewById(R.id.live_3);
        live_3.setImageResource(R.drawable.lives3);

        questions = getResources().getStringArray(R.array.Questions);
        option_1 = getResources().getStringArray(R.array.option_1);
        option_2 = getResources().getStringArray(R.array.option_2);
        option_3 = getResources().getStringArray(R.array.option_3);
        option_4 = getResources().getStringArray(R.array.option_4);
        answer = getResources().getStringArray(R.array.answers);


        blink = AnimationUtils.loadAnimation(this, R.anim.blink);
        shake = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        shake2 = AnimationUtils.loadAnimation(this, R.anim.shake);
        shake3 = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);


        fade_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                rel3.setVisibility(View.VISIBLE);
                score_text.setText(""+score*20);

//                time_text.clearAnimation();
                cdt.cancel();
                cdt.start();

                ran = new Random();

                random = ran.nextInt(57);


                if(rand_int.size()==57) {
                    rand_int.clear();
                }
                if(rand_int.contains(random)){
                    random();
                }
                else{
                    question_no++;

                    score_string = String.valueOf(score);
                    change_question();

                    rand_int.add(random);
                }


            }

            @Override
            public void onAnimationRepeat(Animation animation) {
//                Toast.makeText(Quiz.this, "repeat "+random , Toast.LENGTH_SHORT).show();
            }
        });

        rand_int = new ArrayList<Integer>();


        question_no = 0;
        score=0;

        Button1 = (Button) findViewById(R.id.Button1);
        Button2 = (Button) findViewById(R.id.Button2);
        Button3 = (Button) findViewById(R.id.Button3);
        Button4 = (Button) findViewById(R.id.Button4);


        question = (TextView) findViewById(R.id.textView2);

        Button1.setOnClickListener(this);
        Button2.setOnClickListener(this);
        Button3.setOnClickListener(this);
        Button4.setOnClickListener(this);

        cdt = new CountDownTimer(15000, 1000) {


            public void onTick(long millisUntilFinished) {

                Button1.setClickable(true);
                Button2.setClickable(true);
                Button3.setClickable(true);
                Button4.setClickable(true);

                time_text.setTextColor(Color.parseColor("#9999cc"));
                time_text.setTextSize(20);
                time_text.setText("00:" + millisUntilFinished / 1000);
                time_text.startAnimation(shake);
                if ((millisUntilFinished / 1000)<=5){

                    time_text.setTextColor(Color.parseColor("#ff0000"));
                    time_text.setTextSize(20);
                    time_text.startAnimation(blink);
                }

            }

            public void onFinish() {

                Button1.setClickable(false);
                Button2.setClickable(false);
                Button3.setClickable(false);
                Button4.setClickable(false);

                rel3.setVisibility(View.INVISIBLE);
                textView33.setText("Time Up!");
                textView33.startAnimation(fade_out);


                lives--;

                if (lives==2){
                    live_1.setImageDrawable(null);
                }
                if (lives==1){
                    live_2.setImageDrawable(null);
                }
                if (lives==0){
//                    live_3.setImageDrawable(null);
                    add_to_score_database();

                    Intent it = new Intent(getApplicationContext(), Quiz_score.class);
//                    it.putExtra("user_id", user_id);
                    it.putExtra("score", String.valueOf(score*20));
                    startActivity(it);
                }

                if (lives==-1) {


                }
            }
        };


        show_question();
    }


    @Override
    public void onClick(View v) {

        cdt.cancel();

        Drawable drawableNormal = v.getBackground();

        Drawable drawablePressed = v.getBackground().getConstantState().newDrawable();
        drawablePressed.mutate();
        drawablePressed.setColorFilter(Color.parseColor("#ff0000"), PorterDuff.Mode.SRC_ATOP);

        StateListDrawable listDrawable = new StateListDrawable();
        listDrawable.addState(new int[] {android.R.attr.state_pressed}, drawablePressed);
        listDrawable.addState(new int[] {}, drawableNormal);
        v.setBackground(listDrawable);

        int id = v.getId();
        String selected_answer=null;

        switch (id){
            case R.id.Button1:
                selected_answer=  Button1.getText().toString();
                break;
            case R.id.Button2:
                selected_answer=  Button2.getText().toString();
                break;
            case R.id.Button3:
                selected_answer=  Button3.getText().toString();
                break;
            case R.id.Button4:
                selected_answer=  Button4.getText().toString();
                break;
        }

        if (selected_answer.equals(answer[random])){
            qlay.startAnimation(shake3);
            score++;
            random();
        }

        else{
            Button1.setClickable(false);
            Button2.setClickable(false);
            Button3.setClickable(false);
            Button4.setClickable(false);

            rel3.setVisibility(View.INVISIBLE);

            textView33.startAnimation(fade_out);
            textView33.setText("Wrong!");

//            qlay.startAnimation(shake2);
            lives--;

            if (lives==2){
                live_1.setImageDrawable(null);
            }
            if (lives==1){
                live_2.setImageDrawable(null);
            }
            if (lives==0){
//                live_3.setImageDrawable(null);

                cdt.cancel();

                add_to_score_database();
                Intent it = new Intent(getApplicationContext(), Quiz_score.class);
//                it.putExtra("user_id", user_id);
                it.putExtra("score", String.valueOf(score*20));
                startActivity(it);
            }
            if (lives==-1){

            }


        }



    }

    private void show_question() {
        random();
//        change_question();
    }


    private void random() {
        score_text.setText(""+score*20);

        time_text.clearAnimation();
        cdt.cancel();
        cdt.start();

        ran = new Random();

        random = ran.nextInt(57);


        if(rand_int.size()==57) {
            rand_int.clear();
        }
        if(rand_int.contains(random)){
            random();
        }
        else{
            question_no++;

            score_string = String.valueOf(score);
            change_question();

            rand_int.add(random);
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
    public void onBackPressed(){

    }

    private void add_to_score_database() {
        Database Database = new Database(this);

        SQLiteDatabase DBase = Database.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("user_id", user_id);
        values.put("score", score*20);
        values.put("username", username);
        values.put("email", email);
        values.put("uploaded", "0");

        DBase.insert("scores", null, values);
        DBase.close();
    }

}
