package edu.nutri.breast_feeding_101;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

/**
 * Created by Akano on 12/2/2017.
 */

public class Score_page extends Activity implements CircleDisplay.SelectionListener {

    float score;
    TextView score_text, status_text, recommendation_text;
    String recommendation, score2, status, user_id;
    private CircleDisplay mCircleDisplay;

    UserDetails user_details = new UserDetails();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_page);

        Firebase.setAndroidContext(getApplicationContext());


        Bundle b = getIntent().getExtras();

        score = b.getInt("score");
        user_id = user_details.get_user_id();

        mCircleDisplay = (CircleDisplay) findViewById(R.id.circleDisplay);
        mCircleDisplay.setAnimDuration(score, 4000);
        mCircleDisplay.setValueWidthPercent(55f);
        //		mCircleDisplay.setFormatDigits(1);
        mCircleDisplay.setDimAlpha(80);
        mCircleDisplay.setSelectionListener(this);
        mCircleDisplay.setTouchEnabled(true);
        mCircleDisplay.setUnit("%");
        mCircleDisplay.setStepSize(0.5f);
        mCircleDisplay.showValue(score, 100f, true);

        recommendation_text = (TextView)findViewById(R.id.recommendation_text);
        status_text = (TextView)findViewById(R.id.status_text);

        recommendation=recommendation_text.getText().toString();
        status = status_text.getText().toString();


        if(score>=80){
            status_text.setText(status+ "Pass!");
            status_text.setTextColor(Color.parseColor("#00ff00"));
            recommendation_text.setText(recommendation+ "Proceed to the next course");
        }
        else{
            status_text.setText(status+ "Fail");
            recommendation_text.setText(recommendation+ "Retake the course to proceed");
        }

    }

    public void home(View v){

        Intent it;
        it = new Intent(this, Slider.class);
        startActivity(it);
        finish();

    }
    @Override
    public void onBackPressed(){
        Toast.makeText(getApplicationContext(), "Can't go back", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSelectionUpdate(float val, float maxval) {
        //		Toast.makeText(this, val +" 1 "+maxval, Toast.LENGTH_SHORT).show();
        Log.i("Main", "Selection update: " + val + ", max: " + maxval);
    }

    @Override
    public void onValueSelected(float val, float maxval) {
        //		Toast.makeText(this, val +" 2 "+maxval, Toast.LENGTH_SHORT).show();
        Log.i("Main", "Selection complete: " + val + ", max: " + maxval);
    }

}