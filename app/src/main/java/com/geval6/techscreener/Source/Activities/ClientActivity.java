package com.geval6.techscreener.Source.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.geval6.techscreener.R;
import com.geval6.techscreener.Source.Fragments.CandidateEditorFragment;
import com.geval6.techscreener.Source.Fragments.CandidateFragment;
import com.geval6.techscreener.Source.Fragments.OrderEditorFragment;
import com.geval6.techscreener.Source.Fragments.OrdersFragment;
import com.geval6.techscreener.Source.Fragments.SettingsFragment;


public class ClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        OrdersFragment ordersFragment = new OrdersFragment();
        fragmentTransaction.replace(R.id.activity_main, ordersFragment, "orders");
        fragmentTransaction.commit();
    }


    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        CandidateFragment candidateFragment = (CandidateFragment) manager.findFragmentByTag("candidate");
        OrdersFragment ordersFragment = (OrdersFragment) manager.findFragmentByTag("orders");
        OrderEditorFragment orderEditorFragment = (OrderEditorFragment) manager.findFragmentByTag("orderEditor");
        CandidateEditorFragment candidateEditorFragment = (CandidateEditorFragment) manager.findFragmentByTag("candidateEditor");


        switch (item.getItemId()) {
            case android.R.id.home:
              //  fragmentTransaction.remove(candidateFragment);
                fragmentTransaction.commit();
                manager.popBackStack();
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                return true;

            case R.id.action_settings:
                SettingsFragment settingsFragment = new SettingsFragment();
                fragmentTransaction.replace(R.id.activity_main, settingsFragment, "settings").addToBackStack(null);
                fragmentTransaction.commit();
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        Fragment orders = fragmentManager.findFragmentByTag("orders");
//        Fragment orderEditor = fragmentManager.findFragmentByTag("orderEditor");
//        Fragment candidateEditor = fragmentManager.findFragmentByTag("candidateEditor");
//        Fragment candidate = fragmentManager.findFragmentByTag("candidate");
//
//
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        if (orders.isVisible()) {
//            Log.i("true", "true");
//            switch (item.getItemId()) {
//
//                case R.id.add:
//                    OrderEditorFragment orderEditorFragment = new OrderEditorFragment();
//                    fragmentTransaction.replace(R.id.activity_main, orderEditorFragment, "orderEditor").addToBackStack(null);
//                    fragmentTransaction.commit();
//                    return true;
//
//                case R.id.action_settings:
//                    SettingsFragment settingsFragment = new SettingsFragment();
//                    fragmentTransaction.replace(R.id.activity_main, settingsFragment, "settings").addToBackStack(null);
//                    fragmentTransaction.commit();
//                    return true;
//            }
//        } else if (orderEditor.isVisible()) {
//            switch (item.getItemId()) {
//                case R.id.action_back:
//                    getFragmentManager().popBackStack();
//                    return true;
//            }
//        } else if (candidate.isVisible()) {
//            switch (item.getItemId()) {
//                case R.id.add:
//                    CandidateEditorFragment candidateEditorFragment = new CandidateEditorFragment();
//                    fragmentTransaction.replace(R.id.activity_main, candidateEditorFragment, "candidateEditor").addToBackStack(null);
//                    fragmentTransaction.commit();
//                    return true;
//            }
//        } else if (candidateEditor.isVisible()) {
//            switch (item.getItemId()) {
//                case R.id.action_back:
//                    getFragmentManager().popBackStack();
//                    return true;
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}

