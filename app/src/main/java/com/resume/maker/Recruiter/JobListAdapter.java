package com.resume.maker.Recruiter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.resume.maker.Recruiter.Models.JobDetails;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class JobListAdapter extends RecyclerView.Adapter {

    private List<JobDetails> jobDetailsList;
    private Context context;
    public JobListAdapter(ArrayList<JobDetails> jobDetailsArrayList, Context mContext){

        jobDetailsList = jobDetailsArrayList;
        context = mContext;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_job_post,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final RecyclerViewHolder jobListViewHolder = (RecyclerViewHolder) holder;

        jobListViewHolder.itemTitle.setText(jobDetailsList.get(position).jobTitle);
        jobListViewHolder.itemDescription.setText(jobDetailsList.get(position).jobDescription);
        jobListViewHolder.itemCreatedAt.setText("Created At: "+getDate(jobDetailsList.get(position).createdAt));
        jobListViewHolder.itemDeadline.setText("Deadline: "+getDate(jobDetailsList.get(position).jobDeadline));


    }

    @Override
    public int getItemCount() {
        return jobDetailsList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView itemTitle;
        private TextView itemDescription;
        private TextView itemCreatedAt;
        private TextView itemDeadline;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.item_job_title);
            itemDescription = itemView.findViewById(R.id.item_job_Description);
            itemCreatedAt = itemView.findViewById(R.id.item_job_createdAt);
            itemDeadline = itemView.findViewById(R.id.item_job_Deadline);
        }
    }


    private String getDate(String time) {
        long lTime;
        lTime = Long.parseLong(time);
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(lTime * 1000);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }

}
