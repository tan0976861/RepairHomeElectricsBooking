package com.example.repairhomeelectricsbooking.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDB extends SQLiteOpenHelper {

    public static final String DBNAME = "HomeEBooking.db";

    public static final String TB_USER = "tblUser";
    public static final String TB_USER_USERID = "UserID";
    public static final String TB_USER_ROLEID = "RoleID";
    public static final String TB_USER_USERNAME = "UserName";
    public static final String TB_USER_PASSWORD = "Password";
    public static final String TB_USER_ADDRESS = "Address";
    public static final String TB_USER_EMAIL = "Email";
    public static final String TB_USER_PHONE = "Phone";
    public static final String TB_USER_STATUS = "Status";

    public MyDB(Context context){
        super(context,"HomeEBooking.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tbUser = "CREATE TABLE " + TB_USER + " ( " + TB_USER_USERID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_USER_ROLEID + " TEXT, " + TB_USER_USERNAME + " TEXT, " + TB_USER_PASSWORD + " TEXT, "
                + TB_USER_ADDRESS + " TEXT, " + TB_USER_EMAIL + " TEXT, " + TB_USER_PHONE + " TEXT, "
                + TB_USER_STATUS + " BOOLEAN )";
        db.execSQL(tbUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String tbUser = "DROP TABLE IF EXISTS " + TB_USER ;
        db.execSQL(tbUser);
    }

    public boolean insertData(String username,String password){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        long result = MyDB.insert(TB_USER,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean checkUserName(String username){
        SQLiteDatabase myDB = this.getReadableDatabase();
        String sql = "Select * from " + TB_USER + " where " + TB_USER_USERNAME + " = ?";
        Cursor cursor = myDB.rawQuery(sql,new String[] {username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean checkUserNamePassword(String username,String password){
        SQLiteDatabase myDB = this.getReadableDatabase();
        String sql = "Select * from " + TB_USER + " where " + TB_USER_USERNAME + " = ? and " + TB_USER_PASSWORD + " = ?";
        Cursor cursor = myDB.rawQuery(sql,new String[] {username,password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
}
