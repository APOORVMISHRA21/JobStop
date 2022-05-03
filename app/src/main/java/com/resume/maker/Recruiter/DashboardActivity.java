package com.resume.maker.Recruiter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demo.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.resume.maker.OnboardingActivity;
import com.resume.maker.Recruiter.Models.JobDetails;
import com.resume.maker.Recruiter.Models.RecruiterDetails;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


    private TextView companyName;
    private TextView companyDescription;
    private TextView companyMail;
    private CircleImageView companyDp;
    private ImageView btnLogout;
    private MaterialButton btnCreateNew;
    private ImageView banner1, banner2;
    private RecyclerView jobPostRv;
    private List<JobDetails> mJobList = new ArrayList<>();
    private JobListAdapter jobListAdapter;
    private TextView tvAllJobs;
    private long jobCount = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        companyName = findViewById(R.id.company_name);
        companyMail = findViewById(R.id.company_mail);
        companyDescription = findViewById(R.id.company_description);
        companyDp = findViewById(R.id.company_dp);
        btnLogout = findViewById(R.id.btn_logout);
        btnCreateNew = findViewById(R.id.btn_create_hiring_post);
        banner1 = findViewById(R.id.img_banner_1);
        banner2 = findViewById(R.id.img_banner_2);
        jobPostRv = findViewById(R.id.job_post_rv);
        tvAllJobs = findViewById(R.id.tv_all_jobs);

        //initialise firebase dependencies
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        storageReference = storage.getReference().child("recruiter_dp").child(firebaseUser.getUid());
        databaseReference = firebaseDatabase.getReference("companyDetails").child(firebaseUser.getUid());
        setCompanyData();
        btnLogout.setOnClickListener(this);
        btnCreateNew.setOnClickListener(this);

        if(mJobList.isEmpty()){
            jobPostRv.setVisibility(View.GONE);
            banner2.setVisibility(View.GONE);
            banner1.setVisibility(View.VISIBLE);
            tvAllJobs.setVisibility(View.GONE);
        }
        setUpJobList();
        jobListAdapter = new JobListAdapter((ArrayList<JobDetails>) mJobList,this);
        jobPostRv.setHasFixedSize(true);
        jobPostRv.setLayoutManager(new LinearLayoutManager(this));
        jobPostRv.setAdapter(jobListAdapter);
        jobListAdapter.notifyDataSetChanged();


    }


    private void setUpJobList() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("job_posts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mJobList.clear();
                jobCount = snapshot.getChildrenCount();
                for( DataSnapshot snapshot1: snapshot.getChildren()){
                    JobDetails jobDetails = snapshot1.getValue(JobDetails.class);
                    if(jobDetails.creatorId.equals(firebaseAuth.getUid()))
                    mJobList.add(jobDetails);
                }

                if(!mJobList.isEmpty()){
                    jobPostRv.setVisibility(View.VISIBLE);
                    banner1.setVisibility(View.GONE);
                    banner2.setVisibility(View.VISIBLE);
                    jobListAdapter.notifyDataSetChanged();
                    tvAllJobs.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void setCompanyData() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RecruiterDetails recruiterDetails = snapshot.getValue(RecruiterDetails.class);
                companyName.setText(recruiterDetails.companyName);
                companyMail.setText(recruiterDetails.companyMail);
                companyDescription.setText(recruiterDetails.companyDescription);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(DashboardActivity.this)
                        .load(uri)
                        .circleCrop()
                        .into(companyDp);
            }
        });


    }


    @Override
    public void onClick(View view) {

        if (view == this.btnLogout){
          firebaseAuth.signOut();
          GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getApplicationContext(), GoogleSignInOptions.DEFAULT_SIGN_IN);
          googleSignInClient.signOut();
          googleSignInClient.revokeAccess();
          startActivity(new Intent(this, OnboardingActivity.class));
          finish();
        }

        if (view == this.btnCreateNew){
            Intent intent = new Intent(this, CreateJobActivity.class);
            intent.putExtra("jobCount",jobCount);
            startActivity(intent);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpJobList();
        jobListAdapter.notifyDataSetChanged();
        setCompanyData();
    }
}