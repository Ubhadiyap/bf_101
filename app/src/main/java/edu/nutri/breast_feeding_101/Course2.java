package edu.nutri.breast_feeding_101;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import edu.nutri.breast_feeding_101.R;
@SuppressLint("ValidFragment")
public class Course2 extends Fragment {
	
	public Course2(){
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.course2, container, false);
        Button start_button = (Button) rootView.findViewById(R.id.start_button);
        start_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//startActivity(new Intent(getActivity(), Course2_pre_Assessment.class));
				Intent it = new Intent(getActivity(), Course_preassesment.class);
//	    		it.putExtra("user_id", name);
				it.putExtra("course", "2");
	    		startActivity(new Intent(it));
			}
		});
        
        return rootView;
    }
}