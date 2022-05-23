package com.lm.stopsleeping;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "LM.db";
    private int sleepCnt = 0;
    private String selAlarm;
    private String firstSleep, secondSleep, thirdSleep;

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 데이터베이스 생성시 호출
        // 데이터베이스 -> 테이블 -> 컬럼 -> 값
        db.execSQL("CREATE TABLE IF NOT EXISTS SleepRecord (id INTEGER PRIMARY KEY AUTOINCREMENT, sleepDate TEXT NOT NULL, sleepTime TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS User (id INTEGER PRIMARY KEY AUTOINCREMENT, sleepCnt INTEGER, alarm TEXT," +
                "song TEXT, firstSleep TEXT, secondSleep TEXT, thirdSleep TEXT )");

        db.execSQL("INSERT INTO User (sleepCnt, alarm, song, firstSleep, secondSleep, thirdSleep) " +
                "VALUES('" + 0 + "','" + "" + "','" + "" + "','" + "" + "','" + "" + "','" + "" + "');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    // Record Select
    public ArrayList<DateItem> getRecordList() {
        ArrayList<DateItem> recordItems = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor =db.rawQuery("SELECT * FROM SleepRecord ORDER BY sleepDate DESC", null);

        if(cursor.getCount() != 0) {
            // 조회해온 데이터가 있을 때 내부 수행
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String sleepDate = cursor.getString(cursor.getColumnIndexOrThrow("sleepDate"));
                String sleepTime = cursor.getString(cursor.getColumnIndexOrThrow("sleepTime"));

                DateItem recordItem = new DateItem();
                recordItem.setId(id);
                recordItem.setSleepDate(sleepDate);
                recordItem.setSleepTime(sleepTime);

                Log.e("TAG", id + "," + sleepDate + "," + sleepTime);

                recordItems.add(recordItem);
            }
        }
        cursor.close();
        return recordItems;
    }

    // Record Insert
    public void insertRecord(String sleepDate, String sleepTime) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO SleepRecord (sleepDate, sleepTime) VALUES('" + sleepDate + "','" + sleepTime + "');");
    }

    // Record Delete
    public void deleteRecord(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM SleepRecord WHERE id = '" + id + "'");
    }

    public void UpdateCnt(int cnt) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE User SET sleepCnt = '" + cnt + "'  WHERE id = 1");
    }

    public int SelectCnt() {

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT sleepCnt from User WHERE id = 1", null);

        if(cursor.getCount() != 0) {
            // 조회해온 데이터가 있을 때 내부 수행
            while(cursor.moveToNext()){
                sleepCnt = cursor.getInt(cursor.getColumnIndexOrThrow("sleepCnt"));
            }
        }
        cursor.close();
        return sleepCnt;
    }

    public void UpdateAlarm(CharSequence alm) {
        SQLiteDatabase db = getWritableDatabase();
        if(alm == "사이렌") {
            db.execSQL("UPDATE User SET alarm = 'siren' WHERE id = 1");
        }
        else if(alm == "강아지소리") {
            db.execSQL("UPDATE User SET alarm = 'dog_sound' WHERE id = 1");
        }
        else if(alm == "쨍그랑소리") {
            db.execSQL("UPDATE User SET alarm = 'break_sound' WHERE id = 1");
        }
        else if(alm == "천둥소리") {
            db.execSQL("UPDATE User SET alarm = 'thunder' WHERE id = 1");
        }
    }

    public String SelectAlarm(){

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT alarm from User WHERE id = 1", null);

        if(cursor.getCount() != 0) {
            // 조회해온 데이터가 있을 때 내부 수행
            while(cursor.moveToNext()){
                selAlarm = cursor.getString(cursor.getColumnIndexOrThrow("alarm"));
            }
        }
        cursor.close();
        return selAlarm;
    }

    public void UpdateFirstSleep(String first) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE User SET firstSleep = '" + first + "' WHERE id = 1");
    }

    public void UpdateSecondSleep(String second) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE User SET secondSleep = '" + second + "' WHERE id = 1");
    }

    public void UpdateThirdSleep(String third) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE User SET thirdSleep = '" + third + "' WHERE id = 1");
    }

    public String SelectFirstSleep(){

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT firstSleep from User WHERE id = 1", null);

        if(cursor.getCount() != 0) {
            // 조회해온 데이터가 있을 때 내부 수행
            while(cursor.moveToNext()){
                 firstSleep = cursor.getString(cursor.getColumnIndexOrThrow("firstSleep"));
            }
        }
        cursor.close();
        return firstSleep;
    }

    public String SelectSecondSleep(){

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT secondSleep from User WHERE id = 1", null);

        if(cursor.getCount() != 0) {
            // 조회해온 데이터가 있을 때 내부 수행
            while(cursor.moveToNext()){
                secondSleep = cursor.getString(cursor.getColumnIndexOrThrow("secondSleep"));
            }
        }
        cursor.close();
        return secondSleep;
    }

    public String SelectThirdSleep(){

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT thirdSleep from User WHERE id = 1", null);

        if(cursor.getCount() != 0) {
            // 조회해온 데이터가 있을 때 내부 수행
            while(cursor.moveToNext()){
                thirdSleep = cursor.getString(cursor.getColumnIndexOrThrow("thirdSleep"));
            }
        }
        cursor.close();
        return thirdSleep;
    }


    public void UpdateDB(){
        SQLiteDatabase db = getWritableDatabase();
    }



}
