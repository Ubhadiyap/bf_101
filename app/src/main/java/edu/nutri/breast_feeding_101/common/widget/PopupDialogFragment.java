package edu.nutri.breast_feeding_101.common.widget;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.nutri.breast_feeding_101.R;


public class PopupDialogFragment extends DialogFragment {

    View.OnClickListener listener1, listener2;
    String title, message;
    Boolean status;

    public PopupDialogFragment() {
    }
    public static PopupDialogFragment newInstance(Boolean status, String title, String message) {
        PopupDialogFragment fragment = new PopupDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        args.putBoolean("status", status);
        fragment.setArguments(args);
        return fragment;
    }

    public void attachListener(View.OnClickListener listener1, View.OnClickListener listener2) {
        this.listener1 = listener1;
        this.listener2 = listener2;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            this.title = args.getString("title");
            this.message = args.getString("message");
            this.status = args.getBoolean("status");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popup_dialog, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {

        ImageView dialogImg = view.findViewById(R.id.dialog_img);
        TextView titleTv = view.findViewById(R.id.title_tv);
        TextView messageTv = view.findViewById(R.id.message_tv);
        Button actionButton = view.findViewById(R.id.action_button);
        Button positiveBtn = view.findViewById(R.id.positive_btn);
        Button negativeBtn = view.findViewById(R.id.negative_btn);
        LinearLayout confimationlayout = view.findViewById(R.id.confirmation_layout);

        int Image = status ? R.drawable.help_white_108x108 : R.drawable.help_white_108x108;
        int buttonBg = status ? R.drawable.dialog_success_button_bg : R.drawable.dialog_error_button_bg;

        dialogImg.setImageResource(Image);

        titleTv.setText(title);
        messageTv.setText(message);

        if (listener2 != null){
            actionButton.setVisibility(View.GONE);
        }
        else {
            actionButton.setBackgroundResource(buttonBg);
            confimationlayout.setVisibility(View.GONE);
        }
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener1!=null)
                    listener1.onClick(v);
                else
                    dismiss();
            }
        });
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    listener1.onClick(v);
            }
        });
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    listener2.onClick(v);
            }
        });
    }


}
