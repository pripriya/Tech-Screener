package com.geval6.techscreener.Source.Modals;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class Admin {

    public int id;
    public String firstname;
    public String lastname;
    public String email;
    public String mobile;

    public Admin(HashMap hashMap) {
        this.id = (hashMap.containsKey("id") && hashMap.get("id") instanceof Integer) ? (Integer) hashMap.get("id") : 0;
        this.firstname = (hashMap.containsKey("firstname") && hashMap.get("firstname") instanceof String) ? (String) hashMap.get("firstname") : "";
        this.lastname = (hashMap.containsKey("lastname") && hashMap.get("lastname") instanceof String) ? (String) hashMap.get("lastname") : "";
        this.email = (hashMap.containsKey("email") && hashMap.get("email") instanceof String) ? (String) hashMap.get("email") : "";
        this.mobile = (hashMap.containsKey("mobile") && hashMap.get("mobile") instanceof String) ? (String) hashMap.get("mobile") : "";
    }

    public String encode()
    {
        HashMap hashMap = new HashMap();
        hashMap.put("id",id);
        hashMap.put("firstname", firstname);
        hashMap.put("lastname", lastname);
        hashMap.put("email", email);
        hashMap.put("mobile", mobile);

        return (new Gson()).toJson(hashMap);
    }

    public static Admin decode(String string)
    {
        return new Admin((HashMap)(new Gson()).fromJson(string, HashMap.class));
    }
}
