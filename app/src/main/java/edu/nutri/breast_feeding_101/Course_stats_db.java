package edu.nutri.breast_feeding_101;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Course_stats_db extends SQLiteOpenHelper
{

    String table_name = "course_stat_table";
    private static final  String database_name = "course_stat_db";

    public String select_all(){
        String all = "SELECT  * FROM " +  table_name;

        return all;
    }

    public void delete(){

        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
//        for(int x = 1; x<7;x++) {

            db.delete(table_name, null, null);
//        }
        db.close();

    }

    public void update_uploaded(String column_id2) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("uploaded", "1");

        // updating row
//        db.update(table_name, values, "user_id" + " = ?", new String[] { user_id });
        db.update(table_name, values, "_id" + " = ?", new String[] { column_id2 });
        db.close();

    }

    public void update_database(String username, String user_id, String email, String course_no,
                                String attempts, String success, String failed,
                                String total_score, String average_score, String uploaded) {


				SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("user_id", user_id);
        values.put("email", email);
        values.put("course_no", course_no);
        values.put("attempts", attempts);
        values.put("success", success);
        values.put("failed", failed);
        values.put("total_score", total_score);
        values.put("average_score", average_score);
        values.put("uploaded", uploaded);

						// updating row
//        db.update(table_name, values, "user_id" + " = ?", new String[] { user_id });
        db.update(table_name, values, "course_no" + " = ?", new String[] { course_no });
        db.close();
    }

    public void add_to_database(String username, String user_id, String email, String course_no,
                                String attempts, String success, String failed,
                                String total_score, String average_score, String uploaded) {

        SQLiteDatabase DBase = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put("username", username);
        values.put("user_id", user_id);
        values.put("email", email);
        values.put("course_no", course_no);
        values.put("attempts", attempts);
        values.put("success", success);
        values.put("failed", failed);
        values.put("total_score", total_score);
        values.put("average_score", average_score);
        values.put("uploaded", uploaded);

        DBase.insert(table_name, null, values);
        DBase.close();

    }

    public Course_stats_db(Context context)
    {

        super(context, database_name, null, 1);
    }

    public void onCreate(SQLiteDatabase DBase) {
//        for (int x = 1; x < 7; x++) {

DBase.execSQL("CREATE TABLE " + table_name + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, user_id TEXT, email TEXT, course_no TEXT, attempts TEXT, success TEXT, failed TEXT, total_score TEXT, average_score TEXT, uploaded TEXT)");

//        }
    }
    public void onUpgrade(SQLiteDatabase DBase, int oldVersion, int newVersion)
    {

//        for (int x = 1; x < 7; x++) {

            DBase.execSQL("DROP TABLE IF EXISTS "+ table_name );

//        }
        onCreate(DBase);
    }

}
