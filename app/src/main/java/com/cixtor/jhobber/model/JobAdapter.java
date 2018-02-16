package com.cixtor.jhobber.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cixtor.jhobber.R;

import java.util.ArrayList;

public class JobAdapter extends ArrayAdapter<Job> {
    public JobAdapter(Context context, ArrayList<Job> posts) {
        super(context, 0, posts);
    }

    @Override
    public View getView(int index, View v, ViewGroup group) {
        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_job, group, false);
        }

        Job post = getItem(index);

        ImageView image = (ImageView) v.findViewById(R.id.jobImage);
        TextView company = (TextView) v.findViewById(R.id.jobCompany);
        TextView occupation = (TextView) v.findViewById(R.id.jobOccupation);
        TextView skills = (TextView) v.findViewById(R.id.jobSkills);

        new DownloadImageTask(image).execute(post.getImage());
        company.setText(post.getCompany());
        occupation.setText(post.getOccupation());
        skills.setText(post.getSkills());

        return v;
    }
}
