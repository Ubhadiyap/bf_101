package edu.nutri.breast_feeding_101;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import edu.nutri.breast_feeding_101.R;
public class Course3_unit1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course1_unit1);
	}
	
	public void next(View v){
		startActivity(new Intent(this, Course1_unit2.class));
	}
	
	public void prev(View v){
		startActivity(new Intent(this, Course1_introduction.class));
	}
	
	}


