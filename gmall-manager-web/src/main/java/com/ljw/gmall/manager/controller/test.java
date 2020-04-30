package com.ljw.gmall.manager.controller;

public class test {

    public static int zimu(String [] attr){





        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < attr.length; i++){
            sb. append(attr[i]);
        }
        String s = sb.toString();
        int len=s.length();
        char [] chars = s.toCharArray();
        for(int i=0;i<chars.length;i++){
           for (int j=i+1;j<chars.length;j++)
               if (chars[i]==chars[j])
                   len--;
        }
        return len;

    }


    //串联

    public static String[] chuanliann(String []attr){
        int len=attr.length;
        int sumzu= len*(len-1)-(len*(len-1))/2;
        String a[] = new String[sumzu];
        for (int i=0;i<sumzu;i++){
            a[i]=attr[i]+attr[i+1];
        }
        return a;
    }



    public static void main(String[] args) {
        String [] attr={"un","iq","ue"};
        System.out.println(zimu(attr));
    }
}
