package com.example.goodmorning;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class listManager {
    private DBHelper dbHelper;
    private String TBNAME;

    public listManager(Context context){
        dbHelper = new DBHelper(context);
        TBNAME = dbHelper.TB_NAME;
    }

    public void add(listItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("date",item.getdate());
        values.put("detail",item.getdetail());
        values.put("state",item.getstate());

        db.insert(TBNAME,null,values);
        db.close();
    }

    public listItem findById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME,null,"ID=?",new String[]{String.valueOf(id)},
                null,null,null);

        listItem item = null;
        if(cursor!=null && cursor.moveToFirst()){
            item = new listItem();
            item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            item.setdate(cursor.getString(cursor.getColumnIndex("DATE")));
            item.setdetail(cursor.getString(cursor.getColumnIndex("DETAIL")));
            item.setstate(cursor.getString(cursor.getColumnIndex("STATE")));
            cursor.close();
        }

        db.close();
        return item;
    }

    public ArrayList<HashMap<String, String>> findByContent(String content){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME,null,"DETAIL LIKE ?",new String[]{"%"+content+"%"},
                null,null,null);

        ArrayList<HashMap<String, String>> listItems;
        listItems = new ArrayList<HashMap<String, String>>();

        listItem item = null;
        while(cursor.moveToNext()){
            item = new listItem();
            item.setdate(cursor.getString(cursor.getColumnIndex("DATE")));
            item.setdetail(cursor.getString(cursor.getColumnIndex("DETAIL")));

            HashMap<String, String> map = new HashMap<String,
                    String>();
            String date = item.getdate();
            String detail = item.getdetail();
            map.put("ItemTitle", date);
            map.put("ItemDetail", detail);
            listItems.add(map);
        }

        cursor.close();
        db.close();
        return listItems;
    }

    public void delete(String date){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //删除并重新排序
        db.delete(TBNAME, "DATE=?", new String[]{date});

        db.close();
    }
}

class listItem{
    int Id;
    String date,detail,state;

    public String getdate() {
        return date;
    }

    public String getdetail() {
        return detail;
    }

    public String getstate() {
        return state;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setdate(String d) {
        date = d;
    }

    public void setdetail(String de) {
        detail = de;
    }

    public void setstate(String s) {
        state = s;
    }


}
