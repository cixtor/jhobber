package com.cixtor.jhobber.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cixtor.jhobber.R;
import com.cixtor.jhobber.activity.Main;

import org.json.JSONException;
import org.json.JSONObject;

public class Home extends Fragment implements View.OnClickListener {
    public final String TAG = "JHOBBER_HOME";

    private OnFragmentInteractionListener mListener;
    private EditText mSignupUsername;
    private Button mSignupButton;

    public Home() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mListener != null) {
            mListener.onFragmentInteraction("Home");
        }

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        this.mSignupUsername = (EditText) v.findViewById(R.id.signupUsername);
        this.mSignupButton = (Button) v.findViewById(R.id.signupButton);

        mSignupButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        int buttonId = v.getId();

        try {
            this.onClickSignup(v, buttonId);
        } catch (JSONException e) {
            Snackbar.make(
                    getActivity().findViewById(R.id.flContent),
                    e.getMessage(),
                    Snackbar.LENGTH_SHORT
            ).show();
        }
    }

    private void enableSignupButton() {
        mSignupButton.setEnabled(true);
        mSignupButton.setText(R.string.signup_button);
    }

    private void disableSignupButton() {
        mSignupButton.setEnabled(false);
        mSignupButton.setText(R.string.signup_creating);
    }

    private void onClickSignup(View v, int buttonId) throws JSONException {
        if (buttonId != R.id.signupButton) {
            return;
        }

        this.disableSignupButton();

        Main parent = (Main) this.getActivity();

        String username = mSignupUsername.getText().toString();

        /* skip empty usernames */
        if (username.isEmpty()) {
            parent.alert(getString(R.string.signup_empty_username));
            this.enableSignupButton();
            return;
        }

        JsonObjectRequest obj = new JsonObjectRequest(
                Request.Method.GET,
                parent.WEB_SERVICE + "/signup?username=" + username,
                null,
                this.onClickSignupResponseListener(parent, this),
                this.onClickSignupErrorListener(parent, this)
        );

        parent.addRequestToQueue(obj);
    }

    public Response.Listener<JSONObject> onClickSignupResponseListener(final Main parent, final Home here) {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                try {
                    if (res.getBoolean("ok")) {
                        /* user registration was successful */
                        parent.saveUserAccount(res);
                        parent.enableAdvancedFeatures();
                        parent.alert(getString(R.string.signup_account_created));
                    } else {
                        /* user registration failed */
                        here.enableSignupButton();
                        parent.alert(res.getString("error"));
                    }
                } catch (JSONException e) {
                    parent.alert(e.getMessage());
                }
            }
        };
    }

    public Response.ErrorListener onClickSignupErrorListener(final Main parent, final Home here) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                /* https://stackoverflow.com/a/24700973 */
                parent.alert(error.toString());
                here.enableSignupButton();
            }
        };
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String title);
    }
}
