package info.androidhive.slidingmenu;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Course6_database extends SQLiteOpenHelper
{
	//DATBASE INPUTS
	
	private static final String DATABASE_NAME6="login_database6";
	
	static final String login_database6 = "login_database6";
	
	static final String username="username";
	static final String course6_attempts="course6_attempts";
	static final String course6_success="course6_success";
	static final String course6_failed="course6_failed";
	static final String course6_total_score="course6_total_score"; 
	static final String course6_pre_ass_score="course6_pre_ass_score";
	static final String course6_score="course6_score";
	static final String course6_status="course6_status";
	
	static final String SELECT_ALL6 = 
	"SELECT _ID, username, course6_attempts, course6_success, course6_failed, course6_total_score, course6_pre_ass_score, course6_score, course6_status " + "FROM login_database6";

	public Course6_database(Context context) 
	{
		super(context, DATABASE_NAME6, null, 1);
	}
	
	public void onCreate(SQLiteDatabase DBase) 
{
       String createStmt = "CREATE TABLE "+login_database6+
    		   " (_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, course6_attempts TEXT, course6_success TEXT, course6_failed TEXT, course6_total_score TEXT, course6_pre_ass_score TEXT, course6_score TEXT, course6_status TEXT)";
		
        DBase.execSQL(createStmt);
	    constructDefaultData(DBase);
	}
	
	
	public void onOpen(SQLiteDatabase DBase)
{
        Cursor c = DBase.rawQuery(SELECT_ALL6, null);
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
		DBase.execSQL("DROP TABLE IF EXISTS "+login_database6);
		onCreate(DBase);
	}
}	
