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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cixtor.jhobber.R;
import com.cixtor.jhobber.activity.Base;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Home extends Fragment implements View.OnClickListener {
    public final String TAG = "JHOBBER_HOME";

    private OnFragmentInteractionListener mListener;
    private EditText username;
    private Button signupBtn;

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

        this.username = (EditText) v.findViewById(R.id.username);
        this.signupBtn = (Button) v.findViewById(R.id.signupBtn);

        signupBtn.setOnClickListener(this);

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

    private void onClickSignup(View v, int buttonId) throws JSONException {
        if (buttonId != R.id.signupBtn) {
            return;
        }

        Base parent = (Base) this.getActivity();

        StringRequest obj = new StringRequest(
                Request.Method.POST,
                parent.WEB_SERVICE + "/signup",
                this.onClickSignupResponseListener(parent),
                this.onClickSignupErrorListener()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username.getText().toString());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        parent.addRequestToQueue(obj);
    }

    public Response.Listener<String> onClickSignupResponseListener(final Base parent) {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String out) {
                String message;

                try {
                    JSONObject res = new JSONObject(out);

                    if (res.getBoolean("ok")) {
                        /* user registration was successful */
                        message = "Account successfully created.";
                        parent.setUserAccount(res);
                    } else {
                        /* user registration failed */
                        message = res.getString("error");
                    }
                } catch (JSONException e) {
                    message = e.getMessage();
                }

                Snackbar.make(
                        getActivity().findViewById(R.id.flContent),
                        message,
                        Snackbar.LENGTH_SHORT
                ).show();
            }
        };
    }

    public Response.ErrorListener onClickSignupErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
            }
        };
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String title);
    }
}
