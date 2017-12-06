package edu.nutri.breast_feeding_101;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Course_preassesment extends Activity implements View.OnClickListener, Animation.AnimationListener {

    String score_string;
    Random ran;

    CountDownTimer cdt;

    Animation shake;
    Animation blink;
//    Animation fade_out;

    int done_rand[] = new int[10];

    String username;

    int question_no;
    int random;
    int score;

    TextView question, title_text, textView3;

    Button Button1, Button2, Button3, Button4;

    RelativeLayout qlay, rel2, rel4;

    String user_id, course;
    String pre_ass_score;
    List<Integer> rand_int;

    TextView score_text, time_text,textView33;

    RelativeLayout rel3;
    String [] questions;
    String [] option_1;
    String [] option_2;
    String [] option_3;
    String [] option_4;
    String [] answer;

    UserDetails user_details = new UserDetails();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        Bundle b = getIntent().getExtras();

        course = String.valueOf(b.getInt("course"));

        user_id = user_details.get_user_id();

        textView33 = (TextView)findViewById(R.id.textView33);
        rel3 = (RelativeLayout)findViewById(R.id.rel3);
        rel2 = (RelativeLayout)findViewById(R.id.rel2);
        rel4 = (RelativeLayout)findViewById(R.id.rel4);

        textView33.setVisibility(View.INVISIBLE);
        rel2.setVisibility(View.INVISIBLE);
        rel4.setVisibility(View.INVISIBLE);


        textView3 = (TextView)findViewById(R.id.textView3);
        textView3.setVisibility(View.GONE);

        title_text = (TextView)findViewById(R.id.title_text);
        title_text.setText("Pre Assessment");
        score_text = (TextView)findViewById(R.id.main_title);
        time_text = (TextView)findViewById(R.id.time_text);

        time_text.setTextSize(20);

        qlay = (RelativeLayout)findViewById(R.id.qlay);

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

        cdt = new CountDownTimer(26000, 1000) {

            public void onTick(long millisUntilFinished) {
                if ((millisUntilFinished / 1000)<=6){
                    time_text.setTextSize(20);
                    time_text.startAnimation(blink);
                }

                time_text.setText("00:" + ((millisUntilFinished / 1000)-1));

                if ((millisUntilFinished / 1000)==1){
                    qlay.startAnimation(shake);
                    time_text.setTextSize(20);
                }
            }

            public void onFinish() {
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
            score++;
        }
        else{

        }
        qlay.startAnimation(shake);
    }

    private void show_question() {
        random();
        change_question();
    }

    private void random() {
         ran = new Random();

        switch (course){
            case "1":
                random = ran.nextInt(9);
                break;
            case "2":
                random = ran.nextInt((19+1) -10)+10;
                break;
            case "3":
                random = ran.nextInt((27+1) -20)+20;
                break;
            case "4":
                random = ran.nextInt((37+1) -28)+28;
                break;
            case "5":
                random = ran.nextInt((47+1) -38)+38;
                break;
            case "6":
                random = ran.nextInt((57+1) -48)+48;
                break;
        }

        if (rand_int.size() == 57) {
            rand_int.clear();
        }
        if (rand_int.contains(random)) {
            random();
        } else {
            if (question_no == 5) {

                String score_string = String.valueOf(score*20);
                Intent it = null;

                switch (course){
                    case "1":
                        it = new Intent(this, Course1_introduction.class);
                        break;
                    case "2":
                        it = new Intent(this, Course2_introduction.class);
                        break;
                    case "3":
                        it = new Intent(this, Course3_introduction.class);
                        break;
                    case "4":
                        it = new Intent(this, Course4_introduction.class);
                        break;
                    case "5":
                        it = new Intent(this, Course5_introduction.class);
                        break;
                    case "6":
                        it = new Intent(this, Course6_introduction.class);
                        break;
                }

                user_details.set_pre_ass_score(score_string);

//                it.putExtra("user_id", user_id);
//                it.putExtra("score_string", score_string);

                startActivity(new Intent(it));
                cdt.cancel();
                qlay.clearAnimation();
                time_text.clearAnimation();
                this.finish();

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
    public void onBackPressed(){
        Intent it = new Intent(this, Slider.class);
//        it.putExtra("user_id", user_id);
        startActivity(it);

        cdt.cancel();
        qlay.clearAnimation();
        time_text.clearAnimation();
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
}