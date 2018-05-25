package info.androidhive.slidingmenu;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Course3_database extends SQLiteOpenHelper
{
	//DATBASE INPUTS
	
	private static final String DATABASE_NAME3="login_database3";
	
	static final String login_database3 = "login_database3";
	
	static final String username="username";
	static final String course3_attempts="course3_attempts";
	static final String course3_success="course3_success";
	static final String course3_failed="course3_failed";
	static final String course3_total_score="course3_total_score";
	static final String course3_pre_ass_score="course3_pre_ass_score";
	static final String course3_score="course3_score";
	static final String course3_status="course3_status";
	
static final String SELECT_ALL3 = 
"SELECT _ID, username, course3_attempts, course3_success, course3_failed, course3_failed, course3_total_score, course3_pre_ass_score, course3_score, course3_status " + "FROM login_database3";

	public Course3_database(Context context) 
	{
		super(context, DATABASE_NAME3, null, 1);
	}
	
	public void onCreate(SQLiteDatabase DBase) 
{
       String createStmt = "CREATE TABLE "+login_database3+
    		   " (_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, course3_attempts TEXT, course3_success TEXT, course3_failed TEXT, course3_total_score TEXT, course3_pre_ass_score TEXT, course3_score TEXT, course3_status TEXT)";
		
        DBase.execSQL(createStmt);
	    constructDefaultData(DBase);
	}
	
	
	public void onOpen(SQLiteDatabase DBase)
{
        Cursor c = DBase.rawQuery(SELECT_ALL3, null);
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
		DBase.execSQL("DROP TABLE IF EXISTS "+login_database3);
		onCreate(DBase);
	}
}	
