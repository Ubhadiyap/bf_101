//package info.androidhive.slidingmenu;
//
//import android.app.Activity;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.os.Bundle;
//
///**
// * Created by Akano on 10/27/2016.
// */
//
//public class Session_database extends SQLiteOpenHelper
//{
//    //DATBASE INPUTS
//    private static final String DATABASE_NAME_2="Session_database";
//
//    static final String Session_database = "Session_database";
//
//    static final String user_id="user_id";
//
//    static final String SELECT_ALL_2 =
//            "SELECT _ID, user_id " + "FROM Session_database";
//
//    public Session_database(Context context)
//    {
//        super(context, DATABASE_NAME_2, null, 1);
//    }
//
//    public void onCreate(SQLiteDatabase DBase)
//    {
//        String createStmt = "CREATE TABLE "+Session_database+
//                " (_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id TEXT)";
//
//        DBase.execSQL(createStmt);
//        constructDefaultData(DBase);
//    }
//
//
//    public void onOpen(SQLiteDatabase DBase)
//    {
//        Cursor c = DBase.rawQuery(SELECT_ALL_2, null);
//        if (c.getCount() == 0) // table is empty
//            constructDefaultData(DBase);
//        super.onOpen(DBase);
//    }
//
//    public void constructDefaultData(SQLiteDatabase DBase)
//    {
//
//    }
//
//    public void onUpgrade(SQLiteDatabase DBase, int oldVersion, int newVersion)
//    {
//        android.util.Log.w("Session_database_HELPER", "Evil method to upgrade DBase, will destroy old data");
//        DBase.execSQL("DROP TABLE IF EXISTS "+Session_database);
//        onCreate(DBase);
//    }
//}
