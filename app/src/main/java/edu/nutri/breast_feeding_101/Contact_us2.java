package edu.nutri.breast_feeding_101;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import edu.nutri.breast_feeding_101.R;
/**
 * Created by Akano on 10/29/2016.
 */

public class Contact_us2 extends Activity {

    ImageView imageView3;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;

    String message;
    EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);

        Button button = (Button) findViewById(R.id.button);
        editText2 = (EditText) findViewById(R.id.editText2);

        message = editText2.getText().toString();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"contactbreastfeeding101@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                i.putExtra(Intent.EXTRA_TEXT, editText2.getText().toString());

                startActivity(Intent.createChooser(i, "send mail"));
            }
        });

//        imageView3 = (ImageView) findViewById(R.id.imageView3);
//        imageView3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Create intent to Open Image applications like Gallery, Google Photos
//                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                // Start the Intent
//                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
//            }
//        });
    }


}