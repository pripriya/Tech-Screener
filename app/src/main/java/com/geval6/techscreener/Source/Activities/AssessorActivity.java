package com.geval6.techscreener.Source.Activities;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.geval6.techscreener.R;
import com.geval6.techscreener.Source.Fragments.OrderEditorFragment;
import com.geval6.techscreener.Source.Fragments.SettingsFragment;

import java.util.zip.Inflater;

public class AssessorActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
