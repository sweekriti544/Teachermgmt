package com.example.hpdv4.newp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAdapter extends SQLiteOpenHelper {
    private static final String DATABASE_NAME= "training";
    private static final int DATABASE_VERSION=1;

    private static final String TABLE="teacher";
    private static final String ID="id";
    private static final String USERNAME ="username";
    private static final String ADDRESS="address";
    private static final String FACULTY="faculty";
    private static final String PASSWORD="password";
    public DatabaseAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       sqLiteDatabase.execSQL("create table " + TABLE +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT ,ADDRESS TEXT ,FACULTY TEXT ,PASSWORD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean insertData(String username,String address, String faculty,String password){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME,username);
        contentValues.put(ADDRESS,address);
        contentValues.put(FACULTY,faculty);
        contentValues.put(PASSWORD,password);
        long result = sqLiteDatabase.insert(TABLE,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
public Cursor getAllData(){
    SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    Cursor res=sqLiteDatabase.rawQuery("select * FROM "+TABLE,null);
    return res;
}

public boolean updateData(String id, String username, String address, String faculty, String password){
SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
ContentValues contentValues=new ContentValues();
contentValues.put(ID,id);
contentValues.put(USERNAME,username);
    contentValues.put(ADDRESS, address);
    contentValues.put(FACULTY, faculty);
    contentValues.put(PASSWORD, password);
    sqLiteDatabase.update(TABLE, contentValues,"ID = ?",new String[] { id });
    return true;
}
public Integer deleteData(String id){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE,"ID=?",new String[]{id});
}
}
