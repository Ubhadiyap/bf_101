package edu.nutri.breast_feeding_101;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


public class Update_details extends Activity {

	UserDetails user_details = new UserDetails();

	FileOutputStream out = null;

	long totalSize = 0;
	private static final String TAG = Update_details.class.getSimpleName();

	ImageView imageView;
	private Uri mCropImageUri;

	private static int RESULT_LOAD_IMG = 1;
	String imgDecodableString;

	File f;
	Bitmap b;

	String user_id, email, password;
	String age;
	String marital_status;
	String no_of_children;
	String level_of_educational;
	String religion;
	String pregnancy_status;
	String gender;

	TextView username_ed;

	RadioButton male_rb;
	RadioButton female_rb;

	CheckBox pregnant_cb;


	Spinner age_spinner;
	Spinner marital_spinner;
	Spinner children_spinner;
	Spinner education_spinner;
	Spinner religion_spinner;

	String username, url;

	ProgressDialog pDialog;

	byte[] getBytes;
	Firebase reference1;

	@Override
    protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_details);

		Firebase.setAndroidContext(this);

		url = UserDetails.database_url + "Users/";

		reference1 = new Firebase(url);

		getBytes = null;
		pDialog = new ProgressDialog(this);

		imageView = (ImageView)findViewById(R.id.imageView);

		user_id = user_details.get_user_id();
		username = user_details.get_username();
		email = user_details.get_email();

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				update();

			}
		});

        username_ed = (TextView)findViewById(R.id.username_ed);
		male_rb = (RadioButton)findViewById(R.id.male_rb);
		female_rb = (RadioButton)findViewById(R.id.female_rb);
		pregnant_cb = (CheckBox)findViewById(R.id.pregnant_cb);

		radiogroup();
		populate_spinner();

    			username_ed.setText("Username: " + username);

    }

	public void radiogroup() {
		male_rb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				female_rb.setChecked(false);
				pregnant_cb.setTextColor(Color.parseColor("#cccccc"));
				pregnant_cb.setEnabled(false);
				pregnant_cb.setChecked(false);
			}
		});

		female_rb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				male_rb.setChecked(false);

				pregnant_cb.setTextColor(Color.parseColor("#e35e4c"));
				pregnant_cb.setEnabled(true);
				//pregnant_cb.setChecked(true);
			}
		});
	}

	public void populate_spinner() {

		age_spinner = (Spinner) findViewById(R.id.age_sp);
		marital_spinner = (Spinner) findViewById(R.id.marital_sp);
		education_spinner = (Spinner) findViewById(R.id.education_sp);
		children_spinner = (Spinner) findViewById(R.id.children_sp);
		religion_spinner = (Spinner) findViewById(R.id.religion_sp);

		List<String> list1 = new ArrayList<String>();
		list1.add("Age");
		list1.add("Below 20 yrs");
		list1.add("20-25yrs");
		list1.add("26-30yrs");
		list1.add("31-35yrs");
		list1.add("36-40");
		list1.add("41-45yrs");
		list1.add("46-50yrs");
		list1.add("above 50 yrs");
		ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
				 android.R.layout.simple_spinner_item, list1);
				 dataAdapter1.setDropDownViewResource
				 (android.R.layout.simple_spinner_dropdown_item);

		List<String> list2 = new ArrayList<String>();
		list2.add("Marital Status");
		list2.add("Single");
		list2.add("Married");
		ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
				 android.R.layout.simple_spinner_item, list2);
				 dataAdapter2.setDropDownViewResource
				 (android.R.layout.simple_spinner_dropdown_item);

		List<String> list3 = new ArrayList<String>();
		list3.add("No of Children");
		list3.add("none");
		list3.add("1");
		list3.add("2");
		list3.add("3");
		list3.add("4");
		list3.add("5 & above");
		ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this,
				 android.R.layout.simple_spinner_item, list3);
				 dataAdapter3.setDropDownViewResource
				 (android.R.layout.simple_spinner_dropdown_item);

		List<String> list4 = new ArrayList<String>();
		list4.add("Level Of Education");
		list4.add("Primary Education");
		list4.add("Secondary Education");
		list4.add("Tertiary Education");
		list4.add("No formal Education");
		ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this,
				 android.R.layout.simple_spinner_item, list4);
				 dataAdapter4.setDropDownViewResource
				 (android.R.layout.simple_spinner_dropdown_item);


		List<String> list5 = new ArrayList<String>();
		list5.add("Religion");
		list5.add("Islam");
		list5.add("Christianity");
		list5.add("Others");
		ArrayAdapter<String> dataAdapter5 = new ArrayAdapter<String>(this,
				 android.R.layout.simple_spinner_item, list5);
				 dataAdapter5.setDropDownViewResource
				 (android.R.layout.simple_spinner_dropdown_item);

		 age_spinner.setAdapter(dataAdapter1);
		 marital_spinner.setAdapter(dataAdapter2);
		 children_spinner.setAdapter(dataAdapter3);
		 education_spinner.setAdapter(dataAdapter4);
		 religion_spinner.setAdapter(dataAdapter5);
	}

	public void update(){
		age = age_spinner.getSelectedItem().toString();
		marital_status = marital_spinner.getSelectedItem().toString();
		no_of_children = children_spinner.getSelectedItem().toString();
		level_of_educational = education_spinner.getSelectedItem().toString();
		religion = religion_spinner.getSelectedItem().toString();

		if (user_id.equals("")){
			show_snackbar("No user_id");
		}
		else{

				if(!male_rb.isChecked()&&!female_rb.isChecked()){
					show_snackbar("Select your genger");
				}
				else{
					if(age_spinner.getSelectedItem()=="Age"){
						show_snackbar("Select your age");
					}
					else{
						if(marital_spinner.getSelectedItem()=="Marital Status"){
							show_snackbar("Select your marital status");
						}
						else{
							if(education_spinner.getSelectedItem()=="Level Of Education"){
								show_snackbar("Select your level of education");
							}
							else{
								if(children_spinner.getSelectedItem()=="No of Children"){
									show_snackbar("Select your no of children");
								}
								else{
									if(religion_spinner.getSelectedItem()=="Religion"){
										show_snackbar("Select your religion");
									}
									else{
										if (pregnant_cb.isChecked()==true){
											pregnancy_status = "Pregnant";
										}
										else{
											pregnancy_status = "Not Pregnant";
										}
										if(male_rb.isChecked()){
											gender="Male";
										}
										else {
											gender="Female";
										}
										if(imageView.getDrawable()==null){

											alert();
										}
										else{

											registerUser();
									}}}}}}}}}

	void alert(){

		new AlertDialog.Builder(this)
		.setMessage("No image selected!"+"\n"+ "Continue?")
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						registerUser();

					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				})
		.show();

	}

	private void registerUser() {

		if(imageView.getDrawable()!=null) {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			b.compress(Bitmap.CompressFormat.JPEG, 100, stream);

			getBytes = stream.toByteArray();
		}

		String hour = String.valueOf(Calendar.getInstance().get(Calendar.HOUR));
		String minute = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
		String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1);
		String day = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

		String Time = hour+":"+minute+" "+day+"/"+month+"/"+year;

		Map<String, String> map = new HashMap<String, String>();

		map.put("Name", username);
		map.put("Email", email.toLowerCase());
		map.put("User_id", user_id);
		map.put("Gender", gender);
		map.put("Age", age);
		map.put("Marital_Status", marital_status);
		map.put("Level_of_Education", level_of_educational);
		map.put("Pregnancy_Status", pregnancy_status);
		map.put("No_of_Children", no_of_children);
		map.put("Religion", religion);
		map.put("Time", Time);

		reference1.child(user_id).setValue(map, new Firebase.CompletionListener() {
			@Override
			public void onComplete(FirebaseError firebaseError, Firebase firebase) {
				if (firebaseError != null) {
					System.out.println("Data could not be saved. " + firebaseError.getMessage());
				} else {
					System.out.println("Data saved successfully.");
				}
			}
		});

		//TODO updating firebase

//		Firebase alanRef = reference1.child(user_id);
//		Map<String, Object> nickname = new HashMap<String, Object>();
//		nickname.put("Gender", "Alan The Machine");
//		alanRef.updateChildren(nickname);

		check_database();

	}

	private void check_database() {

		reference1.child(user_id).addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {

				if (snapshot.getValue()==null){
					Toast.makeText(Update_details.this, "Check your Internet and TRY AGAIN!", Toast.LENGTH_SHORT).show();
				}
				else {
					save_image_locally();
				}

			}
			@Override public void onCancelled(FirebaseError error) { }
		});
	}

	private void save_image_locally() {

		File folder = new File(Environment.getExternalStorageDirectory() +
				File.separator + "Breast_feeding_101");
		boolean success = true;
		if (!folder.exists()) {
			success = folder.mkdirs();
		}

		File output = new File(Environment.getExternalStorageDirectory() + File.separator + "Breast_feeding_101/", ".nomedia");
		boolean fileCreated = false;
		try {
			fileCreated = output.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (imageView.getDrawable() != null) {

			try {
				out = new FileOutputStream(Environment.getExternalStorageDirectory() + File.separator + "Breast_feeding_101/" + email + ".jpg");
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			b.compress(Bitmap.CompressFormat.JPEG, 100, out);
			upload_image();
		}
		else{
			//No imageview
		}
		//TODO
		// on success download_image();

	}


	private void upload_image() {

		// TODO
		// on success add_to_database();



		File mypath = new File(Environment.getExternalStorageDirectory()+ File.separator +"Breast_feeding_101/", email+".jpg");

		Uri file = Uri.fromFile(mypath);

//		FirebaseStorage storage = FirebaseStorage.getInstance();
//
//		StorageReference storageRef = storage.getReference();
//
		StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("BF_101/"+file.getLastPathSegment());

		UploadTask uploadTask = storageRef.putFile(file);

		uploadTask.addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(@NonNull Exception exception) {
				// Handle unsuccessful uploads
			}
		}).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


			}
		});

		uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
			@SuppressWarnings("VisibleForTests")

			@Override
			public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
				double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
				if(progress==100.0){
					Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
				}
			}
		}).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
				System.out.println("Upload is paused");
			}
		}).addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(@NonNull Exception exception) {
			}
		}).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
			@SuppressWarnings("VisibleForTests")
			@Override
			public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
			}
		});

	}

	public void next() {

		File folder = new File(Environment.getExternalStorageDirectory() +
				File.separator + "Breast_feeding_101");
		boolean success = true;
		if (!folder.exists()) {
			success = folder.mkdirs();
		}

		File output = new File(Environment.getExternalStorageDirectory() + File.separator + "Breast_feeding_101/", ".nomedia");
		boolean fileCreated = false;
		try {
			fileCreated = output.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (imageView.getDrawable() != null) {

			try {
				out = new FileOutputStream(Environment.getExternalStorageDirectory() + File.separator + "Breast_feeding_101/" + email + ".jpg");
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			b.compress(Bitmap.CompressFormat.JPEG, 100, out);
		}
		else{
			//No imageview
		}
	}

	private void add_to_database() {

		Details_database DBase = new Details_database(getApplicationContext());

		ContentValues values = new ContentValues();
		values.put("username", username);
		values.put("user_id", user_id);
		values.put("age", age);
		values.put("marital_status", marital_status);
		values.put("children", no_of_children);
		values.put("education", level_of_educational);
		values.put("religion", religion);
		values.put("pregnant", pregnancy_status);
		values.put("gender", gender);
		values.put("email", email);
		values.put("unique_id", user_id);
//		values.put("image", getBytes);

		DBase.getWritableDatabase().insert(Details_database.login_database_2, age, values);
	}

	public void image(View view){

		CropImage.startPickImageActivity(this);
}

	@Override
//	@SuppressLint("NewApi")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			Uri imageUri = CropImage.getPickImageResultUri(this, data);

			startCropImageActivity(imageUri);
		}

		if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
			CropImage.ActivityResult result = CropImage.getActivityResult(data);
			if (resultCode == RESULT_OK) {

			f = new File(result.getUri().getPath());
				b = BitmapFactory.decodeFile(String.valueOf(f));
				b = Bitmap.createScaledBitmap(b, 300, 350, false);
				imageView.setImageBitmap(b);

				b = Bitmap.createScaledBitmap(b, 600, 700, false);

				show_snackbar("Cropping successful, Sample: " + result.getSampleSize());
			} else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
				show_snackbar("Cropping failed: " + result.getError());
			}
		}
	}

	private void startCropImageActivity(Uri imageUri) {
		CropImage.activity(imageUri)
				.setGuidelines(CropImageView.Guidelines.ON)
				.setMultiTouchEnabled(true)
				.start(this);
	}


	public void show_snackbar(String text){

		RelativeLayout RelativeLayout1 = (RelativeLayout)findViewById(R.id.RelativeLayout1);

		Snackbar sn = Snackbar.make(RelativeLayout1, text, Snackbar.LENGTH_SHORT);

		View sb = sn.getView();

		TextView tv = (TextView)sb.findViewById(android.support.design.R.id.snackbar_text);
		tv.setTextColor(Color.parseColor("#d9ffaa"));

		sn.show();

	}

	private void showDialog() {
		if (!pDialog.isShowing())
			pDialog.show();
	}

	private void hideDialog() {
		if (pDialog.isShowing())
			pDialog.dismiss();
	}
}


