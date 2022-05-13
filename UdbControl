package com.Coskun.eatie;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

class UDBControl extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="EATIEUS";

    public static final String USERTABLE="USERS";

    public static final String USER_NAME="USER_N";

    public static final String USER_MAIL="USER_M";

    public static final String USER_PASSWORD="USER_P";

    public static final String USER_ID="ID";

    public static final int DATABASE_VERSION=1;

    public static final String AllList="SELECT * FROM  USERS";

    public static final String CREATE_USER_TABLE="CREATE TABLE IF NOT EXISTS USERS("+
            "ID VARCHAR PRIMARY KEY,USER_N VARCHAR,USER_M VARCHAR,USER_P VARCHAR" +
            ")";

    public static final String DropTable="DROP TABLE IF EXISTS USERS";

    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_USER_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
    public boolean DBremove(String UserId)
    {
        try{
            SQLiteDatabase db=this.getWritableDatabase();
            db.delete("USERS","USER_N"+"=?",new String[]{UserId});
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public void DBAdd(String UserName,String UserPassword,String UserMail)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(USER_NAME,UserName);
        values.put(USER_PASSWORD,UserPassword);
        values.put(USER_MAIL,UserMail);
        db.insert(USERTABLE,null,values);

        values.clear();

        db.close();

    }
    public ArrayList<User> AllUsers()
    {
        ArrayList<User> AllUsers=new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(AllList,null);
        User t;
        if(cursor.moveToFirst())
        {
            t=new User(cursor.getString(1),cursor.getString(2),cursor.getString(3));
            AllUsers.add(t);
            while(cursor.moveToNext())
            {
                t=new User(cursor.getString(1),cursor.getString(2),cursor.getString(3));
                AllUsers.add(t);
            }
        }
        db.close();
        return AllUsers;
    }

    public UDBControl(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


}

