package edu.nutri.breast_feeding_101.Rss;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.nutri.breast_feeding_101.MainActivity;
import edu.nutri.breast_feeding_101.R;

public class ListRSSItemsActivity extends ListActivity {
	
	// Progress Dialog
	private ProgressDialog pDialog;

	// Array list for list view
	ArrayList<HashMap<String, String>> rssItemList = new ArrayList<HashMap<String,String>>();

	RSSParser rssParser = new RSSParser();
	
	// button add new website
	ImageButton btnAddSite;
	
	List<RSSItem> rssItems = new ArrayList<RSSItem>();

	RSSFeed rssFeed;
	
	private static String TAG_TITLE = "title";
	private static String TAG_LINK = "link";
	private static String TAG_DESRIPTION = "description";
	private static String TAG_PUB_DATE = "pubDate";
	private static String TAG_GUID = "guid"; // not used

	String user_id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rss_item_list);
		
//		btnAddSite = (ImageButton) findViewById(R.id.btnAddSite);
//		// hiding plus button
//		btnAddSite.setVisibility(View.GONE);

		Bundle b = getIntent().getExtras();
		user_id = b .getString("user_id");


//		String rss_link = "https://bfeeding101.blogspot.com/feeds/";
		String rss_link = "https://nutritionu.wordpress.com/feed/";
		
		/**
		 * Calling a backgroung thread will loads recent articles of a website
		 * @param rss url of website
		 * */
		new loadRSSFeedItems().execute(rss_link);
		
		// selecting single ListView item
        ListView lv = getListView();
 
        // Launching new screen on Selecting Single ListItem
        lv.setOnItemClickListener(new OnItemClickListener() {
 
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Intent in = new Intent(getApplicationContext(), DisPlayWebPageActivity.class);
                
                // getting page url
                String page_url = ((TextView) view.findViewById(R.id.page_url)).getText().toString();
                in.putExtra("page_url", page_url);
                startActivity(in);
            }
        });
	}

	/**
	 * Background Async Task to get RSS Feed Items data from URL
	 * */
	class loadRSSFeedItems extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(
					ListRSSItemsActivity.this);
			pDialog.setMessage("Loading recent articles...");
			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting all recent articles and showing them in listview
		 * */
		@Override
		protected String doInBackground(String... args) {
			// rss link url
			String rss_url = args[0];
			
			// list of rss items
			rssItems = rssParser.getRSSFeedItems(rss_url);
			
			// looping through each item
			for(RSSItem item : rssItems){
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				
				// adding each child node to HashMap key => value
				map.put(TAG_TITLE, item.getTitle());
				map.put(TAG_LINK, item.getLink());
				map.put(TAG_PUB_DATE, item.getPubdate()); // If you want parse the date
				String description = item.getDescription();
				// taking only 200 chars from description
				if(description.length() > 100){
					description = description.substring(0, 97) + "..";
				}
				map.put(TAG_DESRIPTION, description);

				// adding HashList to ArrayList
				rssItemList.add(map);
			}
			
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed items into listview
					 * */
					ListAdapter adapter = new SimpleAdapter(
							ListRSSItemsActivity.this,
							rssItemList, R.layout.rss_item_list_row,
							new String[] { TAG_LINK, TAG_TITLE, TAG_PUB_DATE, TAG_DESRIPTION },
							new int[] { R.id.page_url, R.id.title, R.id.pub_date, R.id.link });
					
					// updating listview
					setListAdapter(adapter);
				}
			});
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String args) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
		}
	}

	@Override
	public void onBackPressed(){
		Intent it;
		it = new Intent(this, MainActivity.class);
//		it.putExtra("user_id", user_id);
		startActivity(it);
		finish();
	}

}
