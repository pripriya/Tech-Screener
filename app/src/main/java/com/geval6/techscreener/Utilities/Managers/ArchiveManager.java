package com.geval6.techscreener.Utilities.Managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.geval6.techscreener.Source.Enumerations.UserType;
import com.geval6.techscreener.Source.Modals.Admin;
import com.geval6.techscreener.Source.Modals.Assessor;
import com.geval6.techscreener.Source.Modals.Client;
import com.geval6.techscreener.Source.Modals.User;


public class ArchiveManager {

    public static Context context;

    private static SharedPreferences sharedPreferences;

    private static SharedPreferences.Editor sharedPreferencesEditor;


    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        ArchiveManager.context = context;
        sharedPreferences = context.getSharedPreferences("ArchiveManager", Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
    }


    public static UserType userType;

    public static UserType getUserType() {
        userType = UserType.fromInteger(sharedPreferences.getInt("userType", 0));
        return userType;
    }

    public static void setUserType(UserType userType) {
        ArchiveManager.userType = userType;
        sharedPreferencesEditor.putInt("userType", userType.ordinal());
        sharedPreferencesEditor.commit();
    }


    private static Admin admin = null;

    public static Admin getAdmin() {
        admin = Admin.decode(sharedPreferences.getString("admin", ""));
        return admin;
    }

    public static void setAdmin(Admin admin) {
        if (admin != null) {
            ArchiveManager.userType = (admin != null) ? UserType.Admin : UserType.None;
            sharedPreferencesEditor.putString("admin", admin.encode());
            sharedPreferencesEditor.commit();
        }
    }

    private static Client client=null;

    public static Client getClient() {
        client = Client.decode(sharedPreferences.getString("client", ""));
        return client;
    }

    public static void setClient(Client client) {
        ArchiveManager.userType = (client != null) ? UserType.Client : UserType.None;
        sharedPreferencesEditor.putString("client", client.encode());
        sharedPreferencesEditor.commit();
    }

    private static Assessor assessor=null;

    public static Assessor getAssessor() {

        assessor = Assessor.decode(sharedPreferences.getString("assessor", ""));
        return assessor;
    }

    public static void setAssessor(Assessor assessor) {

        if (assessor != null) {
            ArchiveManager.userType = (assessor != null) ? UserType.Assessor : UserType.None;
            sharedPreferencesEditor.putString("assessor", assessor.encode());
            sharedPreferencesEditor.commit();
        }
    }

    private static User user = null;

    public static User getUser() {
        user = User.decode(sharedPreferences.getString("assessor", ""));
        return user;
    }

    public static void setUser(User user) {
        if (user != null) {
            ArchiveManager.userType = (user != null) ? UserType.User : UserType.None;
            sharedPreferencesEditor.putString("assessor", user.encode());
            sharedPreferencesEditor.commit();
        }
    }
}