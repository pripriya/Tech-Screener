package com.geval6.techscreener.Source.Fragments.LoginRegister;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
import com.geval6.techscreener.Source.Activities.AssessorActivity;
import com.geval6.techscreener.Source.Activities.ClientActivity;
import com.geval6.techscreener.Source.Activities.SetupActivity;
import com.geval6.techscreener.Source.Modals.Client;
import com.geval6.techscreener.Source.Modals.User;
import com.geval6.techscreener.Utilities.Managers.ArchiveManager;
import com.geval6.techscreener.Utilities.Managers.ComponentsManager;
import com.geval6.techscreener.Utilities.Managers.RequestManager.RequestIdentifier;
import com.geval6.techscreener.Utilities.Managers.RequestManager.RequestListener;
import com.geval6.techscreener.Utilities.Managers.RequestManager.WebRequest;

import java.util.HashMap;

public class RegisterClientFragment extends Fragment {

    private ListView listView;
    public String name = "", address = "", city = "", state = "", country = "", zip = "", phone = "", supportemail = "", website = "";

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
                return 13;
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

                String[] labels = {"", "ORGANIZATION NAME", "ADDRESS", "CITY", "STATE", "COUNTRY", "ZIP", "PHONE", "SUPPORT EMAIL", "WEBSITE", "", "NEXT", ""};
                final String[] values = {name, address, city, state, zip, country, phone, supportemail, website, ""};

                if (position == 0) {
                    convertView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.blank_layout, parent, false);
                } else if (position >= 1 && position < 10) {
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
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                    } else if (position == 4) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                    } else if (position == 5) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                    } else if (position == 6) {
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    } else if (position == 7) {
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    } else if (position == 8) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    } else if (position == 9) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
                    }
                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (position == 1) {
                                name = s.toString();
                            } else if (position == 2) {
                                address = s.toString();
                            } else if (position == 3) {
                                city = s.toString();
                            } else if (position == 4) {
                                state = s.toString();
                            } else if (position == 5) {
                                country = s.toString();
                            } else if (position == 6) {
                                zip = s.toString();
                            } else if (position == 7) {
                                phone = s.toString();
                            } else if (position == 8) {
                                supportemail = s.toString();
                            } else if (position == 9) {
                                website = s.toString();
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {


                        }
                    });

                    if (position == 1) {
                        editText.setText(name);
                    } else if (position == 2) {
                        editText.setText(address);
                    } else if (position == 3) {
                        editText.setText(city);
                    } else if (position == 4) {
                        editText.setText(state);
                    } else if (position == 5) {
                        editText.setText(country);
                    } else if (position == 6) {
                        editText.setText(zip);
                    } else if (position == 7) {
                        editText.setText(phone);
                    } else if (position == 8) {
                        editText.setText(supportemail);
                    } else if (position == 9) {
                        editText.setText(website);
                    }

                } else if (position == 10) {
                    convertView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.blank_layout, parent, false);

                } else if (position == 11) {
                    convertView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.action_button, parent, false);
                    Button button = (Button) convertView.findViewById(R.id.signinButton);
                    button.setText(labels[position]);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            Bundle args = new Bundle();

                            args.putString("name", name);
                            args.putString("address", address);
                            args.putString("city", city);
                            args.putString("state", state);
                            args.putString("country", country);
                            args.putString("zip", zip);
                            args.putString("phone", phone);
                            args.putString("supportemail", supportemail);
                            args.putString("website", website);

                            RegisterUserFragment registerUserFragment = new RegisterUserFragment();
                            registerUserFragment.setArguments(args);
                            fragmentTransaction.replace(R.id.activity_main, registerUserFragment, "registerUser").addToBackStack(null).commit();

                        }
//                            if (!validateInput()) {
//                                Toast.makeText(getContext(), "Please don't leave the fields blank", Toast.LENGTH_SHORT).show();
//
//                            } else if (!validateWebsite()) {
//                                Toast.makeText(getContext(), "Website is invalid", Toast.LENGTH_SHORT).show();
//
//                            } else if (validateInput() && validateWebsite()) {
//                                getUserRegisterAdapter();
//                            }
                        //  }
                    });
                } else if (position == 12) {
                    convertView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.blank_layout, parent, false);

                }
                return convertView;
            }
        };


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
        ((SetupActivity) getActivity()).setActionBarTitle("Register Organization");

    }
}
