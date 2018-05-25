package edu.nutri.breast_feeding_101;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.nutri.breast_feeding_101.R;
public class Community extends Fragment {
    String user_id, email,  username;

    UserDetails user_details = new UserDetails();

	public Community(){
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.community, container, false);

        user_id = user_details.get_user_id();
        username = user_details.get_username();
        email = user_details.get_email();

        TextView b1 = (TextView)rootView.findViewById(R.id.textView34);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(getActivity(), Discussion_Room.class);
                startActivity(it);
            }
        });

        TextView b2 = (TextView)rootView.findViewById(R.id.textView35);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(getActivity(), Faqs.class);

                        startActivity(it);
            }
        });

        final TextView b3 = (TextView)rootView.findViewById(R.id.textView36);
        if (user_details.get_admin() == false){
            b3.setTextColor(Color.parseColor("#ff0000"));
        }
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user_details.get_admin() == true){
                    startActivity(new Intent(getActivity(), Admin.class));
                }
                else {
                    startActivity(new Intent(getActivity(), Admin.class));
                    Toast.makeText(getActivity(), " You're not an Admin ", Toast.LENGTH_SHORT).show();

                }

            }
        });

        return rootView;
    }
}