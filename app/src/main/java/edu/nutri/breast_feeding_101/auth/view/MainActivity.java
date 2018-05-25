//package edu.nutri.breast_feeding_101.auth.view;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.view.Gravity;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//import edu.nutri.breast_feeding_101.R;
//import edu.nutri.breast_feeding_101.common.adapter.NavMenuAdapter;
//import edu.nutri.breast_feeding_101.common.listener.ClickListener;
//import edu.nutri.breast_feeding_101.common.util.LauncherUtil;
//import edu.nutri.breast_feeding_101.common.view.BaseActivity;
////import edu.nutri.breast_feeding_101.dashboard.fragment.ProfileFragment;
//
//public class MainActivity extends BaseActivity implements ClickListener<Integer> {
//
//    ImageView profileImg;
//    TextView emailTv, nameTv;
//    RecyclerView recycler;
//    DrawerLayout drawerLayout;
//    Toolbar toolbar;
//    NavMenuAdapter navMenuAdapter;
//    int frame;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        frame = R.id.frame;
//        initViews();
//    }
//
//    private void initViews() {
//
//        toolbar = findViewById(R.id.tool_bar);
//        profileImg = findViewById(R.id.profile_img);
//        emailTv = findViewById(R.id.email_tv);
//        nameTv = findViewById(R.id.name_tv);
//        recycler = findViewById(R.id.nav_menu_recycler);
//        drawerLayout = findViewById(R.id.drawer_layout);
//
//        setSupportActionBar(toolbar);
//
//        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
//                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();
//
//        //        List<String> tittleList = Arrays.asList(getResources().getStringArray(R.array.nav_drawer_titles));
////        List<int[]> iconList = Arrays.asList(getResources().getIntArray(R.array.nav_drawer_icons));
//
//        int[] iconList = getResources().getIntArray(R.array.nav_drawer_icons);
//        String[] tittleList = getResources().getStringArray(R.array.nav_drawer_titles);
////        Toast.makeText(this, iconList.length+"", Toast.LENGTH_SHORT).show();
//        navMenuAdapter = new NavMenuAdapter(tittleList, iconList, this);
//        recycler.setLayoutManager(new LinearLayoutManager(this));
//        recycler.setAdapter(navMenuAdapter);
//
//
//        LauncherUtil.launchFragment(getSupportFragmentManager(), frame, new ProfileFragment(), false);
//
//    }
//
//    @Override
//    public void onClick(Integer postion) {
//
//        LauncherUtil.launchFragment(getSupportFragmentManager(), frame, getFragment(postion), true );
//        Toast.makeText(this, postion+"", Toast.LENGTH_SHORT).show();
//        toggleDrawer();
//    }
//
//    private Fragment getFragment(Integer postion) {
//
//        switch (postion){
//
//            case 0:
//                return new ProfileFragment();
//            case 1:
//                return new ProfileFragment();
//            case 2:
//                return new ProfileFragment();
//            case 3:
//                return new ProfileFragment();
//            case 4:
//                return new ProfileFragment();
//            case 5:
//                return new ProfileFragment();
//            case 6:
//                return new ProfileFragment();
//        }
//
//        return null;
//
//    }
//
//    private void toggleDrawer() {
//        if (drawerLayout.isDrawerOpen(Gravity.START))
//            drawerLayout.closeDrawer(Gravity.START);
//    }
//
//    public void logOut(View view) {
//        logOut(this);
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (drawerLayout.isDrawerOpen(Gravity.START))
//            drawerLayout.closeDrawer(Gravity.START);
//        else
//            super.onBackPressed();
//    }
//}
