package com.rokkhi.rokkhimarketinganalyst.Utils;

import android.content.Context;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Normalfunc {

   // FirebaseFunctions firebaseFunctions;
    FirebaseFirestore firebaseFirestore;
    Context context;
    private static final String TAG = "Normalfunc";

    public Normalfunc(){
        firebaseFirestore= FirebaseFirestore.getInstance();

    }

    public Normalfunc(Context context){
        //firebaseFunctions= FirebaseFunctions.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        this.context= context;

    }

    public List<String> splitstring(String ss){

        String[] array=ss.trim().split(" +");

        List<String> xx=new ArrayList<>();

        for(int i=0;i<array.length;i++){
            if(i>0)xx.addAll(splitchar(array[i].toLowerCase()));
        }

        xx.addAll(splitchar(ss.toLowerCase()));

        return xx;
    }

    public List<String> splitchar(String ss){

        List<String> xx=new ArrayList<>();
        String yy="";

        if(ss.length()>0 && ss.charAt(0)== '+'){
            xx.add(ss);
            ss=ss.substring(3);
        }

        for(int j=0;j<ss.length();j++){
            yy=yy + ss.charAt(j);
            xx.add(yy.toLowerCase());
        }

        return xx;
    }

}
