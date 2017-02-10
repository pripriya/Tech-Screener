package com.geval6.techscreener.Source.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.geval6.techscreener.R;
import com.geval6.techscreener.Source.Fragments.LoginRegister.LoginFragment;
import com.geval6.techscreener.Source.Fragments.LoginRegister.RegisterClientFragment;
import com.geval6.techscreener.Source.Fragments.LoginRegister.RegisterUserFragment;
import com.geval6.techscreener.Utilities.Managers.ArchiveManager;


public class SetupActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
        fragmentTransaction.replace(R.id.activity_main, loginFragment, "TAG");
        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {
            super.onBackPressed();

    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                RegisterUserFragment registerUser = (RegisterUserFragment) manager.findFragmentByTag("registerUser");
                fragmentTransaction.remove(registerUser);
                fragmentTransaction.commit();
                manager.popBackStack();
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setHomeButtonEnabled(false);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
