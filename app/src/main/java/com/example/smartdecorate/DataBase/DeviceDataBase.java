package com.example.smartdecorate.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.smartdecorate.ENUM.DeviceType;

public class DeviceDataBase extends SQLiteOpenHelper {

    private static final String DB_NAME = "device_db";
    private static final int VERSION = 1;
    private DeviceType deviceType;

    /* DEVICE TABLE */
    private static final String DEVICE_TABLE = "tlb_device";
    private static final String COLUMN_DEVICE_TABLE_ID = "id";
    private static final String COLUMN_DEVICE_TABLE_NAME = "device_name";
    private static final String COLUMN_DEVICE_TABLE_IP = "device_ip";
    private static final String COLUMN_DEVICE_TABLE_TYPE = "device_type";

    private static final String CREATE_DEVICE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " + DEVICE_TABLE + "( " +

            COLUMN_DEVICE_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_DEVICE_TABLE_NAME + " TEXT, " +
            COLUMN_DEVICE_TABLE_IP + " TEXT, " +
            COLUMN_DEVICE_TABLE_TYPE + " TEXT" +
            ");";


    /* LED EFFECTS TABLE */
    private static final String LED_EFFECTS_TABLE = "tlb_ledEffects";
    private static final String COLUMN_LED_EFFECTS_TABLE_ID = "id";
    private static final String COLUMN_LED_EFFECTS_TABLE_COLOR = "color";
    private static final String COLUMN_LED_EFFECTS_TABLE_EFFECT = "effect";
    private static final String COLUMN_LED_EFFECTS_TABLE_SPEED = "speed";
    private static final String COLUMN_LED_EFFECTS_TABLE_BRIGHTNESS = "brightness";

    private static final String CREATE_LED_EFFECTS_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " + LED_EFFECTS_TABLE + "( " +
            COLUMN_LED_EFFECTS_TABLE_ID + " INTEGER, " +
            COLUMN_LED_EFFECTS_TABLE_COLOR + " INTEGER, " +
            COLUMN_LED_EFFECTS_TABLE_EFFECT + " TEXT, " +
            COLUMN_LED_EFFECTS_TABLE_SPEED + " INTEGER, " +
            COLUMN_LED_EFFECTS_TABLE_BRIGHTNESS + " INTEGER " +
            ");";

    /* CATEGORY TABLE */
    private static final String CATEGORY_TABLE = "tlb_category";
    private static final String COLUMN_CATEGORY_TABLE_ID = "id";
    private static final String COLUMN_CATEGORY_TABLE_TITLE = "title";

    private static final String CREATE_CATEGORY_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " + CATEGORY_TABLE + " (" +
            COLUMN_CATEGORY_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_CATEGORY_TABLE_TITLE + " TEXT " + ");";

    /* LIGHT BULB TABLE */
    private static final String LIGHT_BULB_TABLE = "tlb_lightBulb";
    private static final String COLUMN_LIGHT_BULB_TABLE_ID = "id";
    private static final String COLUMN_LIGHT_BULB_TABLE_STATUS = "status";

    private static final String CREATE_LIGHT_BULB_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " + LIGHT_BULB_TABLE + " (" +
            COLUMN_LIGHT_BULB_TABLE_ID + " INTEGER, " +
            COLUMN_LIGHT_BULB_TABLE_STATUS + " INTEGER " + ");";

    public Cursor getLedStripItems() {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String query = "SELECT * FROM " + DEVICE_TABLE +
                " INNER JOIN " + LED_EFFECTS_TABLE + " ON " +
                "tlb_device.id == tlb_ledEffects.id" +
                " WHERE tlb_device.device_type == \"نوار LED\"";

        return sqLiteDatabase.rawQuery(query, null);
    }

    public Cursor getLightBulbItems() {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String query = "SELECT * FROM " + DEVICE_TABLE +
                " INNER JOIN " + LIGHT_BULB_TABLE + " ON " +
                "tlb_device.id == tlb_lightBulb.id" +
                " WHERE tlb_device.device_type == \"لامپ روشنایی\"";

        return sqLiteDatabase.rawQuery(query, null);
    }

    public DeviceDataBase(@Nullable Context context, DeviceType deviceType) {
        super(context, DB_NAME, null, VERSION);

        setDeviceType(deviceType);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            db.execSQL(CREATE_DEVICE_TABLE_QUERY);
            db.execSQL(CREATE_LED_EFFECTS_TABLE_QUERY);
            db.execSQL(CREATE_CATEGORY_TABLE_QUERY);
            db.execSQL(CREATE_LIGHT_BULB_TABLE_QUERY);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertDeviceInfo(String deviceName, String deviceIp, String deviceType) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_DEVICE_TABLE_NAME, deviceName);
        contentValues.put(COLUMN_DEVICE_TABLE_IP, deviceIp);
        contentValues.put(COLUMN_DEVICE_TABLE_TYPE, deviceType);

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        return sqLiteDatabase.insert(DEVICE_TABLE, null, contentValues);
    }

    public Cursor getDeviceInfo() {

        Cursor cursor;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        if (getDeviceType() == DeviceType.LED_STRIP) {
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + LED_EFFECTS_TABLE, null);
        } else {
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DEVICE_TABLE, null);
        }

        return cursor;
    }

    public long insertLedDeviceInfo(long id, int color, String effect, int speed, int brightness) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_LED_EFFECTS_TABLE_ID, id);
        contentValues.put(COLUMN_LED_EFFECTS_TABLE_COLOR, color);
        contentValues.put(COLUMN_LED_EFFECTS_TABLE_EFFECT, effect);
        contentValues.put(COLUMN_LED_EFFECTS_TABLE_SPEED, speed);
        contentValues.put(COLUMN_LED_EFFECTS_TABLE_BRIGHTNESS, brightness);

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        return sqLiteDatabase.insert(LED_EFFECTS_TABLE, null, contentValues);
    }

    public long insertLightBulbInfo(long id, int status) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_LIGHT_BULB_TABLE_ID, id);
        contentValues.put(COLUMN_LIGHT_BULB_TABLE_STATUS, status);

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        return sqLiteDatabase.insert(LIGHT_BULB_TABLE, null, contentValues);
    }

    public long updateLedDeviceInfo(long id, int color, String effect, int speed, int brightness) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_LED_EFFECTS_TABLE_ID, id);
        contentValues.put(COLUMN_LED_EFFECTS_TABLE_COLOR, color);
        contentValues.put(COLUMN_LED_EFFECTS_TABLE_EFFECT, effect);
        contentValues.put(COLUMN_LED_EFFECTS_TABLE_SPEED, speed);
        contentValues.put(COLUMN_LED_EFFECTS_TABLE_BRIGHTNESS, brightness);

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        return sqLiteDatabase.update(LED_EFFECTS_TABLE, contentValues,
                COLUMN_LED_EFFECTS_TABLE_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public Cursor getOneLedDeviceInfo(String id) {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

//        return sqLiteDatabase.rawQuery("SELECT * FROM " + LED_EFFECTS_TABLE, null);

        return sqLiteDatabase.rawQuery("SELECT * FROM " + LED_EFFECTS_TABLE +

                " WHERE " + COLUMN_LED_EFFECTS_TABLE_ID + " = ?", new String[]{id});
    }

    public long insertCategoryInfo(String id, String title) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_CATEGORY_TABLE_ID, Integer.valueOf(id));
        contentValues.put(COLUMN_CATEGORY_TABLE_TITLE, title);

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        return sqLiteDatabase.insert(CATEGORY_TABLE, null, contentValues);
    }

    public Cursor getCategoryInfo() {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        return sqLiteDatabase.rawQuery("SELECT * FROM " + CATEGORY_TABLE, null);
    }

    private long getSizeOfTable(String tableName) {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        //Cursor cursor = sqLiteDatabase.rawQuery("SELECT count(*) FROM " + tableName, null);

        long size = DatabaseUtils.queryNumEntries(sqLiteDatabase, tableName);

        return size;
    }
}
