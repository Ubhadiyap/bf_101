//package info.androidhive.slidingmenu;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//public class Statistics_database extends SQLiteOpenHelper
//{
//	//DATBASE INPUTS
//
//	private static final String DATABASE_NAME="login_database";
//
//	static final String login_database = "login_database";
//
//	static final String user_id="user_id";
//	static final String total_points="total_points";
//	static final String average_score="average_score";
//
//	static final String SELECT_ALL =
//	"SELECT _ID, user_id, total_points, average_score " + "FROM login_database";
//
//	public Statistics_database(Context context)
//	{
//		super(context, DATABASE_NAME, null, 1);
//	}
//
//	public void onCreate(SQLiteDatabase DBase)
//{
//       String createStmt = "CREATE TABLE "+login_database+
//   " (_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id TEXT, total_points TEXT, average_score TEXT)";
//
//        DBase.execSQL(createStmt);
//	    constructDefaultData(DBase);
//	}
//
//
//	public void onOpen(SQLiteDatabase DBase)
//{
//        Cursor c = DBase.rawQuery(SELECT_ALL, null);
//        if (c.getCount() == 0) // table is empty
//            constructDefaultData(DBase);
//        super.onOpen(DBase);
//	}
//
//	public void constructDefaultData(SQLiteDatabase DBase)
//	{
////        android.util.Log.w("login_database2_HELPER", "Constructing default data!");
////
////        // DEFAULT TABLE VALUES
////        ContentValues cv = new ContentValues();
////        cv.put(user_id, "user_id =  Adekola Akano");
////        cv.put(email, "email =  1.8 m");
////        cv.put(password, "password =  65.0 kg");
////        cv.put(TIME, "TIME =  20.06173");
////        cv.put(PLACE, "PLACE =  Normal");
////        cv.put(AMOUNT, "AMOUNT =  70.308 kg");
////        cv.put(WEIGHT, "WEIGHT =  Adekola Akano");
////        cv.put(RANGE, "RANGE =  Adekola Akano");
////
////
////
////
////        DBase.insert(login_database2, null, cv);
//
//
//	}
//
//	public void onUpgrade(SQLiteDatabase DBase, int oldVersion, int newVersion)
//	{
//		android.util.Log.w("login_database_HELPER", "Evil method to upgrade DBase, will destroy old data");
//		DBase.execSQL("DROP TABLE IF EXISTS "+login_database);
//		onCreate(DBase);
//	}
//}
