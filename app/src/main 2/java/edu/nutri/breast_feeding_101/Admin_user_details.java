package edu.nutri.breast_feeding_101;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import edu.nutri.breast_feeding_101.adapter.CircleTransform;

/**
 * Created by Akano on 3/29/2017.
 */

public class Admin_user_details extends Activity {

    String id, file_url, email, url;
    ProgressDialog pd;

    Firebase reference1;

    TextView username, user_email, user_id, gender, age, marital_status, level_of_education, pregnancy_status, no_of_children, religion;
    ImageView imageView3;
    UserDetails user_details = new UserDetails();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_user_details);

        imageView3 = (ImageView) findViewById(R.id.imageView4);

        url = UserDetails.database_url + "Users/";

        reference1 = new Firebase(url);

        username = (TextView)findViewById(R.id.username2);
        user_email = (TextView)findViewById(R.id.email);
        user_id = (TextView)findViewById(R.id.user_id);
        gender = (TextView)findViewById(R.id.gender);
        age = (TextView)findViewById(R.id.age);
        marital_status = (TextView)findViewById(R.id.marital_status);
        level_of_education = (TextView)findViewById(R.id.educational_level);
        pregnancy_status = (TextView)findViewById(R.id.pregnancy_status);
        no_of_children = (TextView)findViewById(R.id.no_of_children);
        religion = (TextView)findViewById(R.id.religion);

        pd = new ProgressDialog(this);

        Bundle b = getIntent().getExtras();
        id = b.getString("user_id");
        email  = b.getString("email");

        RelativeLayout RelativeLayout1 = (RelativeLayout)findViewById(R.id.RelativeLayout1);

        RelativeLayout admin_layout = (RelativeLayout)findViewById(R.id.admin_layout);
        RelativeLayout nonadmin_layout = (RelativeLayout)findViewById(R.id.nonadmin_layout);
        if (user_details.get_admin_email_list().contains(email)){
            RelativeLayout1.removeView(nonadmin_layout);
        }
        else {
            RelativeLayout1.removeView(admin_layout);
            load_details();
        }

        file_url = "http://192.168.43.162/Nutri/BF_101_upload/"+email+".jpg";

    }

    private void load_details() {

        pd.setMessage("Loading details ....");
        pd.show();

        reference1.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.getValue()==null){
                    Toast.makeText(Admin_user_details.this, "User  not found", Toast.LENGTH_SHORT).show();
                }
                else {
                    pd.dismiss();
//                    Toast.makeText(getActivity(), snapshot.getValue()+ " found", Toast.LENGTH_SHORT).show();
                    Map map = snapshot.getValue(Map.class);

                    username.setText("Username: " +map.get("Name").toString());
                    user_email.setText("Email: "+map.get("Email").toString());
                    user_id.setText("User ID: "+map.get("User_id").toString());
                    age.setText("Age: "+map.get("Age").toString());
                    marital_status.setText("Marital Status: "+ map.get("Marital_Status").toString());
                    no_of_children.setText("No. of Children: "+ map.get("No_of_Children").toString());
                    level_of_education.setText("Level of Education: " + map.get("Level_of_Education").toString());
                    religion.setText("Religion: "+map.get("Religion").toString());
                    pregnancy_status.setText("Pregnancy Status: "+ map.get("Pregnancy_Status").toString());
                    gender.setText("Gender: "+ map.get("Gender").toString());

                    set_image();

                    pd.dismiss();
                }
            }
            @Override public void onCancelled(FirebaseError error) { }
        });

    }

    private void set_image() {



        File mypath = new File(Environment.getExternalStorageDirectory() + File.separator + "Breast_feeding_101/", email + ".jpg");

        if (!mypath.exists()) {

            Toast.makeText(getApplicationContext(), "no image", Toast.LENGTH_SHORT).show();

//			RelativeLayout1.addView(button13);
//			RelativeLayout1.addView(button14);

            FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference storageRef = storage.getReference();

            storageRef.child("BF_101/"+ email +".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                    DownloadManager mgr = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
                    DownloadManager.Request request = new DownloadManager.Request(
                            uri);

                    request.setAllowedNetworkTypes(
                            DownloadManager.Request.NETWORK_WIFI
                                    | DownloadManager.Request.NETWORK_MOBILE)
                            .setAllowedOverRoaming(false).setTitle("BF 101")
                            .setDescription("downloading......")
                            .setDestinationInExternalPublicDir("/Breast_feeding_101", email+".jpg");

                    mgr.enqueue(request);

                    Glide.with(Admin_user_details.this).load(uri)
                            .crossFade()
                            .thumbnail(1.0f)
                            .bitmapTransform(new CircleTransform(Admin_user_details.this))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imageView3);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    if (gender.equals("Female")) {
                        Glide.with(Admin_user_details.this).load(R.drawable.profile_female)
                                .crossFade()
                                .thumbnail(1.0f)
                                .bitmapTransform(new CircleTransform(getApplicationContext()))
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imageView3);
                    } else {
                        Glide.with(Admin_user_details.this).load(R.drawable.profile_male)
                                .crossFade()
                                .thumbnail(1.0f)
                                .bitmapTransform(new CircleTransform(getApplicationContext()))
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imageView3);
                    }

                    Toast.makeText(Admin_user_details.this, "No image found", Toast.LENGTH_SHORT).show();
                }
            });


        }else {
            Glide.with(this).load(Uri.fromFile(mypath))
                    .crossFade()
                    .thumbnail(1.0f)
                    .bitmapTransform(new CircleTransform(this))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView3);

        }
    }
    private void Show_error() {
    }


}
