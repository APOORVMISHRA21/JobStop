package com.resume.maker.Recruiter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.R;
import com.fastaccess.datetimepicker.DatePickerFragmentDialog;
import com.fastaccess.datetimepicker.callback.DatePickerCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resume.maker.Recruiter.Models.JobDetails;

public class CreateJobActivity extends AppCompatActivity implements View.OnClickListener, DatePickerCallback {

    String[] jobTypeSpinner = {"Full-Time","Internship"};
    String[] jobModeSpinner = {"On-Site","Remote","Hybrid"};
    String[] jobLocationSpinner = {"Full-Time","Internship"};
    String[] tagSpinner = {"Android","iOS Development","Web Development", "Full Stack Development","Data Science","Machine Learning","Python Development"};

    private Spinner spinnerJobType;
    private Spinner spinnerJobMode;
    private Spinner spinnerJobLocation;
    private Spinner spinnerTags;

    private EditText jobTitle;
    private EditText jobDescription;
    private EditText jobSalary;

    private TextView jobDeadline;
    private ImageView datePicker;
    private Button btnCreate;

    private long deadlineDate= 0;
    private long jobCount =0;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private SharedPreferences sharedPreferences;
    private JobDetails jobDetails;

    private String jobType;
    private String jobMode;
    private String jobLocation;
    private String jobTags;
    private FirebaseUser firebaseUser;
    private Bundle bundle;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        spinnerJobType = findViewById(R.id.job_type);
        spinnerJobMode = findViewById(R.id.job_mode);
        spinnerJobLocation = findViewById(R.id.job_location);
        spinnerTags = findViewById(R.id.job_tags);
        jobTitle = findViewById(R.id.job_title);
        jobDescription = findViewById(R.id.job_description);
        jobSalary = findViewById(R.id.job_salary);
        jobDeadline = findViewById(R.id.job_deadline_tv);
        datePicker = findViewById(R.id.job_deadline);
        btnCreate = findViewById(R.id.btn_submit);
        setupSpinners();
        bundle = getIntent().getExtras();
        if(bundle != null && bundle.containsKey("jobCount")){
            jobCount = bundle.getLong("jobCount");
            jobCount = jobCount + 1;
        }

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("job_posts").child("job-"+jobCount);

        datePicker.setOnClickListener(this);
        btnCreate.setOnClickListener(this);





    }

    private void getJobCount() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("job_posts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                jobCount = snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setupSpinners() {
        ArrayAdapter<String> adapterJobType = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,jobTypeSpinner);
        spinnerJobType.setAdapter(adapterJobType);
        adapterJobType.notifyDataSetChanged();

        ArrayAdapter<String> adapterJobMode = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,jobModeSpinner);
        spinnerJobMode.setAdapter(adapterJobMode);
        adapterJobMode.notifyDataSetChanged();

        ArrayAdapter<String> adapterJobLocation = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,jobLocationSpinner);
        spinnerJobLocation.setAdapter(adapterJobLocation);
        adapterJobLocation.notifyDataSetChanged();

        ArrayAdapter<String> adapterJobTags = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,tagSpinner);
        spinnerTags.setAdapter(adapterJobTags);
        adapterJobTags.notifyDataSetChanged();

        spinnerJobType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jobType = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerJobMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                jobMode = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerJobLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                jobLocation = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerTags.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jobTags = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @Override
    public void onClick(View view) {

        if(view == this.datePicker){
            DatePickerFragmentDialog.newInstance().show(getSupportFragmentManager(), "DatePickerFragmentDialog");
        }

        if (view == this.btnCreate){
            validateAndCreate();
        }

    }

    private void validateAndCreate() {
        String mJobTitle = jobTitle.getText().toString();
        String mJobDescription = jobDescription.getText().toString();
        String mJobSalary = jobSalary.getText().toString();

        if(mJobTitle.equals("")){
            Toast.makeText(this, "Please fill the Job title", Toast.LENGTH_SHORT).show();
        }
        else if(mJobDescription.equals("")){
            Toast.makeText(this, "Please fill the Job description", Toast.LENGTH_SHORT).show();
        }
        else if(mJobSalary.equals("")){
            Toast.makeText(this, "Please fill the Job salary", Toast.LENGTH_SHORT).show();
        }
        else if(deadlineDate == 0){
            Toast.makeText(this, "Please fill job deadline date", Toast.LENGTH_SHORT).show();
        }
        else{
            submit(mJobTitle,mJobDescription,mJobSalary);
            finish();
        }

    }

    private void submit(String mJobTitle, String mJobDescription, String mJobSalary) {
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        jobDetails = new JobDetails(firebaseUser.getUid(),mJobTitle,mJobDescription,mJobSalary,jobType,jobMode,jobLocation, String.valueOf(deadlineDate),ts,jobTags);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(jobDetails);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CreateJobActivity.this, "Failed" + error, Toast.LENGTH_SHORT).show();

            }

        });
    }

    @Override
    public void onDateSet(long date) {
     deadlineDate = date;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}