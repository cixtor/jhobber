package com.cixtor.jhobber;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class JobPostAdapter extends ArrayAdapter<JobPost> {
    public JobPostAdapter(Context context, ArrayList<JobPost> posts) {
        super(context, 0, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_jobpost, parent, false);
        }

        JobPost post = getItem(position);

        ImageView jobLogo = (ImageView) convertView.findViewById(R.id.jobpost_logo);
        TextView jobCompany = (TextView) convertView.findViewById(R.id.jobpost_company);
        TextView jobTitle = (TextView) convertView.findViewById(R.id.jobpost_title);
        TextView jobSkills = (TextView) convertView.findViewById(R.id.jobpost_skills);

        jobLogo.setImageResource(post.getLogoId());
        jobCompany.setText(post.getCompany());
        jobTitle.setText(post.getTitle());
        jobSkills.setText(post.getSkills());

        return convertView;
    }
}
