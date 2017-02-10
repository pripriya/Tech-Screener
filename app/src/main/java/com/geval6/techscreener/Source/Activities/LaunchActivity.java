package com.geval6.techscreener.Source.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.geval6.techscreener.R;
import com.geval6.techscreener.Source.Enumerations.UserType;
import com.geval6.techscreener.Source.Fragments.CandidateFragment;
import com.geval6.techscreener.Source.Fragments.LoginRegister.RegisterUserFragment;
import com.geval6.techscreener.Utilities.Managers.ArchiveManager;


public class LaunchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArchiveManager.setContext(this.getApplicationContext());
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (ArchiveManager.getUserType().equals(UserType.Admin)) {
            Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
            startActivity(intent);
        } else if (ArchiveManager.getUserType().equals(UserType.Assessor)) {
            Intent intent = new Intent(getApplicationContext(), AssessorActivity.class);
            startActivity(intent);
        } else if (ArchiveManager.getUserType().equals(UserType.Client)) {
            Intent intent = new Intent(getApplicationContext(), ClientActivity.class);
            startActivity(intent);
        }
        else if (ArchiveManager.getUserType().equals(UserType.User)) {
            Intent intent = new Intent(getApplicationContext(), ClientActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(getApplicationContext(), SetupActivity.class);
            startActivity(intent);
        }
    }


}

