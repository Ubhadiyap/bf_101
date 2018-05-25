package info.androidhive.slidingmenu;

import java.util.ArrayList;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Database_view extends ListActivity {
	private ArrayList<String> results = new ArrayList<String>();
	private static final int ADD_ID = Menu.FIRST + 1;
    private static final int CLEAR_ID = Menu.FIRST + 2;
    
	private static final int DELETE_ID = Menu.FIRST + 3; // lazy hack :)
	private SQLiteDatabase newDB;
	//private DataBaseControl DBase = null;
	
	private Cursor personCursor = null;
	private Cursor personCursor2 = null;
	
	
	String range;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		openAndQueryDatabase();
        
        displayResultList();
	}
	
private void displayResultList() {
	
    setListAdapter(new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, results));
    getListView().setTextFilterEnabled(true);
		
	}


private void openAndQueryDatabase() {
	try {
		Cursor c = null;

			Database Database = new Database(this.getApplicationContext());
			newDB = Database.getWritableDatabase();
			 c= newDB.rawQuery(Database.SELECT_ALL, null);
		
    	if (c != null ) {
    		
    		if  (c.moveToFirst()) {
    			
//				String range = c.getString(c.getColumnIndex("range"));
//				
//				TextView tView = new TextView(this);
//				tView.setTextSize(30);
//			    tView.setText(""+range);
//			    tView.setTextColor(Color.parseColor("#ff8c00"));
//			    
//			    getListView().addHeaderView(tView);
    			do {
    				//number, food, description, amount, time, place
    				String username	 = c.getString(c.getColumnIndex("username"));
    				String email = c.getString(c.getColumnIndex("email"));
    				String password = c.getString(c.getColumnIndex("password"));
    				
    				results.add("USERNAME: "+username+"\n"+"EMAIL: "+email+"\n"+"PASSWORD: "+password+"\n");
    			}while (c.moveToNext());
    		} 
    	}			
	} catch (SQLiteException se ) {
    	Log.e(getClass().getSimpleName(), "Could not create or Open the database");}
     finally {
    	if (newDB != null) 
    		newDB.close();
    }
		
	}

}