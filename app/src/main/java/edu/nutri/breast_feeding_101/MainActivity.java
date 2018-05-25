package edu.nutri.breast_feeding_101;

import edu.nutri.breast_feeding_101.Rss.ListRSSItemsActivity;
import edu.nutri.breast_feeding_101.adapter.CircleTransform;
import edu.nutri.breast_feeding_101.adapter.NavDrawerListAdapter;
import edu.nutri.breast_feeding_101.common.view.BaseActivity;
import edu.nutri.breast_feeding_101.model.NavDrawerItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends BaseActivity {

	boolean course2;
	boolean course3;
	boolean course4;
	boolean course5;
	boolean course6;

	Fragment fragment = null;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	String user_id;

	Firebase reference2, reference1;
	ArrayList admin_email;

	String score_url, Stat_url;
	Firebase Score_reference, Stat_reference;

	String course_no, attempts, success, failed, total_score, average_score, uploaded, column_id, pre_ass_score, score22, status;

	@SuppressWarnings("ResourceType")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		score_url = UserDetails.database_url + "Course_Scores/";
		Score_reference = new Firebase(score_url);

		Stat_url = UserDetails.database_url + "Course_Stats/";
		Stat_reference = new Firebase(Stat_url);

		user_id = UserDetails.user_id;

		mTitle = mDrawerTitle = getTitle();

		String x = "nav_drawer_items";
		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		navMenuIcons.recycle();

//		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);


		mDrawerLayout.openDrawer(mDrawerList);

	}

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

}
