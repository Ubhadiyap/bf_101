package edu.nutri.breast_feeding_101.common.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import edu.nutri.breast_feeding_101.R;

/**
 * Created by ribads on 4/2/18.
 */

public class NavMenuHolder extends RecyclerView.ViewHolder {
    TextView titileTv;
    ImageView icon;
    public NavMenuHolder(View itemView) {
        super(itemView);
        titileTv = itemView.findViewById(R.id.title_tv);
        icon = itemView.findViewById(R.id.icon);

    }

    public void bindData(String title, int icon) {
        this.titileTv.setText(title);
        this.icon.setImageResource(R.drawable.help_circle);

    }
}
