package edu.nutri.breast_feeding_101;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

/**
 * Created by Akano on 12/8/2016.
 */
@SuppressLint("ValidFragment")
public class Quiz_intro extends Fragment {

    Animation blink;
    String user_id;
    public Quiz_intro(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        blink = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);

        UserDetails user_details = new UserDetails();

        user_id = user_details.get_user_id();

        View rootView = inflater.inflate(R.layout.quiz_intro, container, false);

        Button start_button = (Button) rootView.findViewById(R.id.button1);
        start_button.startAnimation(blink);
        start_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getActivity(), Course1_pre_Assessment.class));

                Intent it = new Intent(getActivity(), Quiz.class);
//                it.putExtra("user_id", user_id);
                startActivity(new Intent(it));



//                Database Database = new Database(getActivity());
//
//                SQLiteDatabase DBase = Database.getWritableDatabase();
//
//                ContentValues values = new ContentValues();
//
//                values.put("user_id", user_id);
//                values.put("score", 13*20);
//                values.put("username", UserDetails.username);
//                values.put("email", UserDetails.email);
//                values.put("uploaded", "0");
//
//                DBase.insert("scores", null, values);
//                DBase.close();
//
//                Intent it = new Intent(getActivity(), Quiz_score.class);
//                it.putExtra("score", "240");
//                it.putExtra("user_id", user_id);
//                startActivity(new Intent(it));
            }
        });

        return rootView;
    }
}
