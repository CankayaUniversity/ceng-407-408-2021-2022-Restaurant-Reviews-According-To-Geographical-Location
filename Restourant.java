package com.Coskun.eatie;

import java.util.ArrayList;

public class Restourant {
    private String RestourantName,RestourantSummary,RestourantTag;
    private ArrayList<Reviews> reviews;
    private double AvrgRate;
public Restourant(){}
public Restourant(String a,double b,String c,String d)   {RestourantName=a;AvrgRate=b;RestourantSummary=c;RestourantTag=d;}

    public String getRestourantName() {return RestourantName;}
    public Double getRestourantRate() {return AvrgRate;}
    public String getRestourantSummary() {return RestourantTag;}
    public String getRestourantTag() {return RestourantSummary;}
    public void setRestourantName(String restourantName) {RestourantName = restourantName;}
    public void setRestourantRate(double restourantRate) {AvrgRate = restourantRate;}
    public void setRestourantSummary(String restourantSummary) {RestourantSummary = restourantSummary;}

    public void setRestourantTag(String restouranttag) {RestourantTag = restouranttag;}
/*
    static public ArrayList<Restourant> getData(Context context)
    {
        ArrayList<Restourant> restourantList=new ArrayList<>();
        RestourantDb db=new RestourantDb();
        restourantList=db.AllRestourants();
        return restourantList;
    }*/
    /*
    static public int getTaggedData(Context context,String TagN,String ResN)
    {
        ArrayList<Restourant> restourantList=new ArrayList<>();
        RestourantDb db=new RestourantDb();
        int q=db.WithGivenTag(TagN,ResN);
        return q;
    }*/
    /*
    static public ArrayList<Restourant> getsearchedName(Context context,String q)
    {
        ArrayList<Restourant> restourantList=new ArrayList<>();
        RestourantDb db=new RestourantDb();
        restourantList=db.WithGivenname(q);
        return restourantList;
    }*/
   /* public double SetAvrgRate (Context context)
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
        return 5;
    }
*/



}