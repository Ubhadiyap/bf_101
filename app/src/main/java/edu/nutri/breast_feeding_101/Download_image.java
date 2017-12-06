package edu.nutri.breast_feeding_101;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Akano on 4/3/2017.
 */

public class Download_image extends Activity{

    UserDetails user_details = new UserDetails();

    ProgressDialog progress;

    Course_stats_db Course_stats_db;

    String url2;
    Firebase Course_Stat_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_image);

        Course_stats_db  = new Course_stats_db(this.getApplicationContext());

        get_Score_and_statistic_data();

        download_image();
    }

    private void download_image() {

        progress = new ProgressDialog(this);
        progress.setMessage("Downloading Image");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.setProgress(10);
        progress.show();

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

        File output2 = new File(Environment.getExternalStorageDirectory() + File.separator + "Breast_feeding_101/", user_details.get_email()+".jpg");
        if(!output2.exists()){

            StorageReference storageRef = FirebaseStorage.getInstance().getReference();

            storageRef.child("BF_101/"+ user_details.get_email() +".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                    DownloadManager mgr = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
                    DownloadManager.Request request = new DownloadManager.Request(
                            uri);

                    request.setAllowedNetworkTypes(
                            DownloadManager.Request.NETWORK_WIFI
                                    | DownloadManager.Request.NETWORK_MOBILE)
                            .setAllowedOverRoaming(false).setTitle("Demo")
                            .setDescription("Something useful. No, really.")
                            .setDestinationInExternalPublicDir("/Breast_feeding_101", user_details.get_email()+".jpg");

                    mgr.enqueue(request);

                    Intent intent = new Intent(getApplicationContext(), Slider.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(Download_image.this, uri.getPath()+"", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Intent intent = new Intent(getApplicationContext(), Slider.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(Download_image.this, "No image found", Toast.LENGTH_SHORT).show();
                }
            });

        }
else {
            Intent intent = new Intent(getApplicationContext(), Slider.class);
            startActivity(intent);
        finish();
            Toast.makeText(this, "exists", Toast.LENGTH_SHORT).show();
        }


    }
    private void get_Score_and_statistic_data() {

        for (int x = 1; x < 7; x++) {

            url2 = UserDetails.database_url + "Course_Stats/Course_" + x;

            Course_Stat_ref = new Firebase(url2);

            Course_Stat_ref.child(user_details.get_user_id()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    if (snapshot.getValue() == null) {
                    } else {
                        Map map = snapshot.getValue(Map.class);

                        String attempts = map.get("attempts").toString();
                        String average_score = map.get("average_score").toString();
                        String course_no = map.get("course_no").toString();
                        String email = map.get("email").toString();
                        String failed = map.get("failed").toString();
                        String success = map.get("success").toString();
                        String total_score = map.get("total_score").toString();
                        String user_id = map.get("user_id").toString();
                        String username = map.get("username").toString();

                        add_to_database(attempts, average_score, course_no, email, failed, success, total_score, user_id, username);
                    }
                }

                @Override
                public void onCancelled(FirebaseError error) {
                }
            });
        }
    }

    private void add_to_database(String attempts, String average_score, String course_no, String email, String failed, String success, String total_score, String user_id, String username) {

        Course_stats_db.add_to_database(username, user_id, email, course_no, attempts, success, failed, total_score, average_score, "1");

    }

}
