package com.geval6.techscreener.Source.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.geval6.techscreener.R;
import com.geval6.techscreener.Source.Activities.AdminActivity;
import com.geval6.techscreener.Source.Activities.ClientActivity;
import com.geval6.techscreener.Source.Modals.Order;
import com.geval6.techscreener.Utilities.Managers.RequestManager.RequestIdentifier;
import com.geval6.techscreener.Utilities.Managers.RequestManager.RequestListener;
import com.geval6.techscreener.Utilities.Managers.RequestManager.WebRequest;

import java.util.ArrayList;
import java.util.HashMap;

public class CandidateEditorFragment extends Fragment implements RequestListener {
    ListView listView;
    public String firstname = "", lastname = "", email = "", mobile = "", skype = "", experience = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_listview, container, false);
        prepareListView(view);
        return view;
    }

    private void prepareListView(View parent) {
        LinearLayout linearLayout = (LinearLayout) parent.findViewById(R.id.linearLayout);
        linearLayout.setPadding(24, 0, 24, 0);
        listView = (ListView) parent.findViewById(R.id.listView);
        listView.setAdapter((getAdapter()));
        listView.setDivider(null);
        listView.setPadding(0, 20, 0, 20);
    }

    private BaseAdapter getAdapter() {
        return new BaseAdapter() {
            @Override
            public int getCount() {
                return 10;
            }

            @Override
            public Object getItem(final int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                String[] labels = {"", "FIRSTNAME", "LASTNAME", "EMAIL", "MOBILE", "SKYPE", "EXPERIENCE", "", "CREATE", ""};
                final String[] values = {firstname, lastname, email, mobile, skype, experience, ""};

                if (position == 0) {
                    convertView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.blank_layout, parent, false);
                } else if (position >= 1 && position < 7) {
                    convertView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.text_input_view, parent, false);

                    final TextView textView = (TextView) convertView.findViewById(R.id.textview);
                    final EditText editText = (EditText) convertView.findViewById(R.id.edittext);

                    textView.setText(labels[position]);
                    editText.setText(values[position]);

                    if (position == 1) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                    } else if (position == 2) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                    } else if (position == 3) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    } else if (position == 4) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                    } else if (position == 5) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                    } else if (position == 6) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                    }

                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (position == 1) {
                                firstname = s.toString();
                            } else if (position == 2) {
                                lastname = s.toString();
                            } else if (position == 3) {
                                email = s.toString();
                            } else if (position == 4) {
                                mobile = s.toString();
                            } else if (position == 5) {
                                skype = s.toString();
                            } else if (position == 6) {
                                experience = s.toString();
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });

                    if (position == 1) {
                        editText.setText(firstname);
                    } else if (position == 2) {
                        editText.setText(lastname);
                    } else if (position == 2) {
                        editText.setText(email);
                    } else if (position == 2) {
                        editText.setText(mobile);
                    } else if (position == 2) {
                        editText.setText(skype);
                    } else if (position == 2) {
                        editText.setText(experience);
                    }
                } else if (position == 7) {
                    convertView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.blank_layout, parent, false);
                } else if (position == 8) {
                    convertView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.action_button, parent, false);
                    Button button = (Button) convertView.findViewById(R.id.signinButton);
                    button.setText(labels[position]);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            executeAddCandidate();
                        }
                    });
                } else if (position == 9) {
                    convertView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.blank_layout, parent, false);
                }
                return convertView;
            }
        };
    }

    private void executeAddCandidate() {
        HashMap parameters = new HashMap();

        parameters.put("client_id", "91");
        parameters.put("user_id", "65");
        parameters.put("firstname", firstname);
        parameters.put("lastname", lastname);
        parameters.put("email", email);
        parameters.put("mobile", mobile);
        parameters.put("skype", skype);
        parameters.put("experience", experience);

        WebRequest webRequest = new WebRequest(RequestIdentifier.addCandidate, parameters, CandidateEditorFragment.this, getContext());
        webRequest.execute();
    }

    @Override
    public void onBeginRequest() {

    }

    @Override
    public void onRequestSucceeded(RequestIdentifier identifier, String message, Object... content) {
        if (identifier == RequestIdentifier.addCandidate) {
            ArrayList<Order> orders = (ArrayList<Order>) content[0];
        }
    }

    @Override
    public void onRequestFailed(RequestIdentifier identifier, String message, Object... content) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_back, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.action_back);
        menuItem.setTitle("Cancel");
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:

                ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
                actionBar.setDisplayHomeAsUpEnabled(true);
                getFragmentManager().popBackStack();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onResume() {
        super.onResume();
        if (this.getActivity() instanceof ClientActivity) {
            ((ClientActivity) getActivity()).setActionBarTitle("New Candidate");
        } else if (this.getActivity() instanceof AdminActivity) {
            ((AdminActivity) getActivity()).setActionBarTitle("New Candidate");
        }
    }
}
