package com.example.mycard.helper;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class ArabicNumber {

    public static  String GetArNumbers(String text){
        return text.replace("0" , "٠").replace("1","١").replace("2","٢")
                .replace("3","٣").replace("4" , "٤").replace("5" ,"٥")
                .replace("6" ,"٦").replace("7" ,"٧").replace("8" , "٨").replace("9" , "٩");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static  String GetTextDate(String text){

        LocalDate date = LocalDate.parse(text);
        DayOfWeek day = date.getDayOfWeek();
        return  GetArabicDate(String.valueOf(day)) ;
    }

    public static String GetArabicDate(String text){
        return  text.replace("FRIDAY" , "الجمعة").replace("SATURDAY","السبت").replace("SUNDAY","الاحد")
                .replace("MONDAY","الاثنين").replace("TUESDAY" , "الثلاثاء").replace("WEDNESDAY" ,"الاربعاء")
                .replace("THURSDAY" ,"الخميس");
    }
}
