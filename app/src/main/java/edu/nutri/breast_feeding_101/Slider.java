package edu.nutri.breast_feeding_101;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.widget.TextView;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

//import edu.nutri.breast_feeding_101.Fragments.Home;
//import edu.nutri.breast_feeding_101.Fragments.Home;
import edu.nutri.breast_feeding_101.Fragments.Courses;
import edu.nutri.breast_feeding_101.Rss.ListRSSItemsActivity;
import edu.nutri.breast_feeding_101.adapter.CircleTransform;

public class Slider extends AppCompatActivity {
    NavigationView navigationView;
    Context cc;
    ImageView profile_image;
    private View navHeader;
    TextView txtName;
    TextView txtemail;
    LinearLayout nav_layout;

    String username, email, url2, url;

    String user_id;

    Firebase reference2, reference1;
    ArrayList admin_email;

    String score_url, Stat_url;
    Firebase Score_reference, Stat_reference;

    String course_no, attempts, success, failed, total_score, average_score, uploaded, column_id, pre_ass_score, score22, status;

    UserDetails user_details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user_details = new UserDetails();

//        Toast.makeText(Slider.this, user_details.get_username()+ " ////////////////// "+ user_details.get_user_id() + " "+ user_details.get_email(), Toast.LENGTH_SHORT).show();
        get_details();

        askForPermission();
//        requestPermissions( new String[] {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        cc = this;

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView =  findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
        profile_image = navHeader.findViewById(R.id.imageView2);
        txtName = navHeader.findViewById(R.id.name);
        txtemail =  navHeader.findViewById(R.id.email);
        nav_layout= navHeader.findViewById(R.id.nav_layout);
//        nav_layout.setBackgroundResource(R.drawable.nav_header);

        txtName.setText(username+"");
        txtemail.setText(email+"");

        Glide.with(this).load(R.drawable.kola)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profile_image);
//        navigationView.setNavigationItemSelectedListener(this);

        setUpNavigationView();

        from_main();

    }

    private void from_main() {

        admin_email = new ArrayList( );
        url2 = UserDetails.database_url + "Admins/";

        Firebase.setAndroidContext(this);

        url = UserDetails.database_url + "Version/";

        reference1 = new Firebase(url);

        reference2 = new Firebase(url2);

        score_url = UserDetails.database_url + "Course_Scores/";
        Score_reference = new Firebase(score_url);

        Stat_url = UserDetails.database_url + "Course_Stats/";
        Stat_reference = new Firebase(Stat_url);

//        if (UserDetails.checked_update==false) {
//            check_update();
//        }

        user_id = user_details.get_user_id();


//        lock_courses();

        get_admins();
        set_default_admins();
        Check_firebase();

    }
private void Check_firebase() {
		Firebase connectedRef = new Firebase(UserDetails.database_url + ".info/connected");

		connectedRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				boolean connected = snapshot.getValue(Boolean.class);
				if (connected) {

					upload_stat_to_mysql();
					upload_score_to_mysql();

//					Toast.makeText(MainActivity.this, "connected", Toast.LENGTH_SHORT).show();
				} else {
//					Toast.makeText(MainActivity.this, "not connected", Toast.LENGTH_SHORT).show();
				}
			}
			@Override
			public void onCancelled(FirebaseError error) {
				System.err.println("Listener was cancelled");
			}
		});
	}

	private void get_details() {
		email = user_details.get_email();
        user_id = user_details.get_user_id();
        username = user_details.get_username();

        Toast.makeText(this, email+" -- "+user_details.get_username()+ "-- "+user_details.get_user_id(), Toast.LENGTH_SHORT).show();
	}

	private void set_default_admins() {

		//Should be in lowercase

        user_details.set_admin_email_list("kolaak47@yahoo.com");
        user_details.set_admin_email_list("akano.adekola@gmail.com");

//		if (user_details.get_admin_email_list().contains(email.toLowerCase())){
//            user_details.set_admin(true);
//		}
	}

	private void get_admins() {

		reference2.addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String s) {
				Map map = dataSnapshot.getValue(Map.class);

				String email2 = map.get("Email").toString().toLowerCase();

                user_details.set_admin_email_list(email2);

				if (email.toLowerCase().equals(email2)){
                    user_details.set_admin(true);
				}

			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String s) {

			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {

			}

			@Override
			public void onChildMoved(DataSnapshot dataSnapshot, String s) {

			}

			@Override
			public void onCancelled(FirebaseError firebaseError) {

			}
		});

	}

    private void upload_score_to_mysql() {

        Course_score_db db = new Course_score_db(getApplicationContext());
        Cursor c = db.getWritableDatabase().rawQuery(db.select_all(), null);

        if (c.getCount()!=0){

            if (c.moveToFirst()){
                do {
                    course_no = c.getString(c.getColumnIndex("course_no"));
                    pre_ass_score = c.getString(c.getColumnIndex("pre_ass_score"));
                    score22 = c.getString(c.getColumnIndex("score"));
                    status = c.getString(c.getColumnIndex("status"));
                    column_id =  c.getString(c.getColumnIndex("_id"));
                    uploaded = c.getString(c.getColumnIndex("uploaded"));

                    if (uploaded.equals("0")){
//						Toast.makeText(getApplicationContext(), c.getString(c.getColumnIndex("_id"))+"}  "+course_no+" "+pre_ass_score+" "+score22+" "+status, Toast.LENGTH_LONG).show();
                        upload_course_score(course_no, pre_ass_score, score22, status, column_id);
                    }

                }while (c.moveToNext());
            }
        }

    }

    void upload_course_score(final String course_no2, final String pre_ass_score2, final String score2, final String status2, final String column_id2){

        String hour = String.valueOf(Calendar.getInstance().get(Calendar.HOUR));
        String minute = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1);
        String day = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        String Time = hour+":"+minute+" "+day+"/"+month+"/"+year;
        Map<String, String> map = new HashMap<String, String>();

        map.put("Username", user_details.get_username());
        map.put("Email", user_details.get_email());
        map.put("User_id", user_id);
        map.put("Course_no", course_no2);
        map.put("Pre_ass_score", pre_ass_score2);
        map.put("Score", score2);
        map.put("Status", status2);
        map.put("Time", Time);

        score_url = UserDetails.database_url + "Course_Scores/Course_"+course_no;
        Score_reference = new Firebase(score_url);

        Score_reference.push().setValue(map, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
//					Toast.makeText(getApplicationContext(), "Data could not be saved", Toast.LENGTH_SHORT).show();
                } else {
//					Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

                    Course_score_db db = new Course_score_db(getApplicationContext());
                    db.update_uploaded(column_id2);
                }
            }
        });
    }


    private void upload_stat_to_mysql() {

//		upload_course_stat();
        Course_stats_db db = new Course_stats_db(getApplicationContext());
        Cursor c = db.getWritableDatabase().rawQuery(db.select_all(), null);

        if (c.getCount()!=0){
            if (c.moveToFirst()){
                do {

                    course_no = c.getString(c.getColumnIndex("course_no"));
                    attempts = c.getString(c.getColumnIndex("attempts"));
                    success = c.getString(c.getColumnIndex("success"));
                    failed = c.getString(c.getColumnIndex("failed"));
                    total_score = c.getString(c.getColumnIndex("total_score"));
                    average_score = c.getString(c.getColumnIndex("average_score"));
                    uploaded = c.getString(c.getColumnIndex("uploaded"));
                    column_id = c.getString(c.getColumnIndex("_id"));

                    if (uploaded.equals("0")){
                        upload_course_stat(course_no, attempts, success, failed, total_score, average_score, column_id);
                    }

                }while (c.moveToNext());
            }
        }

    }

    void upload_course_stat(final String course_no, final String attempts, final String success, final String failed, final String total_score, final String average_score, final String column_id){

        String hour = String.valueOf(Calendar.getInstance().get(Calendar.HOUR));
        String minute = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1);
        String day = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        String Time = hour+":"+minute+" "+day+"/"+month+"/"+year;
        Map<String, String> map = new HashMap<String, String>();

        map.put("username", user_details.get_username());
        map.put("email", user_details.get_email());
        map.put("user_id", user_id);
        map.put("course_no", course_no);
        map.put("attempts", attempts);
        map.put("success", success);
        map.put("failed", failed);
        map.put("total_score", total_score);
        map.put("average_score", average_score);
        map.put("Time", Time);

        Stat_url = UserDetails.database_url + "Course_Stats/Course_"+course_no;
        Stat_reference = new Firebase(Stat_url);

        Stat_reference.child(user_id).setValue(map, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
//					Toast.makeText(getApplicationContext(), "Data could not be saved", Toast.LENGTH_SHORT).show();
                } else {
//					Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                    Course_stats_db db = new Course_stats_db(getApplicationContext());
                    db.update_uploaded(column_id);
                }
            }
        });
    }

    private void setUpNavigationView() {

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;

        fragment = new Courses();

        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.add(R.id.frame, fragment);
        fragmentTransaction.commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        drawer.setSelected(true);
        drawer.closeDrawer(GravityCompat.START);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = null;

                int id = menuItem.getItemId();

                if (id == R.id.nav_course_1) {
                    fragment = new Courses();

                } else if (id == R.id.nav_statistics) {
                    fragment = new Statistics();

                } else if (id == R.id.nav_community) {
                    fragment = new Community();
//                    fragmentManager.popBackStack();
                } else if (id == R.id.nav_quiz) {
                    fragment = new Quiz_intro();
                } else if (id == R.id.nav_blog) {
                    Intent it = new Intent(getApplicationContext(), ListRSSItemsActivity.class);
				startActivity(it);
                } else if (id == R.id.nav_about_us) {
                    fragment = new Contact_us();

                } else if (id == R.id.nav_share) {

                } else if (id == R.id.nav_send) {

                } else if (id == R.id.nav_logout){
                    FirebaseAuth.getInstance().signOut();
				startActivity(new Intent(getApplicationContext(), Login.class));

				finish();
                }

                if (fragment != null) {
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.frame, fragment);
                    fragmentTransaction.commit();

                    DrawerLayout drawer = findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void askForPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getApplicationContext() , android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getApplicationContext() , android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,  android.Manifest.permission.WRITE_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(this,  android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Storage access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("please confirm Storage access");//TODO put real question
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions( new String[] {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        }
                    });
                    builder.show();
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this,  new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},1);


                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }else{
//                getContact();
                Toast.makeText(this, "granted", Toast.LENGTH_SHORT).show();
            }
        }
        else{
//            getContact();
            Toast.makeText(this, "granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    getContact();
                    Toast.makeText(this, "granted", Toast.LENGTH_SHORT).show();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
//                    ToastMaster.showMessage(getActivity(),"No permission for contacts");
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}