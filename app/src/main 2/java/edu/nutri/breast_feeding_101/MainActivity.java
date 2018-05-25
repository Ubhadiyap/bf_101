//package edu.nutri.breast_feeding_101;
//
//import edu.nutri.breast_feeding_101.Rss.ListRSSItemsActivity;
//import edu.nutri.breast_feeding_101.adapter.CircleTransform;
//import edu.nutri.breast_feeding_101.adapter.NavDrawerListAdapter;
//import edu.nutri.breast_feeding_101.model.NavDrawerItem;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.Map;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.Fragment;
//import android.app.FragmentManager;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.res.Configuration;
//import android.content.res.TypedArray;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.os.Environment;
//import android.support.v4.app.ActionBarDrawerToggle;
//import android.support.v4.widget.DrawerLayout;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.firebase.client.ChildEventListener;
//import com.firebase.client.DataSnapshot;
//import com.firebase.client.Firebase;
//import com.firebase.client.FirebaseError;
//import com.firebase.client.ValueEventListener;
//import com.google.firebase.auth.FirebaseAuth;
//
//public class MainActivity extends Activity {
//
//	boolean course2;
//	boolean course3;
//	boolean course4;
//	boolean course5;
//	boolean course6;
//
//	Fragment fragment = null;
//
//	private DrawerLayout mDrawerLayout;
//	private ListView mDrawerList;
//	private ActionBarDrawerToggle mDrawerToggle;
//
//	// nav drawer title
//	private CharSequence mDrawerTitle;
//
//	// used to store app title
//	private CharSequence mTitle;
//
//	// slide menu items
//	private String[] navMenuTitles;
//	private TypedArray navMenuIcons;
//
//	private ArrayList<NavDrawerItem> navDrawerItems;
//	private NavDrawerListAdapter adapter;
//
//	String username, email, url2, url;
//
//	String user_id;
//
//	Firebase reference2, reference1;
//	ArrayList admin_email;
//
//	String score_url, Stat_url;
//	Firebase Score_reference, Stat_reference;
//
//	String course_no, attempts, success, failed, total_score, average_score, uploaded, column_id, pre_ass_score, score22, status;
//
//	@SuppressWarnings("ResourceType")
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//
//
//		admin_email = new ArrayList( );
//		url2 = UserDetails.database_url + "Admins/";
//
//		Firebase.setAndroidContext(this);
//
//		url = UserDetails.database_url + "Version/";
//
//		reference1 = new Firebase(url);
//
//		reference2 = new Firebase(url2);
//
//		score_url = UserDetails.database_url + "Course_Scores/";
//		Score_reference = new Firebase(score_url);
//
//		Stat_url = UserDetails.database_url + "Course_Stats/";
//		Stat_reference = new Firebase(Stat_url);
//
//		if (UserDetails.checked_update==false) {
//			check_update();
//		}
//
//		user_id = UserDetails.user_id;
//
//		get_email_and_username();
//
//		mTitle = mDrawerTitle = getTitle();
//
//		String x = "nav_drawer_items";
//		// load slide menu items
//		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
//
//		navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
//
//		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
//
//		navDrawerItems = new ArrayList<NavDrawerItem>();
//
//		lock_courses();
//
//		navMenuIcons.recycle();
//
//		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
//
//		adapter = new NavDrawerListAdapter(getApplicationContext(),
//				navDrawerItems);
//		mDrawerList.setAdapter(adapter);
//
//		// enabling action bar app icon and behaving it as toggle button
//		getActionBar().setDisplayHomeAsUpEnabled(true);
//		getActionBar().setHomeButtonEnabled(true);
//
//		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
//				R.drawable.ic_drawer, //nav menu toggle icon
//				R.string.app_name, // nav drawer open - description for accessibility
//				R.string.app_name // nav drawer close - description for accessibility
//		) {
//			public void onDrawerClosed(View view) {
//				getActionBar().setTitle(mTitle);
//				// calling onPrepareOptionsMenu() to show action bar icons
//				invalidateOptionsMenu();
//			}
//
//			public void onDrawerOpened(View drawerView) {
//				getActionBar().setTitle(mDrawerTitle);
//				// calling onPrepareOptionsMenu() to hide action bar icons
//				invalidateOptionsMenu();
//			}
//		};
//		mDrawerLayout.setDrawerListener(mDrawerToggle);
//
//		if (savedInstanceState == null) {
//			// on first time display view for first nav item default
//			check_default();
//
//		}
//
//		mDrawerLayout.openDrawer(mDrawerList);
//
//		get_admins();
//		set_default_admins();
//		Check_firebase();
//	}
//
//	private void check_update() {
//
//			reference1.child("BF101").addValueEventListener(new ValueEventListener() {
//				@Override
//				public void onDataChange(DataSnapshot snapshot) {
//
//					if (snapshot.getValue() != null) {
//
//						Map map = snapshot.getValue(Map.class);
//
//						String update_info = map.get("Update").toString();
//						int update_Version_code = Integer.parseInt(map.get("Version_Code").toString());
//						float update_Version_Name = Float.parseFloat(map.get("Version_Name").toString());
//
//
//						int version_code = BuildConfig.VERSION_CODE;
//						String version_name = BuildConfig.VERSION_NAME;
//
//						if (update_Version_code > version_code){
//
//							show_update_dialog(update_info, update_Version_Name, version_name);
//
//						}
//						UserDetails.checked_update = true;
//					}
//				}
//				@Override public void onCancelled(FirebaseError error) {
//				}
//			});
//
//	}
//
//	private void show_update_dialog(String update_info, float update_Version_Name, String version_name) {
//
//		String message = "Current Version: "+version_name+"\n\n" + "New Version: "+update_Version_Name+"\n\n"+"New Features:\n"+update_info;
//
//		AlertDialog.Builder alert = new AlertDialog.Builder(this);
//		alert.setTitle("New Update Available!");
//		alert.setMessage(message);
//		alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				Toast.makeText(MainActivity.this, "update", Toast.LENGTH_SHORT).show();
//
//			}
//		});
//		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//
//			}
//		});
//		alert.show();
//
//
//	}
//
//	private void Check_firebase() {
//		Firebase connectedRef = new Firebase(UserDetails.database_url + ".info/connected");
//
//		connectedRef.addValueEventListener(new ValueEventListener() {
//			@Override
//			public void onDataChange(DataSnapshot snapshot) {
//				boolean connected = snapshot.getValue(Boolean.class);
//				if (connected) {
//
//					upload_stat_to_mysql();
//					upload_score_to_mysql();
//
////					Toast.makeText(MainActivity.this, "connected", Toast.LENGTH_SHORT).show();
//				} else {
////					Toast.makeText(MainActivity.this, "not connected", Toast.LENGTH_SHORT).show();
//				}
//			}
//			@Override
//			public void onCancelled(FirebaseError error) {
//				System.err.println("Listener was cancelled");
//			}
//		});
//	}
//
//	private void get_email_and_username() {
//		Database database = new Database(this);
//		Cursor cursor = database.getWritableDatabase().rawQuery(database.SELECT_ALL, null);
//
//		if (cursor.getCount() != 0 ) {
//			if(cursor.moveToFirst()){
//				do {
//					if(user_id.equals(cursor.getString(cursor.getColumnIndex("user_id")))){
//						UserDetails.email = cursor.getString(cursor.getColumnIndex("email"));
//						UserDetails.user_id = user_id;
//						UserDetails.username = cursor.getString(cursor.getColumnIndex("username"));
//						username = cursor.getString(cursor.getColumnIndex("username"));
//						email = cursor.getString(cursor.getColumnIndex("email"));
//					}
//
//				}while (cursor.moveToNext());
//			}}
//	}
//
//	private void set_default_admins() {
//
//		//Should be in lowercase
//
//		UserDetails.add_admin("kolaak47@yahoo.com");
//		UserDetails.add_admin("akano.adekola@gmail.com");
//
//		if (UserDetails.admin_email.contains(email.toLowerCase())){
//			UserDetails.admin = true;
//		}
//	}
//
//	private void get_admins() {
//
//		reference2.addChildEventListener(new ChildEventListener() {
//			@Override
//			public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//				Map map = dataSnapshot.getValue(Map.class);
//
//				String email2 = map.get("Email").toString().toLowerCase();
//
//				UserDetails.add_admin(email2);
//
//				if (email.toLowerCase().equals(email2)){
//					UserDetails.admin = true;
//				}
//
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
//		});
//
//	}
//
//	private void check_default() {
//
//
//		SQLiteDatabase newDB;
//		SQLiteDatabase newDB2;
//
//		Details_database Details_database = new Details_database(this.getApplicationContext());
//		Database Database = new Database(this.getApplicationContext());
//
//		newDB = Details_database.getWritableDatabase();
//		newDB2 = Database.getWritableDatabase();
//
//		Cursor c = newDB.rawQuery(Details_database.SELECT_ALL_2, null);
//		//Cursor c2 = newDB2.rawQuery(Database.SELECT_ALL, null);
//		int x = 0;
//		if (c.getCount() != 0 ) {
//			if  (c.moveToFirst()) {
//
//				do {
//					if (user_id.equals(c.getString(c.getColumnIndex("user_id"))))
//					{
//						displayView(9);
//						//fragment = new Details(user_id);
//						x++;
//					}
//
//
//				}while (c.moveToNext());
//			}}
//
//		if(x==0){
//			displayView(1);
//		}
//
//	}
//
//	@SuppressWarnings("ResourceType")
//	private void lock_courses() {
//
//		File mypath =new File(Environment.getExternalStorageDirectory()+ File.separator +"Breast_feeding_101/", email+".jpg");
//		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], R.drawable.breast_feeding ));
//		// Find People
//		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1), false, "2552"));
//		// Photos
//		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(3, -1), false, "22"));
//
//		check_course1_db();
//		check_course2_db();
//		check_course3_db();
//		check_course4_db();
//		check_course5_db();
//
//		navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], navMenuIcons.getResourceId(9, -1)));
//		navDrawerItems.add(new NavDrawerItem(navMenuTitles[9], navMenuIcons.getResourceId(10, -1)));
//		navDrawerItems.add(new NavDrawerItem(navMenuTitles[10], navMenuIcons.getResourceId(11, -1)));
//		navDrawerItems.add(new NavDrawerItem(navMenuTitles[11], navMenuIcons.getResourceId(12, -1)));
//		navDrawerItems.add(new NavDrawerItem(navMenuTitles[12], navMenuIcons.getResourceId(13, -1)));
//		navDrawerItems.add(new NavDrawerItem(navMenuTitles[13], navMenuIcons.getResourceId(14, -1)));
//
//	}
//	@SuppressWarnings("ResourceType")
//	private void check_course1_db() {
//
//		SQLiteDatabase newDB;
//
//		Course_stats_db Course_stats_db  = new Course_stats_db(this.getApplicationContext());
//		newDB = Course_stats_db.getWritableDatabase();
//		Cursor c = newDB.rawQuery(Course_stats_db.select_all(), null);
//
//		int x = 0;
//		if (c.getCount() != 0 ) {
//			if  (c.moveToFirst()) {
//
//				do {
//					if ( user_id.equals(c.getString(c.getColumnIndex("user_id"))) && c.getString(c.getColumnIndex("course_no")).equals("1") )
//					{
////						Toast.makeText(this, "found 1", Toast.LENGTH_SHORT).show();
//						int course1_success= Integer.parseInt(c.getString(c.getColumnIndex("success")));
//						if(course1_success>0){
//							navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(4, -1), false, "22"));
//							course2=false;
//							x++;
//							break;
//						}
//
//					}
//				}while (c.moveToNext());
//			}}
//
//		if(x==0){
//			navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(4, -1), true, "22"));
//			course2=true;
//		}
//		newDB.close();
//	}
//	@SuppressWarnings("ResourceType")
//	private void check_course2_db() {
//
//		SQLiteDatabase newDB;
//
//		Course_stats_db Course_stats_db  = new Course_stats_db(this.getApplicationContext());
//		newDB = Course_stats_db.getWritableDatabase();
//		Cursor c = newDB.rawQuery(Course_stats_db.select_all(), null);
//
//		int x = 0;
//		if (c.getCount() != 0 ) {
//			if  (c.moveToFirst()) {
//
//				do {
//					if (user_id.equals(c.getString(c.getColumnIndex("user_id")))  && c.getString(c.getColumnIndex("course_no")).equals("2") )
//					{
////						Toast.makeText(this, "found 2", Toast.LENGTH_SHORT).show();
//						int course2_success= Integer.parseInt(c.getString(c.getColumnIndex("success")));
//						if(course2_success>0){
//							navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(5, -1), false, "22"));
//							course3=false;
//							x++;
//							break;
//						}
//					}
//				}while (c.moveToNext());
//			}}
//
//		if(x==0){
//			navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(5, -1), true, "22"));
//			course3=true;
//		}
//		newDB.close();
//	}
//	@SuppressWarnings("ResourceType")
//	private void check_course3_db() {
//
//		SQLiteDatabase newDB;
//
//		Course_stats_db Course_stats_db  = new Course_stats_db(this.getApplicationContext());
//		newDB = Course_stats_db.getWritableDatabase();
//		Cursor c = newDB.rawQuery(Course_stats_db.select_all(), null);
//
//		int x = 0;
//		if (c.getCount() != 0 ) {
//			if  (c.moveToFirst()) {
//
//				do {
//					if (user_id.equals(c.getString(c.getColumnIndex("user_id")))  && c.getString(c.getColumnIndex("course_no")).equals("3") )
//					{
////						Toast.makeText(this, "found 3", Toast.LENGTH_SHORT).show();
//						int course3_success= Integer.parseInt(c.getString(c.getColumnIndex("success")));
//						if(course3_success>0){
//							navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(6, -1), false, "50+"));
//							course4=false;
//							x++;
//							break;
//						}
//					}
//				}while (c.moveToNext());
//			}}
//
//		if(x==0){
//			navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(6, -1), true, "50+"));
//			course4=true;
//		}
//		newDB.close();
//	}
//	@SuppressWarnings("ResourceType")
//	private void check_course4_db() {
//
//		SQLiteDatabase newDB;
//
//		Course_stats_db Course_stats_db  = new Course_stats_db(this.getApplicationContext());
//		newDB = Course_stats_db.getWritableDatabase();
//		Cursor c = newDB.rawQuery(Course_stats_db.select_all(), null);
//
//		int x = 0;
//		if (c.getCount() != 0 ) {
//			if  (c.moveToFirst()) {
//
//				do {
//					if (user_id.equals(c.getString(c.getColumnIndex("user_id")))  && c.getString(c.getColumnIndex("course_no")).equals("4") )
//					{
////						Toast.makeText(this, "found 4", Toast.LENGTH_SHORT).show();
//						int course4_success= Integer.parseInt(c.getString(c.getColumnIndex("success")));
//						if(course4_success>0){
//							navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(7, -1), false, "22"));
//							course5=false;
//							x++;
//							break;
//						}
//					}
//				}while (c.moveToNext());
//			}}
//
//		if(x==0){
//			navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(7, -1), true, "22"));
//			course5=true;
//		}
//		newDB.close();
//	}
//	@SuppressWarnings("ResourceType")
//	private void check_course5_db() {
//
//		SQLiteDatabase newDB;
//
//		Course_stats_db Course_stats_db  = new Course_stats_db(this.getApplicationContext());
//		newDB = Course_stats_db.getWritableDatabase();
//		Cursor c = newDB.rawQuery(Course_stats_db.select_all(), null);
//
//		int x = 0;
//		if (c.getCount() != 0 ) {
//			if  (c.moveToFirst()) {
//
//				do {
//					if (user_id.equals(c.getString(c.getColumnIndex("user_id"))) && c.getString(c.getColumnIndex("course_no")).equals("5") )
//					{
//						int course5_success= Integer.parseInt(c.getString(c.getColumnIndex("success")));
//						if(course5_success>0){
//							navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(8, -1), false, "22"));
//							course6=false;
//							x++;
//							break;
//						}
//					}
//				}while (c.moveToNext());
//			}}
//
//		if(x==0){
//			navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(8, -1), true, "22"));
//			course6=true;
//		}
//		newDB.close();
//	}
//
//
//
//	/**
//	 * Slide menu item click listener
//	 * */
//	private class SlideMenuClickListener implements
//			ListView.OnItemClickListener {
//		@Override
//		public void onItemClick(AdapterView<?> parent, View view, int position,
//								long id) {
//			// display view for selected nav drawer item
//			displayView(position);
//		}
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// toggle nav drawer on selecting action bar app icon/title
//		if (mDrawerToggle.onOptionsItemSelected(item)) {
//			return true;
//		}
//		// Handle action bar actions click
//		switch (item.getItemId()) {
//			case R.id.action_settings:
//
//				return true;
//			default:
//				return super.onOptionsItemSelected(item);
//		}
//	}
//
//	/* *
//	 * Called when invalidateOptionsMenu() is triggered
//	 */
//	@Override
//	public boolean onPrepareOptionsMenu(Menu menu) {
//		// if nav drawer is opened, hide the action items
//		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
//		return super.onPrepareOptionsMenu(menu);
//	}
//
//	/**
//	 * Diplaying fragment view for selected nav drawer list item
//	 * */
//	private void displayView(int position) {
//
//		switch (position) {
//			case 0:
////				fragment = new HomeFragment();
//				details();
//				break;
//			case 1:
//				details();
//				break;
//			case 2:
//				fragment = new Course1(user_id);
//				break;
//			case 3:
//				if(course2==true){
//					Toast.makeText(getApplicationContext(), "Complete the previous courses", Toast.LENGTH_SHORT).show();
////					fragment = new Statistics(user_id);
//				}
//				else{
//					fragment = new Course2(user_id);
//				}
//				break;
//			case 4:
//				if(course3==true){
//					Toast.makeText(getApplicationContext(), "Complete the previous courses", Toast.LENGTH_SHORT).show();
////					fragment = new Statistics(user_id);
//				}
//				else{
//					fragment = new Course3(user_id);
//				}
//				break;
//			case 5:
//				if(course4==true){
//					Toast.makeText(getApplicationContext(), "Complete the previous courses", Toast.LENGTH_SHORT).show();
//				}
//				else{
//					fragment = new Course4(user_id);
//				}
//				break;
//
//			case 6:
//				if(course5==true){
//					Toast.makeText(getApplicationContext(), "Complete the previous courses", Toast.LENGTH_SHORT).show();
//
//				}
//				else{
//					fragment = new Course5(user_id);
//				}
//				break;
//			case 7:
//				if(course6==true){
//					Toast.makeText(getApplicationContext(), "Complete the previous courses", Toast.LENGTH_SHORT).show();
//
//				}
//				else{
//					fragment = new Course6(user_id);
//				}
//				break;
//			case 8:
//				fragment = new Quiz_intro(user_id);
//				break;
//			case 9:
//				fragment = new Statistics(username, user_id);
//				break;
//			case 10:
//				fragment = new Community();
//				break;
//			case 11:
//
//				Intent it;
//				it = new Intent(this, ListRSSItemsActivity.class);
//				it.putExtra("user_id", user_id);
//				startActivity(it);
//
//				break;
//			case 12:
//				fragment = new Contact_us();
//				break;
//			case 13:
//
//				FirebaseAuth.getInstance().signOut();
//				startActivity(new Intent(this, Login.class));
//
//				finish();
//
//				break;
//			default:
//				break;
//		}
//
//		if (fragment != null) {
//			FragmentManager fragmentManager = getFragmentManager();
//			fragmentManager.beginTransaction()
//					.replace(R.id.frame_container, fragment).commit();
//
//			mDrawerList.setItemChecked(position, true);
//			mDrawerList.setSelection(position);
//			setTitle(navMenuTitles[position]);
//			mDrawerLayout.closeDrawer(mDrawerList);
//		} else {
//			// error in creating fragment
//			Log.e("MainActivity", "Error in creating fragment");
//		}
//	}
//
//	private void make_Toast(String text ) {
//		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
//	}
//
//
//	private void details() {
//
//		SQLiteDatabase newDB;
//		SQLiteDatabase newDB2;
//
//		Details_database Details_database = new Details_database(this.getApplicationContext());
//
//		newDB = Details_database.getWritableDatabase();
//
//		Cursor c = newDB.rawQuery(Details_database.SELECT_ALL_2, null);
//		int x = 0;
//		if (c.getCount() != 0 ) {
//			if  (c.moveToFirst()) {
//
//				do {
//					if (email.equals(c.getString(c.getColumnIndex("email"))))
//					{
//
////						Toast.makeText(this, "a", Toast.LENGTH_SHORT).show();
//						fragment = new Details(email, username);
//						x++;
//					}
//
//				}while (c.moveToNext());
//			}}
//
//		if(x==0){
////			Toast.makeText(getApplicationContext(), "b", Toast.LENGTH_SHORT).show();
//			fragment = new Get_details();
//
//		}
//	}
//
//	@Override
//	public void setTitle(CharSequence title) {
//		mTitle = title;
//		getActionBar().setTitle(mTitle);
//	}
//
//	/**
//	 * When using the ActionBarDrawerToggle, you must call it during
//	 * onPostCreate() and onConfigurationChanged()...
//	 */
//
//	@Override
//	protected void onPostCreate(Bundle savedInstanceState) {
//		super.onPostCreate(savedInstanceState);
//		// Sync the toggle state after onRestoreInstanceState has occurred.
//		mDrawerToggle.syncState();
//	}
//
//	@Override
//	public void onConfigurationChanged(Configuration newConfig) {
//		super.onConfigurationChanged(newConfig);
//		// Pass any configuration change to the drawer toggls
//		mDrawerToggle.onConfigurationChanged(newConfig);
//	}
//
//	@Override
//	public void onBackPressed(){
//
//	}
//			//TODO
////	String query = "SELECT * FROM todo WHERE category='" + vg;
////
////	Cursor  cursor = database.rawQuery(query,null);
////
////	if (cursor != null) {
////		cursor.moveToFirst();
////	}
////	return cursor;
//
//	private void upload_score_to_mysql() {
//
//		Course_score_db db = new Course_score_db(getApplicationContext());
//		Cursor c = db.getWritableDatabase().rawQuery(db.select_all(), null);
//
//		if (c.getCount()!=0){
//
//			if (c.moveToFirst()){
//				do {
//					course_no = c.getString(c.getColumnIndex("course_no"));
//					pre_ass_score = c.getString(c.getColumnIndex("pre_ass_score"));
//					score22 = c.getString(c.getColumnIndex("score"));
//					status = c.getString(c.getColumnIndex("status"));
//					column_id =  c.getString(c.getColumnIndex("_id"));
//					uploaded = c.getString(c.getColumnIndex("uploaded"));
//
//					if (uploaded.equals("0")){
////						Toast.makeText(getApplicationContext(), c.getString(c.getColumnIndex("_id"))+"}  "+course_no+" "+pre_ass_score+" "+score22+" "+status, Toast.LENGTH_LONG).show();
//						upload_course_score(course_no, pre_ass_score, score22, status, column_id);
//					}
//
//				}while (c.moveToNext());
//			}
//		}
//
//	}
//
//	void upload_course_score(final String course_no2, final String pre_ass_score2, final String score2, final String status2, final String column_id2){
//
//		String hour = String.valueOf(Calendar.getInstance().get(Calendar.HOUR));
//		String minute = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
//		String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
//		String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1);
//		String day = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
//
//		String Time = hour+":"+minute+" "+day+"/"+month+"/"+year;
//		Map<String, String> map = new HashMap<String, String>();
//
//		map.put("Username", UserDetails.username);
//		map.put("Email", UserDetails.email);
//		map.put("User_id", user_id);
//		map.put("Course_no", course_no2);
//		map.put("Pre_ass_score", pre_ass_score2);
//		map.put("Score", score2);
//		map.put("Status", status2);
//		map.put("Time", Time);
//
//		score_url = UserDetails.database_url + "Course_Scores/Course_"+course_no;
//		Score_reference = new Firebase(score_url);
//
//		Score_reference.push().setValue(map, new Firebase.CompletionListener() {
//			@Override
//			public void onComplete(FirebaseError firebaseError, Firebase firebase) {
//				if (firebaseError != null) {
////					Toast.makeText(getApplicationContext(), "Data could not be saved", Toast.LENGTH_SHORT).show();
//				} else {
////					Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
//
//					Course_score_db db = new Course_score_db(getApplicationContext());
//					db.update_uploaded(column_id2);
//				}
//			}
//		});
//	}
//
//
//	private void upload_stat_to_mysql() {
//
////		upload_course_stat();
//		Course_stats_db db = new Course_stats_db(getApplicationContext());
//		Cursor c = db.getWritableDatabase().rawQuery(db.select_all(), null);
//
//		if (c.getCount()!=0){
//			if (c.moveToFirst()){
//				do {
//
//					course_no = c.getString(c.getColumnIndex("course_no"));
//					attempts = c.getString(c.getColumnIndex("attempts"));
//					success = c.getString(c.getColumnIndex("success"));
//					failed = c.getString(c.getColumnIndex("failed"));
//					total_score = c.getString(c.getColumnIndex("total_score"));
//					average_score = c.getString(c.getColumnIndex("average_score"));
//					uploaded = c.getString(c.getColumnIndex("uploaded"));
//					column_id = c.getString(c.getColumnIndex("_id"));
//
//					if (uploaded.equals("0")){
//						upload_course_stat(course_no, attempts, success, failed, total_score, average_score, column_id);
//					}
//
//				}while (c.moveToNext());
//			}
//		}
//
//	}
//
//	void upload_course_stat(final String course_no, final String attempts, final String success, final String failed, final String total_score, final String average_score, final String column_id){
//
//		String hour = String.valueOf(Calendar.getInstance().get(Calendar.HOUR));
//		String minute = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
//		String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
//		String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1);
//		String day = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
//
//		String Time = hour+":"+minute+" "+day+"/"+month+"/"+year;
//		Map<String, String> map = new HashMap<String, String>();
//
//		map.put("username", UserDetails.username);
//		map.put("email", UserDetails.email);
//		map.put("user_id", user_id);
//		map.put("course_no", course_no);
//		map.put("attempts", attempts);
//		map.put("success", success);
//		map.put("failed", failed);
//		map.put("total_score", total_score);
//		map.put("average_score", average_score);
//		map.put("Time", Time);
//
//		Stat_url = UserDetails.database_url + "Course_Stats/Course_"+course_no;
//		Stat_reference = new Firebase(Stat_url);
//
//		Stat_reference.child(user_id).setValue(map, new Firebase.CompletionListener() {
//			@Override
//			public void onComplete(FirebaseError firebaseError, Firebase firebase) {
//				if (firebaseError != null) {
////					Toast.makeText(getApplicationContext(), "Data could not be saved", Toast.LENGTH_SHORT).show();
//				} else {
////					Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
//					Course_stats_db db = new Course_stats_db(getApplicationContext());
//					db.update_uploaded(column_id);
//				}
//			}
//		});
//	}
//
//}
