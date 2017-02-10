package com.geval6.techscreener.Utilities.Managers.RequestManager;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.geval6.techscreener.Source.Enumerations.UserType;
import com.geval6.techscreener.Source.Modals.Admin;
import com.geval6.techscreener.Source.Modals.Assessor;
import com.geval6.techscreener.Source.Modals.Candidate;
import com.geval6.techscreener.Source.Modals.Client;
import com.geval6.techscreener.Source.Modals.Order;
import com.geval6.techscreener.Source.Modals.Scheduler;
import com.geval6.techscreener.Source.Modals.User;
import com.geval6.techscreener.Utilities.Managers.ArchiveManager;
import com.geval6.techscreener.Utilities.Managers.ComponentsManager;
import com.geval6.techscreener.Utilities.Managers.SettingsManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WebRequest extends AsyncTask<Void, Void, HashMap> {

    RequestListener listener;
    HashMap parameters;
    RequestIdentifier requestIdentifier;
    Context context;

    public WebRequest(RequestIdentifier requestIdentifier, HashMap parameters, RequestListener requestListener, Context context) {
        this.requestIdentifier = requestIdentifier;
        this.parameters = parameters;
        this.listener = requestListener;
        this.context = context;
    }

    public WebRequest(RequestIdentifier requestIdentifier, RequestListener requestListener, Context context) {
        this.requestIdentifier = requestIdentifier;
        this.parameters = new HashMap();
        this.listener = requestListener;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        listener.onBeginRequest();
        super.onPreExecute();
    }

    @Override
    protected HashMap doInBackground(Void... params) {
        URL url = null;
        try {

            String string = urlForIdentifier(requestIdentifier, parameters);
            string = string.replaceAll(" ", "%20");
            url = new URL(string);
            Log.i("url", url.toString());

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(methodForIdentifier(requestIdentifier));
            httpURLConnection.setRequestProperty("Content-Type", "application/json");

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return (HashMap) RequestFunctions.objectFromJson(response.toString());

            } else {
                Log.i("Error", "error");
            }
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(HashMap hashMap) {

        Boolean status = (Boolean) hashMap.get("status");
        String message = (hashMap.containsKey("message") && hashMap.get("message") instanceof String) ? (String) hashMap.get("message") : "";
        HashMap content = (hashMap.containsKey("content") && hashMap.get("content") instanceof HashMap) ? (HashMap) hashMap.get("content") : new HashMap();
        if (requestIdentifier == RequestIdentifier.authenticateUser) {

            if (status == true) {
                Admin admin = null;
                if (content.containsKey("administrators") && content.get("administrators") instanceof ArrayList) {
                    ArrayList<HashMap> administrators = (ArrayList<HashMap>) content.get("administrators");
                    admin = new Admin(administrators.get(0));
                    ArchiveManager.setUserType(UserType.Admin);
                }
                Scheduler scheduler = null;
                if (content.containsKey("schedulers") && content.get("schedulers") instanceof ArrayList) {
                    scheduler = new Scheduler((HashMap) content.get("schedulers"));
                }
                User user = null;
                if (content.containsKey("users") && content.get("users") instanceof ArrayList) {

                    ArrayList<HashMap> users = (ArrayList<HashMap>) content.get("users");
                    user = new User(users.get(0));
                    ArchiveManager.setUserType(UserType.User);

                }
                Assessor assessor = null;
                if (content.containsKey("assessors") && content.get("assessors") instanceof ArrayList) {
                    assessor = new Assessor((HashMap) content.get("assessors"));
                    ArchiveManager.setUserType(UserType.Assessor);
                }
                Client client = null;
                if (content.containsKey("clients") && content.get("clients") instanceof ArrayList) {
                    assessor = new Assessor((HashMap) content.get("clients"));
                    ArchiveManager.setUserType(UserType.Client);
                }
                listener.onRequestSucceeded(requestIdentifier, message, admin, scheduler, user, assessor, client);
            } else {
                listener.onRequestFailed(requestIdentifier, message, null);
            }
        }
        else if (requestIdentifier == RequestIdentifier.registerAssessor) {
            if (status == true && content.containsKey("assessors")) {
                ArrayList<Assessor> assessors = (content.containsKey("assessors")) ? Assessor.getObjects((ArrayList<HashMap>) content.get("assessors")) : new ArrayList<Assessor>();
                listener.onRequestSucceeded(requestIdentifier, message, assessors.get(0));
            } else {
                listener.onRequestFailed(requestIdentifier, message, null);
            }
        }
        else if (requestIdentifier == RequestIdentifier.registerClient) {

            if (status == true && content.containsKey("clients")) {
                ArrayList<Client> clients = (content.containsKey("clients")) ? Client.getObjects((ArrayList<HashMap>) content.get("clients")) : new ArrayList<Client>();
                listener.onRequestSucceeded(requestIdentifier, message, clients.get(0));

                if (content.containsKey("users")) {
                    ArrayList<User> users = (content.containsKey("users")) ? User.getObjects((ArrayList<HashMap>) content.get("users")) : new ArrayList<User>();
                    listener.onRequestSucceeded(requestIdentifier, message, clients.get(0), users.get(0));

                } else {
                    listener.onRequestFailed(requestIdentifier, message, null);
                }
            }
        }


        else if (requestIdentifier == RequestIdentifier.getClients) {
            if (status == true && content.containsKey("clients")) {
                ArrayList<Client> clients = (content.containsKey("clients")) ? Client.getObjects((ArrayList<HashMap>) content.get("clients")) : new ArrayList<Client>();
                listener.onRequestSucceeded(requestIdentifier, message, clients);
            } else {
                listener.onRequestFailed(requestIdentifier, message, null);
            }
        }
        else if (requestIdentifier == RequestIdentifier.getAssessors) {
            if (status == true && content.containsKey("assessors")) {
                ArrayList<Assessor> assessors = (content.containsKey("assessors")) ? Assessor.getObjects((ArrayList<HashMap>) content.get("assessors")) : new ArrayList<Assessor>();
                listener.onRequestSucceeded(requestIdentifier, message, assessors);
            } else {
                listener.onRequestFailed(requestIdentifier, message, null);
            }
        }


        else if (requestIdentifier == RequestIdentifier.assessorDetail) {
            if (status == true && content.containsKey("assessors")) {
                HashMap assessor = (HashMap) content.get("assessors");
                ArrayList<Assessor> assessors = (assessor.containsKey("assessors")) ? Assessor.getObjects((ArrayList<HashMap>) assessor.get("assessors")) : new ArrayList<Assessor>();
                listener.onRequestSucceeded(requestIdentifier, message, assessors);
            } else {
                listener.onRequestFailed(requestIdentifier, message, null);
            }
        }
        else if (requestIdentifier == RequestIdentifier.clientDetail) {
            if (status == true && content.containsKey("clients")) {
                HashMap client = (HashMap) content.get("clients");
                ArrayList<Client> clients = (client.containsKey("clients")) ? Client.getObjects((ArrayList<HashMap>) client.get("clients")) : new ArrayList<Client>();
                listener.onRequestSucceeded(requestIdentifier, message, clients);
            } else {
                listener.onRequestFailed(requestIdentifier, message, null);
            }
        }


        else if (requestIdentifier == RequestIdentifier.updateAssessorStatus) {
            if (status == true) {
                listener.onRequestSucceeded(requestIdentifier, message, null);

            } else {
                listener.onRequestFailed(requestIdentifier, message, null);
            }
        }
        else if (requestIdentifier == RequestIdentifier.updateClientStatus) {
            if (status == true) {
                listener.onRequestSucceeded(requestIdentifier, message, null);
            } else {
                Log.i("messge", message);
                listener.onRequestFailed(requestIdentifier, message, null);
            }
        }


        else if (requestIdentifier == RequestIdentifier.getCandidates) {
            if (status == true && content.containsKey("candidates")) {
                ArrayList<Candidate> candidates = (content.containsKey("candidates")) ? Candidate.getObjects((ArrayList<HashMap>) content.get("candidates")) : new ArrayList<Candidate>();
                listener.onRequestSucceeded(requestIdentifier, message, candidates);
            } else {
                listener.onRequestFailed(requestIdentifier, message, null);
            }
        }
        else if (requestIdentifier == RequestIdentifier.addCandidate) {
            if (status == true && content.containsKey("candidates")) {
                ArrayList<Candidate> candidates = (content.containsKey("candidates")) ? Candidate.getObjects((ArrayList<HashMap>) content.get("candidates")) : new ArrayList<Candidate>();
                listener.onRequestSucceeded(requestIdentifier, message, candidates);
            } else {
                listener.onRequestFailed(requestIdentifier, message, null);
            }
        }


        else if (requestIdentifier == RequestIdentifier.getOrders) {
            if (status == true && content.containsKey("orders")) {
                ArrayList<Order> orders = (content.containsKey("orders")) ? Order.getObjects((ArrayList<HashMap>) content.get("orders")) : new ArrayList<Order>();
                listener.onRequestSucceeded(requestIdentifier, message, orders);
            } else {
                listener.onRequestFailed(requestIdentifier, message, null);
            }
        }
        else if (requestIdentifier == RequestIdentifier.createOrder) {
            if (status == true && content.containsKey("orders")) {
                ArrayList<Order> orders = (hashMap.containsKey("orders")) ? Order.getObjects((ArrayList<HashMap>) hashMap.get("orders")) : new ArrayList<Order>();
                listener.onRequestSucceeded(requestIdentifier, message, orders);
            } else {
                listener.onRequestFailed(requestIdentifier, message, null);
            }
        }
        super.onPostExecute(hashMap);
    }


    private String methodForIdentifier(RequestIdentifier requestIdentifier) {
        switch (requestIdentifier) {
            case authenticateUser:
                return "POST";
            case registerUser:
                return "POST";
            case registerClient:
                return "POST";
            case registerAssessor:
                return "POST";
            case updateClientStatus:
                return "POST";
            case updateAssessorStatus:
                return "POST";
            case addCandidate:
                return "POST";
            case createOrder:
                return "POST";
            default:
                return "GET";
        }
    }

    private String nameForIdentifier(RequestIdentifier requestIdentifier) {
        switch (requestIdentifier) {
            case authenticateUser:
                return "users/authenticate?email={email}&password={password}";
            case registerClient:
                return "clients?name={name}&address={address}&city={city}&state={state}&country={country}&zip={zip}&phone={phone}&support={support}&website={website}&firstname={firstname}&lastname={lastname}&email={email}&mobile={mobile}&password={password}&designation={designation}";
            case registerAssessor:
                return "assessors/register?firstname={firstname}&lastname={lastname}&email={email}&mobile={mobile}&password={password}&company={company}&designation={designation}&experience={experience}&summary={summary}";
            case usersList:
                return "users/id";
            case getAssessors:
                return "assessors?page={page}&size={size}";
            case getClients:
                return "clients?page={page}&size={size}";
            case assessorDetail:
                return "assessors/id";
            case clientDetail:
                return "clients/id";
            case updateClientStatus:
                return "clients/{id}?status={status}";
            case updateAssessorStatus:
                return "assessors/{id}?status={status}";
            case getCandidates:
                return "candidates?client_id={client_id}&user_id={user_id}&page={page}&size={size}";
            case addCandidate:
                return "candidates?client_id={client_id}&user_id={user_id}&firstname={firstname}&lastname={lastname}&email={email}&mobile={mobile}&skype={skype}&experience={experience}";
            case getOrders:
                return "orders?client_id={client_id}&user_id={user_id}&page={page}&size={size}";
            case createOrder:
                return "orders?client_id={client_id}&user_id={user_id}&candidate_id={candidate_id}&title={title}&description={description}";

            default:
                return "";
        }
    }

    private String urlForIdentifier(RequestIdentifier requestIdentifier, HashMap parameters) {
        return (SettingsManager.hostRoot + SettingsManager.service + ComponentsManager.stringByReplacingValues(nameForIdentifier(requestIdentifier), parameters));
    }
}
