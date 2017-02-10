package com.geval6.techscreener.Source.Fragments;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.geval6.techscreener.R;
import com.geval6.techscreener.Source.Enumerations.Status;
import com.geval6.techscreener.Source.Modals.Client;
import com.geval6.techscreener.Utilities.Managers.RequestManager.RequestIdentifier;
import com.geval6.techscreener.Utilities.Managers.RequestManager.RequestListener;
import com.geval6.techscreener.Utilities.Managers.RequestManager.WebRequest;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientsFragment extends Fragment implements RequestListener {

    ListView listView;
    LinearLayout progressCircle;
    private int id;
    BottomSheetDialog bottomSheetDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_listview, container, false);
        prepareView(view);
        return view;
    }

    private void prepareView(View parent) {
        listView = (ListView) parent.findViewById(R.id.listView);
        progressCircle = (LinearLayout) parent.findViewById(R.id.progressCircle);
        executeRequest();
    }

    private BaseAdapter getAdapter(final ArrayList<Client> clients) {
        return new BaseAdapter() {
            @Override
            public int getCount() {
                return clients.size();
            }

            @Override
            public Object getItem(int position) {
                return clients.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {

                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.client_item, parent, false);


                final Button statusButton;
                TextView nameTextview, orgnameTextview, locationTextview;

                statusButton = (Button) convertView.findViewById(R.id.statusButton);
                nameTextview = (TextView) convertView.findViewById(R.id.nameTextView);
                nameTextview.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_black));
                orgnameTextview = (TextView) convertView.findViewById(R.id.orgNameTextView);
                orgnameTextview.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_black));
                locationTextview = (TextView) convertView.findViewById(R.id.locationTextView);
                locationTextview.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_grey));

                nameTextview.setText(clients.get(position).name);
                nameTextview.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_blue));
                orgnameTextview.setText(clients.get(position).name);

                locationTextview.setText(clients.get(position).city + ", " + clients.get(position).country);

                if (clients.get(position).status.equals(Status.pending)) {
                    statusButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
                    statusButton.setText("PENDING");
                    statusButton.getBackground().setColorFilter(ContextCompat.getColor(getActivity(), R.color.color_orange), PorterDuff.Mode.MULTIPLY);
                } else if (clients.get(position).status.equals(Status.approved)) {
                    statusButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
                    statusButton.setText("APPROVED");
                    statusButton.getBackground().setColorFilter(ContextCompat.getColor(getActivity(), R.color.color_green), PorterDuff.Mode.MULTIPLY);
                } else if (clients.get(position).status.equals(Status.hold)) {
                    statusButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
                    statusButton.setText("HOLD");
                    statusButton.getBackground().setColorFilter(ContextCompat.getColor(getActivity(), R.color.color_lightgrey), PorterDuff.Mode.MULTIPLY);
                } else if (clients.get(position).status.equals(Status.rejected)) {
                    statusButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_white));
                    statusButton.setText("REJECTED");
                    statusButton.getBackground().setColorFilter(ContextCompat.getColor(getActivity(), R.color.color_maroon), PorterDuff.Mode.MULTIPLY);
                }
                statusButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clients.get(position).status.equals(Status.pending)) {
                            showBottomDialog(2, clients.get(position).status);
                            id = clients.get(position).id;
                        } else if (clients.get(position).status.equals(Status.approved)) {
                            showBottomDialog(1, clients.get(position).status);
                            id = clients.get(position).id;
                        } else if (clients.get(position).status.equals(Status.hold)) {
                            showBottomDialog(1, clients.get(position).status);
                            id = clients.get(position).id;
                        } else if (clients.get(position).status.equals(Status.rejected)) {
                            showBottomDialog(1, clients.get(position).status);
                            id = clients.get(position).id;
                        }
                    }
                });
                return convertView;
            }
        };
    }

    private void executeRequest() {
        progressCircle.setVisibility(View.VISIBLE);
        HashMap parameters = new HashMap();
        parameters.put("page", "0");
        parameters.put("size", "20");

        WebRequest webRequest = new WebRequest(RequestIdentifier.getClients, parameters, ClientsFragment.this, getContext());
        webRequest.execute();
    }

    @Override
    public void onBeginRequest() {

    }

    @Override
    public void onRequestSucceeded(RequestIdentifier identifier, String message, Object... content) {

        progressCircle.setVisibility(View.GONE);
        if (identifier == RequestIdentifier.getClients) {
            ArrayList<Client> clients = (ArrayList<Client>) content[0];
            listView.setAdapter(getAdapter(clients));
        } else if (identifier == RequestIdentifier.updateClientStatus) {
            executeRequest();
            Log.i("message", message);
        }
    }

    @Override
    public void onRequestFailed(RequestIdentifier identifier, String message, Object... content) {
        progressCircle.setVisibility(View.GONE);

        if (identifier == RequestIdentifier.getClients) {
            Log.i("message", message);
        } else if (identifier == RequestIdentifier.updateClientStatus) {
            Log.i("message", message);
        }
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
                                executeClientRequest("1");
                            }
                        });
                    } else if (position == 1) {
                        button.setText("Reject");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bottomSheetDialog.dismiss();
                                executeClientRequest("3");
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
                                    executeClientRequest("5");
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
                                    executeClientRequest("1");
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
                                    executeClientRequest("1");
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

    private void executeClientRequest(String status) {
        HashMap parameters = new HashMap();
        parameters.put("id", String.valueOf(id));
        parameters.put("status", status);
        WebRequest webRequest = new WebRequest(RequestIdentifier.updateClientStatus, parameters, ClientsFragment.this, getContext());
        webRequest.execute();
    }
}


