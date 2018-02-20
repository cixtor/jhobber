package com.cixtor.jhobber.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cixtor.jhobber.R;
import com.cixtor.jhobber.activity.Main;

import java.util.ArrayList;

public class JobAdapter extends RecyclerView.Adapter<JobViewHolder> {
    private Main parent;
    private ArrayList<Job> data;

    public JobAdapter(Main activity, ArrayList<Job> jobs) {
        this.data = jobs;
        this.parent = activity;
    }

    @Override
    public JobViewHolder onCreateViewHolder(ViewGroup group, int viewType) {
        View v = LayoutInflater.from(group.getContext())
                .inflate(R.layout.fragment_job, group, false);

        return new JobViewHolder(parent, v, data);
    }

    @Override
    public void onBindViewHolder(JobViewHolder v, int position) {
        Job job = data.get(position);

        new DownloadImageTask(parent, v.getImage()).execute(job.getImage());

        v.getCompany().setText(job.getCompany());
        v.getOccupation().setText(job.getTitle());
        v.getSkills().setText(job.getSkills());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
