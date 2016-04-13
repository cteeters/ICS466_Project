package ics466.oahuhikes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "hikes.db";
    public static final String TABLE_NAME = "hikes_table";
    public static final String COL1 = "ID";
    public static final String COL2 = "NAME";
    public static final String COL3 = "LENGTH";
    public static final String COL4 = "DIFFICULTY";

    public SQLiteHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String SQL_String = "CREATE TABLE " + TABLE_NAME + " (" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL2 + " TEXT,"
                + COL3 + " INTEGER," + COL4 + " INTEGER)";
    db.execSQL(SQL_String);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor search(String searchParam, String searchBy)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        searchParam.trim();
        Cursor res = db.query(TABLE_NAME, new String[] {COL2, COL3, COL4}, searchBy + "=" + searchParam, null, null, null, "NAME", null);
        return res;
    }
}
