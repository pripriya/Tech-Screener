package com.geval6.techscreener.Source.Fragments;


import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.geval6.techscreener.R;
import com.geval6.techscreener.Source.Activities.AdminActivity;
import com.geval6.techscreener.Source.Activities.ClientActivity;
import com.geval6.techscreener.Source.Enumerations.Status;
import com.geval6.techscreener.Source.Modals.Candidate;
import com.geval6.techscreener.Utilities.Managers.RequestManager.RequestIdentifier;
import com.geval6.techscreener.Utilities.Managers.RequestManager.RequestListener;
import com.geval6.techscreener.Utilities.Managers.RequestManager.WebRequest;

import java.util.ArrayList;
import java.util.HashMap;

public class CandidateFragment extends Fragment implements RequestListener {
    ListView listView;
    LinearLayout progressCircle;
    private int id;
    String client_id, user_id;
    BottomSheetDialog bottomSheetDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        client_id = getArguments().getString("client_id");
        user_id = getArguments().getString("user_id");
        View view = inflater.inflate(R.layout.layout_listview, container, false);
        prepareListView(view);
        return view;
    }

    private void prepareListView(View parent) {
        listView = (ListView) parent.findViewById(R.id.listView);
        progressCircle = (LinearLayout) parent.findViewById(R.id.progressCircle);
        executeLoadCandidates();
    }

    private BaseAdapter getAdapter(final ArrayList<Candidate> candidates) {
        return new BaseAdapter() {
            @Override
            public int getCount() {
                return candidates.size();
            }

            @Override
            public Object getItem(final int position) {
                return candidates.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.candidate_item, parent, false);
                Button statusButton;
                TextView experienceTextview, nameTextview, emailTextview;

                statusButton = (Button) convertView.findViewById(R.id.statusButton);
                experienceTextview = (TextView) convertView.findViewById(R.id.experiencetextview);
                experienceTextview.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_black));
                nameTextview = (TextView) convertView.findViewById(R.id.nametextview);
                nameTextview.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_black));
                emailTextview = (TextView) convertView.findViewById(R.id.emailtextview);
                emailTextview.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_grey));

                experienceTextview.setText(String.valueOf(candidates.get(position).experience));
                nameTextview.setText(candidates.get(position).firstname + " " + candidates.get(position).lastname);
                emailTextview.setText(candidates.get(position).email);

                if (candidates.get(position).status.equals("Pending")) {
                    statusButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
                    statusButton.setText("Pending");
                    statusButton.getBackground().setColorFilter(ContextCompat.getColor(getActivity(), R.color.color_orange), PorterDuff.Mode.MULTIPLY);
                } else if (candidates.get(position).status.equals("Approved")) {
                    statusButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
                    statusButton.setText("Approved");
                    statusButton.getBackground().setColorFilter(ContextCompat.getColor(getActivity(), R.color.color_green), PorterDuff.Mode.MULTIPLY);
                } else if (candidates.get(position).status.equals(Status.hold)) {
                    statusButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
                    statusButton.setText("HOLD");
                    statusButton.getBackground().setColorFilter(ContextCompat.getColor(getActivity(), R.color.color_lightgrey), PorterDuff.Mode.MULTIPLY);
                } else if (candidates.get(position).status.equals("Rejected")) {
                    statusButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
                    statusButton.setText("REJECTED");
                    statusButton.getBackground().setColorFilter(ContextCompat.getColor(getActivity(), R.color.color_maroon), PorterDuff.Mode.MULTIPLY);
                }

//                statusButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (candidates.get(position).status.equals(Status.pending)) {
//                            showBottomDialog(2, candidates.get(position).status);
//                            id = candidates.get(position).id;
//                        } else if (candidates.get(position).status.equals(Status.approved)) {
//                            showBottomDialog(1, candidates.get(position).status);
//                            id = candidates.get(position).id;
//                        } else if (candidates.get(position).status.equals(Status.hold)) {
//                            showBottomDialog(1, candidates.get(position).status);
//                            id = candidates.get(position).id;
//                        } else if (candidates.get(position).status.equals(Status.rejected)) {
//                            showBottomDialog(1, candidates.get(position).status);
//                            id = candidates.get(position).id;
//                        }
//                    }
//                });
                return convertView;
            }
        };
    }

    private void executeLoadCandidates() {
        HashMap parameters = new HashMap();

        parameters.put("client_id", client_id);
        parameters.put("user_id", user_id);
        parameters.put("page", "0");
        parameters.put("size", "10");

        WebRequest webRequest = new WebRequest(RequestIdentifier.getCandidates, parameters, CandidateFragment.this, getContext());
        webRequest.execute();
    }

    @Override
    public void onBeginRequest() {
        progressCircle.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestSucceeded(RequestIdentifier identifier, String message, Object... content) {

        progressCircle.setVisibility(View.GONE);
        if (identifier == RequestIdentifier.getCandidates) {
            ArrayList<Candidate> candidates = (ArrayList<Candidate>) content[0];
            listView.setAdapter(getAdapter(candidates));
        }
    }

    @Override
    public void onRequestFailed(RequestIdentifier identifier, String message, Object... content) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void onResume() {
        super.onResume();
        if (this.getActivity() instanceof ClientActivity) {
            ((ClientActivity) getActivity()).setActionBarTitle("Candidates");
        } else if (this.getActivity() instanceof AdminActivity) {
            ((AdminActivity) getActivity()).setActionBarTitle("Candidates");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_add, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.action_settings);
        menuItem.setVisible(isHidden());
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CandidateEditorFragment candidateEditorFragment = new CandidateEditorFragment();
                fragmentTransaction.replace(R.id.activity_main, candidateEditorFragment, "candidateEditor").addToBackStack(null);
                fragmentTransaction.commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
