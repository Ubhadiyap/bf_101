 package edu.nutri.breast_feeding_101;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

 public class Course6_unit1 extends Activity {
	String user_id;
//	String pre_ass_score;

	 TextView textView3, main_title, sub_title;

	 UserDetails user_details = new UserDetails();

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.courses);
//		Bundle b = getIntent().getExtras();
		user_id = user_details.get_user_id();
//		pre_ass_score = b.getString("score_string");

		main_title = (TextView)findViewById(R.id.main_title);
		main_title.setText(R.string.c6);

		sub_title = (TextView)findViewById(R.id.gender);
		sub_title.setText(R.string.c6u2);

		textView3 = (TextView)findViewById(R.id.age);
		textView3.setText(R.string.c6d);
	}
	
	public void next(View v){

		Intent it = new Intent(this, Course_quiz.class);
		it.putExtra("course", "6");
//		it.putExtra("user_id", user_id);
//		it.putExtra("score_string", pre_ass_score);
		startActivity(new Intent(it));
	}
	
	public void prev(View v){

		Intent it = new Intent(this, Course6_introduction.class);
//		it.putExtra("user_id", user_id); it.putExtra("score_string", pre_ass_score);
		startActivity(new Intent(it));
	}
	
	}


