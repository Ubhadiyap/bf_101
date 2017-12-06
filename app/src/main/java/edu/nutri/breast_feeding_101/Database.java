package edu.nutri.breast_feeding_101;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper
{
	//DATBASE INPUTS
	private static final String DATABASE_NAME="login_database";

	static final String login_database = "login_database";
	
	static final String username="username";
	static final String email="email";
	static final String user_id="user_id";


//	static String sign = Sign_up.username;
//	static String log = Login.username2;

	public void update_uploaded(String column_id2) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put("uploaded", "1");

		// updating row
//        db.update(table_name, values, "user_id" + " = ?", new String[] { user_id });
		db.update("scores", values, "_id" + " = ?", new String[] { column_id2 });
		db.close();

	}

static final String SELECT_ALL = "SELECT  * FROM login_database";
	String all_scores = "SELECT  * FROM scores";


	public Database(Context context)
	{
		super(context, DATABASE_NAME, null, 1);
	}
	
	public void onCreate(SQLiteDatabase DBase) 
{
       String createStmt = "CREATE TABLE "+login_database+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, email TEXT, user_id TEXT, hint TEXT)";

	String createStmt2 = "CREATE TABLE scores (_id INTEGER PRIMARY KEY AUTOINCREMENT, score INTEGER, user_id TEXT, email TEXT, username TEXT, uploaded TEXT)";

        DBase.execSQL(createStmt);
		DBase.execSQL(createStmt2);
	}
	

	public void onOpen(SQLiteDatabase DBase)
{
        Cursor c = DBase.rawQuery(SELECT_ALL, null);
        if (c.getCount() == 0) // table is empty

        super.onOpen(DBase);
	}


	public void onUpgrade(SQLiteDatabase DBase, int oldVersion, int newVersion) 
	{
		android.util.Log.w("login_database_HELPER", "Evil method to upgrade DBase, will destroy old data");
		DBase.execSQL("DROP TABLE IF EXISTS "+login_database);
		DBase.execSQL("DROP TABLE IF EXISTS scores");
		onCreate(DBase);
	}
}