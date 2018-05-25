package edu.nutri.breast_feeding_101.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.nutri.breast_feeding_101.R;
import edu.nutri.breast_feeding_101.common.adapter.holder.NavMenuHolder;
import edu.nutri.breast_feeding_101.common.listener.ClickListener;

/**
 * Created by ribads on 4/2/18.
 */

public class NavMenuAdapter extends RecyclerView.Adapter<NavMenuHolder> {

    int[] nav_drawer_icons;
    String[] nav_drawer_titles;
    ClickListener listener;

    public NavMenuAdapter(String[] nav_drawer_titles, int[] nav_drawer_icons, ClickListener listener) {
        this.nav_drawer_icons = nav_drawer_icons;
        this.nav_drawer_titles = nav_drawer_titles;
        this.listener = listener;
    }

    @Override
    public NavMenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_nav_items, parent, false);

        return new NavMenuHolder(view);
    }

    @Override
    public void onBindViewHolder(NavMenuHolder holder, int position) {

        String title = nav_drawer_titles[position];
        int icon = nav_drawer_icons[position];
        holder.bindData(title, icon);
        holder.itemView.setOnClickListener(v -> {
            listener.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return nav_drawer_icons.length;
    }
}
