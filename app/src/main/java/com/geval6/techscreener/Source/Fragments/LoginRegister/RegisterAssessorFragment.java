package com.geval6.techscreener.Source.Fragments.LoginRegister;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.geval6.techscreener.R;
import com.geval6.techscreener.Source.Activities.AdminActivity;
import com.geval6.techscreener.Source.Activities.AssessorActivity;
import com.geval6.techscreener.Source.Activities.SetupActivity;
import com.geval6.techscreener.Source.Modals.Admin;
import com.geval6.techscreener.Source.Modals.Assessor;
import com.geval6.techscreener.Source.Modals.User;
import com.geval6.techscreener.Utilities.Managers.ArchiveManager;
import com.geval6.techscreener.Utilities.Managers.ComponentsManager;
import com.geval6.techscreener.Utilities.Managers.RequestManager.RequestIdentifier;
import com.geval6.techscreener.Utilities.Managers.RequestManager.RequestListener;
import com.geval6.techscreener.Utilities.Managers.RequestManager.WebRequest;

import java.text.NumberFormat;
import java.util.HashMap;

public class RegisterAssessorFragment extends Fragment implements RequestListener {

    private ListView listView;
    private String firstname = "", lastname = "", email = "", mobile = "", password = "", passwordCheck = "", organization = "", designation = "", totalExperience = "", summary = "";
    ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_listview, container, false);
        prepareListView(view);
        setHasOptionsMenu(true);
        return view;
    }

    private void prepareListView(View parent) {
        LinearLayout linearLayout = (LinearLayout) parent.findViewById(R.id.linearLayout);
        linearLayout.setPadding(24, 0, 24, 0);
        listView = (ListView) parent.findViewById(R.id.listView);
        listView.setAdapter(getAssessorLoginAdapter());
        listView.setDivider(null);
        listView.setPadding(0, 20, 0, 20);
    }

    private BaseAdapter getAssessorLoginAdapter() {
        return new BaseAdapter() {
            @Override
            public int getCount() {
                return 14;
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

                String[] labels = {"", "FIRSTNAME", "LASTNAME", "EMAIL", "MOBILE", "PASSWORD", "CONFIRM PASSWORD", "COMPANY", "DESIGNATION", "TOTAL EXPERIENCE", "SUMMARY", "", "REGISTER", ""};
                final String[] values = {firstname, lastname, email, mobile, password, passwordCheck, "", organization, designation, totalExperience, summary, ""};
                if (position == 0) {
                    convertView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.blank_layout, parent, false);
                } else if (position >= 1 && position < 11) {
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
                        editText.setInputType(InputType.TYPE_CLASS_PHONE);
                    } else if (position == 5) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    } else if (position == 6) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    } else if (position == 7) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                    } else if (position == 8) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                    } else if (position == 9) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT);
                    } else if (position == 10) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
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
                                organization = s.toString();
                            } else if (position == 8) {
                                designation = s.toString();
                            } else if (position == 9) {
                                totalExperience = s.toString();
                            } else if (position == 10) {
                                summary = s.toString();
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
                        editText.setText(organization);
                    } else if (position == 8) {
                        editText.setText(designation);
                    } else if (position == 9) {
                        editText.setText(totalExperience);
                    } else if (position == 10) {
                        editText.setText(summary);
                    }
                } else if (position == 11) {
                    convertView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.blank_layout, parent, false);
                } else if (position == 12) {
                    convertView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.action_button, parent, false);
                    Button button = (Button) convertView.findViewById(R.id.signinButton);
                    button.setText(labels[position]);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!validateInput()) {
                                Toast.makeText(getContext(), "Please don't leave the fields blank", Toast.LENGTH_SHORT).show();
                            } else if (!validatePassword()) {
                                Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();

                            } else if (validateInput() && validatePassword()) {
                                executeRequest();
                            }

                        }
                    });
                } else if (position == 13) {
                    convertView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.blank_layout, parent, false);
                }
                return convertView;
            }
        };
    }

    private boolean validateInput() {
        return firstname.length() > 0 && lastname.length() > 0 && ComponentsManager.isEmail(email) && mobile.length() > 0 && password.length() > 0 && passwordCheck.length() > 0 && organization.length() > 0
                && designation.length() > 0 && totalExperience.length() > 0 && summary.length() > 0;
    }

    private boolean validatePassword() {
        return password.equals(passwordCheck);
    }

    private void executeRequest() {
        HashMap parameters = new HashMap();
        parameters.put("firstname", firstname);
        parameters.put("lastname", lastname);
        parameters.put("email", email);
        parameters.put("mobile", mobile);
        parameters.put("password", password);
        parameters.put("company", organization);
        parameters.put("designation", designation);
        parameters.put("experience", totalExperience);
        parameters.put("summary", summary);

        WebRequest webRequest = new WebRequest(RequestIdentifier.registerAssessor, parameters, RegisterAssessorFragment.this, getContext());
        webRequest.execute();
    }

    @Override
    public void onBeginRequest() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading please wait..");
        progressDialog.show();
    }

    @Override
    public void onRequestSucceeded(RequestIdentifier identifier, String message, Object... content) {
        if (identifier == RequestIdentifier.registerAssessor) {
            ArchiveManager.setAssessor((Assessor) content[0]);
            Log.i("s", message.toString());
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            loadAssessorActivity();
            progressDialog.dismiss();
        }
    }

    @Override
    public void onRequestFailed(RequestIdentifier identifier, String message, Object... content) {
        progressDialog.dismiss();
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void loadAssessorActivity() {

        Intent intent = new Intent(getContext(), AssessorActivity.class);
        startActivity(intent);
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
                getFragmentManager().popBackStackImmediate();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onResume() {
        super.onResume();
        ((SetupActivity) getActivity()).setActionBarTitle("Register Assessor");

    }
}

