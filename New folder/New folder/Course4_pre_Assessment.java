package edu.nutri.breast_feeding_101;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import edu.nutri.breast_feeding_101.R;
public class Course4_pre_Assessment extends Activity implements View.OnClickListener, Animation.AnimationListener {

	String score_string;
	Random ran;

	CountDownTimer cdt;

	Animation shake;
	Animation blink;
	Animation fade_out;

	int done_rand[] = new int[10];

	String username;

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
	String [] answer;	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz);

		Bundle b = getIntent().getExtras();
		user_id = b.getString("user_id");

		title_text = (TextView)findViewById(R.id.title_text);
		title_text.setText("Pre Assessment");
		score_text = (TextView)findViewById(R.id.score_text);
		time_text = (TextView)findViewById(R.id.time_text);

		qlay = (RelativeLayout)findViewById(R.id.qlay);

		questions = getResources().getStringArray(R.array.Questions);
		option_1 = getResources().getStringArray(R.array.option_1);
		option_2 = getResources().getStringArray(R.array.option_2);
		option_3 = getResources().getStringArray(R.array.option_3);
		option_4 = getResources().getStringArray(R.array.option_4);
		answer = getResources().getStringArray(R.array.answers);

		blink = AnimationUtils.loadAnimation(this, R.anim.blink);
		shake = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);

		fade_out.setAnimationListener(this);

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
				if ((millisUntilFinished / 1000)<=5){
					time_text.setTextSize(30);
					time_text.startAnimation(blink);
				}

				time_text.setText("Time 00:" + millisUntilFinished / 1000);
			}

			public void onFinish() {
				time_text.setTextSize(25);
				random();
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

			Toast.makeText(this, "wrong", Toast.LENGTH_SHORT).show();
		}
		random();
		qlay.startAnimation(shake);
	}

	private void show_question() {
		random();
		change_question();
	}


	private void random() {
		score_text.setText("Score: " + score * 20);



		ran = new Random();

		random = ran.nextInt((37+1) -28)+28;

		if (rand_int.size() == 57) {
			rand_int.clear();
		}
		if (rand_int.contains(random)) {
			random();
		} else {
			if (question_no == 5) {

				String score_string = String.valueOf(score*20);

				Intent it = new Intent(this, Course4_introduction.class);

				it.putExtra("user_id", user_id);
				it.putExtra("score_string", score_string);

				startActivity(new Intent(it));

				Toast.makeText(this, ""+score, Toast.LENGTH_SHORT).show();

				this.finish();

			} else {
				cdt.start();
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
		Intent it = new Intent(this, MainActivity.class);
		it.putExtra("user_id", user_id);
		startActivity(it);
	}

	@Override
	public void onAnimationStart(Animation animation) {
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		random();
	}

	private void add_to_score_database() {
		Database Database = new Database(this);

		SQLiteDatabase DBase = Database.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put("user_id", user_id);
		values.put("score", score*20);

		DBase.insert("scores", null, values);
		DBase.close();
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
	}
}