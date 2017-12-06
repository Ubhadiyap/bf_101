package edu.nutri.breast_feeding_101;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.nutri.breast_feeding_101.R;


/**
 * Created by Akano on 10/28/2016.
 */

public class Contact_us extends Fragment {

    String message;
    EditText editText2;

    String name;
    public Contact_us(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.contact_us, container, false);

        Button button = (Button)rootView.findViewById(R.id.button);
        editText2 = (EditText)rootView.findViewById(R.id.editText2);

        message = editText2.getText().toString();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"contactbreastfeeding101@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Feedback" );
                i.putExtra(Intent.EXTRA_TEXT, editText2.getText().toString());

                startActivity(Intent.createChooser(i, "send mail"));
            }
        });
        return rootView;
    }
}
