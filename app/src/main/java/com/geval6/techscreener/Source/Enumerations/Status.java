package com.geval6.techscreener.Source.Enumerations;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;

import com.geval6.techscreener.R;

public class Status {

    public static String pending = "Pending";
    public static String approved = "Approved";
    public static String rejected = "Rejected";
    public static String hold = "Hold";


    public enum UpdateStatus {
        pending,
        approved,
        rejected,
        hold,
        none;
    }

    public static String value(String rawValue) {
        switch (rawValue) {
            case "PENDING":
                return pending;
            case "APPROVED":
                return approved;
            case "REJECTED":
                return rejected;
            case "HOLD":
                return hold;
            default:
                return null;
        }
    }
        static int numeralForStatus(UpdateStatus status){
            switch (status) {
                case pending:
                return 2;
                case approved:
                return 1;
                case rejected:
                return 3;
                case hold:
                return 5;
                default:
                    return 0;
            }
        }
}
