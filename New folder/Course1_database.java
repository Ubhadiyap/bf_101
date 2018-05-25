package info.androidhive.slidingmenu;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Course1_database extends SQLiteOpenHelper
{
	//DATBASE INPUTS
	
	private static final String DATABASE_NAME1="login_database1";
	
	static final String login_database1 = "login_database1";
	
	static final String username="username";
	static final String course1_attempts="course1_attempts";
	static final String course1_success="course1_success";
	static final String course1_failed="course1_failed";
	static final String course1_total_score="course1_total_score";
	static final String course1_pre_ass_score="course1_pre_ass_score";
	static final String course1_score="course1_score";
	static final String course1_status="course1_status";
	
static final String SELECT_ALL1 = 
"SELECT _ID, username, course1_attempts, course1_success, course1_failed, course1_failed, course1_total_score, course1_pre_ass_score, course1_score, course1_status " + "FROM login_database1";
	
	public Course1_database(Context context) 
	{
		super(context, DATABASE_NAME1, null, 1);
	}
	
	public void onCreate(SQLiteDatabase DBase) 
{
       String createStmt = "CREATE TABLE "+login_database1+
" (_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, course1_attempts TEXT, course1_success TEXT, course1_failed TEXT, course1_total_score TEXT, course1_pre_ass_score TEXT, course1_score TEXT, course1_status TEXT)";
		
        DBase.execSQL(createStmt);
	    constructDefaultData(DBase);
	}
	
	
	public void onOpen(SQLiteDatabase DBase)
{
        Cursor c = DBase.rawQuery(SELECT_ALL1, null);
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
		DBase.execSQL("DROP TABLE IF EXISTS "+login_database1);
		onCreate(DBase);
	}
}	
