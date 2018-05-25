package edu.nutri.breast_feeding_101;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Details_database extends SQLiteOpenHelper
{
	//DATBASE INPUTS
	private static final String DATABASE_NAME_2="login_database_2";

	static final String login_database_2 = "login_database_2";

	static final String user_id="user_id";
	static final String gender="gender";
	static final String age="age";
	static final String marital_status="marital_status";
	static final String education="education";
	static final String pregnant="pregnant";
	static final String children="children";
	static final String religion="religion";



	public String select_all(){
		String all = "SELECT  * FROM " +  login_database_2;
//		String all = "select * from "+login_database_2+" where user_id='"+ value +"'";
		return all;
	}


	static final String SELECT_ALL_2 =
			"SELECT _ID, username, user_id, gender, age, marital_status, education, pregnant, children, religion, unique_id, email " + "FROM login_database_2";
//
//static final String SELECT_ALL2 = "SELECT * FROM login_database2 where user_id = '"+log+"'";
//static final String SELECT_ALL3 = "SELECT * FROM login_database2 where user_id = '"+sign+"'";


	public Details_database(Context context)
	{
		super(context, DATABASE_NAME_2, null, 1);
	}

	public void onCreate(SQLiteDatabase DBase)
	{
		String createStmt = "CREATE TABLE "+login_database_2+
				" (_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, user_id TEXT, gender TEXT, age TEXT, marital_status TEXT, education TEXT, pregnant TEXT, children TEXT, religion TEXT, unique_id TEXT, email TEXT)";


		DBase.execSQL(createStmt);
		constructDefaultData(DBase);
	}


	public void onOpen(SQLiteDatabase DBase)
	{
		Cursor c = DBase.rawQuery(SELECT_ALL_2, null);
		if (c.getCount() == 0) // table is empty
			constructDefaultData(DBase);
		super.onOpen(DBase);
	}

	public void constructDefaultData(SQLiteDatabase DBase)
	{


	}

	public void onUpgrade(SQLiteDatabase DBase, int oldVersion, int newVersion)
	{
		android.util.Log.w("login_database_2_HELPER", "Evil method to upgrade DBase, will destroy old data");
		DBase.execSQL("DROP TABLE IF EXISTS "+login_database_2);
		onCreate(DBase);
	}
}
