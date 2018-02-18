package com.cixtor.jhobber.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cixtor.jhobber.R;
import com.cixtor.jhobber.activity.Main;
import com.cixtor.jhobber.model.DownloadImageTask;
import com.cixtor.jhobber.model.Job;
import com.cixtor.jhobber.model.JobAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Profile extends Fragment {

    private Main parent;
    private RecyclerView mJobView;
    private JobAdapter mJobAdapter;
    private RecyclerView.LayoutManager mJobManager;
    private OnFragmentInteractionListener mListener;

    public Profile() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parent = (Main) this.getActivity();

        if (mListener != null) {
            mListener.onFragmentInteraction("Profile");
        }

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        mJobView = (RecyclerView) v.findViewById(R.id.jobPosts);
        mJobManager = new LinearLayoutManager(parent);
        mJobView.setLayoutManager(mJobManager);
        mJobView.scrollToPosition(0);

        this.setProfileData(v);

        this.loadJobPosts(v);

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

    private void setProfileData(View v) {
        if (!parent.isValidAccount()) {
            return;
        }

        TextView tvFullname = (TextView) v.findViewById(R.id.profileFullname);
        tvFullname.setText(parent.getUserAccount().getFullName());

        TextView tvOccupation = (TextView) v.findViewById(R.id.profileOccupation);
        tvOccupation.setText(parent.getUserAccount().getOccupation());

        String avatar = parent.getUserAccount().getAvatar();
        new DownloadImageTask(parent, (ImageView) v.findViewById(R.id.profileAvatar)).execute(avatar);
        new DownloadImageTask(parent, (ImageView) v.findViewById(R.id.profileAvatarBack)).execute(avatar);
    }

    private void loadJobPosts(View v) {
        JsonObjectRequest obj = new JsonObjectRequest(
                Request.Method.GET,
                parent.WEB_SERVICE + "/jobs",
                null,
                this.getJobsResponseListener(parent, this),
                this.getJobsErrorListener(parent, this)
        );

        parent.addRequestToQueue(obj);
    }

    private Response.Listener<JSONObject> getJobsResponseListener(final Main parent, final Profile here) {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                try {
                    here.loadJobPostsIntoList(res);
                } catch (JSONException e) {
                    parent.alert(e.getMessage());
                }
            }
        };
    }

    public Response.ErrorListener getJobsErrorListener(final Main parent, final Profile here) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                /* https://stackoverflow.com/a/24700973 */
                parent.alert(error.toString());
            }
        };
    }

    private void loadJobPostsIntoList(JSONObject res) throws JSONException {
        if (!res.getBoolean("ok")) {
            parent.alert(res.getString("error"));
            return;
        }

        ArrayList<Job> jobs = new ArrayList<Job>();
        JSONArray items = res.getJSONArray("jobs");
        int total = items.length();

        for (int i = 0; i < total; i++) {
            Job job = new Job();

            JSONObject item = items.getJSONObject(i);

            job.setID(item.getInt("id"));
            job.setCompany(item.getString("company"));
            job.setImage(item.getString("image"));
            job.setOccupation(item.getString("occupation"));
            job.setSkills(item.getString("skills"));

            jobs.add(job);
        }

        mJobAdapter = new JobAdapter(parent, jobs);

        mJobView.setAdapter(mJobAdapter);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String title);
    }
}
