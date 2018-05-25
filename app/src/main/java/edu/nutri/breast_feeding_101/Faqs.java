package edu.nutri.breast_feeding_101;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Akano on 2/16/2017.
 */



public class Faqs extends Activity {

    TextView answer;
    RelativeLayout main_lay;
    LinearLayout l1;
    LinearLayout l2;
    LinearLayout l3;
    LinearLayout l4;
    LinearLayout l5;
    LinearLayout l6;
    LinearLayout l7;
    LinearLayout l8;
    LinearLayout l9;

    String [] faq_answers;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faqs);

        main_lay = (RelativeLayout)findViewById(R.id.main_lay);
        l1 = (LinearLayout)findViewById(R.id.l1);
//        l1.setBackgroundResource(R.drawable.rounded_edittext6);
        l1.setBackgroundColor(Color.parseColor("#ffffffff"));
        l2 = (LinearLayout)findViewById(R.id.l2);
        l2.setBackgroundColor(Color.parseColor("#ffffffff"));
        l3 = (LinearLayout)findViewById(R.id.l3);
        l3.setBackgroundColor(Color.parseColor("#ffffffff"));
        l4 = (LinearLayout)findViewById(R.id.l4);
        l4.setBackgroundColor(Color.parseColor("#ffffffff"));
        l5 = (LinearLayout)findViewById(R.id.l5);
        l5.setBackgroundColor(Color.parseColor("#ffffffff"));
        l6 = (LinearLayout)findViewById(R.id.l6);
        l6.setBackgroundColor(Color.parseColor("#ffffffff"));
        l7 = (LinearLayout)findViewById(R.id.l7);
        l7.setBackgroundColor(Color.parseColor("#ffffffff"));
        l8 = (LinearLayout)findViewById(R.id.l8);
        l8.setBackgroundColor(Color.parseColor("#ffffffff"));
        l9 = (LinearLayout)findViewById(R.id.l9);
        l9.setBackgroundColor(Color.parseColor("#ffffffff"));

        String [] faq = getResources().getStringArray(R.array.faq);
        faq_answers = getResources().getStringArray(R.array.faq_answers);

        answer = new TextView(this);


        TextView q1 = (TextView)findViewById(R.id.q1);
        q1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reset();
                TextView answer2 = new TextView(getApplicationContext());
                answer2.setTextColor(Color.parseColor("#ff0000"));
                answer2.setText(faq_answers[0]);

                l1.addView(answer2);
            }
        });
        q1.setText(faq[0]);

        TextView q2 = (TextView)findViewById(R.id.q2);
        q2.setText(faq[1]);
        q2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                TextView answer2 = new TextView(getApplicationContext());
                answer2.setTextColor(Color.parseColor("#ff0000"));
                answer2.setText(faq_answers[1]);

                l2.addView(answer2);
            }
        });

        TextView q3 = (TextView)findViewById(R.id.q3);
        q3.setText(faq[2]);
        q3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                TextView answer2 = new TextView(getApplicationContext());
                answer2.setTextColor(Color.parseColor("#ff0000"));
                answer2.setText(faq_answers[2]);

                l3.addView(answer2);
            }
        });

        TextView q4 = (TextView)findViewById(R.id.q4);
        q4.setText(faq[3]);
        q4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                TextView answer2 = new TextView(getApplicationContext());
                answer2.setTextColor(Color.parseColor("#ff0000"));
                answer2.setText(faq_answers[3]);

                l4.addView(answer2);
            }
        });

        TextView q5 = (TextView)findViewById(R.id.q5);
        q5.setText(faq[4]);
        q5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                TextView answer2 = new TextView(getApplicationContext());
                answer2.setTextColor(Color.parseColor("#ff0000"));
                answer2.setText(faq_answers[4]);

                l5.addView(answer2);
            }
        });

        TextView q6 = (TextView)findViewById(R.id.q6);
        q6.setText(faq[5]);
        q6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                TextView answer2 = new TextView(getApplicationContext());
                answer2.setTextColor(Color.parseColor("#ff0000"));
                answer2.setText(faq_answers[5]);

                l6.addView(answer2);
            }
        });

        TextView q7 = (TextView)findViewById(R.id.q7);
        q7.setText(faq[6]);
        q7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                TextView answer2 = new TextView(getApplicationContext());
                answer2.setTextColor(Color.parseColor("#ff0000"));
                answer2.setText(faq_answers[6]);

                l7.addView(answer2);
            }
        });

        TextView q8 = (TextView)findViewById(R.id.q8);
        q8.setText(faq[7]);
        q8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                TextView answer2 = new TextView(getApplicationContext());
                answer2.setTextColor(Color.parseColor("#ff0000"));
                answer2.setText(faq_answers[7]);

                l8.addView(answer2);
            }
        });

        TextView q9 = (TextView)findViewById(R.id.q9);
        q9.setText(faq[8]);
        q9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                TextView answer2 = new TextView(getApplicationContext());
                answer2.setTextColor(Color.parseColor("#ff0000"));
                answer2.setText(faq_answers[8]);

                l9.addView(answer2);
            }
        });


    }

    void reset(){

        l1.removeAllViews();
        l2.removeAllViews();
        l3.removeAllViews();
        l4.removeAllViews();
        l5.removeAllViews();
        l6.removeAllViews();
        l7.removeAllViews();
        l8.removeAllViews();
        l9.removeAllViews();
    }
}
