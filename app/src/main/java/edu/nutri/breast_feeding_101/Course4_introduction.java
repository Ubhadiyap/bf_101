package edu.nutri.breast_feeding_101;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Course4_introduction extends Activity {

	UserDetails user_details = new UserDetails();
	String user_id;
	int x ;
	TextView textView3, main_title, sub_title;
//	String pre_ass_score;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.courses);
		
		Bundle b = getIntent().getExtras();
//		pre_ass_score = b.getString("score_string");

		user_id = user_details.get_user_id();

		x=1;

		main_title = (TextView)findViewById(R.id.main_title);
		main_title.setText(R.string.c4);

		sub_title = (TextView)findViewById(R.id.gender);
		main_title.setText("");

		textView3 = (TextView)findViewById(R.id.age);
		textView3.setText(R.string.c4a);
	}
	
public void next(View v){
		
		x++;
		if (x==1){
			textView3.setText(R.string.c4a);
		}
		else if (x==2){
			textView3.setText(R.string.c4b);
		}
		else if (x==3){
			textView3.setText(R.string.c4c);
		}
		else if (x==4){
			textView3.setText(R.string.c4d);
		}
		else if (x==5){
			textView3.setText(R.string.c4e);
		}
		else{

			Intent it = new Intent(this, Course_quiz.class);
			it.putExtra("course", "4");
//			it.putExtra("user_id", user_id);
//			it.putExtra("score_string", pre_ass_score);
			startActivity(new Intent(it));
	}
	}
	
	public void prev(View v){
		x--;
		if (x==1){
			textView3.setText(R.string.c4a);
		}
		else if (x==2){
			textView3.setText(R.string.c4b);
		}
		else if (x==3){
			textView3.setText(R.string.c4c);
		}
		else if (x==4){
			textView3.setText(R.string.c4d);
		}
		else if (x==5){
			textView3.setText(R.string.c4e);
		}
		else if (x==0){
			x=1;
			Toast.makeText(getApplicationContext(), "Can't go back", Toast.LENGTH_SHORT).show();

	}
	
	}
	@Override
	public void onBackPressed(){
		Toast.makeText(getApplicationContext(), "Can't go back", Toast.LENGTH_SHORT).show();
	}
	}
