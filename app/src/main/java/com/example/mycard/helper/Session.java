package com.example.mycard.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    private SharedPreferences prefs;

    public Session(Context context) {

        prefs = context.getSharedPreferences("UserData" , 0);
    }

    public void setSaved (boolean flag)
    {
        prefs.edit().putBoolean( "Saved"  , flag).commit();
    }

    public boolean getSaved ()
    {
        return prefs.getBoolean("Saved" , false);
    }

    public void LogOut ()
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

    public void setId(int MemberId)
    {
        prefs.edit().putInt("Id" , MemberId ).commit();
    }

    public int getId()
    {
        return prefs.getInt("Id" , 0);
    }


    public void setType (String Type)
    {
        prefs.edit().putString("Type" , Type).commit();
    }

    public String getType ()
    {
        return prefs.getString("Type" , "");
    }

    public void setFName(String Name)
    {
        prefs.edit().putString("FName" ,Name ).commit();
    }

    public String getFName()
    {
        return prefs.getString("FName" , "");
    }

    public void setLName(String Name)
    {
        prefs.edit().putString("LName" ,Name ).commit();
    }

    public String getLName()
    {
        return prefs.getString("LName" , "");
    }





}