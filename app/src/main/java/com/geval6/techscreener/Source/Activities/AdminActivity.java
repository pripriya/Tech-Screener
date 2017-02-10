package com.geval6.techscreener.Source.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.geval6.techscreener.R;
import com.geval6.techscreener.Source.Adapters.NavigationDrawerAdapter;
import com.geval6.techscreener.Source.Fragments.AssessorsFragment;
import com.geval6.techscreener.Source.Fragments.ClientsFragment;
import com.geval6.techscreener.Source.Fragments.OrdersFragment;
import com.geval6.techscreener.Source.Fragments.SettingsFragment;
import com.geval6.techscreener.Source.Modals.DrawerItems;

public class
AdminActivity extends AppCompatActivity {

    private String[] navigationDrawerItemTitles;
    private DrawerLayout drawerLayout;
    private ListView listView;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    ActionBarDrawerToggle mDrawerToggle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        mTitle = mDrawerTitle = getTitle();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, new ClientsFragment()).commit();
        }
        mTitle = mDrawerTitle = getTitle();
        navigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listView = (ListView) findViewById(R.id.left_drawer);

        setupToolbar();

        DrawerItems[] drawerItem = new DrawerItems[4];

        drawerItem[0] = new DrawerItems(R.drawable.client, "Clients");
        drawerItem[1] = new DrawerItems(R.drawable.client, "Assessors");
        drawerItem[2] = new DrawerItems(R.drawable.order, "Orders");
        drawerItem[3] = new DrawerItems(R.drawable.settings, "Settings");

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(this, R.layout.navigation_drawer_items, drawerItem);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new DrawerItemClickListener());
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.addDrawerListener(mDrawerToggle);
        setupDrawerToggle();

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new ClientsFragment();
                break;
            case 1:
                fragment = new AssessorsFragment();
                break;
            case 2:
                fragment = new OrdersFragment();
                break;
            case 3:
                fragment = new SettingsFragment();
                break;
            default:
                fragment = new ClientsFragment();
                break;
        }

        if (fragment != null) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.activity_main, fragment).commit();

                listView.setItemChecked(position, true);
                listView.setSelection(position);
                setTitle(navigationDrawerItemTitles[position]);
                drawerLayout.closeDrawer(listView);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    void setupDrawerToggle() {
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerToggle.syncState();
    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
