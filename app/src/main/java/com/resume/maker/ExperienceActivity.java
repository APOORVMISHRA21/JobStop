package com.resume.maker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;

import com.google.gson.Gson;
import com.resume.maker.adapters.ExperienceAdapter;
import com.resume.maker.fragments.ExperienceFragment;
import com.resume.maker.interfaces.ExperienceClick;
import com.resume.maker.models.ExperienceModel;

import java.util.ArrayList;
import java.util.List;


public class ExperienceActivity extends AppCompatActivity implements ExperienceClick {
    private static final String TAG = "AcademicActivity>>";
    ExperienceAdapter adapter;
    LinearLayout back;
    ImageView btn_add;
    List<ExperienceModel> dataList = null;
    ExperienceModel itemdata;
    LinearLayout layout_toolbar;
    RecyclerView rcitem;
    SharedPreferences store;
    TextView titaltext,nextBtn,backBtn;



    @Override

    @RequiresApi(api = 24)
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.items_list);
        initToolbar();
        this.store = SharedPreferences.getInstance(this, "resumemaker");
        this.titaltext = (TextView) findViewById(R.id.titaltext);
        this.btn_add = (ImageView) findViewById(R.id.btn_add);
//        this.btn_add.setImageResource(R.drawable.add2);
        this.rcitem = (RecyclerView) findViewById(R.id.rcitem);
        this.backBtn = (TextView) findViewById(R.id.backBtn);
        this.nextBtn = (TextView) findViewById(R.id.nextBtn);
        this.rcitem.setLayoutManager(new LinearLayoutManager(this));
        initData();
        this.titaltext.setText("Work Experience");

        this.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), AcademicActivity.class));
                finish();
            }
        });
        this.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ReferenceActivity.class));
            }
        });
        this.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExperienceActivity.this.getSupportFragmentManager().beginTransaction().add(R.id.f1_container, new ExperienceFragment()).addToBackStack(null).commit();

            }
        });
        this.adapter = new ExperienceAdapter(this, this.dataList, this);
        this.rcitem.setAdapter(this.adapter);
        this.adapter.notifyDataSetChanged();
    }

    private void initData() {
        if (this.dataList == null) {
            this.dataList = new ArrayList();
            getSharedpref();
        }
    }

    private void getSharedpref() {
        try {
            if (this.store.contains(getString(R.string.experience_organization1))) {
                Log.d("sharedpref>>2", this.store.getString(getString(R.string.experience_organization1), ""));
                String string = this.store.getString(getString(R.string.experience_organization1), "");
                String string2 = this.store.getString(getString(R.string.experience_designation1), "");
                String string3 = this.store.getString(getString(R.string.experience_role1), "");
                this.dataList.add(new ExperienceModel(string, string2, this.store.getString(getString(R.string.experience_fromdate1), ""), this.store.getString(getString(R.string.experience_todate1), ""), this.store.getString(getString(R.string.experience_pre_cur1_radio), ""), string3));
                Log.d("academic>>>1", "academic>>>1");
            }
            if (this.store.contains(getString(R.string.experience_organization2))) {
                String string4 = this.store.getString(getString(R.string.experience_organization2), "");
                String string5 = this.store.getString(getString(R.string.experience_designation2), "");
                String string6 = this.store.getString(getString(R.string.experience_role2), "");
                this.dataList.add(new ExperienceModel(string4, string5, this.store.getString(getString(R.string.experience_fromdate2), ""), this.store.getString(getString(R.string.experience_todate2), ""), this.store.getString(getString(R.string.experience_pre_cur2_radio), ""), string6));
                Log.d("academic>>>2", "academic>>>2");
            }
            if (this.store.contains(getString(R.string.experience_organization3))) {
                String string7 = this.store.getString(getString(R.string.experience_organization3), "");
                String string8 = this.store.getString(getString(R.string.experience_designation3), "");
                String string9 = this.store.getString(getString(R.string.experience_role3), "");
                this.dataList.add(new ExperienceModel(string7, string8, this.store.getString(getString(R.string.experience_fromdate3), ""), this.store.getString(getString(R.string.experience_todate3), ""), this.store.getString(getString(R.string.experience_pre_cur3_radio), ""), string9));
                Log.d("academic>>>3", "academic>>>3");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("sharedpref>>2", "null");
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else if (ExperienceFragment.showback) {
            backdialog();
        } else {
            getSupportFragmentManager().popBackStack();
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
//        backdialog();
    }

    @Override
    public void callback(ExperienceModel experienceModel) {
        this.itemdata = experienceModel;
        nextflag();
    }

    private void nextflag() {
        Gson gson = new Gson();
        new ExperienceModel();
        ExperienceFragment experienceFragment = new ExperienceFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", ExperienceAdapter.selectedposition);
        bundle.putString("obj", gson.toJson(this.dataList));
        experienceFragment.setArguments(bundle);
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.frag_slide_up, 0, 0, R.anim.frag_slide_down);
        beginTransaction.replace(R.id.f1_container, experienceFragment);
        beginTransaction.addToBackStack(null);
        beginTransaction.commit();
        Log.d("state>>put", gson.toJson(this.dataList));
    }

    private void initToolbar() {
        Window window = getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
//        if (Build.VERSION.SDK_INT >= 21) {
//            window.setStatusBarColor(ContextCompat.getColor(this, R.color.color_experience));
//        }
        this.layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
//        this.layout_toolbar.setBackgroundResource(R.drawable.toolbar_experience);
        this.back = (LinearLayout) findViewById(R.id.back);
        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExperienceActivity.this.onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void backdialog() {
        DialogInterface.OnClickListener r0 = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == -3) {
                    dialogInterface.dismiss();
                } else if (i == -1) {
                    dialogInterface.dismiss();
                    ExperienceActivity.this.getSupportFragmentManager().popBackStack();
                    ExperienceActivity.this.finish();
                    ExperienceActivity.this.overridePendingTransition(0, 0);
                    ExperienceActivity experienceActivity = ExperienceActivity.this;
                    experienceActivity.startActivity(experienceActivity.getIntent());
                    ExperienceActivity.this.overridePendingTransition(0, 0);
//                    finishAffinity();
//                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                }
            }
        };
        new AlertDialog.Builder(this).setMessage("Discard editing?").setPositiveButton("Ok", r0).setTitle("").setNeutralButton("Cancel", r0).show();
    }
}
