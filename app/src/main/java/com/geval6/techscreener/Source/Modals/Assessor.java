package com.geval6.techscreener.Source.Modals;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class Assessor {

    public String status;
    public int id;
    public String firstname;
    public String lastname;
    public String email;
    public String mobile;
    public String company;
    public String designation;
    public double experience;
    public String summary;

    public Assessor(HashMap hashMap) {

        this.status = (hashMap.containsKey("status") && hashMap.get("status") instanceof String) ? (String) hashMap.get("status") : "";
        this.id = (hashMap.containsKey("id") && hashMap.get("id") instanceof Integer) ? (Integer) hashMap.get("id") : 0;
        this.firstname = (hashMap.containsKey("firstname") && hashMap.get("firstname") instanceof String) ? (String) hashMap.get("firstname") : "";
        this.lastname = (hashMap.containsKey("lastname") && hashMap.get("lastname") instanceof String) ? (String) hashMap.get("lastname") : "";
        this.email = (hashMap.containsKey("email") && hashMap.get("email") instanceof String) ? (String) hashMap.get("email") : "";
        this.mobile = (hashMap.containsKey("mobile") && hashMap.get("mobile") instanceof String) ? (String) hashMap.get("mobile") : "";
        this.company = (hashMap.containsKey("company") && hashMap.get("company") instanceof String) ? (String) hashMap.get("company") : "";
        this.designation = (hashMap.containsKey("designation") && hashMap.get("designation") instanceof String) ? (String) hashMap.get("designation") : "";
        this.experience = (hashMap.containsKey("experience") && hashMap.get("experience") instanceof Double) ? (Double) hashMap.get("experience") : 0;
        this.summary = (hashMap.containsKey("summary") && hashMap.get("summary") instanceof String) ? (String) hashMap.get("summary") : "";
    }

    public String encode() {
        HashMap hashMap = new HashMap();
        hashMap.put("id", id);
        hashMap.put("firstname", firstname);
        hashMap.put("lastname", lastname);
        hashMap.put("email", email);
        hashMap.put("mobile", mobile);
        hashMap.put("company", company);
        hashMap.put("designation", designation);
        hashMap.put("experience", experience);
        hashMap.put("summary", experience);
        return (new Gson()).toJson(hashMap);
    }

    public static Assessor decode(String string) {

        return new Assessor((HashMap) (new Gson()).fromJson(string, HashMap.class));
    }

    public static ArrayList<Assessor> getObjects(ArrayList<HashMap> arrayList) {
        ArrayList<Assessor> assessor = new ArrayList<Assessor>();
        for (HashMap item : arrayList) {
            assessor.add(new Assessor(item));
        }
        return assessor;
    }
}
