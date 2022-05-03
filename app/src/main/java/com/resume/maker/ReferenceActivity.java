package com.resume.maker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.demo.R;

import java.util.regex.Pattern;


public class ReferenceActivity extends AppCompatActivity {
    private static final String TAG = "AcademicActivity>>";
    LinearLayout back;
    String desig;
    EditText et_rDesignation;
    EditText et_rMail;
    EditText et_rName;
    EditText et_rOrganization;
    EditText et_rPhone;
    int flag = 0;
    LinearLayout layout_toolbar;
    ImageView ll_save;
    String mail;
    String name;
    String orgnm;
    String phone;
    SharedPreferences store;
    TextView titaltext,backBtn,nextBtn;



    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_references);
        this.titaltext = (TextView) findViewById(R.id.titaltext);
        this.titaltext.setText("References");
        initToolbar();
        this.store = SharedPreferences.getInstance(this, "resumemaker");
        this.et_rName = (EditText) findViewById(R.id.et_rName);
        this.et_rMail = (EditText) findViewById(R.id.et_rMail);
        this.et_rPhone = (EditText) findViewById(R.id.et_rPhone);
        this.et_rOrganization = (EditText) findViewById(R.id.et_rOrganization);
        this.et_rDesignation = (EditText) findViewById(R.id.et_rDesignation);
        this.ll_save = (ImageView) findViewById(R.id.ll_save);
        this.backBtn = (TextView) findViewById(R.id.backBtn);
        this.nextBtn = (TextView) findViewById(R.id.nextBtn);
        getSharedpref();
        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.FullscreenAd(this);
        this.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), ExperienceActivity.class));
                finish();
            }
        });
        this.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SkillsActivity.class));
            }
        });

        this.ll_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReferenceActivity referenceActivity = ReferenceActivity.this;
                if (referenceActivity.isEmpty(referenceActivity.et_rName)) {
                    Toast.makeText(ReferenceActivity.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                ReferenceActivity referenceActivity2 = ReferenceActivity.this;
                if (referenceActivity2.isEmpty(referenceActivity2.et_rPhone)) {
                    Toast.makeText(ReferenceActivity.this, "Please Enter Contact", Toast.LENGTH_SHORT).show();
                    return;
                }
                ReferenceActivity referenceActivity3 = ReferenceActivity.this;
                if (referenceActivity3.isEmpty(referenceActivity3.et_rOrganization)) {
                    Toast.makeText(ReferenceActivity.this, "Please Enter Organization", Toast.LENGTH_SHORT).show();
                    return;
                }
                ReferenceActivity referenceActivity4 = ReferenceActivity.this;
                if (referenceActivity4.isEmpty(referenceActivity4.et_rDesignation)) {
                    Toast.makeText(ReferenceActivity.this, "Please Enter Designation", Toast.LENGTH_SHORT).show();
                    return;
                }
                ReferenceActivity referenceActivity5 = ReferenceActivity.this;
                if (!referenceActivity5.isValidMobile(referenceActivity5.et_rPhone.getText().toString())) {
                    Toast.makeText(ReferenceActivity.this, "Please Enter valid Phone Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!ReferenceActivity.this.et_rMail.getText().toString().equals("")) {
                    ReferenceActivity referenceActivity6 = ReferenceActivity.this;
                    if (!referenceActivity6.isValidMail(referenceActivity6.et_rMail.getText().toString())) {
                        Toast.makeText(ReferenceActivity.this, "Please Enter valid Email", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                ReferenceActivity referenceActivity7 = ReferenceActivity.this;
                if (referenceActivity7.isEmpty(referenceActivity7.et_rMail)) {
                    ReferenceActivity.this.et_rMail.setText(" ");
                }
                ReferenceActivity referenceActivity8 = ReferenceActivity.this;
                referenceActivity8.name = referenceActivity8.et_rName.getText().toString();
                ReferenceActivity referenceActivity9 = ReferenceActivity.this;
                referenceActivity9.mail = referenceActivity9.et_rMail.getText().toString();
                ReferenceActivity referenceActivity10 = ReferenceActivity.this;
                referenceActivity10.phone = referenceActivity10.et_rPhone.getText().toString();
                ReferenceActivity referenceActivity11 = ReferenceActivity.this;
                referenceActivity11.orgnm = referenceActivity11.et_rOrganization.getText().toString();
                ReferenceActivity referenceActivity12 = ReferenceActivity.this;
                referenceActivity12.desig = referenceActivity12.et_rDesignation.getText().toString();
                ReferenceActivity.this.store.saveString(ReferenceActivity.this.getString(R.string.reference_name1), ReferenceActivity.this.name);
                ReferenceActivity.this.store.saveString(ReferenceActivity.this.getString(R.string.reference_mail1), ReferenceActivity.this.mail);
                ReferenceActivity.this.store.saveString(ReferenceActivity.this.getString(R.string.reference_contact1), ReferenceActivity.this.phone);
                ReferenceActivity.this.store.saveString(ReferenceActivity.this.getString(R.string.reference_orgnm1), ReferenceActivity.this.orgnm);
                ReferenceActivity.this.store.saveString(ReferenceActivity.this.getString(R.string.reference_desig1), ReferenceActivity.this.desig);
                ReferenceActivity.this.finish();

            }
        });
    }

     private boolean save(){

             ReferenceActivity referenceActivity = ReferenceActivity.this;
             if (referenceActivity.isEmpty(referenceActivity.et_rName)) {
                 Toast.makeText(ReferenceActivity.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                 return false;
             }
             ReferenceActivity referenceActivity2 = ReferenceActivity.this;
             if (referenceActivity2.isEmpty(referenceActivity2.et_rPhone)) {
                 Toast.makeText(ReferenceActivity.this, "Please Enter Contact", Toast.LENGTH_SHORT).show();
                 return false;
             }
             ReferenceActivity referenceActivity3 = ReferenceActivity.this;
             if (referenceActivity3.isEmpty(referenceActivity3.et_rOrganization)) {
                 Toast.makeText(ReferenceActivity.this, "Please Enter Organization", Toast.LENGTH_SHORT).show();
                 return false;
             }
             ReferenceActivity referenceActivity4 = ReferenceActivity.this;
             if (referenceActivity4.isEmpty(referenceActivity4.et_rDesignation)) {
                 Toast.makeText(ReferenceActivity.this, "Please Enter Designation", Toast.LENGTH_SHORT).show();
                 return false;
             }
             ReferenceActivity referenceActivity5 = ReferenceActivity.this;
             if (!referenceActivity5.isValidMobile(referenceActivity5.et_rPhone.getText().toString())) {
                 Toast.makeText(ReferenceActivity.this, "Please Enter valid Phone Number", Toast.LENGTH_SHORT).show();
                 return false;
             }
             if (!ReferenceActivity.this.et_rMail.getText().toString().equals("")) {
                 ReferenceActivity referenceActivity6 = ReferenceActivity.this;
                 if (!referenceActivity6.isValidMail(referenceActivity6.et_rMail.getText().toString())) {
                     Toast.makeText(ReferenceActivity.this, "Please Enter valid Email", Toast.LENGTH_SHORT).show();
                     return false;
                 }
             }
             ReferenceActivity referenceActivity7 = ReferenceActivity.this;
             if (referenceActivity7.isEmpty(referenceActivity7.et_rMail)) {
                 ReferenceActivity.this.et_rMail.setText(" ");
             }
             ReferenceActivity referenceActivity8 = ReferenceActivity.this;
             referenceActivity8.name = referenceActivity8.et_rName.getText().toString();
             ReferenceActivity referenceActivity9 = ReferenceActivity.this;
             referenceActivity9.mail = referenceActivity9.et_rMail.getText().toString();
             ReferenceActivity referenceActivity10 = ReferenceActivity.this;
             referenceActivity10.phone = referenceActivity10.et_rPhone.getText().toString();
             ReferenceActivity referenceActivity11 = ReferenceActivity.this;
             referenceActivity11.orgnm = referenceActivity11.et_rOrganization.getText().toString();
             ReferenceActivity referenceActivity12 = ReferenceActivity.this;
             referenceActivity12.desig = referenceActivity12.et_rDesignation.getText().toString();
             ReferenceActivity.this.store.saveString(ReferenceActivity.this.getString(R.string.reference_name1), ReferenceActivity.this.name);
             ReferenceActivity.this.store.saveString(ReferenceActivity.this.getString(R.string.reference_mail1), ReferenceActivity.this.mail);
             ReferenceActivity.this.store.saveString(ReferenceActivity.this.getString(R.string.reference_contact1), ReferenceActivity.this.phone);
             ReferenceActivity.this.store.saveString(ReferenceActivity.this.getString(R.string.reference_orgnm1), ReferenceActivity.this.orgnm);
             ReferenceActivity.this.store.saveString(ReferenceActivity.this.getString(R.string.reference_desig1), ReferenceActivity.this.desig);
//             ReferenceActivity.this.finish();
    return false;

     }
    public boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    private void getSharedpref() {
        try {
            if (this.store.getString(getString(R.string.reference_name1), "") != null) {
                Log.d("sharedpref>>2", this.store.getString(getString(R.string.reference_name1), ""));
                String string = this.store.getString(getString(R.string.reference_name1), "");
                String string2 = this.store.getString(getString(R.string.reference_mail1), "");
                String string3 = this.store.getString(getString(R.string.reference_contact1), "");
                String string4 = this.store.getString(getString(R.string.reference_orgnm1), "");
                String string5 = this.store.getString(getString(R.string.reference_desig1), "");
                this.et_rName.setText(string);
                this.et_rMail.setText(string2);
                this.et_rPhone.setText(string3);
                this.et_rOrganization.setText(string4);
                this.et_rDesignation.setText(string5);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("sharedpref>>2", "null");
        }
    }


    public boolean isValidMobile(String str) {
        if (Pattern.matches("[a-zA-Z]+", str) || str.length() <= 6 || str.length() > 13) {
            return false;
        }
        return true;
    }


    public boolean isValidMail(String str) {
        return Patterns.EMAIL_ADDRESS.matcher(str).matches();
    }

    @Override
    public void onBackPressed() {
//        backdialog();
    finish();
    }

    private void initToolbar() {
        Window window = getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
//        if (Build.VERSION.SDK_INT >= 21) {
//            window.setStatusBarColor(ContextCompat.getColor(this, R.color.color_reference));
//        }
        this.layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
//        this.layout_toolbar.setBackgroundResource(R.drawable.toolbar_ref);
        this.back = (LinearLayout) findViewById(R.id.back);
        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReferenceActivity.this.onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

//    public void backdialog() {
//        DialogInterface.OnClickListener
//                r0 = new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                if (i == -3) {
//                    dialogInterface.dismiss();
//                } else if (i == -1) {
//                    dialogInterface.dismiss();
//                    ReferenceActivity.this.finish();
////                    finishAffinity();
//                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
//                }
//            }
//        };
//        new AlertDialog.Builder(this).setMessage("Discard editing?").setPositiveButton("Ok", r0).setTitle("").setNeutralButton("Cancel", r0).show();
//    }
}
