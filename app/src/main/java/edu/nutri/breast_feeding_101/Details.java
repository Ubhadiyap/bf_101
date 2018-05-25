package edu.nutri.breast_feeding_101;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import edu.nutri.breast_feeding_101.adapter.CircleTransform;

import static android.app.Activity.RESULT_OK;
@SuppressLint("ValidFragment")
public class Details extends Fragment {

	FileOutputStream out = null;
	File f;
	Bitmap b;

	String user_id;
	String username, email;
	public Details(String user_id2, String username2){
		user_id=user_id2;
		username = username2;

	}
	
	String details, gender;

	RelativeLayout RelativeLayout1;
	Button button1, button13, button14;
	TextView textView0;
	//TextView textView1;
	TextView textView2;
	TextView textView3;
	TextView textView4;
	TextView textView5;
	TextView textView6;
	TextView textView7;
	TextView textView8;
	ImageView imageView3;

//	private static final String urlNavHeaderBg = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";
//	private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg";

	private static int RESULT_LOAD_IMG = 1;
	String imgDecodableString;


	View rootView;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

//		Glide.with(this).load(urlNavHeaderBg)
//				.crossFade()
//				.diskCacheS\trategy(DiskCacheStrategy.ALL)
//				.into(imgNavHeaderBg);

		// Loading profile image


        rootView = inflater.inflate(R.layout.details, container, false);
//		Toast.makeText(getActivity(), user_id+" "+ username, Toast.LENGTH_SHORT).show();

		RelativeLayout1 = (RelativeLayout)rootView.findViewById(R.id.RelativeLayout1);

		button13 = (Button)rootView.findViewById(R.id.button13);
		button13.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				upload();
			}
		});

		button14 = (Button)rootView.findViewById(R.id.button14);
		button14.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				confirm();
			}
		});

		RelativeLayout1.removeView(button13);
		RelativeLayout1.removeView(button14);

       // button1 = (Button)rootView.findViewById(R.id.button1);
		textView0 = (TextView)rootView.findViewById(R.id.username2);
		//textView1 = (TextView)rootView.findViewById(R.id.textView1);
		textView2 = (TextView)rootView.findViewById(R.id.gender);
		textView3 = (TextView)rootView.findViewById(R.id.age);
		textView4 = (TextView)rootView.findViewById(R.id.marital_status);
		textView5 = (TextView)rootView.findViewById(R.id.educational_level);
		textView6 = (TextView)rootView.findViewById(R.id.pregnancy_status);
		textView7 = (TextView)rootView.findViewById(R.id.no_of_children);
		textView8 = (TextView)rootView.findViewById(R.id.textView8);

		imageView3 = (ImageView)rootView.findViewById(R.id.imageView4);

		get_email();
		SQLiteDatabase newDB;

		Details_database Details_database = new Details_database(this.getActivity());

		newDB = Details_database.getWritableDatabase();

		Cursor c = newDB.rawQuery(Details_database.SELECT_ALL_2, null);
		int y = c.getCount();

		int x = 0;

		if (y != 0 ) {
			x=x+1;
    		if  (c.moveToFirst()) {
		
		do {
//			Toast.makeText(getActivity(), user_id+" "+ c.getString(c.getColumnIndex("user_id")), Toast.LENGTH_SHORT).show();
			// TODO
		if (user_id.equals(c.getString(c.getColumnIndex("email"))))
		{
			gender = c.getString(c.getColumnIndex("gender"));

			textView2.setText("Gender: " + c.getString(c.getColumnIndex("gender")));
			textView3.setText("Age: " + c.getString(c.getColumnIndex("age")));
			textView4.setText("Marital status: " + c.getString(c.getColumnIndex("marital_status")));
			textView5.setText("Educational level: " + c.getString(c.getColumnIndex("education")));
			textView6.setText("Pregnancy status: " + c.getString(c.getColumnIndex("pregnant")));
			textView7.setText("No of children: " + c.getString(c.getColumnIndex("children")));
			textView8.setText("Religion: " + c.getString(c.getColumnIndex("religion")));
			textView0.setText("Username: " + c.getString(c.getColumnIndex("username")));


		}
				
		}while (c.moveToNext());}}
    	
	if(x==0){
		Toast.makeText(getActivity(), "impossible", Toast.LENGTH_SHORT).show();
	}

//		File mypath2 =new File(Environment.getExternalStorageDirectory()+ File.separator +"Breast_feeding_101/", user_id+".jpg");

		File mypath =new File(Environment.getExternalStorageDirectory()+ File.separator +"Breast_feeding_101/", user_id+".jpg");

		if (!mypath.exists()){

			Toast.makeText(getActivity(), "no image", Toast.LENGTH_SHORT).show();

//			RelativeLayout1.addView(button13);
//			RelativeLayout1.addView(button14);

			if (gender.equals("Female") ){
//				Toast.makeText(getActivity(), "female", Toast.LENGTH_SHORT).show();
				Glide.with(this).load(R.drawable.profile_female)
						.crossFade()
						.thumbnail(1.0f)
						.bitmapTransform(new CircleTransform(getActivity()))
						.diskCacheStrategy(DiskCacheStrategy.ALL)
						.into(imageView3);
			}
			else {
//				Toast.makeText(getActivity(), "male", Toast.LENGTH_SHORT).show();
				Glide.with(this).load(R.drawable.profile_male)
						.crossFade()
						.thumbnail(1.0f)
						.bitmapTransform(new CircleTransform(getActivity()))
						.diskCacheStrategy(DiskCacheStrategy.ALL)
						.into(imageView3);
			}

		}
//		imageView3.setImageURI(Uri.fromFile(mypath));
			else {
			Glide.with(this).load(Uri.fromFile(mypath))
					.crossFade()
					.thumbnail(1.0f)
					.bitmapTransform(new CircleTransform(getActivity()))
					.diskCacheStrategy(DiskCacheStrategy.ALL)
					.into(imageView3);

			}

    	return rootView;
    	}

	void get_email(){
		Database db = new Database(getActivity());
		Cursor c = db.getWritableDatabase().rawQuery(db.SELECT_ALL, null);

		if (c.getCount()!=0){
			if (c.moveToFirst()){
				do {
					if (user_id.equals(c.getString(c.getColumnIndex("user_id")))){
						email = c.getString(c.getColumnIndex("email"));

					}
				}while (c.moveToNext());
			}
		}
	}

	public void upload(){
		CropImage.startPickImageActivity(getActivity());
	}
	@Override
//	@SuppressLint("NewApi")
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			Uri imageUri = CropImage.getPickImageResultUri(getActivity(), data);

			startCropImageActivity(imageUri);
		}

		if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
			CropImage.ActivityResult result = CropImage.getActivityResult(data);
			if (resultCode == RESULT_OK) {

				f = new File(result.getUri().getPath());
//				Toast.makeText(this, result.getUri().getPath()+"", Toast.LENGTH_LONG).show();
//				File sourceFile = new File(String.valueOf(f));
//				((ImageView) findViewById(R.id.imageView)).setImageURI(result.getUri());
				b = BitmapFactory.decodeFile(String.valueOf(f));
				b = Bitmap.createScaledBitmap(b, 300, 350, false);
				imageView3.setImageBitmap(b);

				b = Bitmap.createScaledBitmap(b, 600, 700, false);

//				ByteArrayOutputStream stream = new ByteArrayOutputStream();
//				b.compress(Bitmap.CompressFormat.JPEG, 0, stream);
//
//				getBytes = stream.toByteArray();

//				show_snackbar("Cropping successful, Sample: " + result.getSampleSize());
//				Toast.makeText(this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG).show();
			} else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//				show_snackbar("Cropping failed: " + result.getError());
//				Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
			}
		}
	}

	private void startCropImageActivity(Uri imageUri) {
		CropImage.activity(imageUri)
				.setGuidelines(CropImageView.Guidelines.ON)
				.setMultiTouchEnabled(true)
				.start(getActivity());
	}
//	@Override
////	@SuppressLint("NewApi")
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//		if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
//			Uri imageUri = CropImage.getPickImageResultUri(getActivity(), data);
//
//			startCropImageActivity(imageUri);
//		}
//
//		if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//			CropImage.ActivityResult result = CropImage.getActivityResult(data);
//			if (resultCode == RESULT_OK) {
//				Toast.makeText(getActivity(), "yesss", Toast.LENGTH_SHORT).show();
//				f = new File(result.getUri().getPath());
////				Toast.makeText(this, result.getUri().getPath()+"", Toast.LENGTH_LONG).show();
////				File sourceFile = new File(String.valueOf(f));
////				((ImageView) findViewById(R.id.imageView)).setImageURI(result.getUri());
//				b = BitmapFactory.decodeFile(String.valueOf(f));
//				b = Bitmap.createScaledBitmap(b, 300, 350, false);
//				imageView3.setImageBitmap(b);
//
//				RelativeLayout1.addView(button14);
//
//				b = Bitmap.createScaledBitmap(b, 600, 700, false);
//
////				ByteArrayOutputStream stream = new ByteArrayOutputStream();
////				b.compress(Bitmap.CompressFormat.JPEG, 0, stream);
////
////				getBytes = stream.toByteArray();
//
////				show_snackbar("Cropping successful, Sample: " + result.getSampleSize());
////				Toast.makeText(this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG).show();
//			} else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//				Toast.makeText(getActivity(), result.getError()+"yesss", Toast.LENGTH_SHORT).show();
////				show_snackbar("Cropping failed: " + result.getError());
////				Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
//			}
//			else{
//				Toast.makeText(getActivity(), "aaa", Toast.LENGTH_SHORT).show();
//			}
//		}else{
//			Toast.makeText(getActivity(), "bbbb", Toast.LENGTH_SHORT).show();
//		}
//	}
//
//	private void startCropImageActivity(Uri imageUri) {
//		CropImage.activity(imageUri)
//				.setGuidelines(CropImageView.Guidelines.ON)
//				.setMultiTouchEnabled(true)
//				.start(getActivity());
//	}


	public void confirm() {

		File folder = new File(Environment.getExternalStorageDirectory() +
				File.separator + "Breast_feeding_101");
		boolean success = true;
		if (!folder.exists()) {
			success = folder.mkdirs();
		}
		if (success) {
			// Do something on success
		} else {
			// Do something else on failure
		}

		File output = new File(Environment.getExternalStorageDirectory()+File.separator +"Breast_feeding_101/", ".nomedia");
		boolean fileCreated = false;
		try {
			fileCreated = output.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}


		try {
			out = new FileOutputStream(Environment.getExternalStorageDirectory()+ File.separator +"Breast_feeding_101/"+ email+".jpg");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		if(imageView3.getDrawable()!=null) {

			b.compress(Bitmap.CompressFormat.JPEG, 100, out);

			Intent it = new Intent(getActivity(), MainActivity.class);
//			it.putExtra("user_id", user_id);
			startActivity(it);
//			finish();

		}


	}

	}
