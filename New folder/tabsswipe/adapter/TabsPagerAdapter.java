//package info.androidhive.tabsswipe.adapter;
//
//import info.androidhive.slidingmenu.Introduction;
//import info.androidhive.slidingmenu.Pre_Assessment;
//import info.androidhive.slidingmenu.Quiz;
//import info.androidhive.slidingmenu.Summary;
//import info.androidhive.slidingmenu.Unit1;
//import info.androidhive.slidingmenu.Unit2;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//
//public class TabsPagerAdapter extends FragmentPagerAdapter {
//
//	public TabsPagerAdapter(FragmentManager fm) {
//		super(fm);
//	}
//
//	@Override
//	public Fragment getItem(int index) {
//
//		switch (index) {
//		
//		case 0:
//			// Games fragment activity
//			return new Introduction();
//		case 1:
//			// Movies fragment activity
//			return new Unit1();
//		case 2:
//			// Movies fragment activity
//			return new Unit2();
//		case 3:
//			// Movies fragment activity
//			return new Summary();
//		
//		}
//
//		return null;
//	}
//
//	@Override
//	public int getCount() {
//		// get item count - equal to number of tabs
//		return 4;
//	}
//
//}
