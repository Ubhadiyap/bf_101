package edu.nutri.breast_feeding_101;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;

import java.util.Map;

@SuppressLint("ValidFragment")
public class Statistics extends Fragment {

	String name, email;
	String user_id;

	UserDetails user_details = new UserDetails();

	public Statistics(){
		name = user_details.get_username();
		user_id = user_details.get_user_id();
	}

	String column_id, uploaded;

	float total = 0;
	float total_average = 0;

	LinearLayout l1;
	LinearLayout l2;
	LinearLayout l3;
	LinearLayout l4;
	LinearLayout l5;
	LinearLayout l6;

	TextView average_score_text;
	TextView point_text;

	TextView t1;
	TextView t2;
	TextView t3;
	TextView t4;
	TextView t5;

	TextView t1b;
	TextView t2b;

	TextView t3b;
	TextView t4b;

	String stage = "Professional";
	int attempt = 5;
	int pass = 3;
	int fail = 2;
	int course_average_score = 78;

	TextView textView4;

	String t1_text;
	String t2_text;
	String t3_text;
	String t4_text;
	String t5_text;

	float point = 23456;
	float total_average_score;

	int course1_attempts;
	int course1_success;
	int course1_failed;
	int course1_total_score;
	float course1_average_score;

	int course2_attempts;
	int course2_success;
	int course2_failed;
	int course2_total_score;
	float course2_average_score;

	int course3_attempts;
	int course3_success;
	int course3_failed;
	int course3_total_score;
	float course3_average_score;

	int course4_attempts;
	int course4_success;
	int course4_failed;
	int course4_total_score;
	float course4_average_score;

	int course5_attempts;
	int course5_success;
	int course5_failed;
	int course5_total_score;
	float course5_average_score;

	int course6_attempts;
	int course6_success;
	int course6_failed;
	int course6_total_score;
	float course6_average_score;

	RatingBar ratingBar1;
	int rating = 0;

	String course_no;
	String attempts;
	String success;
	String failed;
	String total_score ;
	String average_score;

	String pre_ass_score;
	String score;
	String status;

	String score_url, Stat_url;
	Firebase Score_reference, Stat_reference;
//    private NativeExpressAdView mAdView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.statistics, container, false);

		Firebase.setAndroidContext(getActivity());

//		mAdView = (NativeExpressAdView) rootView.findViewById(R.id.adView);

		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				// Check the LogCat to get your test device ID
				.addTestDevice("7D8AB0062B2FC24E9C68BFC765C104C7")
//                .addTestDevice("C04B1BFFB0774708339BC273F8A43708")
				.build();

//		mAdView.loadAd(adRequest);

//		mAdView.setAdListener(new AdListener() {
//			@Override
//			public void onAdLoaded() {
//			}
//
//			@Override
//			public void onAdClosed() {
//				Toast.makeText(getActivity(), "Ad is closed!", Toast.LENGTH_SHORT).show();
//			}
//
//			@Override
//			public void onAdFailedToLoad(int errorCode) {
//				Toast.makeText(getActivity(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
//			}
//
//			@Override
//			public void onAdLeftApplication() {
//				Toast.makeText(getActivity(), "Ad left application!", Toast.LENGTH_SHORT).show();
//			}
//
//			@Override
//			public void onAdOpened() {
//				super.onAdOpened();
//			}
//		});

		score_url = UserDetails.database_url + "Course_Scores/";
		Score_reference = new Firebase(score_url);

		Stat_url = UserDetails.database_url + "Course_Stats/";
		Stat_reference = new Firebase(Stat_url);

//		Button button22 = (Button)rootView.findViewById(R.id.button22);
//		button22.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				test();
//			}
//		});

		ratingBar1 = (RatingBar)rootView.findViewById(R.id.ratingBar1);

		average_score_text = (TextView)rootView.findViewById(R.id.age);
		point_text = (TextView)rootView.findViewById(R.id.main_title);
		//TODO
		//check_statistics_db();
		check_course1_db();
		check_course2_db();
		check_course3_db();
		check_course4_db();
		check_course5_db();
		check_course6_db();

		average_score_text.setText(average_score_text.getText().toString()+ String.valueOf(total_average/total)+"%");
		ratingBar1.setRating(rating);

		textView4 = (TextView)rootView.findViewById(R.id.marital_status);
		textView4.setText("Username: " + name);
		t1 = (TextView) rootView.findViewById(R.id.t1);
		t2 = (TextView) rootView.findViewById(R.id.t2);
		t3 = (TextView) rootView.findViewById(R.id.t3);
		t4 = (TextView) rootView.findViewById(R.id.t4);
		t5 = (TextView) rootView.findViewById(R.id.t5);

		t1b = (TextView) rootView.findViewById(R.id.t1b);
		t2b = (TextView) rootView.findViewById(R.id.t2b);
		t3b = (TextView) rootView.findViewById(R.id.t3b);
		t4b = (TextView) rootView.findViewById(R.id.t4b);

		l1 = (LinearLayout) rootView.findViewById(R.id.linearLayout1);
		l1.setBackgroundColor(Color.parseColor("#FEFFe1"));

		l2 = (LinearLayout) rootView.findViewById(R.id.LinearLayout01);
		l2.setBackgroundColor(Color.parseColor("#FEFFe1"));

		l3 = (LinearLayout) rootView.findViewById(R.id.LinearLayout02);
		l3.setBackgroundColor(Color.parseColor("#FEFFe1"));

		l4 = (LinearLayout) rootView.findViewById(R.id.LinearLayout03);
		l4.setBackgroundColor(Color.parseColor("#FEFFe1"));

		l5 = (LinearLayout) rootView.findViewById(R.id.LinearLayout04);
		l5.setBackgroundColor(Color.parseColor("#FEFFe1"));

		l6 = (LinearLayout) rootView.findViewById(R.id.LinearLayout05);
		l6.setBackgroundColor(Color.parseColor("#FEFFe1"));

		t1_text = t1.getText().toString();
		t2_text = t2.getText().toString();
		t3_text = t3.getText().toString();
		t4_text = t4.getText().toString();
		t5_text = t5.getText().toString();

		t1.setText(t1_text+": "+course1_attempts);
		t2.setText(t2_text+": "+course1_success);
		t3.setText(t3_text+": "+course1_failed);
		t4.setText(t4_text+": "+course1_average_score);
		t5.setText(t5_text+": " + course1_total_score);

		Button b1 = (Button) rootView.findViewById(R.id.button1);
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {b1(); }});

		Button b2 = (Button) rootView.findViewById(R.id.button2);
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {b2(); }});

		Button b3 = (Button) rootView.findViewById(R.id.button3);
		b3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {b3(); }});

		Button b4 = (Button) rootView.findViewById(R.id.button4);
		b4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {b4(); }});

		Button b5 = (Button) rootView.findViewById(R.id.button5);
		b5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {b5(); }});
		Button b6 = (Button) rootView.findViewById(R.id.button6);
		b6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {b6(); }});
		clear();

		email = user_details.get_email();

		return rootView;

	}

//	@Override
//	public void onPause() {
//		if (mAdView != null) {
//			mAdView.pause();
//		}
//		super.onPause();
//	}
//
//	@Override
//	public void onResume() {
//		super.onResume();
//		if (mAdView != null) {
//			mAdView.resume();
//		}
//	}
//
//	@Override
//	public void onDestroy() {
//		if (mAdView != null) {
//			mAdView.destroy();
//		}
//		super.onDestroy();
//	}

//	private void test() {
//
////		Firebase ref = new Firebase("https://nutrition-64795.firebaseio.com/messages/Others_0/");
//		Firebase ref = new Firebase(UserDetails.database_url + "Users/");
//
//		Query queryRef = ref.orderByChild("Level_of_Education");
//
//		queryRef.addChildEventListener(new ChildEventListener() {
//			@Override
//			public void onChildAdded(DataSnapshot snapshot, String previousChild) {
//				Map map = snapshot.getValue(Map.class);
//				Toast.makeText(getActivity(), map.get("Level_of_Education").toString()+"", Toast.LENGTH_SHORT).show();
//			}
//
//			@Override
//			public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//			}
//
//			@Override
//			public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//			}
//
//			@Override
//			public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//			}
//
//			@Override
//			public void onCancelled(FirebaseError firebaseError) {
//
//			}
//			// ....
//		});
//
//	}


	private void check_course1_db() {

		SQLiteDatabase newDB;

		Course_stats_db Course_stats_db  = new Course_stats_db(getActivity());
		Cursor c = Course_stats_db.getWritableDatabase().rawQuery(Course_stats_db.select_all(), null);

		int x = 0;
		if (c.getCount() != 0 ) {
			total=total+1;
			if  (c.moveToFirst()) {

				do {
//			Toast.makeText(getActivity(), c.getString(c.getColumnIndex("course_no"))+"  "+ c.getString(c.getColumnIndex("attempts")), Toast.LENGTH_SHORT).show();
					if (user_id.equals(c.getString(c.getColumnIndex("user_id"))) && c.getString(c.getColumnIndex("course_no")).equals("1") )
					{
						course1_attempts = Integer.parseInt(c.getString(c.getColumnIndex("attempts")));
						course1_success= Integer.parseInt(c.getString(c.getColumnIndex("success")));
						course1_failed= Integer.parseInt(c.getString(c.getColumnIndex("failed")));
						course1_total_score= Integer.parseInt(c.getString(c.getColumnIndex("total_score")));
						course1_average_score = course1_total_score/course1_attempts;

						x++;
					}
				}while (c.moveToNext());
			}
			total_average =total_average+course1_average_score;
		}

		if (course1_success>0){
			rating=rating+1;
		}
		if(x==0){
			course1_attempts=0;
			course1_success=0;
			course1_failed=0;
			course1_total_score=0;
			course1_average_score=0;
		}

	}

	private void check_course2_db() {

		SQLiteDatabase newDB;

		Course_stats_db Course_stats_db  = new Course_stats_db(getActivity());
		newDB = Course_stats_db.getWritableDatabase();
		Cursor c = newDB.rawQuery(Course_stats_db.select_all(), null);

		int x = 0;
		if (c.getCount() != 0 ) {
			total=total+1;
			if  (c.moveToFirst()) {

				do {
					if (user_id.equals(c.getString(c.getColumnIndex("user_id"))) && c.getString(c.getColumnIndex("course_no")).equals("2") )
					{
						course2_attempts = Integer.parseInt(c.getString(c.getColumnIndex("attempts")));
						course2_success= Integer.parseInt(c.getString(c.getColumnIndex("success")));
						course2_failed= Integer.parseInt(c.getString(c.getColumnIndex("failed")));
						course2_total_score= Integer.parseInt(c.getString(c.getColumnIndex("total_score")));
						course2_average_score= course2_total_score/course2_attempts;

						x++;
					}
				}while (c.moveToNext());
			}
			total_average =total_average+course2_average_score;
		}
		if (course2_success>0){
			rating=rating+1;
		}
		if(x==0){
			course2_attempts=0;
			course2_success=0;
			course2_failed=0;
			course2_total_score=0;
			course2_average_score=0;
		}

	}

	private void check_course3_db() {

		SQLiteDatabase newDB;

		Course_stats_db Course_stats_db  = new Course_stats_db(getActivity());
		newDB = Course_stats_db.getWritableDatabase();
		Cursor c = newDB.rawQuery(Course_stats_db.select_all(), null);

		int x = 0;
		if (c.getCount() != 0 ) {
			total=total+1;
			if  (c.moveToFirst()) {

				do {
					if (user_id.equals(c.getString(c.getColumnIndex("user_id"))) && c.getString(c.getColumnIndex("course_no")).equals("3") )
					{
						course3_attempts = Integer.parseInt(c.getString(c.getColumnIndex("attempts")));
						course3_success= Integer.parseInt(c.getString(c.getColumnIndex("success")));
						course3_failed= Integer.parseInt(c.getString(c.getColumnIndex("failed")));
						course3_total_score= Integer.parseInt(c.getString(c.getColumnIndex("total_score")));
						course3_average_score= course3_total_score/course3_attempts;

						x++;
					}
				}while (c.moveToNext());
			}
			total_average =total_average+course3_average_score;
		}
		if (course3_success>0){
			rating=rating+1;
		}
		if(x==0){
			course3_attempts=0;
			course3_success=0;
			course3_failed=0;
			course3_total_score=0;
			course3_average_score=0;
		}

	}

	private void check_course4_db() {

		SQLiteDatabase newDB;

		Course_stats_db Course_stats_db  = new Course_stats_db(getActivity());
		newDB = Course_stats_db.getWritableDatabase();
		Cursor c = newDB.rawQuery(Course_stats_db.select_all(), null);

		int x = 0;
		if (c.getCount() != 0 ) {
			total=total+1;
			if  (c.moveToFirst()) {

				do {
					if (user_id.equals(c.getString(c.getColumnIndex("user_id"))) && c.getString(c.getColumnIndex("course_no")).equals("4") )
					{
						course4_attempts = Integer.parseInt(c.getString(c.getColumnIndex("attempts")));
						course4_success= Integer.parseInt(c.getString(c.getColumnIndex("success")));
						course4_failed= Integer.parseInt(c.getString(c.getColumnIndex("failed")));
						course4_total_score= Integer.parseInt(c.getString(c.getColumnIndex("total_score")));
						course4_average_score= course4_total_score/course4_attempts;

						x++;
					}
				}while (c.moveToNext());
			}
			total_average =total_average+course4_average_score;
		}
		if (course4_success>0){
			rating=rating+1;
		}
		if(x==0){
			course4_attempts=0;
			course4_success=0;
			course4_failed=0;
			course4_total_score=0;
			course4_average_score=0;
		}

	}

	private void check_course5_db() {

		SQLiteDatabase newDB;

		Course_stats_db Course_stats_db  = new Course_stats_db(getActivity());
		newDB = Course_stats_db.getWritableDatabase();
		Cursor c = newDB.rawQuery(Course_stats_db.select_all(), null);

		int x = 0;
		if (c.getCount() != 0 ) {
			total=total+1;
			if  (c.moveToFirst()) {

				do {
					if (user_id.equals(c.getString(c.getColumnIndex("user_id"))) && c.getString(c.getColumnIndex("course_no")).equals("5") )
					{
						course5_attempts = Integer.parseInt(c.getString(c.getColumnIndex("attempts")));
						course5_success= Integer.parseInt(c.getString(c.getColumnIndex("success")));
						course5_failed= Integer.parseInt(c.getString(c.getColumnIndex("failed")));
						course5_total_score= Integer.parseInt(c.getString(c.getColumnIndex("total_score")));
						course5_average_score= course5_total_score/course5_attempts;

						x++;
					}
				}while (c.moveToNext());
			}
			total_average =total_average+course5_average_score;
		}
		if (course5_success>0){
			rating=rating+1;
		}
		if(x==0){
			course5_attempts=0;
			course5_success=0;
			course5_failed=0;
			course5_total_score=0;
			course5_average_score=0;
		}

	}

	private void check_course6_db() {

		SQLiteDatabase newDB;

		Course_stats_db Course_stats_db  = new Course_stats_db(getActivity());
		newDB = Course_stats_db.getWritableDatabase();
		Cursor c = newDB.rawQuery(Course_stats_db.select_all(), null);

		int x = 0;
		if (c.getCount() != 0 ) {
			total=total+1;
			if  (c.moveToFirst()) {

				do {
					if (user_id.equals(c.getString(c.getColumnIndex("user_id"))) && c.getString(c.getColumnIndex("course_no")).equals("6") )
					{
						course6_attempts = Integer.parseInt(c.getString(c.getColumnIndex("attempts")));
						course6_success= Integer.parseInt(c.getString(c.getColumnIndex("success")));
						course6_failed= Integer.parseInt(c.getString(c.getColumnIndex("failed")));
						course6_total_score= Integer.parseInt(c.getString(c.getColumnIndex("total_score")));
						course6_average_score= course6_total_score/course6_attempts;

						x++;
					}
				}while (c.moveToNext());
			}
			total_average =total_average+course6_average_score;
		}
		if (course6_success>0){
			rating=rating+1;
		}
		if(x==0){
			course6_attempts=0;
			course6_success=0;
			course6_failed=0;
			course6_total_score=0;
			course6_average_score=0;
		}

	}
	public void b1(){
		int x = l1.getChildCount();

		clear();

		if((x==0)){

			t1.setText(t1_text+": "+course1_attempts);
			t2.setText(t2_text+": "+course1_success);
			t3.setText(t3_text+": "+course1_failed);
			t4.setText(t4_text+": "+course1_average_score);
			t5.setText(t5_text+": " + course1_total_score);

			l1.addView(t1);
			l1.addView(t1b);
			l1.addView(t2);
			l1.addView(t2b);
			l1.addView(t3);
			l1.addView(t3b);
			l1.addView(t4);
			l1.addView(t4b);
			l1.addView(t5);}

		else{

		}


	}
	public void b2(){
		int x = l2.getChildCount();

		clear();

		if((x==0)){

			t1.setText(t1_text+": "+course2_attempts);
			t2.setText(t2_text+": "+course2_success);
			t3.setText(t3_text+": "+course2_failed);
			t4.setText(t4_text+": "+course2_average_score);
			t5.setText(t5_text+": " + course2_total_score);

			l2.addView(t1);
			l2.addView(t1b);
			l2.addView(t2);
			l2.addView(t2b);
			l2.addView(t3);
			l2.addView(t3b);
			l2.addView(t4);
			l2.addView(t4b);
			l2.addView(t5);
		}
		else{

		}
	}
	public void b3(){
		int x = l3.getChildCount();

		clear();

		if((x==0)){

			t1.setText(t1_text+": "+course3_attempts);
			t2.setText(t2_text+": "+course3_success);
			t3.setText(t3_text+": "+course3_failed);
			t4.setText(t4_text+": "+course3_average_score);
			t5.setText(t5_text+": " + course3_total_score);

			l3.addView(t1);
			l3.addView(t1b);
			l3.addView(t2);
			l3.addView(t2b);
			l3.addView(t3);
			l3.addView(t3b);
			l3.addView(t4);
			l3.addView(t4b);
			l3.addView(t5);

		}
		else{

		}}
	public void b4(){
		int x = l4.getChildCount();

		clear();

		if((x==0)){

			t1.setText(t1_text+": "+course4_attempts);
			t2.setText(t2_text+": "+course4_success);
			t3.setText(t3_text+": "+course4_failed);
			t4.setText(t4_text+": "+course4_average_score);
			t5.setText(t5_text+": " + course4_total_score);

			l4.addView(t1);
			l4.addView(t1b);
			l4.addView(t2);
			l4.addView(t2b);
			l4.addView(t3);
			l4.addView(t3b);
			l4.addView(t4);
			l4.addView(t4b);
			l4.addView(t5);
		}
		else{

		}}
	public void b5(){
		int x = l5.getChildCount();

		clear();

		if((x==0)){

			t1.setText(t1_text+": "+course5_attempts);
			t2.setText(t2_text+": "+course5_success);
			t3.setText(t3_text+": "+course5_failed);
			t4.setText(t4_text+": "+course5_average_score);
			t5.setText(t5_text+": " + course5_total_score);

			l5.addView(t1);
			l5.addView(t1b);
			l5.addView(t2);
			l5.addView(t2b);
			l5.addView(t3);
			l5.addView(t3b);
			l5.addView(t4);
			l5.addView(t4b);
			l5.addView(t5);
		}
		else{

		}}

	public void b6(){
		int x = l6.getChildCount();

		clear();

		if((x==0)){

			t1.setText(t1_text+": "+course6_attempts);
			t2.setText(t2_text+": "+course6_success);
			t3.setText(t3_text+": "+course6_failed);
			t4.setText(t4_text+": "+course6_average_score);
			t5.setText(t5_text+": " + course6_total_score);

			l6.addView(t1);
			l6.addView(t1b);
			l6.addView(t2);
			l6.addView(t2b);
			l6.addView(t3);
			l6.addView(t3b);
			l6.addView(t4);
			l6.addView(t4b);
			l6.addView(t5);
		}
		else{

		}}
	public void clear(){
		l1.removeAllViews();
		l2.removeAllViews();
		l3.removeAllViews();
		l4.removeAllViews();
		l5.removeAllViews();
		l6.removeAllViews();
	}
}
