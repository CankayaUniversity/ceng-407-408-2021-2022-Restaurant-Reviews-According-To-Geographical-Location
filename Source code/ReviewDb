package com.Coskun.eatie;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

class ReviewDb extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="EATIEREW";

    public static final String REVIEW_TABLE="REVIEWS";

    public static final String REVIEWER_NAME="USER_N";

    public static final String REVIEW="REV";

    public static final String RESTOURANT="RES";

    public static final String RATE="REV_R";

    public static final String REW_ID="ID";

    public static final int DATABASE_VERSION=1;

    public static final String AllList="SELECT * FROM  REVIEWS";

    public static final String CREATE_Rew_TABLE="CREATE TABLE IF NOT EXISTS REVIEWS("+
            "ID VARCHAR PRIMARY KEY,USER_N VARCHAR,REV VARCHAR,RES VARCHAR,REV_R INT" +
            ")";

    public static final String DropTable="DROP TABLE IF EXISTS REVIEWS";

    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_Rew_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
    public boolean DBremoveAllFromOneUser(String UserName)
    {
        try{
            SQLiteDatabase db=this.getWritableDatabase();

            db.delete("REVIEWS","USER_N"+"=?",new String[]{UserName});
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public void DBAdd(String UserName,String RestourantName,String Review,int Rate)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(REVIEWER_NAME,UserName);
        values.put(RESTOURANT,RestourantName);
        values.put(REVIEW,Review);
        values.put(RATE,Rate);
        db.insert(REVIEW_TABLE,null,values);

        values.clear();

        db.close();

    }
    public ArrayList<Reviews> AllReviews()  
    {
        ArrayList<Reviews> AllReviews=new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(AllList,null);
        Reviews t=null;
        if(cursor.moveToFirst())
        {
            t=new Reviews(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4));
            AllReviews.add(t);
            while(cursor.moveToNext())
            {
                t=new Reviews(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4));
                AllReviews.add(t);
            }
        }
        db.close();
        return AllReviews;
    }

    public ReviewDb(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


}

