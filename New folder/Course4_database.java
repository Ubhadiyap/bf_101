package info.androidhive.slidingmenu;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Course4_database extends SQLiteOpenHelper
{
	//DATBASE INPUTS

	private static final String DATABASE_NAME4="login_database4";
	
	static final String login_database4 = "login_database4";
	
	static final String username="username";
	static final String course4_attempts="course4_attempts";
	static final String course4_success="course4_success";
	static final String course4_failed="course4_failed";
	static final String course4_total_score="course4_total_score";
	static final String course4_pre_ass_score="course4_pre_ass_score";
	static final String course4_score="course4_score";
	static final String course4_status="course4_status";
static final String SELECT_ALL4 = 
"SELECT _ID, username, course4_attempts, course4_success, course4_failed, course4_failed, course4_total_score, course4_pre_ass_score, course4_score, course4_status " + "FROM login_database4";

	public Course4_database(Context context) 
	{
		super(context, DATABASE_NAME4, null, 1);
	}
	
	public void onCreate(SQLiteDatabase DBase) 
{
       String createStmt = "CREATE TABLE "+login_database4+
    		   " (_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, course4_attempts TEXT, course4_success TEXT, course4_failed TEXT, course4_total_score TEXT, course4_pre_ass_score TEXT, course4_score TEXT, course4_status TEXT)";
		
        DBase.execSQL(createStmt);
	    constructDefaultData(DBase);
	}
	
	
	public void onOpen(SQLiteDatabase DBase)
{
        Cursor c = DBase.rawQuery(SELECT_ALL4, null);
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
		DBase.execSQL("DROP TABLE IF EXISTS "+login_database4);
		onCreate(DBase);
	}
}	
