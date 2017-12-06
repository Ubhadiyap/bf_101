//package edu.nutri.breast_feeding_101.Fragments;
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.graphics.Rect;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.design.widget.AppBarLayout;
//import android.support.design.widget.CollapsingToolbarLayout;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.util.TypedValue;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//
//import com.bumptech.glide.Glide;
//import edu.nutri.breast_feeding_101.Album;
////import edu.nutri.breast_feeding_101.AlbumsAdapter;
//import edu.nutri.breast_feeding_101.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link Home.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link Home#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class Home extends Fragment {
//
//    EditText search_ed;
//    ImageView search;
//    RelativeLayout search_rel;
//    RelativeLayout toolbar_rel;
//
//    private RecyclerView recyclerView;
////    private AlbumsAdapter adapter;
//    private List<Album> albumList;
//
//    CollapsingToolbarLayout collapsingToolbar;
//    AppBarLayout appBarLayout;
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//    private OnFragmentInteractionListener mListener;
//
//    public Home() {
//        // Required empty public constructor
////        search_rel = null;
////        toolbar_rel = null;
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment Home.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static Home newInstance(String param1, String param2) {
//        Home fragment = new Home();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//        int x;
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View rootView = inflater.inflate(R.layout.course1, container, false);
//
////        collapsingToolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
////        appBarLayout = (AppBarLayout) rootView.findViewById(R.id.appbar);
////
////        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
////        toolbar.setTitle(null);
////
////        x = 0;
////
////        search = rootView.findViewById(R.id.search);
////        search_ed = rootView.findViewById(R.id.search_ed);
////        search_rel = rootView.findViewById(R.id.search_rel);
////        toolbar_rel = rootView.findViewById(R.id.toolbar_rel);
////
////        toolbar_rel.removeView(search_rel);
////
////        search.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                toggle_search_ed();
////            }
////        });
////        initCollapsingToolbar();
////
////        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
////
////        albumList = new ArrayList<>();
////        adapter = new AlbumsAdapter(getActivity(), albumList);
////
////        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
////        recyclerView.setLayoutManager(mLayoutManager);
////        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
////        recyclerView.setItemAnimator(new DefaultItemAnimator());
////        recyclerView.setAdapter(adapter);
////
////        prepareAlbums();
////
////        try {
////            Glide.with(this).load(R.drawable.cover2).into((ImageView) rootView.findViewById(R.id.backdrop));
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
//        return rootView;
//
//    }
//
//    private void toggle_search_ed() {
//        search_ed.setText("");
//if (x==0) {
//    toolbar_rel.addView(search_rel);
//    x = 1;
//}
//else{
//    toolbar_rel.removeView(search_rel);
//    x = 0;
//}
//    }
//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    onAttach
//
//    private void initCollapsingToolbar() {
//        collapsingToolbar.setTitle(" ");
//        appBarLayout.setExpanded(true);
//
//        // hiding & showing the title when toolbar expanded & collapsed
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            boolean isShow = false;
//            int scrollRange = -1;
//
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (scrollRange == -1) {
//                    scrollRange = appBarLayout.getTotalScrollRange();
//                }
//                if (scrollRange + verticalOffset == 0) {
//                    collapsingToolbar.setTitle(getString(R.string.app_name));
//                    isShow = true;
//                } else if (isShow) {
//                    collapsingToolbar.setTitle(" ");
//                    isShow = false;
//                }
//            }
//        });
//    }
//
//    /**
//     * Adding few albums for testing
//     */
//    private void prepareAlbums() {
//        int[] covers = new int[]{
//                R.drawable.clutch_football,
//                R.drawable.clutch_football,
//                R.drawable.clutch_football,
//                R.drawable.clutch_football,
//                R.drawable.clutch_football,
//                R.drawable.clutch_football,
//                R.drawable.clutch_football,
//                R.drawable.clutch_football,
//                R.drawable.clutch_football,
//                R.drawable.clutch_football,
//                R.drawable.clutch_football,
//                R.drawable.clutch_football,
//                R.drawable.clutch_football,
//                R.drawable.clutch_football,
//                R.drawable.clutch_football,
//                R.drawable.clutch_football,
//                R.drawable.clutch_football,
//                R.drawable.clutch_football,
//                R.drawable.clutch_football,
//                R.drawable.clutch_football,
//                R.drawable.clutch_football,
//                R.drawable.clutch_football,
//                R.drawable.clutch_football};
//
////        File output = new File(Environment.getExternalStorageDirectory() + File.separator + "Jumbo_Guild/Chat_Images/" + "kola" + ".jpg");
//        Album a = new Album("Oranges", "N30 per piece", covers[0], "Bodija market - Oyo");
//        albumList.add(a);
//
//        a = new Album("Grand oil", "N350 @ l litre", covers[1], "Bodija market - Oyo");
//        albumList.add(a);
//
//        a = new Album("Stallion rice", "N23.5k per bag", covers[2], "Bodija market - Oyo");
//        albumList.add(a);
//
//        a = new Album("Carrots", "N5 per stick", covers[3], "Bodija market - Oyo");
//        albumList.add(a);
//
//        a = new Album("Coconut oil", "N400 - 1.5L", covers[4], "Bodija market - Oyo");
//        albumList.add(a);
//
//        a = new Album("Dangote rice", "N22k per bag", covers[5], "Bodija market - Oyo");
//        albumList.add(a);
//
//        a = new Album("Grand oil", "N550 - 2L", covers[6], "Bodija market - Oyo");
//        albumList.add(a);
//
//        a = new Album("Kings veg. oil", "N450 - 1.5L", covers[7], "Bodija market - Oyo");
//        albumList.add(a);
//
//        a = new Album("Kings palm oil", "N300 - 1L", covers[8], "Bodija market - Oyo");
//        albumList.add(a);
//
//        a = new Album("Lake rice", "N25k per bag", covers[9], "Bodija market - Oyo");
//        albumList.add(a);
//
//        a = new Album("local palm oil", "N400 - 75cl", covers[10], "Bodija market - Oyo");
//        albumList.add(a);
//
//        a = new Album("local veg. oil", "N920 - 75cl", covers[11], "Bodija market - Oyo");
//        albumList.add(a);
//
//        a = new Album("Mama happy rice", "N19k per bag", covers[12], "Bodija market - Oyo");
//        albumList.add(a);
//
//        a = new Album("Mama gold rice", "N21k per bag", covers[13], "Bodija market - Oyo");
//        albumList.add(a);
//
//        a = new Album("Mangoes", "N35 per piece", covers[14], "Bodija market - Oyo");
//        albumList.add(a);
//
//        a = new Album("Onions", "N2k per basket", covers[15], "Bodija market - Oyo");
//        albumList.add(a);
//
//        a = new Album("Power oil", "N650 - 1L", covers[16], "Bodija market - Oyo");
//        albumList.add(a);
//
//        a = new Album("Sunflower oil", "N900 - 2L", covers[17], "Bodija market - Oyo");
//        albumList.add(a);
//
//        a = new Album("Tomatoes", "N5k per basket", covers[18], "Bodija market - Oyo");
//        albumList.add(a);
//
//        a = new Album("Watermelon", "N350 per piece", covers[19], "Bodija market - Oyo");
//        albumList.add(a);
//
//        a = new Album("Yam", "N300 per tuber", covers[20], "Bodija market - Oyo");
//        albumList.add(a);
//
//        a = new Album("Snail", "N400 per piece", covers[21], "Bodija market - Oyo");
//        albumList.add(a);
//
//        a = new Album("local palm oil", "N1.5k - 5L", covers[22], "Bodija market - Oyo");
//        albumList.add(a);
//
//
////        adapter.notifyDataSetChanged();
//    }
//
//    /**
//     * RecyclerView item decoration - give equal margin around grid item
//     */
//    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
//
//        private int spanCount;
//        private int spacing;
//        private boolean includeEdge;
//
//        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
//            this.spanCount = spanCount;
//            this.spacing = spacing;
//            this.includeEdge = includeEdge;
//        }
//
//        @Override
//        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//            int position = parent.getChildAdapterPosition(view); // item position
//            int column = position % spanCount; // item column
//
//            if (includeEdge) {
//                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
//                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
//
//                if (position < spanCount) { // top edge
//                    outRect.top = spacing;
//                }
//                outRect.bottom = spacing; // item bottom
//            } else {
//                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
//                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
//                if (position >= spanCount) {
//                    outRect.top = spacing; // item top
//                }
//            }
//        }
//    }
//
//    /**
//     * Converting dp to pixel
//     */
//    private int dpToPx(int dp) {
//        Resources r = getResources();
//        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
//    }
//}
