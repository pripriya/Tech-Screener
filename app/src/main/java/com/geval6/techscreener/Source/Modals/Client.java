package com.geval6.techscreener.Source.Modals;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class Client {

    public String status;
    public int id;
    public String name;
    public String address;
    public String city;
    public String state;
    public String country;
    public String zip;

    public String phone;
    public String email;
    public String website;

    public User user;


    public Client(HashMap hashMap) {

        this.status = (hashMap.containsKey("status") && hashMap.get("status") instanceof String) ? (String) hashMap.get("status") : "";
        this.id = (hashMap.containsKey("id") && hashMap.get("id") instanceof Integer) ? (Integer) hashMap.get("id") : 0;
        this.name = (hashMap.containsKey("name") && hashMap.get("name") instanceof String) ? (String) hashMap.get("name") : "";
        this.address = (hashMap.containsKey("address") && hashMap.get("address") instanceof String) ? (String) hashMap.get("address") : "";

        this.city = (hashMap.containsKey("city") && hashMap.get("city") instanceof String) ? (String) hashMap.get("city") : "";
        this.state = (hashMap.containsKey("state") && hashMap.get("state") instanceof String) ? (String) hashMap.get("state") : "";
        this.country = (hashMap.containsKey("country") && hashMap.get("country") instanceof String) ? (String) hashMap.get("country") : "";
        this.zip = (hashMap.containsKey("zip") && hashMap.get("zip") instanceof String) ? (String) hashMap.get("zip") : "";
        this.phone = (hashMap.containsKey("phone") && hashMap.get("phone") instanceof String) ? (String) hashMap.get("phone") : "";

        this.email = (hashMap.containsKey("email") && hashMap.get("email") instanceof String) ? (String) hashMap.get("email") : "";
        this.website = (hashMap.containsKey("website") && hashMap.get("website") instanceof String) ? (String) hashMap.get("website") : "";

        this.user = (hashMap.containsKey("user") && hashMap.get("user") instanceof HashMap) ? new User((HashMap) hashMap.get("user")) : null;
    }

    public String encode() {
        HashMap hashMap = new HashMap();
        hashMap.put("id", id);
        hashMap.put("name", name);
        hashMap.put("address", address);
        hashMap.put("city", city);
        hashMap.put("state", state);
        hashMap.put("country", country);
        hashMap.put("zip", zip);
        hashMap.put("phone", phone);
        hashMap.put("email", email);
        hashMap.put("website", website);
        return (new Gson()).toJson(hashMap);
    }

    public static Client decode(String string) {
        return new Client((HashMap) (new Gson()).fromJson(string, HashMap.class));
    }

    public static ArrayList<Client> getObjects(ArrayList<HashMap> arrayList) {
        ArrayList<Client> client = new ArrayList<Client>();
        for (HashMap item : arrayList) {
            client.add(new Client(item));
        }
        return client;
    }
}
