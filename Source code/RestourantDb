package com.Coskun.eatie;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class RestourantDb extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="EATIERES";

    public static final String RESTOURANT_TABLE="RESTOURANTS";

    public static final String RESTOURANT_NAME="RES_N";

    public static final String RESTOURANT_SUM="RES_S";

    public static final String RES_RATE="RES_R";

    public static final String RES_ID="RESTOURANT_ID";

    public static final int DATABASE_VERSION=1;

    public static final String AllList="SELECT * FROM  RESTOURANTS";

    public static final String CREATE_RESTOURANT="CREATE TABLE IF NOT EXISTS RESTOURANTS("+
            "RESTOURANT_ID VARCHAR PRIMARY KEY,RES_N VARCHAR,RES_R REAL,RES_S VARCHAR" +
            ")";

    public static final String DropTable="DROP TABLE IF EXISTS RESTOURANTS";

    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_RESTOURANT);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
    public boolean DBremove(String resname)
    {
        try{
            SQLiteDatabase db=this.getWritableDatabase();
            db.delete("RESTOURANTS","RES_N"+"=?",new String[]{resname});
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public boolean ReAverage(String RestourantName,double c)
    {
       try {
           SQLiteDatabase db=this.getWritableDatabase();
           ContentValues Val=new ContentValues();
           Val.put("RES_R",c);
           db.update("RESTOURANTS",Val,"RES_N"+"=?",new String[]{RestourantName});
            return true;
       }
       catch (Exception e)
       {
           e.printStackTrace();
           return false;
       }

    }
    public void DBAdd(String res_name,String res_sum,double res_rate)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(RESTOURANT_NAME,res_name);
        values.put(RESTOURANT_SUM,res_sum);
        values.put(RES_RATE,res_rate);
        db.insert(RESTOURANT_TABLE,null,values);

        values.clear();

        db.close();
    }
    public ArrayList<Restourant> AllRestourants()
    {
        ArrayList<Restourant> allrestourants=new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(AllList,null);
        Restourant t=null;
        if(cursor.moveToFirst())
        {
            t=new Restourant(cursor.getString(1),cursor.getDouble(2),cursor.getString(3));
            allrestourants.add(t);
            while(cursor.moveToNext())
            {
                t=new Restourant(cursor.getString(1),cursor.getDouble(2),cursor.getString(3));
                allrestourants.add(t);
            }
        }
        db.close();
        return allrestourants;
    }
    public RestourantDb(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


}

