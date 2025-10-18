package com.example.pollapp.messaging;

public class Jsons {
  
  public static String s(String v){
    return v==null ? "null" : "\"" + v.replace("\\","\\\\").replace("\"","\\\"") + "\"";
  }
}
