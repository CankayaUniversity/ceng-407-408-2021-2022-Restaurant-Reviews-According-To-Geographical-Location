package com.Coskun.eatie;

public class User {

private String UserName,UserMail,UserPassword;
    public User()
    {
        UserName="";
        UserMail="";
        UserPassword="";
    }
    public User(String a,String b,String c)
    {
        UserName=a;
        UserMail=b;
        UserPassword=c;
    }
    public String getUserName(){return UserName;}
    public String getUserMail(){return UserMail;}
    public String getUserPassword(){return UserPassword;}
    public void setUserMail(String userMail) {UserMail = userMail;}
    public void setUserName(String userName) {UserName = userName;}
    public void setUserPassword(String userPassword) {UserPassword = userPassword;}
/*
    static public ArrayList<User> GetUserData(Context context)
    {
        UDBControl db=new UDBControl(context);
        ArrayList UserList=db.AllUsers();
        return UserList;
    }
 */
   /*
    static public boolean ControlUserName(Context context,String SendedName)
    {
        UDBControl db=new UDBControl(context);
        ArrayList<User> UserList=db.AllUsers();
        for (User a:UserList)
        {
            if(a.getUserName().equals(SendedName))
                return true;
        }
        return false;
    }
    */
    /*
    static public ArrayList<String> GetNameList(Context context){
        UDBControl db=new UDBControl(context);
        ArrayList<User> UserList=db.AllUsers();
        ArrayList<String>NameList=new ArrayList<String>();
        for(User a:UserList)
        {
            NameList.add(a.getUserName());
        }
        return NameList;
    }

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
    static public boolean DeleteAll(Context context)
    {
        boolean cntrl=true;
        UDBControl db=new UDBControl(context);
        ArrayList<String> UserList=GetNameList(context);
        for(String a:UserList)
        {
            if(!db.DBremove(a))
                cntrl=false;
        }
        return cntrl;
    }

*/
}