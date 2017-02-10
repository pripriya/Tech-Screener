package com.geval6.techscreener.Source.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.geval6.techscreener.R;
import com.geval6.techscreener.Source.Activities.AdminActivity;
import com.geval6.techscreener.Source.Activities.ClientActivity;
import com.geval6.techscreener.Source.Activities.SetupActivity;

public class SettingsFragment extends Fragment {
    ListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_listview, container, false);
        prepareListView(view);
        return view;
    }

    private void prepareListView(View parent) {
        listView = (ListView) parent.findViewById(R.id.listView);
        listView.setAdapter(getAdapter());
        listView.setDivider(null);
        listView.setPadding(0, 20, 0, 20);
    }

    private BaseAdapter getAdapter() {
        return new BaseAdapter() {
            @Override
            public int getCount() {
                return 0;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return convertView;
            }
        };
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logout:
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("ArchiveManager", Context.MODE_PRIVATE).edit();
                editor.putInt("userType", 0);
                editor.commit();
                Toast.makeText(getActivity(), "Log Out Successful", Toast.LENGTH_SHORT).show();
                getActivity().finish();
                startActivity(new Intent(getActivity(), SetupActivity.class));
                return true;
//            case android.R.id.home:
//                Log.i("true", "tr");
//                getFragmentManager().popBackStack();
//                return true;

        }
        return super.onOptionsItemSelected(item);

    }

    public void onResume() {
        super.onResume();
        if (this.getActivity() instanceof ClientActivity) {
            ((ClientActivity) getActivity()).setActionBarTitle("Settings");
        } else if (this.getActivity() instanceof AdminActivity) {
            ((AdminActivity) getActivity()).setActionBarTitle("Settings");
        }
    }

}
