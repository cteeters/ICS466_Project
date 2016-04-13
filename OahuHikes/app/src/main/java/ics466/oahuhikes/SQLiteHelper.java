package ics466.oahuhikes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

public class SQLiteHelper extends SQLiteAssetHelper
{
    private static final String DATABASE_NAME = "hikes.db";
    private static final int DB_VERSION = 1;
    private static final String DATABASE_PATH = "/data/data/ics466.oahuhikes/databases/";
    private static final String TABLE_NAME = "hikes_table";
    private static final String COL1 = "_id";
    private static final String COL2 = "NAME";
    private static final String COL3 = "LENGTH";
    private static final String COL4 = "DIFFICULTY";
    private static final String COL5 = "RATING";
    private SQLiteDatabase myDB;
    private final Context context;

    public SQLiteHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DB_VERSION);
        this.context = context;
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
        Cursor res = db.query(TABLE_NAME, new String[] {COL2, COL3, COL4, COL5}, searchBy + "= '" + searchParam + "'", null, null, null, "NAME", null);
        return res;
    }
}
