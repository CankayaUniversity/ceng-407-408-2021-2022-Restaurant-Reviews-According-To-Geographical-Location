package com.Coskun.eatie;


public class Reviews {

    private String ReviewMaker,Review,WhichRestourant;
    private int rate,totalreviewrate;
    public Reviews()
    {
        ReviewMaker="";
        Review="";
        WhichRestourant="";
        rate=0;
        totalreviewrate=0;
    }
    public Reviews(String a,String b,String c,int d,int e)
    {
        ReviewMaker=a;
        Review=b;
        WhichRestourant=c;
        rate=d;
        totalreviewrate=e;
    }
    public String getReviewMaker(){return ReviewMaker;}
    public String getReview(){return Review;}
    public int getRate(){return rate;}
    public String getWhichRestourant() {return WhichRestourant;}
    public void setReview(String newreview) {Review = newreview;}
    public void setRate(int newrate) {rate = newrate;}

/*
    static public ArrayList<String> GetAllReviewsFromUser(Context context,String Name)
    {
        ReviewDb db=new ReviewDb();
        ArrayList <String> reviews=new ArrayList<>();
        ArrayList <Reviews> reviewList=db.AllReviews();
        for(Reviews q:reviewList)
        {
            if(q.getReviewMaker().equals(Name))
            {
                reviews.add(q.getReview());
            }
        }
        return reviews;
    }
    static public ArrayList<Reviews> GetAllReviewsForRestourant(Context context,String RestourantName)
    {
        ReviewDb db=new ReviewDb();
        ArrayList<Reviews> reviewforrestourant=new ArrayList<>();
        ArrayList<Reviews> AllReviews=db.AllReviews();
        for(Reviews q:AllReviews)
        {
            if(q.getWhichRestourant().equals(RestourantName))
                reviewforrestourant.add(q);
        }
        return reviewforrestourant;
    }
    static public ArrayList<Reviews> GetAllReviews(Context context)
    {
        ReviewDb db=new ReviewDb();
        ArrayList<Reviews> AllReviews=db.AllReviews();
        return AllReviews;
    }
    static public ArrayList<String> GetNameList(Context context){
        0
        ArrayList<User> UserList=db.AllUsers();
        ArrayList<String>NameList=new ArrayList<String>();
        for(User a:UserList)
        {
            NameList.add(a.getUserName());
        }
        return NameList;
    }
    */
/*
    static public boolean DeleteUser(Context context,String a)
    {
        UDBControl db=new UDBControl(context);
        ArrayList<User> UserList=db.AllUsers();
        for(User Test:UserList)
        {
            if(Test.getUserName().equals(a))
            {
                db.DBremove(a);
                return true;
            }
        }
        return false;
    }
*/
    /*
    static public boolean DeleteAllReviewsFromUser(Context context,String q)
    {
        boolean cntrl=true;
        ReviewDb db=new ReviewDb(context);
            if(!db.DBremoveAllFromOneUser(q)) cntrl=false;
            return cntrl;
    }
*/
}

