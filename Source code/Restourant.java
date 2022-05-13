package com.Coskun.eatie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

public class Restourant {
    private String RestourantName,RestourantSummary;
    private ArrayList<Reviews> reviews;
    private double AvrgRate;
public Restourant(){}
public Restourant(String a,double b,String c)   {RestourantName=a;AvrgRate=b;RestourantSummary=c;}

    public String getRestourantName() {return RestourantName;}
    public Double getRestourantRate() {return AvrgRate;}
    public String getRestourantSummary() {return RestourantSummary;}

    public void setRestourantName(String restourantName) {RestourantName = restourantName;}
    public void setRestourantRate(double restourantRate) {AvrgRate = restourantRate;}
    public void setRestourantSummary(String restourantSummary) {RestourantSummary = restourantSummary;}

    static public ArrayList<Restourant> getData(Context context)
    {
        ArrayList<Restourant> restourantList=new ArrayList<>();
        RestourantDb db=new RestourantDb(context);
        restourantList=db.AllRestourants();
        return restourantList;
    }

    public double SetAvrgRate (Context context)
    {
        Double c=0.0;
        Integer i=0;
        ArrayList <Reviews> reviews=new ArrayList<>();
        reviews=Reviews.GetAllReviewsForRestourant(context,this.RestourantName);
        for(Reviews q:reviews)
        {
            c=q.getRate()+c;
            i++;
        }
        c=c/i;
        return c;
    }
