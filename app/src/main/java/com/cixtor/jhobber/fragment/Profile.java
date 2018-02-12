package com.cixtor.jhobber.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cixtor.jhobber.R;
import com.cixtor.jhobber.activity.Main;
import com.cixtor.jhobber.model.DownloadImageTask;
import com.cixtor.jhobber.model.JobPost;
import com.cixtor.jhobber.model.JobPostAdapter;

import java.util.ArrayList;

public class Profile extends Fragment implements AdapterView.OnItemClickListener {

    private Main parent;
    private OnFragmentInteractionListener mListener;

    public Profile() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.parent = (Main) this.getActivity();

        if (mListener != null) {
            mListener.onFragmentInteraction("Profile");
        }

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        this.setProfileData(v);

        final ArrayList<JobPost> jobPosts = JobPost.getJobPosts();
        JobPostAdapter adapter = new JobPostAdapter(getActivity(), jobPosts);
        ListView listView = (ListView) v.findViewById(R.id.job_posts);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

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
        new DownloadImageTask((ImageView) v.findViewById(R.id.profileAvatar)).execute(avatar);
        new DownloadImageTask((ImageView) v.findViewById(R.id.profileAvatarBack)).execute(avatar);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        JobPost jobPost = (JobPost) parent.getItemAtPosition(position);

        this.parent.alert("Resume has been sent to " + jobPost.getCompany());
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String title);
    }
}
