package com.geval6.techscreener.Source.Modals;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class Order {

    public int id;
    public String name = "";
    public String status = "";


    public Order(HashMap hashMap) {
        this.id = (hashMap.containsKey("id") && hashMap.get("id") instanceof Integer) ? (Integer) hashMap.get("id") : 0;
        this.name = (hashMap.containsKey("title") && hashMap.get("title") instanceof String) ? (String) hashMap.get("title") : "";
        this.status = (hashMap.containsKey("status") && hashMap.get("status") instanceof String) ? (String) hashMap.get("status") : "";
    }

    public static ArrayList<Order> getObjects(ArrayList<HashMap> arrayList) {
        ArrayList<Order> order = new ArrayList<Order>();
        for (HashMap item : arrayList) {
            order.add(new Order(item));
        }
        return order;
    }
}
