package edu.nutri.breast_feeding_101;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.firebase.auth.FirebaseAuth;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import edu.nutri.breast_feeding_101.R;

public class Sign_up extends Activity{

	UserDetails user_details = new UserDetails();

	static String username;
	String email;
	String password;
	String password2;

	private ProgressBar progressBar;

	EditText username_ed;
	EditText email_ed, hint_ed;
	EditText password_ed;
	EditText password2_ed;
	TextView login_text;

	String user_id;
	boolean connecivity;

	private FirebaseAuth auth;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up);

		connecivity = false;

		Firebase.setAndroidContext(this);

		auth = FirebaseAuth.getInstance();

//		progressBar = (ProgressBar) findViewById(R.id.progressBar);

		hint_ed = (EditText)findViewById(R.id.hint_ed);

		username_ed = (EditText)findViewById(R.id.username_ed);
//		username_ed.setText("kola");

		email_ed = (EditText)findViewById(R.id.email_ed);
//		email_ed.setText("kolaak47@yahoo.com");

		password_ed = (EditText)findViewById(R.id.password_ed);
//		password_ed.setText("kolaak47");

		password2_ed = (EditText)findViewById(R.id.password2_ed);
//		password2_ed.setText("kolaak47");

		login_text = (TextView)findViewById(R.id.login_text);

		ImageView imageView = (ImageView)findViewById(R.id.imageView);
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it = new Intent(Sign_up.this, Contact_us2.class);
				startActivity(it);
			}
		});

		Check_firebase();

	}

	public void signup(View v) {

		username = username_ed.getText().toString();
		email = email_ed.getText().toString();
		password = password_ed.getText().toString();
		password2 = password2_ed.getText().toString();

		if (username.isEmpty()) {
			username_ed.setError("Emter a username!");
		} else if (email.isEmpty() || !email.contains("@")) {
			email_ed.setError("Invalid email!");
		} else if (password.length() < 6) {
			password_ed.setError(getString(R.string.minimum_password));
		} else if (!password2.equals(password)) {
			password2_ed.setError("Passwords don't match");
		} else {

			if (connecivity == false) {
				Toast.makeText(this, "No Connection Found", Toast.LENGTH_SHORT).show();
			} else {
				String email = "";

				SQLiteDatabase newDB;

				Database Database = new Database(this.getApplicationContext());
				newDB = Database.getWritableDatabase();
				Cursor c = newDB.rawQuery(Database.SELECT_ALL, null);

				if (c.getCount() != 0) {
					if (c.moveToFirst()) {

						int x = 0;

						do {
							email = c.getString(c.getColumnIndex("email"));

							if (email.equals(email_ed.getText().toString())) {

								show_snackbar(email + " already exists");
								x++;
							}

						} while (c.moveToNext());

						if (x == 0) {
							ProgressBar prog = new ProgressBar(this);

							final AlertDialog.Builder alert = new AlertDialog.Builder(this);
							alert.setMessage("Please wait...");
							alert.setView(prog);
							prog.setVisibility(View.VISIBLE);

							final AlertDialog alertDialog = alert.show();

							auth.createUserWithEmailAndPassword(email_ed.getText().toString().trim(), password_ed.getText().toString().trim())
									.addOnCompleteListener(Sign_up.this, new OnCompleteListener<AuthResult>() {
										@Override
										public void onComplete(@NonNull Task<AuthResult> task) {

											alertDialog.dismiss();
											// If sign in fails, display a message to the user. If sign in succeeds
											// the auth state listener will be notified and logic to handle the
											// signed in user can be handled in the listener.
											if (!task.isSuccessful()) {
												show_snackbar("Authentication failed." + task.getException());
											} else {
												add_to_database();

												startActivity(new Intent(getApplicationContext(), Login.class));
//												user_id = auth.getCurrentUser().getUid();
//												user_details.set_user_id(auth.getCurrentUser().getUid());
//
											}
										}
									});
						}
					}

				} else {

					final ProgressDialog alertDialog = new ProgressDialog(this);
					alertDialog.setMessage("Please wait.....");
					alertDialog.show();

					auth.createUserWithEmailAndPassword(email_ed.getText().toString().trim(), password_ed.getText().toString().trim())
							.addOnCompleteListener(Sign_up.this, new OnCompleteListener<AuthResult>() {
								@Override
								public void onComplete(@NonNull Task<AuthResult> task) {
//								Toast.makeText(Sign_up.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

									alertDialog.dismiss();

									// If sign in fails, display a message to the user. If sign in succeeds
									// the auth state listener will be notified and logic to handle the
									// signed in user can be handled in the listener.
									if (!task.isSuccessful()) {
//									Toast.makeText(Sign_up.this, "Authentication failed." + task.getException(),
//											Toast.LENGTH_SHORT).show();

										show_snackbar("Authentication failed." + task.getException());
									} else {
										add_to_database();

										startActivity(new Intent(getApplicationContext(), Login.class));
//											startActivity(new Intent(Sign_up.this, Slider.class));
											finish();
									}
								}
							});
				}
			}
		}
	}
	private void add_to_database() {

//		add_to_sessiondb();
		user_id = auth.getCurrentUser().getUid();
		Database DBase = new Database(this);

		ContentValues values = new ContentValues();


		values.put("username", username);
		values.put("email", email_ed.getText().toString().trim());
		values.put("user_id", user_id);
		values.put("hint", hint_ed.getText().toString());

		DBase.getWritableDatabase().insert(DBase.login_database, null, values);



		Intent it = new Intent(this, Slider.class);
		startActivity(it);
		finish();

	}

	public void login(View v){
		startActivity(new Intent(this, Login.class));
		//finish();
	}
	public void reset_password(View v){
		startActivity(new Intent(this, Reset_password.class));
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main2, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Handle action bar actions click
		switch (item.getItemId()) {
			case R.id.action_close:

				Intent it = new Intent(this, Contact_us2.class);
				startActivity(it);

				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void Check_firebase() {
		Firebase connectedRef = new Firebase(UserDetails.database_url + ".info/connected");
		connectedRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				boolean connected = snapshot.getValue(Boolean.class);
				if (connected) {
					connecivity = true;
//					Toast.makeText(Sign_up.this, "connected", Toast.LENGTH_SHORT).show();
				} else {
					connecivity = false;
//					Toast.makeText(Sign_up.this, "not connected", Toast.LENGTH_SHORT).show();
				}
			}
			@Override
			public void onCancelled(FirebaseError error) {
				System.err.println("Listener was cancelled");
			}
		});
	}

	public void show_snackbar(String text){
		RelativeLayout RelativeLayout1 = (RelativeLayout)findViewById(R.id.RelativeLayout1);
		Snackbar sn = Snackbar.make(RelativeLayout1, text, Snackbar.LENGTH_SHORT);

		View v = sn.getView();

		TextView tv = (TextView)v.findViewById(android.support.design.R.id.snackbar_text);
		tv.setTextColor(Color.parseColor("#d9ffaa"));

		sn.show();
	}


	@Override
	public void onBackPressed(){

	}


}
