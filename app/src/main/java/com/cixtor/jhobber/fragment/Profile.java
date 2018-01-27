package com.cixtor.jhobber.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cixtor.jhobber.model.JobPost;
import com.cixtor.jhobber.model.JobPostAdapter;
import com.cixtor.jhobber.R;

import java.util.ArrayList;

public class Profile extends Fragment {

    private OnFragmentInteractionListener mListener;

    public Profile() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        final ArrayList<JobPost> jobPosts = JobPost.getJobPosts();
        JobPostAdapter adapter = new JobPostAdapter(getActivity(), jobPosts);
        ListView listView = (ListView) rootView.findViewById(R.id.job_posts);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JobPost jobPost = (JobPost) parent.getItemAtPosition(position);

                Snackbar.make(
                        getActivity().findViewById(R.id.flContent),
                        "Resume has been sent to " + jobPost.getCompany(),
                        Snackbar.LENGTH_SHORT
                ).show();
            }
        });

        if (mListener != null) {
            mListener.onFragmentInteraction("Profile");
        }

        return rootView;
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String title);
    }
}
