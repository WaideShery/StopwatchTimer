package com.neirx.stopwatchtimer.utility;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.neirx.stopwatchtimer.custom.Lap;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    // константы для конструктора
    private static final String DATABASE_NAME = "lapsDB";
    private static final int DATABASE_VERSION = 1;
    private final String TABLE_NAME = "laps";
    private final String KEY_ID = "_id";
    private final String KEY_STOPWATCH_NUM = "stopwatch_num";
    private final String KEY_TIME_NUM = "time_num";
    private final String KEY_TIME_DIFFERENCE = "time_difference";
    private final String KEY_TOTAL_TIME = "total_time";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ALARMS_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_STOPWATCH_NUM + " INTEGER," + //порядковые номера секундомеров
                KEY_TIME_NUM + " INTEGER," + //порядковые номера времени кругов секундомера
                KEY_TIME_DIFFERENCE + " TEXT," + //разница с общим временем предыдущего круга
                KEY_TOTAL_TIME + " TEXT" + ")"; //общее время круга
        db.execSQL(CREATE_ALARMS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addLap(Lap lap) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_STOPWATCH_NUM, lap.getStopwatchNum());
        values.put(KEY_TIME_NUM, lap.getTimeNum());
        values.put(KEY_TIME_DIFFERENCE, lap.getTimeDifference());
        values.put(KEY_TOTAL_TIME, lap.getTimeTotal());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    /**
     * Получить все записи базы данных
     * @return null, если нет ни одной записи
     */
    public List<Lap> getLaps(){
        List<Lap> lapList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int stopwatchNum;
        int timeNum;
        String timeTotal;
        String timeDifference;
        if (cursor.moveToFirst()) {
            do {
                stopwatchNum = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_STOPWATCH_NUM)));
                timeNum = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_TIME_NUM)));
                timeTotal = cursor.getString(cursor.getColumnIndex(KEY_TOTAL_TIME));
                timeDifference = cursor.getString(cursor.getColumnIndex(KEY_TIME_DIFFERENCE));
                Lap alarm = new Lap(stopwatchNum, timeNum, timeTotal, timeDifference);

                lapList.add(alarm);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lapList;
    }

    public void clearLaps() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }
}
