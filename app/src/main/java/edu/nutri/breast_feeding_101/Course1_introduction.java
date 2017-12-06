package edu.nutri.breast_feeding_101;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Course1_introduction extends Activity {
	int x ;
	TextView course_note, main_title, sub_title;
	String user_id;
	
//	String pre_ass_score;

	UserDetails user_details = new UserDetails();
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.courses);
		x=1;
		main_title = (TextView)findViewById(R.id.main_title);
		main_title.setText(R.string.c1);

		sub_title = (TextView)findViewById(R.id.gender);
		sub_title.setText("");

		course_note = (TextView)findViewById(R.id.age);

		course_note.setText(R.string.c1a);
		
//		Bundle b = getIntent().getExtras();
//		pre_ass_score = b.getString("score_string");

		user_id = user_details.get_user_id();
	}
	
	public void next(View v){


		x++;
		if (x==1){
			course_note.setText(R.string.c1a);
		}
		else if (x==2){
			course_note.setText(R.string.c1b);
		}
		else if (x==3){
			course_note.setText(R.string.c1c);
		}
		else if (x==4){
			course_note.setText(R.string.c1d);
		}
		else{
		//startActivity(new Intent(this, Course1_quiz.class));

		Intent it = new Intent(this, Course_quiz.class);
		it.putExtra("course", "1");
//		it.putExtra("user_id", user_id);
//			Toast.makeText(getApplicationContext(), user_id+" ", Toast.LENGTH_SHORT).show();
//			it.putExtra("score_string", pre_ass_score);
		startActivity(new Intent(it));
	}
	}
	
	public void prev(View v){
		x--;
		if (x==1){
			course_note.setText(R.string.c1a);
		}
		else if (x==2){
			course_note.setText(R.string.c1b);
		}
		else if (x==3){
			course_note.setText(R.string.c1c);
		}
		else if (x==4){
			course_note.setText(R.string.c1d);
		}
		else if (x==0){
			Toast.makeText(getApplicationContext(), "Can't go back", Toast.LENGTH_SHORT).show();
			x=1;

	}
	
	}
	@Override
	public void onBackPressed(){
		Toast.makeText(getApplicationContext(), "Can't go back", Toast.LENGTH_SHORT).show();
	}	
}
