package edu.nutri.breast_feeding_101;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import edu.nutri.breast_feeding_101.adapter.CircleTransform;

public class Chat extends Activity {

    UserDetails user_details = new UserDetails();

    Bitmap b;
    MenuItem closeOption;
    ImageView sendButton;
    EditText messageArea;
    LinearLayout layout;
    ScrollView scrollView;
    String Time, comment_code;
    String url;
    int comment_count;
    Firebase reference1;
//    ArrayList admin_email;
//    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ActionBar actionBar = getActionBar();

//        actionBar.setDisplayHomeAsUpEnabled(true);

        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "Breast_feeding_101" + File.separator + "Chat_Images");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }

        comment_count = 0;

        Bundle b2 = getIntent().getExtras();
        comment_code = b2.getString("comment_code");

        layout = (LinearLayout)findViewById(R.id.layout1);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);

        url = UserDetails.database_url +  "messages/"+comment_code;

        Firebase.setAndroidContext(this);
        reference1 = new Firebase(url);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_message();
            }
        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);

                String message = map.get("Message").toString();
                String userName = map.get("Name").toString();
                String time = map.get("Time").toString();
                String user_id = map.get("User_id").toString();
                String email = map.get("Email").toString().toLowerCase();

                comment_count++;

                //TODO username
                if(email.equals(user_details.get_email())){
                    addMessageBox(message, 1, time, "You", email, user_id);
                }
                else{
                    addMessageBox( message, 2, time, userName, email, user_id);
                }
                scrollView.post(new Runnable()
                {
                    public void run()
                    {
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                });
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

    private void send_message() {

        String messageText = messageArea.getText().toString();

        if(!messageText.equals("")){

            String hour = String.valueOf(Calendar.getInstance().get(Calendar.HOUR));
            String minute = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));

            String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
            String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1);
            String day = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

            Time = hour+":"+minute+" "+day+"/"+month+"/"+year;

            Map<String, String> map = new HashMap<String, String>();
            map.put("Message", messageText);
            map.put("Time", Time);
            map.put("Name", user_details.get_username());
            map.put("Email", user_details.get_email());
            map.put("User_id", user_details.get_user_id());
            reference1.push().setValue(map, new Firebase.CompletionListener() {
                @Override
                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                    if (firebaseError != null) {
                    } else {
                        String  comment_url = UserDetails.database_url + "Discussions/comments";
                        Firebase comment_ref = new Firebase(comment_url);

                        HashMap<String,Object> map2 = new HashMap<String,Object>();

                        map2.put("Comment", comment_count);

                        comment_ref.child(comment_code).setValue(map2);

//                        Toast.makeText(Chat.this, comment_count+"", Toast.LENGTH_SHORT).show();
                    }}});

            messageArea.setText("");
        }
    }

    public void addMessageBox(final String message, int type, String time, String userName, final String email, String user_id) {

        final String id = user_id;
        final String mail = email;

        TextView textView = new TextView(Chat.this);
        textView.setTextColor(Color.parseColor("#ff4141"));

        if (user_details.get_admin_email_list().contains(email)) {
            userName = userName + " (ADMIN)";
            textView.setTextColor(Color.parseColor("#ff0000"));
        }

        textView.setText(userName);
        textView.setTextSize(15);

        ImageView img = null;
        TextView textView2 = null;

        if (message.length() >= 5) {
            if (message.substring(0, 5).equals("IMAGE")) {

                img = new ImageView(this);
                img.setImageResource(R.drawable.profile_male);

                File folder = new File(Environment.getExternalStorageDirectory() +
                        File.separator + "Breast_feeding_101" + File.separator + "Chat_Images");
                boolean success = true;
                if (!folder.exists()) {
                    success = folder.mkdirs();
                }

                File output = new File(Environment.getExternalStorageDirectory() + File.separator + "Breast_feeding_101/Chat_Images/" + message + ".jpg");

                if (output.exists()){

                    img.setImageURI(Uri.fromFile(output));
                }
                else{
                    FirebaseStorage storage = FirebaseStorage.getInstance();

                    StorageReference storageRef = storage.getReference();

                    final ImageView finalImg = img;
                    storageRef.child("BF_101/"+ message +".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            DownloadManager mgr = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
                            DownloadManager.Request request = new DownloadManager.Request(
                                    uri);

                            request.setAllowedNetworkTypes(
                                    DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                                    .setAllowedOverRoaming(false).setTitle("BF 101")
                                    .setDescription("downloading......")
                                    .setDestinationInExternalPublicDir("/Breast_feeding_101", message+".jpg");

                            mgr.enqueue(request);

                            Glide.with(getApplicationContext()).load(uri)
                                    .crossFade()
                                    .thumbnail(1.0f)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(finalImg);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
//                            Toast.makeText(getApplicationContext(), "No image found", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            } else {
                textView2 = new TextView(Chat.this);
                textView2.setText(message);
                textView2.setTextSize(15);
            }
        } else {
            textView2 = new TextView(Chat.this);
            textView2.setText(message);
            textView2.setTextSize(15);
        }
        TextView textView3 = new TextView(Chat.this);
        textView3.setText(time);
        textView3.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        textView3.setTextSize(13);

        RelativeLayout.LayoutParams param4 = new RelativeLayout.LayoutParams(450, RelativeLayout.LayoutParams.WRAP_CONTENT);
        param4.setMargins(0, 0, 0, 20);

        RelativeLayout relativeLayout3 = new RelativeLayout(this);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_details.get_admin() == true) {

                    Intent it = new Intent(getApplicationContext(), Admin_user_details.class);
                    it.putExtra("user_id", id);
                    it.putExtra("email", mail);
                    startActivity(it);

                } else {
                    if (user_details.get_admin_email_list().contains(mail)) {
                        Intent it = new Intent(getApplicationContext(), Admin_user_details.class);
                        it.putExtra("user_id", id);
                        it.putExtra("email", mail);
                        startActivity(it);
                    }
                }
            }
        });


        if (type == 1) {
            ll.setBackgroundResource(R.drawable.rounded_corner1);
            param4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else {
            ll.setBackgroundResource(R.drawable.rounded_corner2);
            param4.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        }

        ll.addView(textView);

        if (message.length() >= 5) {
            if (message.substring(0, 5).equals("IMAGE")) {
                ll.addView(img);
            } else {
                ll.addView(textView2);
            }
        }

            else{
                            ll.addView(textView2);
                    }

        ll.addView(textView3);

        relativeLayout3.addView(ll, param4);

        layout.addView(relativeLayout3);

        TextView bl = new TextView(this);
        bl.setTextSize(12);
        bl.setText(" ");
        layout.addView(bl);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main4, menu);
        closeOption = menu.findItem(R.id.action_settings);
        closeOption.setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            CropImage.startPickImageActivity(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
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

                File f = new File(result.getUri().getPath());
               b = BitmapFactory.decodeFile(String.valueOf(f));
                b = Bitmap.createScaledBitmap(b, 750, 750, false);

                save_image_locally();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//				Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }
    private void save_image_locally() {

        String hour = String.valueOf(Calendar.getInstance().get(Calendar.HOUR));
        String minute = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));

        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1);
        String day = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        Time = hour+":"+minute+" "+day+"/"+month+"/"+year;

        String second = String.valueOf(Calendar.getInstance().get(Calendar.SECOND));
        String message = "IMAGE"+hour+minute+second+day+month+year;

        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "Breast_feeding_101");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }

        File output = new File(Environment.getExternalStorageDirectory() + File.separator + "Breast_feeding_101/Chat_Images/", ".nomedia");
        boolean fileCreated = false;
        try {
            fileCreated = output.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

            FileOutputStream out = null;
            try {
                out = new FileOutputStream(Environment.getExternalStorageDirectory() + File.separator + "Breast_feeding_101/Chat_Images/" + message + ".jpg");
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            b.compress(Bitmap.CompressFormat.JPEG, 100, out);

            upload_image(Time, message);

    }

    private void upload_image(final String time, final String message) {

        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReference();

        File mypath =new File(Environment.getExternalStorageDirectory()+ File.separator +"Breast_feeding_101/Chat_Images/", message+".jpg");

        Uri file = Uri.fromFile(mypath);
        StorageReference riversRef = storageRef.child("BF_101/"+file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
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

                    add_to_database(time, message);
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

    private void add_to_database(String time, String message) {


        Map<String, String> map = new HashMap<String, String>();
        map.put("Message", message);
        map.put("Time", time);
        map.put("Name", user_details.get_username());
        map.put("Email", user_details.get_email());
        map.put("User_id", user_details.get_user_id());

        reference1.push().setValue(map, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                } else {
                    String  comment_url = UserDetails.database_url + "Discussions/comments";
                    Firebase comment_ref = new Firebase(comment_url);

                    HashMap<String,Object> map2 = new HashMap<String,Object>();

                    map2.put("Comment", comment_count);

                    comment_ref.child(comment_code).setValue(map2);
                }
            }
        });
        messageArea.setText("");
    }
}