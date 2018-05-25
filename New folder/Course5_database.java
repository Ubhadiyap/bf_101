package info.androidhive.slidingmenu;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Course5_database extends SQLiteOpenHelper
{

	private static final String DATABASE_NAME5="login_database5";
	static final String login_database5 = "login_database5";
	
	static final String username="username";
	static final String course5_attempts="course5_attempts";
	static final String course5_success="course5_success";
	static final String course5_failed="course5_failed";
	static final String course5_total_score="course5_total_score";
	static final String course5_pre_ass_score="course5_pre_ass_score";
	static final String course5_score="course5_score";
	static final String course5_status="course5_status";
	
	static final String SELECT_ALL5 = 
			"SELECT _ID, username, course5_attempts, course5_success, course5_failed, course5_failed, course5_total_score, course5_pre_ass_score, course5_score, course5_status " + "FROM login_database5";
	
	public Course5_database(Context context) 
	{
		super(context, DATABASE_NAME5, null, 1);
	}
	
	public void onCreate(SQLiteDatabase DBase) 
{
       String createStmt = "CREATE TABLE "+login_database5+
" (_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, course5_attempts TEXT, course5_success TEXT, course5_failed TEXT, course5_total_score TEXT, course5_pre_ass_score TEXT, course5_score TEXT, course5_status TEXT)";
		
        DBase.execSQL(createStmt);
	    constructDefaultData(DBase);
	}
	
	
	public void onOpen(SQLiteDatabase DBase)
{
        Cursor c = DBase.rawQuery(SELECT_ALL5, null);
        if (c.getCount() == 0) // table is empty
            constructDefaultData(DBase);
        super.onOpen(DBase);
	}
	
	public void constructDefaultData(SQLiteDatabase DBase)
	{
//        android.util.Log.w("login_database2_HELPER", "Constructing default data!");
//        
//        // DEFAULT TABLE VALUES
//        ContentValues cv = new ContentValues();
//        cv.put(username, "username =  Adekola Akano");
//        cv.put(email, "email =  1.8 m");
//        cv.put(password, "password =  65.0 kg");
//        cv.put(TIME, "TIME =  20.06173");
//        cv.put(PLACE, "PLACE =  Normal");
//        cv.put(AMOUNT, "AMOUNT =  70.308 kg");
//        cv.put(WEIGHT, "WEIGHT =  Adekola Akano");
//        cv.put(RANGE, "RANGE =  Adekola Akano");
//        
//       
//        
//      
//        DBase.insert(login_database2, null, cv);

        
	}

	public void onUpgrade(SQLiteDatabase DBase, int oldVersion, int newVersion) 
	{
		android.util.Log.w("login_database2_HELPER", "Evil method to upgrade DBase, will destroy old data");
		DBase.execSQL("DROP TABLE IF EXISTS "+login_database5);
		onCreate(DBase);
	}
}	
