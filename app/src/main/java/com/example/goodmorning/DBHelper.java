package com.example.goodmorning;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "test.db";
    public static final String TB_NAME = "todolist";

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context){
        this(context,DB_NAME,null,VERSION);
    }

    public static void main(String[] args) {
        String query = "select sqlite_version() AS sqlite_version";
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(":memory:",null);
        Cursor cursor = db.rawQuery(query,null);
        String sqliteVersion = "";
        if(cursor.moveToNext()){
            sqliteVersion = cursor.getString(0);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TB_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,DATE TEXT,DETAIL TEXT,STATE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

