package com.example.realtimedatabase.Database;

import static android.content.ContentValues.TAG;

import static java.lang.Float.parseFloat;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.realtimedatabase.SensorValues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper
{

    private final Context context;

    public DatabaseHelper(Context context)
    {
        super(context, Config.DATABASE_NAME, null, Config.DATABASE_VERSION);//passing values to the SQLiteOpenHelper
        this.context = context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_COURSE = " CREATE TABLE " + Config.SENSOR_TABLE_NAME +
                " (" + Config.COLUMN_SENSOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Config.COLUMN_PH + " TEXT NOT NULL,"
                + Config.COLUMN_PPM + " TEXT NOT NULL,"
                + Config.COLUMN_TEMPERATURE+" TEXT NOT NULL)";

        Log.d(TAG, CREATE_TABLE_COURSE);

        db.execSQL(CREATE_TABLE_COURSE); //executes our table in the SQL database
        Log.d(TAG, "db created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public long insertSensorValues(SensorValues sensorValues)
    {
        long id=-1;
        SQLiteDatabase db=this.getWritableDatabase(); //opened to the database to write in it

        ContentValues contentValues=new ContentValues(); //holds the value we want to store in our database

        contentValues.put(Config.COLUMN_PH,SensorValues.getpH());
        contentValues.put(Config.COLUMN_PPM,SensorValues.getPpm());
        contentValues.put(Config.COLUMN_TEMPERATURE,SensorValues.getTemperature());

        try

        {
            id=db.insertOrThrow(Config.SENSOR_TABLE_NAME,null,contentValues);
            Log.d(TAG, "Sensor Values inserted in the database");

        }

        catch(SQLException e)
        {

            Log.d(TAG,"EXCEPTION:"+e);
            Toast.makeText(context, "Operation Failed!:="+e,Toast.LENGTH_LONG).show();

        }

        finally
        {

            db.close();

        }

        return id;

    }

    public List<SensorValues> getAllSensorValues() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        // this function returns a cursor, therefore we need a cursor to analyze the data
        List<SensorValues> sensorValues = null;
        try {
            cursor = db.query(Config.SENSOR_TABLE_NAME, null, null, null, null, null, null); //read all the values from the table

            if (cursor != null)  //if cursor isn't empty
            {

                if (cursor.moveToFirst())    //going to the first row of information
                {
                    sensorValues = new ArrayList<>();

                    do
                    {
                        //reading values from sql table
                        @SuppressLint("Range") float pH = parseFloat(cursor.getString(cursor.getColumnIndex(Config.COLUMN_PH)));
                        @SuppressLint("Range") float PPM = parseFloat(cursor.getString(cursor.getColumnIndex(Config.COLUMN_PPM)));
                        @SuppressLint("Range") float temperature = parseFloat(cursor.getString(cursor.getColumnIndex(Config.COLUMN_TEMPERATURE)));

                        sensorValues.add(new SensorValues(pH, PPM, temperature));

                    } while (cursor.moveToNext());   //move cursor to the next step

                    Log.d(TAG, "Sensor Values stored in the List");
                    return sensorValues;
                }
            }
        }

        catch (SQLException e)

        {
            Log.d(TAG, "EXCEPTION:" + e);
            Toast.makeText(context, "Operation Failed!:=" + e, Toast.LENGTH_LONG).show();
        }

        finally {

            if (cursor != null) {
                cursor.close();

                db.close();

            }
        }

        return Collections.emptyList();

    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Config.SENSOR_TABLE_NAME,null,null);
        db.close();
    }


}
