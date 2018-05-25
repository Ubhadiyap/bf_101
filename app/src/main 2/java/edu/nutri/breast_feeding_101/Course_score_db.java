package edu.nutri.breast_feeding_101;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class Course_score_db extends SQLiteOpenHelper
{

    String table_name = "course_score_table";
    private static final String database_name = "course_score_db";

    public String select_all(){
        String all = "SELECT  * FROM " +  table_name;
        return all;
    }

    public void delete(){

        SQLiteDatabase db = this.getWritableDatabase();

            db.delete(table_name, null, null);

        db.close();

    }

    public void update_uploaded(String column_id2) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("uploaded", "1");

        db.update(table_name, values, "_id" + " = ?", new String[] { column_id2 });
        db.close();

    }

    public void add_to_database(String username, String user_id, String email,
                                String course_no, String pre_ass_score, String score,
                                String status, String uploaded) {

        SQLiteDatabase DBase = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("user_id", user_id);
        values.put("email", email);
        values.put("course_no", course_no);
        values.put("pre_ass_score", pre_ass_score);
        values.put("score", score);
        values.put("status", status);
        values.put("uploaded", uploaded);

        DBase.insert(table_name, null, values);
        DBase.close();

    }

    public Course_score_db(Context context)
    {

        super(context, database_name, null, 1);
    }

    public void onCreate(SQLiteDatabase DBase) {
//        for (int x = 1; x < 7; x++) {

            DBase.execSQL("CREATE TABLE " + table_name + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, user_id TEXT, email TEXT, course_no TEXT, pre_ass_score TEXT, score TEXT, status TEXT, uploaded TEXT)");
//        }
    }
    public void onUpgrade(SQLiteDatabase DBase, int oldVersion, int newVersion)
    {

//        for (int x = 1; x < 7; x++) {

            DBase.execSQL("DROP TABLE IF EXISTS "+table_name);

//        }
        onCreate(DBase);
    }


}
