package com.geval6.techscreener.Source.Enumerations;


public enum UserType {
    None,
    Admin,
    Client,
    Assessor,
    User;

    public static UserType fromInteger(int ordinal) {
        switch (ordinal) {
            case 1:
                return Admin;
            case 2:
                return Client;
            case 3:
                return Assessor;
            case 4:
                return User;
            default:
                return None;
        }
    }
}
