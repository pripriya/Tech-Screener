package com.geval6.techscreener.Source.Fragments.LoginRegister;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geval6.techscreener.R;
import com.geval6.techscreener.Source.Activities.AdminActivity;
import com.geval6.techscreener.Source.Activities.AssessorActivity;
import com.geval6.techscreener.Source.Activities.ClientActivity;
import com.geval6.techscreener.Source.Activities.SetupActivity;
import com.geval6.techscreener.Source.Enumerations.UserType;
import com.geval6.techscreener.Source.Modals.Admin;
import com.geval6.techscreener.Source.Modals.Assessor;
import com.geval6.techscreener.Source.Modals.Client;
import com.geval6.techscreener.Source.Modals.User;
import com.geval6.techscreener.Utilities.Managers.ArchiveManager;
import com.geval6.techscreener.Utilities.Managers.RequestManager.RequestIdentifier;
import com.geval6.techscreener.Utilities.Managers.RequestManager.RequestListener;
import com.geval6.techscreener.Utilities.Managers.RequestManager.WebRequest;


import java.util.HashMap;


public class LoginFragment extends Fragment implements RequestListener {

    private ListView listView;
    private String username = "", password = "";
    ProgressDialog progressDialog;
    BottomSheetDialog bottomSheetDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_listview, container, false);
        prepareListView(view);

        if (this instanceof LoginFragment) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        return view;
    }

    private void prepareListView(View parent) {
        LinearLayout linearLayout = (LinearLayout) parent.findViewById(R.id.linearLayout);
        linearLayout.setPadding(34, 0, 34, 0);
        listView = (ListView) parent.findViewById(R.id.listView);
        listView.setAdapter(getLoginAdapter());
        listView.setDivider(null);
        listView.setPadding(0, 20, 0, 20);
        listView.addHeaderView(getLoginHeaderView());
        listView.addFooterView(getLoginFooterView());
    }

    private BaseAdapter getLoginAdapter() {
        return new BaseAdapter() {
            @Override
            public int getCount() {
                return 4;
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
            public View getView(final int position, View convertView, ViewGroup parent) {
                String[] labels = {"USERNAME", "PASSWORD", "", "LOGIN"};
                final String[] values = {username, password, "", ""};

                if (position >= 0 && position < 2) {
                    convertView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.text_input_view, parent, false);

                    final TextView textView = (TextView) convertView.findViewById(R.id.textview);
                    final EditText editText = (EditText) convertView.findViewById(R.id.edittext);

                    textView.setText(labels[position]);
                    editText.setText(values[position]);

                    if (position == 0) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
                    } else if (position == 1) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    }

                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (position == 0) {
                                username = s.toString();
                            } else if (position == 1) {
                                password = s.toString();
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                } else if (position == 2) {
                    convertView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.blank_layout, parent, false);
                } else if (position == 3) {
                    convertView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.action_button, parent, false);
                    Button button = (Button) convertView.findViewById(R.id.signinButton);
                    button.setText(labels[position]);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (validateInput()) {
                                executeRequest();
                            } else {
                                Toast.makeText(getContext(), "Please don't leave the fields blank", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                return convertView;
            }
        };
    }

    private View getLoginHeaderView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.login_header_layout, null, false).findViewById(R.id.line1);
        return layout;
    }

    private View getLoginFooterView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.login_header_layout, null, false).findViewById(R.id.line2);
        TextView textView = (TextView) layout.findViewById(R.id.signupTextview);
        textView.setPadding(0, 20, 0, 0);
        SpannableStringBuilder builder = new SpannableStringBuilder();

        String one = "Dont have an account?";
        SpannableString redSpannable = new SpannableString(one);
        redSpannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.color_grey)), 0, one.length(), 0);
        builder.append(redSpannable);

        String two = " Sign up";
        SpannableString redSpannable1 = new SpannableString(two);
        redSpannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.color_blue)), 0, two.length(), 0);
        builder.append(redSpannable1);
        textView.setText(builder, TextView.BufferType.SPANNABLE);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDialog();
            }
        });
        return layout;
    }

    private boolean validateInput() {
        return username.length() > 0 && password.length() > 0;
    }

    private void executeRequest() {
        HashMap parameters = new HashMap();
        parameters.put("email", username);
        parameters.put("password", password);

        WebRequest webRequest = new WebRequest(RequestIdentifier.authenticateUser, parameters, LoginFragment.this, getContext());
        webRequest.execute();
    }

    @Override
    public void onBeginRequest() {
        this.progressDialog = new ProgressDialog(getContext());
        this.progressDialog.setMessage("Loading please wait..");
        this.progressDialog.show();
    }

    @Override
    public void onRequestSucceeded(RequestIdentifier identifier, String message, Object... content) {
        progressDialog.dismiss();
        if (identifier == RequestIdentifier.authenticateUser) {
            if (ArchiveManager.userType.equals(UserType.Admin)) {
                ArchiveManager.setAdmin((Admin) content[0]);
                loadAdminActivity();
            } else if (ArchiveManager.userType.equals(UserType.User)) {
                ArchiveManager.setUser((User) content[0]);
                loadClientActivity();
            } else if (ArchiveManager.userType.equals(UserType.Assessor)) {
                ArchiveManager.setAssessor((Assessor) content[0]);
                loadAssessorActivity();
            } else if (ArchiveManager.userType.equals(UserType.Client)) {
                ArchiveManager.setClient((Client) content[0]);
                loadClientActivity();
            }
        }
    }

    @Override
    public void onRequestFailed(RequestIdentifier identifier, String message, Object... content) {
        this.progressDialog.dismiss();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void loadAdminActivity() {

        Intent intent = new Intent(getContext(), AdminActivity.class);
        startActivity(intent);
    }

    private void loadAssessorActivity() {

        Intent intent = new Intent(getContext(), AssessorActivity.class);
        startActivity(intent);
    }

    private void loadClientActivity() {

        Intent intent = new Intent(getContext(), ClientActivity.class);
        startActivity(intent);
    }

    public void showBottomDialog() {

        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        bottomSheetDialog = new BottomSheetDialog(getActivity());
        ListView listView = (ListView) sheetView.findViewById(R.id.listview);
        listView.setAdapter(BottomAdapter());
        listView.setDivider(null);
        listView.addFooterView(footerView());
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();
    }

    private BaseAdapter BottomAdapter() {
        return new BaseAdapter() {
            @Override
            public int getCount() {
                return 2;
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
                if (position == 0) {
                    button.setText("Register Organization");
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bottomSheetDialog.dismiss();
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            RegisterClientFragment register_client_fragment = new RegisterClientFragment();
                            fragmentTransaction.replace(R.id.activity_main, register_client_fragment).addToBackStack(null).commit();

                        }
                    });
                } else if (position == 1) {
                    button.setText("Register Assessor");
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bottomSheetDialog.dismiss();
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            RegisterAssessorFragment register_assessor_fragment = new RegisterAssessorFragment();
                            fragmentTransaction.replace(R.id.activity_main, register_assessor_fragment).addToBackStack(null).commit();
                        }
                    });
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

    public void onResume() {
        super.onResume();
        ((SetupActivity) getActivity()).setActionBarTitle("Tech Screener");
    }
}


