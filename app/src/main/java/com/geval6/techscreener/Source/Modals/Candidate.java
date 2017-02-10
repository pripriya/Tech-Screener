package com.geval6.techscreener.Source.Modals;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;


public class Candidate {


    public int id;
    public String status;
    public String firstname="";
    public String lastname="";
    public String email="";
    public String mobile="";
    public String skype="";
    public Double experience=0.0;


    public Candidate(HashMap hashMap) {

        this.status = (hashMap.containsKey("status") && hashMap.get("status") instanceof String) ? (String) hashMap.get("status") : "";
        this.id = (hashMap.containsKey("id") && hashMap.get("id") instanceof Integer) ? (Integer) hashMap.get("id") : 0;
        this.firstname = (hashMap.containsKey("firstname") && hashMap.get("firstname") instanceof String) ? (String) hashMap.get("firstname") : "";
        this.lastname = (hashMap.containsKey("lastname") && hashMap.get("lastname") instanceof String) ? (String) hashMap.get("lastname") : "";
        this.email = (hashMap.containsKey("email") && hashMap.get("email") instanceof String) ? (String) hashMap.get("email") : "";
        this.mobile = (hashMap.containsKey("mobile") && hashMap.get("mobile") instanceof String) ? (String) hashMap.get("mobile") : "";
        this.skype = (hashMap.containsKey("skype") && hashMap.get("skype") instanceof String) ? (String) hashMap.get("skype") : "";
        this.experience = (hashMap.containsKey("experience") && hashMap.get("experience") instanceof Double) ? (Double) hashMap.get("experience") : 0.0;


    }

    public String encode() {
        HashMap hashMap = new HashMap();
        hashMap.put("id", id);
        hashMap.put("firstname", firstname);
        hashMap.put("lastname", lastname);
        hashMap.put("email", email);
        hashMap.put("mobile", mobile);
        hashMap.put("skype", skype);
        hashMap.put("experience", experience);

        return (new Gson()).toJson(hashMap);
    }

    public static Candidate decode(String string) {
        return new Candidate((HashMap) (new Gson()).fromJson(string, HashMap.class));
    }
    public static ArrayList<Candidate> getObjects(ArrayList<HashMap> arrayList) {
        ArrayList<Candidate> users = new ArrayList<Candidate>();
        for (HashMap item : arrayList) {
            users.add(new Candidate(item));
        }
        return users;
    }
}
