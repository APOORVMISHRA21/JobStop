package com.resume.maker;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfDocument;
import android.graphics.text.LineBreaker;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.demo.R;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.resume.maker.adapters.ResumeSampleAdapter;
import com.resume.maker.models.ResumeSampleModel;
import com.resume.maker.utils.MoveViewTouchListener;
import com.resume.maker.utils.ZoomLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class ResumeSample1Activity extends AppCompatActivity {
    LinearLayout back;
    private Bitmap bitmap;
    Bitmap bmp;
    int flag = 0;
    Button imgdone;
    ImageView imgmain;
    ImageView imgundo;
    ImageView ivuserprofile;
    LinearLayout layoutabout;
    LinearLayout layoutedu;
    LinearLayout layoutedu1;
    LinearLayout layoutedu2;
    LinearLayout layoutedu3;
    LinearLayout layouthobby;
    LinearLayout layoutlanguage;
    RelativeLayout layoutmain;
    LinearLayout layoutpersonalinfo1;
    LinearLayout layoutpersonalinfo2;
    LinearLayout layoutref;
    LinearLayout layoutskilles;
    LinearLayout layoutwexp;
    LinearLayout layoutwexp1;
    LinearLayout layoutwexp2;
    LinearLayout layoutwexp3;
    float newHeight;
    float screenWidth;
    SharedPreferences store;
    TextView tv_aboutme;
    TextView tv_address;
    TextView tv_clgnm1;
    TextView tv_clgnm2;
    TextView tv_clgnm3;
    TextView tv_degree1;
    TextView tv_degree2;
    TextView tv_degree3;
    TextView tv_desig1;
    TextView tv_desig2;
    TextView tv_desig3;
    TextView tv_email;
    TextView tv_field;
    TextView tv_fromtime1;
    TextView tv_fromtime2;
    TextView tv_fromtime3;
    TextView tv_hobby1;
    TextView tv_hobby2;
    TextView tv_hobby3;
    TextView tv_hobby4;
    TextView tv_hobby5;
    TextView tv_lan1;
    TextView tv_lan2;
    TextView tv_lan3;
    TextView tv_lan4;
    TextView tv_lan5;
    TextView tv_name;
    TextView tv_orgnm1;
    TextView tv_orgnm2;
    TextView tv_orgnm3;
    TextView tv_pernum1;
    TextView tv_pernum2;
    TextView tv_pernum3;
    TextView tv_perorcpga1;
    TextView tv_perorcpga2;
    TextView tv_perorcpga3;
    TextView tv_phone;
    TextView tv_refemail;
    TextView tv_refnm;
    TextView tv_reforgnm;
    TextView tv_refphone;
    TextView tv_skill1;
    TextView tv_skill2;
    TextView tv_skill3;
    TextView tv_skill4;
    TextView tv_skill5;
    TextView tv_totime1;
    TextView tv_totime2;
    TextView tv_totime3;
    TextView tv_year1;
    TextView tv_year2;
    TextView tv_year3;
    TextView tvrole1;
    TextView tvrole2;
    TextView tvrole3;


    @TargetApi(21)
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back);
    }


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (ResumeSampleAdapter.selectedposition == 0) {
            setContentView(R.layout.activity_resume_sample1);
            Log.d("resumeposition>>1", ResumeSampleAdapter.selectedposition + "");
        } else if (ResumeSampleAdapter.selectedposition == 1) {
            setContentView(R.layout.activity_resume_sample2);
        } else if (ResumeSampleAdapter.selectedposition == 2) {
            setContentView(R.layout.activity_resume_sample3);
        } else if (ResumeSampleAdapter.selectedposition == 3) {
            setContentView(R.layout.activity_resume_sample4);
        } else if (ResumeSampleAdapter.selectedposition == 4) {
            setContentView(R.layout.activity_resume_sample5);
        } else if (ResumeSampleAdapter.selectedposition == 5) {
            setContentView(R.layout.activity_resume_sample6);
        } else if (ResumeSampleAdapter.selectedposition == 6) {
            setContentView(R.layout.activity_resume_sample7);
        } else if (ResumeSampleAdapter.selectedposition == 7) {
            setContentView(R.layout.activity_resume_sample8);
        } else if (ResumeSampleAdapter.selectedposition == 8) {
            setContentView(R.layout.activity_resume_sample9);
        } else if (ResumeSampleAdapter.selectedposition == 9) {
            setContentView(R.layout.activity_resume_sample10);
        } else if (ResumeSampleAdapter.selectedposition == 10) {
            setContentView(R.layout.activity_resume_sample11);
        } else if (ResumeSampleAdapter.selectedposition == 11) {
            setContentView(R.layout.activity_resume_sample12);
        } else if (ResumeSampleAdapter.selectedposition == 12) {
            setContentView(R.layout.activity_resume_sample13);
        } else if (ResumeSampleAdapter.selectedposition == 13) {
            setContentView(R.layout.activity_resume_sample14);
        } else if (ResumeSampleAdapter.selectedposition == 14) {
            setContentView(R.layout.activity_resume_sample15);
        }
        initToolbar();


        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.FullscreenAd(this);


        new Gson();
        String stringExtra = getIntent().getStringExtra("obj");
        getIntent().getIntExtra("position", 0);
        int i = ((ResumeSampleModel) ((List) new Gson().fromJson(stringExtra, new TypeToken<List<ResumeSampleModel>>() {
        }.getType())).get(ResumeSampleAdapter.selectedposition)).getfrm2();
        this.store = SharedPreferences.getInstance(this, "resumemaker");
        this.bmp = BitmapFactory.decodeResource(getResources(), i);
        this.layoutmain = (RelativeLayout) findViewById(R.id.layoutrelativemain);
        this.layoutedu = (LinearLayout) findViewById(R.id.layoutedu);
        this.layoutedu1 = (LinearLayout) findViewById(R.id.layoutedu1);
        this.layoutedu2 = (LinearLayout) findViewById(R.id.layoutedu2);
        this.layoutedu3 = (LinearLayout) findViewById(R.id.layoutedu3);
        this.layoutpersonalinfo1 = (LinearLayout) findViewById(R.id.layoutpersonalinfo1);
        this.layoutpersonalinfo2 = (LinearLayout) findViewById(R.id.layoutpersonalinfo2);
        this.layoutabout = (LinearLayout) findViewById(R.id.layoutabout);
        this.layoutwexp = (LinearLayout) findViewById(R.id.layoutwexp);
        this.layoutwexp1 = (LinearLayout) findViewById(R.id.layoutwexp1);
        this.layoutwexp2 = (LinearLayout) findViewById(R.id.layoutwexp2);
        this.layoutwexp3 = (LinearLayout) findViewById(R.id.layoutwexp3);
        this.layoutskilles = (LinearLayout) findViewById(R.id.layoutskilles);
        this.layoutlanguage = (LinearLayout) findViewById(R.id.layoutlanguage);
        this.layouthobby = (LinearLayout) findViewById(R.id.layouthobby);
        this.layoutref = (LinearLayout) findViewById(R.id.layoutref);
        this.imgmain = (ImageView) findViewById(R.id.imgmain);
        this.back = (LinearLayout) findViewById(R.id.back);
        this.imgdone = (Button) findViewById(R.id.imgdone);
        this.imgundo = (ImageView) findViewById(R.id.imgundo);
        this.ivuserprofile = (ImageView) findViewById(R.id.ivuserprofile);
        this.tv_name = (TextView) findViewById(R.id.tv_name);
        this.tv_field = (TextView) findViewById(R.id.tv_field);
        this.tv_aboutme = (TextView) findViewById(R.id.tv_aboutme);
        this.tv_email = (TextView) findViewById(R.id.tv_email);
        this.tv_phone = (TextView) findViewById(R.id.tv_phone);
        this.tv_address = (TextView) findViewById(R.id.tv_address);
        this.tv_skill1 = (TextView) findViewById(R.id.tv_skill1);
        this.tv_skill2 = (TextView) findViewById(R.id.tv_skill2);
        this.tv_skill3 = (TextView) findViewById(R.id.tv_skill3);
        this.tv_skill4 = (TextView) findViewById(R.id.tv_skill4);
        this.tv_skill5 = (TextView) findViewById(R.id.tv_skill5);
        this.tv_clgnm1 = (TextView) findViewById(R.id.tv_clgnm1);
        this.tv_year1 = (TextView) findViewById(R.id.tv_year1);
        this.tv_degree1 = (TextView) findViewById(R.id.tv_degree1);
        this.tv_pernum1 = (TextView) findViewById(R.id.tv_pernum1);
        this.tv_perorcpga1 = (TextView) findViewById(R.id.tv_perorcpga1);
        this.tv_clgnm2 = (TextView) findViewById(R.id.tv_clgnm2);
        this.tv_year2 = (TextView) findViewById(R.id.tv_year2);
        this.tv_degree2 = (TextView) findViewById(R.id.tv_degree2);
        this.tv_pernum2 = (TextView) findViewById(R.id.tv_pernum2);
        this.tv_perorcpga2 = (TextView) findViewById(R.id.tv_perorcpga2);
        this.tv_clgnm3 = (TextView) findViewById(R.id.tv_clgnm3);
        this.tv_year3 = (TextView) findViewById(R.id.tv_year3);
        this.tv_degree3 = (TextView) findViewById(R.id.tv_degree3);
        this.tv_pernum3 = (TextView) findViewById(R.id.tv_pernum3);
        this.tv_perorcpga3 = (TextView) findViewById(R.id.tv_perorcpga3);
        this.tv_orgnm1 = (TextView) findViewById(R.id.tv_orgnm1);
        this.tv_fromtime1 = (TextView) findViewById(R.id.tv_fromtime1);
        this.tv_totime1 = (TextView) findViewById(R.id.tv_totime1);
        this.tv_desig1 = (TextView) findViewById(R.id.tv_desig1);
        this.tvrole1 = (TextView) findViewById(R.id.tvrole1);
        this.tv_orgnm2 = (TextView) findViewById(R.id.tv_orgnm2);
        this.tv_fromtime2 = (TextView) findViewById(R.id.tv_fromtime2);
        this.tv_totime2 = (TextView) findViewById(R.id.tv_totime2);
        this.tv_desig2 = (TextView) findViewById(R.id.tv_desig2);
        this.tvrole2 = (TextView) findViewById(R.id.tvrole2);
        this.tv_orgnm3 = (TextView) findViewById(R.id.tv_orgnm3);
        this.tv_fromtime3 = (TextView) findViewById(R.id.tv_fromtime3);
        this.tv_totime3 = (TextView) findViewById(R.id.tv_totime3);
        this.tv_desig3 = (TextView) findViewById(R.id.tv_desig3);
        this.tvrole3 = (TextView) findViewById(R.id.tvrole3);
        this.tv_lan1 = (TextView) findViewById(R.id.tv_lan1);
        this.tv_lan2 = (TextView) findViewById(R.id.tv_lan2);
        this.tv_lan3 = (TextView) findViewById(R.id.tv_lan3);
        this.tv_lan4 = (TextView) findViewById(R.id.tv_lan4);
        this.tv_lan5 = (TextView) findViewById(R.id.tv_lan5);
        this.tv_hobby1 = (TextView) findViewById(R.id.tv_hobby1);
        this.tv_hobby2 = (TextView) findViewById(R.id.tv_hobby2);
        this.tv_hobby3 = (TextView) findViewById(R.id.tv_hobby3);
        this.tv_hobby4 = (TextView) findViewById(R.id.tv_hobby4);
        this.tv_hobby5 = (TextView) findViewById(R.id.tv_hobby5);
        this.tv_refnm = (TextView) findViewById(R.id.tv_refnm);
        this.tv_reforgnm = (TextView) findViewById(R.id.tv_reforgnm);
        this.tv_refphone = (TextView) findViewById(R.id.tv_refphone);
        this.tv_refemail = (TextView) findViewById(R.id.tv_refemail);

        layoutmain.setClipToOutline(true);

        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResumeSample1Activity resumeSample1Activity = ResumeSample1Activity.this;
                resumeSample1Activity.flag = 2;
                ResumeSample1Activity.this.onBackPressed();

            }
        });
        this.imgdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResumeSample1Activity resumeSample1Activity = ResumeSample1Activity.this;
                resumeSample1Activity.flag = 1;
                try {
                    Log.d("size>>>", " " + ResumeSample1Activity.this.layoutmain.getWidth() + "  " + ResumeSample1Activity.this.layoutmain.getWidth());
                    ResumeSample1Activity.this.bitmap = ResumeSample1Activity.loadBitmapFromView(ResumeSample1Activity.this.layoutmain, (int) ResumeSample1Activity.this.layoutmain.getWidth(), (int) ResumeSample1Activity.this.layoutmain.getHeight());
                    ResumeSample1Activity.this.createPdf();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        this.imgundo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResumeSample1Activity.this.refreshActivity();
            }
        });
        this.layoutmain.addView(new ZoomLayout(this));
        Bitmap bitmap = this.bmp;
        if (bitmap != null) {
            bitmap.getWidth();
            this.bmp.getHeight();
            Display defaultDisplay = getWindowManager().getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            defaultDisplay.getMetrics(displayMetrics);
            this.screenWidth = (float) displayMetrics.widthPixels;
            this.newHeight = this.screenWidth;
            if (!(this.bmp.getWidth() == 0 || this.bmp.getHeight() == 0)) {
                this.newHeight = (this.screenWidth * ((float) this.bmp.getHeight())) / ((float) this.bmp.getWidth());
            }
            Log.d("final_bmp", this.bmp.getWidth() + "," + this.bmp.getHeight());
            Log.d("final_bmp", this.screenWidth + "," + this.newHeight);
            this.bmp = Bitmap.createScaledBitmap(this.bmp, (int) this.screenWidth, (int) this.newHeight, false);
            new BitmapDrawable(getResources(), this.bmp);
            this.imgmain.setImageBitmap(this.bmp);
        }
        getSharedpref();
        ImageView imageView = this.ivuserprofile;
        imageView.setOnTouchListener(new MoveViewTouchListener(imageView));
        LinearLayout linearLayout = this.layoutedu;
        linearLayout.setOnTouchListener(new MoveViewTouchListener(linearLayout));
        LinearLayout linearLayout2 = this.layoutwexp;
        linearLayout2.setOnTouchListener(new MoveViewTouchListener(linearLayout2));
        LinearLayout linearLayout3 = this.layoutskilles;
        linearLayout3.setOnTouchListener(new MoveViewTouchListener(linearLayout3));
        LinearLayout linearLayout4 = this.layoutlanguage;
        linearLayout4.setOnTouchListener(new MoveViewTouchListener(linearLayout4));
        LinearLayout linearLayout5 = this.layouthobby;
        linearLayout5.setOnTouchListener(new MoveViewTouchListener(linearLayout5));
        LinearLayout linearLayout6 = this.layoutref;
        linearLayout6.setOnTouchListener(new MoveViewTouchListener(linearLayout6));
        LinearLayout linearLayout7 = this.layoutpersonalinfo1;
        linearLayout7.setOnTouchListener(new MoveViewTouchListener(linearLayout7));
        LinearLayout linearLayout8 = this.layoutpersonalinfo2;
        linearLayout8.setOnTouchListener(new MoveViewTouchListener(linearLayout8));
        LinearLayout linearLayout9 = this.layoutabout;
        linearLayout9.setOnTouchListener(new MoveViewTouchListener(linearLayout9));
    }

    private void getSharedpref() {
        String str;
        String str2;
        try {
            if (this.store.contains(getString(R.string.personalinfo_name))) {
                Log.d("sharedpref>>r1", this.store.getString(getString(R.string.personalinfo_name), ""));
                String string = this.store.getString(getString(R.string.personalinfo_profile), "");
                String string2 = this.store.getString(getString(R.string.personalinfo_name), "");
                String string3 = this.store.getString(getString(R.string.personalinfo_phone), "");
                String string4 = this.store.getString(getString(R.string.personalinfo_email), "");
                String string5 = this.store.getString(getString(R.string.personalinfo_address), "");
                String string6 = this.store.getString(getString(R.string.personalinfo_field), "");
                String string7 = this.store.getString(getString(R.string.personalinfo_aboutme), "");
                this.ivuserprofile.setImageURI(Uri.parse(string));
                this.tv_name.setText(string2);
                this.tv_phone.setText(string3);
                this.tv_email.setText(string4);
                this.tv_address.setText(string5);
                this.tv_field.setText(string6);
                if (Build.VERSION.SDK_INT >= 26) {
                    this.tv_aboutme.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
                }
                this.tv_aboutme.setText(string7);
            }
            if (this.store.contains(getString(R.string.skill1))) {
                String string8 = this.store.getString(getString(R.string.skill1), "");
                TextView textView = this.tv_skill1;
                textView.setText("• " + string8);
            } else {
                this.tv_skill1.setVisibility(View.GONE);
            }
            if (this.store.contains(getString(R.string.skill2))) {
                String string9 = this.store.getString(getString(R.string.skill2), "");
                TextView textView2 = this.tv_skill2;
                textView2.setText("• " + string9);
            } else {
                this.tv_skill2.setVisibility(View.GONE);
            }
            if (this.store.contains(getString(R.string.skill3))) {
                String string10 = this.store.getString(getString(R.string.skill3), "");
                TextView textView3 = this.tv_skill3;
                textView3.setText("• " + string10);
            } else {
                this.tv_skill3.setVisibility(View.GONE);
            }
            if (this.store.contains(getString(R.string.skill4))) {
                String string11 = this.store.getString(getString(R.string.skill4), "");
                TextView textView4 = this.tv_skill4;
                textView4.setText("• " + string11);
            } else {
                this.tv_skill4.setVisibility(View.GONE);
            }
            if (this.store.contains(getString(R.string.skill5))) {
                String string12 = this.store.getString(getString(R.string.skill5), "");
                TextView textView5 = this.tv_skill5;
                textView5.setText("• " + string12);
            } else {
                this.tv_skill5.setVisibility(View.GONE);
            }
            String str3 = "%";
            if (this.store.contains(getString(R.string.academic_degree1))) {
                String string13 = this.store.getString(getString(R.string.academic_institute1), "");
                String string14 = this.store.getString(getString(R.string.academic_year1), "");
                String string15 = this.store.getString(getString(R.string.academic_degree1), "");
                String string16 = this.store.getString(getString(R.string.academic_percgpa1), "");
                if (this.store.getString(getString(R.string.academic_percgpa1_radio), "").equals("Percentage")) {
                    str2 = str3;
                } else {
                    str2 = "CGPA";
                }
                TextView textView6 = this.tv_clgnm1;
                textView6.setText("• " + string13);
                TextView textView7 = this.tv_year1;
                textView7.setText("| " + string14);
                this.tv_degree1.setText(string15);
                this.tv_pernum1.setText(string16);
                this.tv_perorcpga1.setText(str2);
            } else {
                this.layoutedu1.setVisibility(View.GONE);
            }
            if (this.store.contains(getString(R.string.academic_degree2))) {
                String string17 = this.store.getString(getString(R.string.academic_institute2), "");
                String string18 = this.store.getString(getString(R.string.academic_year2), "");
                String string19 = this.store.getString(getString(R.string.academic_degree2), "");
                String string20 = this.store.getString(getString(R.string.academic_percgpa2), "");
                if (this.store.getString(getString(R.string.academic_percgpa2_radio), "").equals("Percentage")) {
                    str = str3;
                } else {
                    str = "CGPA";
                }
                TextView textView8 = this.tv_clgnm2;
                textView8.setText("• " + string17);
                TextView textView9 = this.tv_year2;
                textView9.setText("| " + string18);
                this.tv_degree2.setText(string19);
                this.tv_pernum2.setText(string20);
                this.tv_perorcpga2.setText(str);
            } else {
                this.layoutedu2.setVisibility(View.GONE);
            }
            if (this.store.contains(getString(R.string.academic_degree3))) {
                String string21 = this.store.getString(getString(R.string.academic_institute3), "");
                String string22 = this.store.getString(getString(R.string.academic_year3), "");
                String string23 = this.store.getString(getString(R.string.academic_degree3), "");
                String string24 = this.store.getString(getString(R.string.academic_percgpa3), "");
                if (!this.store.getString(getString(R.string.academic_percgpa3_radio), "").equals("Percentage")) {
                    str3 = "CGPA";
                }
                TextView textView10 = this.tv_clgnm3;
                textView10.setText("• " + string21);
                TextView textView11 = this.tv_year3;
                textView11.setText("| " + string22);
                this.tv_degree3.setText(string23);
                this.tv_pernum3.setText(string24);
                this.tv_perorcpga3.setText(str3);
            } else {
                this.layoutedu3.setVisibility(View.GONE);
            }
            if (this.store.contains(getString(R.string.experience_organization1))) {
                Log.d("exp1>>>", this.store.contains(getString(R.string.experience_organization1)) + ">");
                String string25 = this.store.getString(getString(R.string.experience_organization1), "");
                String string26 = this.store.getString(getString(R.string.experience_designation1), "");
                String string27 = this.store.getString(getString(R.string.experience_role1), "");
                String string28 = this.store.getString(getString(R.string.experience_fromdate1), "");
                String string29 = this.store.getString(getString(R.string.experience_todate1), "");
                TextView textView12 = this.tv_orgnm1;
                textView12.setText("• " + string25);
                this.tv_desig1.setText(string26);
                this.tvrole1.setText(string27);
                TextView textView13 = this.tv_fromtime1;
                textView13.setText("|" + string28 + "-" + string29);
            } else {
                this.layoutwexp1.setVisibility(View.GONE);
            }
            if (this.store.contains(getString(R.string.experience_organization2))) {
                String string30 = this.store.getString(getString(R.string.experience_organization2), "");
                String string31 = this.store.getString(getString(R.string.experience_designation2), "");
                String string32 = this.store.getString(getString(R.string.experience_role2), "");
                String string33 = this.store.getString(getString(R.string.experience_fromdate2), "");
                String string34 = this.store.getString(getString(R.string.experience_todate2), "");
                TextView textView14 = this.tv_orgnm2;
                textView14.setText("• " + string30);
                this.tv_desig2.setText(string31);
                this.tvrole2.setText(string32);
                TextView textView15 = this.tv_fromtime2;
                textView15.setText("|" + string33 + "-" + string34);
            } else {
                this.layoutwexp2.setVisibility(View.GONE);
            }
            if (this.store.contains(getString(R.string.experience_organization3))) {
                String string35 = this.store.getString(getString(R.string.experience_organization3), "");
                String string36 = this.store.getString(getString(R.string.experience_designation3), "");
                String string37 = this.store.getString(getString(R.string.experience_role3), "");
                String string38 = this.store.getString(getString(R.string.experience_fromdate3), "");
                String string39 = this.store.getString(getString(R.string.experience_todate3), "");
                TextView textView16 = this.tv_orgnm3;
                textView16.setText("• " + string35);
                this.tv_desig3.setText(string36);
                this.tvrole3.setText(string37);
                TextView textView17 = this.tv_fromtime3;
                textView17.setText("|" + string38 + "-" + string39);
            } else {
                this.layoutwexp3.setVisibility(View.GONE);
            }
            if (this.store.contains(getString(R.string.language1))) {
                String string40 = this.store.getString(getString(R.string.language1), "");
                TextView textView18 = this.tv_lan1;
                textView18.setText("• " + string40);
            } else {
                this.tv_lan1.setVisibility(View.GONE);
            }
            if (this.store.contains(getString(R.string.language2))) {
                String string41 = this.store.getString(getString(R.string.language2), "");
                TextView textView19 = this.tv_lan2;
                textView19.setText("• " + string41);
            } else {
                this.tv_lan2.setVisibility(View.GONE);
            }
            if (this.store.contains(getString(R.string.language3))) {
                String string42 = this.store.getString(getString(R.string.language3), "");
                TextView textView20 = this.tv_lan3;
                textView20.setText("• " + string42);
            } else {
                this.tv_lan3.setVisibility(View.GONE);
            }
            if (this.store.contains(getString(R.string.language4))) {
                String string43 = this.store.getString(getString(R.string.language4), "");
                TextView textView21 = this.tv_lan4;
                textView21.setText("• " + string43);
            } else {
                this.tv_lan4.setVisibility(View.GONE);
            }
            if (this.store.contains(getString(R.string.language5))) {
                String string44 = this.store.getString(getString(R.string.language5), "");
                TextView textView22 = this.tv_lan5;
                textView22.setText("• " + string44);
            } else {
                this.tv_lan5.setVisibility(View.GONE);
            }
            if (this.store.contains(getString(R.string.hobby1))) {
                String string45 = this.store.getString(getString(R.string.hobby1), "");
                TextView textView23 = this.tv_hobby1;
                textView23.setText("• " + string45);
            } else {
                this.tv_hobby1.setVisibility(View.GONE);
            }
            if (this.store.contains(getString(R.string.hobby2))) {
                String string46 = this.store.getString(getString(R.string.hobby2), "");
                TextView textView24 = this.tv_hobby2;
                textView24.setText("• " + string46);
            } else {
                this.tv_hobby2.setVisibility(View.GONE);
            }
            if (this.store.contains(getString(R.string.hobby3))) {
                String string47 = this.store.getString(getString(R.string.hobby3), "");
                TextView textView25 = this.tv_hobby3;
                textView25.setText("• " + string47);
            } else {
                this.tv_hobby3.setVisibility(View.GONE);
            }
            if (this.store.contains(getString(R.string.hobby4))) {
                String string48 = this.store.getString(getString(R.string.hobby4), "");
                TextView textView26 = this.tv_hobby4;
                textView26.setText("• " + string48);
            } else {
                this.tv_hobby4.setVisibility(View.GONE);
            }
            if (this.store.contains(getString(R.string.hobby5))) {
                String string49 = this.store.getString(getString(R.string.hobby5), "");
                TextView textView27 = this.tv_hobby5;
                textView27.setText("• " + string49);
            } else {
                this.tv_hobby5.setVisibility(View.GONE);
            }
            if (this.store.contains(getString(R.string.reference_name1))) {
                String string50 = this.store.getString(getString(R.string.reference_name1), "");
                String string51 = this.store.getString(getString(R.string.reference_orgnm1), "");
                String string52 = this.store.getString(getString(R.string.reference_contact1), "");
                String string53 = this.store.getString(getString(R.string.reference_mail1), "");
                TextView textView28 = this.tv_refnm;
                textView28.setText("• " + string50);
                this.tv_reforgnm.setText(string51);
                this.tv_refphone.setText(string52);
                this.tv_refemail.setText(string53);
                return;
            }
            this.layoutref.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("sharedpref>>r1", "null");
        }
    }

    public void createPdf() {
        WindowManager windowManager = (WindowManager) getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.heightPixels;
        int i2 = displayMetrics.widthPixels;
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.Page startPage = pdfDocument.startPage(new PdfDocument.PageInfo.Builder((int) this.screenWidth, (int) this.newHeight, 1).create());
        Canvas canvas = startPage.getCanvas();
        Paint paint = new Paint();
        canvas.drawPaint(paint);
        this.bitmap = Bitmap.createScaledBitmap(this.bitmap, (int) this.screenWidth, (int) this.newHeight, true);
        paint.setColor(-16776961);
        canvas.drawBitmap(this.bitmap, 0.0f, 0.0f, (Paint) null);
        pdfDocument.finishPage(startPage);
        File externalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(externalStorageDirectory.getAbsolutePath() + File.separator + getString(R.string.app_name));
        scanFile(externalStorageDirectory.getAbsolutePath());
        if (!file.exists()) {
            file.mkdirs();
        }


        File save_file = new File(file + "/" + String.format("%d.pdf", Long.valueOf(System.currentTimeMillis())));
        try {
            pdfDocument.writeTo(new FileOutputStream(save_file));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        pdfDocument.close();
        Toast.makeText(this, "PDF saved =" + save_file.toString(), Toast.LENGTH_SHORT).show();
    }

    private void scanFile(String str) {
        MediaScannerConnection.scanFile(this, new String[]{str}, null, new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String str2, Uri uri) {
                Log.d("scan>>", "Finished scanning " + str2);
            }
        });
    }

    private void openGeneratedPDF() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/DCIM/pdffromlayout.pdf");
        if (file.exists()) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(67108864);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException unused) {
                Toast.makeText(this, "No Application available to createdpdf pdf", Toast.LENGTH_LONG).show();
            }
        }
    }


    public void refreshActivity() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    public static Bitmap loadBitmapFromView(View view, int i, int i2) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(createBitmap));
        return createBitmap;
    }


    @Override
    protected void onResume() {
        super.onResume();

    }
}
