package com.resume.maker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.demo.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.resume.maker.Recruiter.RecruiterDetailsActivity;

public class OnboardingActivity extends AppCompatActivity {

    private CardView studentCard, recruiterCard;

    private String TAG = "OnBoardingActivity";

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleClient;

    private SharedPreferences sharedPref;

    private final int STUDENT_REQUEST_CODE = 100, RECRUITER_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        sharedPref = new SharedPreferences(this);
        initAuthentication();
        initViews();
    }

    private void initAuthentication(){
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleClient = GoogleSignIn.getClient(this, gso);
        mGoogleClient.revokeAccess();
    }

    private void initViews() {
        studentCard = findViewById(R.id.student_login);
        recruiterCard = findViewById(R.id.recruiter_login);

        studentCard.setOnClickListener(v -> {
            signInWithGoogle(STUDENT_REQUEST_CODE);
        });

        recruiterCard.setOnClickListener(v -> {
            signInWithGoogle(RECRUITER_REQUEST_CODE);
        });
    }

    private void signInWithGoogle(int requestCode){
        Intent signInIntent = mGoogleClient.getSignInIntent();
        startActivityForResult(signInIntent, requestCode);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == STUDENT_REQUEST_CODE || requestCode == RECRUITER_REQUEST_CODE){
            Task<GoogleSignInAccount> task = GoogleSignIn
                    .getSignedInAccountFromIntent(data);
            handleSignInResult(task, requestCode);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask, int requestCode){
        try{
            GoogleSignInAccount account =
                    completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account.getIdToken(), requestCode);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    private void firebaseAuthWithGoogle(String idToken, int requestCode){
        AuthCredential credential =
                GoogleAuthProvider.getCredential(idToken,
                        null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this,
                        task -> {
                            if(task.isSuccessful()){
                                Log.e(TAG, "SignIn Google Successful");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user, requestCode);
                            } else{
                                Log.e(TAG, "SignIn Google Failed");
                            }
                        });
    }


    private void updateUI(FirebaseUser user,int requestCode) {
        Log.e(TAG, "@UpdateUI");
        switch (requestCode) {
            case STUDENT_REQUEST_CODE:
                Log.e(TAG, "Student Login");
                sharedPref.setCurrentUserType(STUDENT_REQUEST_CODE);
                startActivity(new Intent(OnboardingActivity.this
                        , HomeActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
                break;

            case RECRUITER_REQUEST_CODE:
                Log.e(TAG, "Recruiter Login");
                sharedPref.setCurrentUserType(RECRUITER_REQUEST_CODE);
                startActivity(new Intent(OnboardingActivity.this
                        , RecruiterDetailsActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
                break;
        }
    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

}