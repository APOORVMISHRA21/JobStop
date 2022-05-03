package com.resume.maker.Recruiter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.demo.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.resume.maker.Recruiter.Models.RecruiterDetails;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecruiterDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private CircleImageView recruiterDp;
    private ImageView recruiterDpchange;
    private EditText recruiterName;
    private Button btnSubmit;
    private EditText companyName;
    private EditText companyMail;
    private EditText companyDescription;
    private TextView emailValidityText;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    RecruiterDetails recruiterDetails;
    private Uri recruiterDpUri;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_details);
        recruiterDp = findViewById(R.id.recruiter_dp);
        recruiterName = findViewById(R.id.company_name);
        recruiterDpchange = findViewById(R.id.btn_change_dp);
        btnSubmit = findViewById(R.id.btn_submit);
        companyName = findViewById(R.id.company_name);
        companyMail = findViewById(R.id.company_mail);
        companyDescription = findViewById(R.id.company_description);
        emailValidityText = findViewById(R.id.email_validity_text);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        // Initialize firebase auth
        firebaseAuth=FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("companyDetails").child(firebaseUser.getUid());

        if(firebaseUser!=null)
        {
            Glide.with(this)
                    .load(getDrawable(R.drawable.ic_placeholder_dp))
                    .circleCrop()
                    .into(recruiterDp);
        }
        companyMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String email = "" + charSequence;
                if (email.matches(emailPattern) && charSequence.length() > 0)
                {
                    emailValidityText.setVisibility(View.VISIBLE);
                    emailValidityText.setText("Valid email");
                    emailValidityText.setTextColor(Color.parseColor("#008000"));
                }
                else
                {
                    emailValidityText.setVisibility(View.VISIBLE);
                    emailValidityText.setText("Invalid email. Please enter a valid email!");
                    emailValidityText.setTextColor(Color.parseColor("#FF0000"));

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
        recruiterDpchange.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(view == this.recruiterDpchange){
            pickImage();
        }

        if(view == this.btnSubmit){
            validateData();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            Uri imageUri = data.getData();
            recruiterDpUri = imageUri;
            recruiterDp.setImageURI(imageUri);
        }
        else if(resultCode == ImagePicker.RESULT_ERROR){
            Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private void pickImage() {
        ImagePicker.Companion.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start();
    }

    private void uploadImage(){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        StorageReference reference = storageReference.child("recruiter_dp").child(firebaseAuth.getUid());
        reference.putFile(recruiterDpUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Toast.makeText(RecruiterDetailsActivity.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(RecruiterDetailsActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        progressDialog.setMessage("Uploaded " + (int)progress + "%");
                    }
                });
    }


    private void validateData() {
        String mCompanyName = companyName.getText().toString();
        String mCompanyMail = companyMail.getText().toString();
        String mCompanyDescription = companyDescription.getText().toString();
        if(mCompanyName.equals("")){
            Toast.makeText(this, "Please fill the company name", Toast.LENGTH_SHORT).show();
        }
        else if(mCompanyMail.equals("")){
            Toast.makeText(this, "Please fill the company mail", Toast.LENGTH_SHORT).show();
        }
        else if(mCompanyDescription.equals("")){
            Toast.makeText(this, "Please fill the company description", Toast.LENGTH_SHORT).show();
        }
        else{
            if(recruiterDpUri!= null)
            uploadImage();
            SubmitData(mCompanyName,mCompanyMail,mCompanyDescription);

        }
    }

    private void SubmitData(String mCompanyName, String mCompanyMail, String mCompanyDescription) {

        recruiterDetails = new RecruiterDetails(mCompanyName,mCompanyMail,mCompanyDescription);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(recruiterDetails);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RecruiterDetailsActivity.this, "Failed" + error, Toast.LENGTH_SHORT).show();
            }
        });

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(RecruiterDetailsActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);

    }

}