package com.cixtor.jhobber.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cixtor.jhobber.R;
import com.cixtor.jhobber.activity.Main;

import java.util.ArrayList;

public class JobViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private Main parent;
    private ArrayList<Job> data;

    private final ImageView image;
    private final TextView company;
    private final TextView occupation;
    private final TextView skills;

    public JobViewHolder(Main activity, View v, ArrayList<Job> jobs) {
        super(v);

        v.setOnClickListener(this);

        this.data = jobs;
        this.parent = activity;

        this.image = (ImageView) v.findViewById(R.id.jobImage);
        this.company = (TextView) v.findViewById(R.id.jobCompany);
        this.occupation = (TextView) v.findViewById(R.id.jobOccupation);
        this.skills = (TextView) v.findViewById(R.id.jobSkills);
    }

    public ImageView getImage() {
        return image;
    }

    public TextView getCompany() {
        return company;
    }

    public TextView getOccupation() {
        return occupation;
    }

    public TextView getSkills() {
        return skills;
    }

    @Override
    public void onClick(View v) {
        Job job = data.get(getAdapterPosition());

        parent.alert("Resume has been sent to " + job.getCompany());
    }
}
