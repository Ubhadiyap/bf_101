package edu.nutri.breast_feeding_101;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import edu.nutri.breast_feeding_101.R;
@SuppressLint("ValidFragment")
public class Course6 extends Fragment {

	public Course6(){
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.course6, container, false);
        Button start_button = (Button) rootView.findViewById(R.id.start_button);
        start_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent it = new Intent(getActivity(), Course_preassesment.class);
//	    		it.putExtra("user_id", name);
				it.putExtra("course", "6");
	    		startActivity(new Intent(it));
			}
		});
        
        return rootView;
    }
}
