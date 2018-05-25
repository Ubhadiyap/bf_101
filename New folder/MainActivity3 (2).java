package edu.nutri.breast_feeding_101;

import edu.nutri.breast_feeding_101.CircleDisplay.SelectionListener;
import edu.nutri.breast_feeding_101.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity3 extends Activity implements SelectionListener {
    
    private CircleDisplay mCircleDisplay;
    
//    Course1_quiz main = new Course1_quiz();
    
//    public float score = main.score;
    public float score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//    	Bundle b = getIntent().getExtras();
//        score2 = Float.parseFloat( b.getString("score"));
//        score = score2 * 20;
        score=40;
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main3);
        
        mCircleDisplay = (CircleDisplay) findViewById(R.id.circleDisplay);
        mCircleDisplay.setAnimDuration(score, 4000);
        mCircleDisplay.setValueWidthPercent(55f);
        mCircleDisplay.setFormatDigits(1);
        mCircleDisplay.setDimAlpha(80);
        mCircleDisplay.setSelectionListener(this);
        mCircleDisplay.setTouchEnabled(true);
        mCircleDisplay.setUnit("%");
        mCircleDisplay.setStepSize(0.5f);
        mCircleDisplay.showValue(score, 100f, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId() == R.id.refresh) mCircleDisplay.showValue(score, 100f, true);
//        if(item.getItemId() == R.id.github) {
//            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/PhilJay/CircleDisplay"));
//            startActivity(i);
//        }
        return true;
    }

    @Override
    public void onSelectionUpdate(float val, float maxval) {
        Log.i("Main", "Selection update: " + val + ", max: " + maxval);
    }

    @Override
    public void onValueSelected(float val, float maxval) {
        Log.i("Main", "Selection complete: " + val + ", max: " + maxval);
    }
}
