package edu.nutri.breast_feeding_101;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Course5_unit2 extends Activity {

	String user_id;
	int x ;
	TextView textView3, main_title, sub_title;
//	String pre_ass_score;

	UserDetails user_details = new UserDetails();

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.courses);
		
//		Bundle b = getIntent().getExtras();
		user_id = user_details.get_user_id();
//		pre_ass_score = b.getString("score_string");
		x=1;

		main_title = (TextView)findViewById(R.id.main_title);
		main_title.setText(R.string.c5);

		sub_title = (TextView)findViewById(R.id.gender);
		sub_title.setText(R.string.c5u3);

		textView3 = (TextView)findViewById(R.id.age);
		textView3.setText(R.string.c5e);
	}
	
	public void next(View v){
		
		x++;
		if (x==1){
			textView3.setText(R.string.c5e);
		}
		else if (x==2){
			textView3.setText(R.string.c5f);
		}
		else if (x==3){
			textView3.setText(R.string.c5g);
		}
		else{

		Intent it = new Intent(this, Course_quiz.class);
			it.putExtra("course", "5");
//		it.putExtra("user_id", user_id);
//			it.putExtra("score_string", pre_ass_score);
		startActivity(new Intent(it));
	}
	}
	
	public void prev(View v){
		x--;
		if (x==1){
			textView3.setText(R.string.c5e);
		}
		else if (x==2){
			textView3.setText(R.string.c5f);
		}
		else if (x==3){
			textView3.setText(R.string.c5g);
		}
		else if (x==0){

			Intent it = new Intent(this, Course5_unit1.class);
//			it.putExtra("user_id", user_id); it.putExtra("score_string", pre_ass_score);
			startActivity(new Intent(it));
	}
	
	}}
