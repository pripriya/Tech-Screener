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
import android.util.Log;
import android.view.KeyEvent;
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

import com.geval6.techscreener.R;
import com.geval6.techscreener.Source.Activities.AdminActivity;
import com.geval6.techscreener.Source.Activities.ClientActivity;
import com.geval6.techscreener.Source.Activities.SetupActivity;
import com.geval6.techscreener.Source.Enumerations.Status;
import com.geval6.techscreener.Source.Fragments.LoginRegister.RegisterUserFragment;
import com.geval6.techscreener.Source.Modals.Client;
import com.geval6.techscreener.Source.Modals.Order;
import com.geval6.techscreener.Source.Modals.User;
import com.geval6.techscreener.Utilities.Managers.ArchiveManager;
import com.geval6.techscreener.Utilities.Managers.RequestManager.RequestIdentifier;
import com.geval6.techscreener.Utilities.Managers.RequestManager.RequestListener;
import com.geval6.techscreener.Utilities.Managers.RequestManager.WebRequest;

import java.util.ArrayList;
import java.util.HashMap;

public class OrdersFragment extends Fragment implements RequestListener {
    ListView listView;
    BottomSheetDialog bottomSheetDialog;
    LinearLayout progressCircle;
    private int id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_listview, container, false);
        prepareListView(view);
        setHasOptionsMenu(true);
        return view;
    }
    private void prepareListView(View parent) {
        listView = (ListView) parent.findViewById(R.id.listView);
        progressCircle = (LinearLayout) parent.findViewById(R.id.progressCircle);
        executeLoadOrders();
    }

    private BaseAdapter getAdapter(final ArrayList<Order> orders) {
        return new BaseAdapter() {
            @Override
            public int getCount() {
                return orders.size();
            }

            @Override
            public Object getItem(int position) {
                return orders.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.order_item, parent, false);

                Button statusButton;
                TextView textview, nametextview, descriptiontextview;

                statusButton = (Button) convertView.findViewById(R.id.statusButton);
                textview = (TextView) convertView.findViewById(R.id.textview);
                textview.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_black));
                nametextview = (TextView) convertView.findViewById(R.id.nametextview);
                nametextview.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_black));
                descriptiontextview = (TextView) convertView.findViewById(R.id.descriptiontextview);
                descriptiontextview.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_grey));

                nametextview.setText(String.valueOf(orders.get(position).name));

                if (orders.get(position).status.equals("Pending")) {
                    statusButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
                    statusButton.setText("Pending");
                    statusButton.getBackground().setColorFilter(ContextCompat.getColor(getActivity(), R.color.color_orange), PorterDuff.Mode.MULTIPLY);
                } else if (orders.get(position).status.equals("Approved")) {
                    statusButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
                    statusButton.setText("Approved");
                    statusButton.getBackground().setColorFilter(ContextCompat.getColor(getActivity(), R.color.color_green), PorterDuff.Mode.MULTIPLY);
                } else if (orders.get(position).status.equals(Status.hold)) {
                    statusButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
                    statusButton.setText("HOLD");
                    statusButton.getBackground().setColorFilter(ContextCompat.getColor(getActivity(), R.color.color_lightgrey), PorterDuff.Mode.MULTIPLY);
                } else if (orders.get(position).status.equals("Rejected")) {
                    statusButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
                    statusButton.setText("REJECTED");
                    statusButton.getBackground().setColorFilter(ContextCompat.getColor(getActivity(), R.color.color_maroon), PorterDuff.Mode.MULTIPLY);
                }
                statusButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (orders.get(position).status.equals(Status.pending)) {
                            showBottomDialog(2, orders.get(position).status);
                            id = orders.get(position).id;
                        } else if (orders.get(position).status.equals(Status.approved)) {
                            showBottomDialog(1, orders.get(position).status);
                            id = orders.get(position).id;
                        } else if (orders.get(position).status.equals(Status.hold)) {
                            showBottomDialog(1, orders.get(position).status);
                            id = orders.get(position).id;
                        } else if (orders.get(position).status.equals(Status.rejected)) {
                            showBottomDialog(1, orders.get(position).status);
                            id = orders.get(position).id;
                        }
                    }
                });

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle args = new Bundle();

                        args.putString("client_id", "91");
                        args.putString("user_id", "65");

                        CandidateFragment candidateFragment = new CandidateFragment();
                        candidateFragment.setArguments(args);
                        fragmentTransaction.replace(R.id.activity_main, candidateFragment,"candidate").addToBackStack(null).commit();
                    }
                });
                return convertView;
            }
        };
    }

    private void executeLoadOrders() {
        HashMap parameters = new HashMap();
        parameters.put("client_id", "91");
        parameters.put("user_id", "65");
        parameters.put("page", "0");
        parameters.put("size", "20");


        WebRequest webRequest = new WebRequest(RequestIdentifier.getOrders, parameters, OrdersFragment.this, getContext());
        webRequest.execute();
    }

    @Override
    public void onBeginRequest() {

        progressCircle.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestSucceeded(RequestIdentifier identifier, String message, Object... content) {
        progressCircle.setVisibility(View.GONE);
        if (identifier == RequestIdentifier.getOrders) {
            ArrayList<Order> orders = (ArrayList<Order>) content[0];
            listView.setAdapter(getAdapter(orders));
        }
    }

    @Override
    public void onRequestFailed(RequestIdentifier identifier, String message, Object... content) {

    }

    public void showBottomDialog(int count, String status) {
        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        bottomSheetDialog = new BottomSheetDialog(getActivity());
        View view = (View) sheetView.findViewById(R.id.view);
        view.setVisibility(View.GONE);
        TextView textView = (TextView) sheetView.findViewById(R.id.textview);
        textView.setVisibility(View.GONE);
        ListView listView = (ListView) sheetView.findViewById(R.id.listview);
        listView.setAdapter(BottomAdapter(count, status));
        listView.addFooterView(footerView());
        listView.setDivider(null);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();
    }

    public BaseAdapter BottomAdapter(final int count, final String status) {
        return new BaseAdapter() {
            @Override
            public int getCount() {
                return count;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.button, parent, false);

                Button button = (Button) convertView.findViewById(R.id.button);


                if (count == 2 && status.equals(Status.pending)) {
                    if (position == 0) {
                        button.setText("Approve");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bottomSheetDialog.dismiss();
                                executeOrderRequest("1");
                            }
                        });
                    } else if (position == 1) {
                        button.setText("Reject");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bottomSheetDialog.dismiss();
                                executeOrderRequest("3");
                            }
                        });
                    }
                } else if (count == 1) {
                    if (status.equals(Status.approved)) {
                        if (position == 0) {
                            button.setText("Hold");
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    bottomSheetDialog.dismiss();
                                    executeOrderRequest("5");
                                }
                            });
                        }
                    } else if (status.equals(Status.hold)) {
                        if (position == 0) {
                            button.setText("Approve");
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    bottomSheetDialog.dismiss();
                                    executeOrderRequest("1");
                                }
                            });
                        }
                    } else if (status.equals(Status.rejected)) {
                        if (position == 0) {
                            button.setText("Approve");
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    bottomSheetDialog.dismiss();
                                    executeOrderRequest("1");
                                }
                            });
                        }
                    }
                }
                return convertView;
            }
        };
    }

    public View footerView() {

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Button button = (Button) layoutInflater.inflate(R.layout.button, null, false).findViewById(R.id.button);
        button.setText("Cancel");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        return button;
    }

    private void executeOrderRequest(String status) {
        HashMap parameters = new HashMap();
        parameters.put("id", String.valueOf(id));
        parameters.put("status", status);

        WebRequest webRequest = new WebRequest(RequestIdentifier.updateAssessorStatus, parameters, OrdersFragment.this, getContext());
        webRequest.execute();
    }

    public void onResume() {
        super.onResume();
        if(this.getActivity() instanceof ClientActivity){
            ((ClientActivity) getActivity()).setActionBarTitle("Order");
        }
       else if(this.getActivity() instanceof AdminActivity){
            ((AdminActivity) getActivity()).setActionBarTitle("Order");
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_add, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (item.getItemId()) {
            case R.id.add:
                OrderEditorFragment orderEditorFragment = new OrderEditorFragment();
                fragmentTransaction.replace(R.id.activity_main, orderEditorFragment, "orderEditor").addToBackStack(null);
                fragmentTransaction.commit();
                return true;

//            case R.id.action_settings:
//                SettingsFragment settingsFragment = new SettingsFragment();
//                fragmentTransaction.replace(R.id.activity_main, settingsFragment, "settings").addToBackStack(null);
//                fragmentTransaction.commit();
//                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
