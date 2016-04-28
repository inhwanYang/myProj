package com.example.yang.saving_water;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper {

    public DBManager(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // 서버를 사용할 수 없어 DB 간략화

        db.execSQL("CREATE TABLE HOMEUSE_IN_MONTH( DEVICE_ID INTEGER, MONTH INTEGER, " +
                "First FLOAT,Second FLOAT,Third FLOAT,Fourth FLOAT, Fifth FLOAT, sixth FLOAT);");
        //가정 월 별 사용량
        db.execSQL("CREATE TABLE HOMEUSE_IN_DAY(DEVICE_ID INTEGER," +
                "First FLOAT,Second FLOAT,Third FLOAT,Fourth  FLOAT, Fifth FLOAT, sixth FLOAT,Seventh FLOAT,eighth FLOAT);");
        // 가정 시간대 별 평균 사용량
        db.execSQL("CREATE TABLE MAPPOINT( _id INTEGER PRIMARY KEY AUTOINCREMENT, description TEXT, w FLOAT, k FLOAT,title TEXT);");
        // 맵 DB
        db.execSQL("CREATE TABLE WATERSAVED( HOME_ID INTEGER PRIMARY KEY AUTOINCREMENT, DEVICE_ID INTEGER, " +
                "First FLOAT,Second FLOAT,Third FLOAT,Fourth  FLOAT, Fifth FLOAT, sixth FLOAT);");
        // 가정 절약량
        db.execSQL("CREATE TABLE GROUP_TALK(USER_ID INTEGER, SAYING TEXT, GROUP_ID INTEGER);");
        // 대화 내용
        db.execSQL("CREATE TABLE NEWS( _id INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, BODY TEXT);");
        // 뉴스 소식
        db.execSQL("CREATE TABLE USER(USER_EMAIL TEXT, PASSWORD TEXT, DEVICE_ID INTEGER);");
        //사용자 정보
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insert(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public void update(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public void delete(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public LatLng PointMapMarker(GoogleMap mMap) {
        SQLiteDatabase db = getReadableDatabase();
        String str = "";
        LatLng point = null;
        MarkerOptions marker;
        Cursor cursor = db.rawQuery("select * from MAPPOINT", null);
        while (cursor.moveToNext()) {
            point = new LatLng(cursor.getFloat(2), cursor.getFloat(3));
            marker = new MarkerOptions().position(point);
            marker.title(cursor.getString(4));
            marker.snippet(cursor.getString(1));
            mMap.addMarker(marker).showInfoWindow();
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));
        cursor.close();
        return point;
    }

    public float[] getData(int index) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select First, Second, Third, Fourth, Fifth, sixth " +
                "from HOMEUSE_IN_MONTH WHERE MONTH =" + index, null);
        float[] arr = new float[6];
        while (cursor.moveToNext()) {
            for (int i = 0; i < 6; i++) {
                arr[i] = cursor.getFloat(i);
            }
            cursor.close();
            return arr;
        }
        cursor.close();
        return arr;
    }

    public float[] getData_In_Day(int index) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select First, Second, Third, Fourth, Fifth, sixth " +
                "FROM HOMEUSE_IN_DAY WHERE DEVICE_ID =" + index, null);
        float[] arr = new float[7];
        arr[0] = 0;
        while (cursor.moveToNext()) {
            for (int i = 1; i < 7; i++) {
                arr[i] = cursor.getFloat(i - 1);
            }
            cursor.close();
            return arr;
        }
        cursor.close();
        return arr;
    }

    public float[] getSavedData(int index) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select First, Second, Third, Fourth, Fifth, sixth " +
                "from WATERSAVED WHERE DEVICE_ID =" + index, null);
        float[] arr = new float[6];
        while (cursor.moveToNext()) {
            for (int i = 0; i < 6; i++) {
                arr[i] = cursor.getFloat(i);
            }
            cursor.close();
            return arr;
        }
        cursor.close();
        return arr;
    }

    public void setNews(ArrayList<String> arrayList) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select TITLE FROM NEWS", null);
        while (cursor.moveToNext()) {
            arrayList.add(cursor.getString(0));
        }
        cursor.close();
    }

    public String getNewsBody(int position) {
        SQLiteDatabase db = getReadableDatabase();
        String str = "";
        Cursor cursor = db.rawQuery("select BODY FROM NEWS WHERE _id=" + position, null);
        while (cursor.moveToNext()) {
            str = cursor.getString(0);
            cursor.close();
            return str;
        }
        cursor.close();
        return str;
    }

    public void getTalk(int USER_ID, CustomAdapter m_Adapter) {
        SQLiteDatabase db = getReadableDatabase();
        String str;
        Cursor cursor = db.rawQuery("select USER_ID, SAYING FROM GROUP_TALK ", null);
        while (cursor.moveToNext()) {
            if (USER_ID == cursor.getInt(0)) {
                str = cursor.getString(1);
                m_Adapter.add(str, 1);
            } else {
                str = cursor.getString(1);
                m_Adapter.add(str, 0);
            }

        }
        cursor.close();
    }
    // 해당 유저 정보 찾기
    public boolean select_user(String mail, String dev_id) {

        SQLiteDatabase db = getReadableDatabase();
        String str = "";
        Cursor cursor = db.rawQuery("select USER_EMAIL FROM USER WHERE USER_EMAIL = '" + mail+"'", null);
        while (cursor.moveToNext()) {
            cursor.close();
            return true;
        }
        cursor = db.rawQuery("select USER_EMAIL FROM USER WHERE DEVICE_ID =" + dev_id, null);
        while (cursor.moveToNext()) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }
    public boolean check_ID_PASSWORD(String Mail, String Pass) {
        SQLiteDatabase db = getReadableDatabase();
        String str = "";
        Cursor cursor = db.rawQuery("select USER_EMAIL FROM USER WHERE USER_EMAIL ='" + Mail + "' AND PASSWORD ='" + Pass+"'", null);
        while (cursor.moveToNext()) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }
    //디바이스 정보 얻기
    public int getDeviceID(String id)
    {
        int device_id =0;
        SQLiteDatabase db = getReadableDatabase();
        String str = "";
        Cursor cursor = db.rawQuery("select DEVICE_ID FROM USER WHERE USER_EMAIL ='" + id + "'", null);
        while (cursor.moveToNext()) {
            device_id = cursor.getInt(0);
        }
        cursor.close();
        return device_id;
    }

}