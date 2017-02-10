package com.geval6.techscreener.Source.Fragments.LoginRegister;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
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
import com.geval6.techscreener.Source.Activities.ClientActivity;
import com.geval6.techscreener.Source.Activities.SetupActivity;
import com.geval6.techscreener.Source.Modals.Client;
import com.geval6.techscreener.Utilities.Managers.ArchiveManager;
import com.geval6.techscreener.Utilities.Managers.RequestManager.RequestIdentifier;
import com.geval6.techscreener.Utilities.Managers.RequestManager.RequestListener;
import com.geval6.techscreener.Utilities.Managers.RequestManager.WebRequest;

import java.util.HashMap;
import java.util.zip.Inflater;


public class RegisterUserFragment extends Fragment implements RequestListener {

    private ListView listView;
    public String name = "", address = "", city = "", state = "", country = "", zip = "", phone = "", supportemail = "", website = "";
    private String firstname = "", lastname = "", email = "", mobile = "", password = "", passwordCheck = "", designation = "";
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        name= getArguments().getString("name");
        address= getArguments().getString("address");
        city= getArguments().getString("city");
        state= getArguments().getString("state");
        country= getArguments().getString("country");
        zip= getArguments().getString("zip");
        phone= getArguments().getString("phone");
        supportemail= getArguments().getString("supportemail");
        website= getArguments().getString("website");

        View view = inflater.inflate(R.layout.layout_listview, container, false);
        prepareListView(view);
        setHasOptionsMenu(true);
        return view;
    }


    private void prepareListView(View parent) {
        LinearLayout linearLayout = (LinearLayout) parent.findViewById(R.id.linearLayout);
        linearLayout.setPadding(24, 0, 24, 0);
        listView = (ListView) parent.findViewById(R.id.listView);
        listView.setAdapter((getUserRegisterAdapter()));
        listView.setDivider(null);
        listView.setPadding(0, 20, 0, 20);
    }

    private BaseAdapter getUserRegisterAdapter() {
        return new BaseAdapter() {
            @Override
            public int getCount() {
                return 11;
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

                String[] labels = {"", "FIRSTNAME", "LASTNAME", "EMAIL", "MOBILE", "PASSWORD", "CONFIRM PASSWORD", "DESIGNATION", "", "REGISTER", ""};
                final String[] values = {firstname, lastname, email, mobile, password, passwordCheck, designation, ""};

                if (position == 0) {
                    convertView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.blank_layout, parent, false);
                } else if (position >= 1 && position < 8) {
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
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    } else if (position == 5) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    } else if (position == 6) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    } else if (position == 7) {
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
                                editText.setSelection(editText.getText().length());
                                password = s.toString();
                            } else if (position == 6) {
                                editText.setSelection(editText.getText().length());
                                passwordCheck = s.toString();
                            } else if (position == 7) {
                                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                                designation = s.toString();
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
                    } else if (position == 3) {
                        editText.setText(email);
                    } else if (position == 4) {
                        editText.setText(mobile);
                    } else if (position == 5) {
                        editText.setText(password);
                    } else if (position == 6) {
                        editText.setText(passwordCheck);
                    } else if (position == 7) {
                        editText.setText(designation);
                    }
                } else if (position == 8) {
                    convertView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.blank_layout, parent, false);
                } else if (position == 9) {
                    convertView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.action_button, parent, false);
                    Button button = (Button) convertView.findViewById(R.id.signinButton);
                    button.setText(labels[position]);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            executeRequest();
                        }
                    });
                } else if (position == 10) {
                    convertView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.blank_layout, parent, false);
                }
                return convertView;
            }
        };
    }

    private void executeRequest() {
        HashMap parameters = new HashMap();
        parameters.put("name", name);
        parameters.put("address", address);
        parameters.put("city",city);
        parameters.put("state", state);
        parameters.put("country",country);
        parameters.put("zip", zip);
        parameters.put("phone",phone);
        parameters.put("support", supportemail);
        parameters.put("website", website);
        parameters.put("firstname", firstname);
        parameters.put("lastname", lastname);
        parameters.put("email", email);
        parameters.put("mobile", mobile);
        parameters.put("password", password);
        parameters.put("designation", designation);

        WebRequest webRequest = new WebRequest(RequestIdentifier.registerClient, parameters, RegisterUserFragment.this, getContext());
        webRequest.execute();
    }

//    private boolean validateInput() {
//        return name.length() > 0 && address.length() > 0 && city.length() > 0 && state.length() > 0 && country.length() > 0 && zip.length() > 0
//                && phone.length() > 0 && ComponentsManager.isEmail(supportemail) && website.length() > 0;
//    }
//
//    private boolean validateWebsite() {
//        return Patterns.WEB_URL.matcher(website).matches();
//    }

    @Override
    public void onBeginRequest() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading please wait..");
        progressDialog.show();
    }

    @Override
    public void onRequestSucceeded(RequestIdentifier identifier, String message, Object... content) {
        if (identifier == RequestIdentifier.registerClient) {
            ArchiveManager.setClient((Client) content[0]);
            listView.setAdapter(getUserRegisterAdapter());
            loadClientActivity();
            progressDialog.dismiss();
        }
    }

    @Override
    public void onRequestFailed(RequestIdentifier identifier, String message, Object... content) {
        progressDialog.dismiss();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void loadClientActivity() {
        Intent intent = new Intent(getContext(), ClientActivity.class);
        startActivity(intent);
    }

    public void onResume() {
        super.onResume();
        ((SetupActivity) getActivity()).setActionBarTitle("Register User");

    }
}
