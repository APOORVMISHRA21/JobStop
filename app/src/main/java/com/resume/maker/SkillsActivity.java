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
import com.resume.maker.adapters.SkillAdapter;
import com.resume.maker.fragments.SkillsFragment;
import com.resume.maker.interfaces.SkillClick;
import com.resume.maker.models.SkillModel;

import java.util.ArrayList;
import java.util.List;


public class SkillsActivity extends AppCompatActivity implements SkillClick {
    private static final String TAG = "AcademicActivity>>";
    String about;
    SkillAdapter adapter;
    LinearLayout back;
    ImageView btn_add;
    List<SkillModel> dataList = null;
    int flag = 0;
    SkillModel itemdata;
    LinearLayout layout_toolbar;
    RecyclerView rcitem;
    SharedPreferences store;
    TextView titaltext,backBtn,nextBtn;



    @Override

    @RequiresApi(api = 24)
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.items_list);
        initToolbar();
        this.store = SharedPreferences.getInstance(this, "resumemaker");
        this.titaltext = (TextView) findViewById(R.id.titaltext);
        this.btn_add = (ImageView) findViewById(R.id.btn_add);
//        this.btn_add.setImageResource(R.drawable.add5);
        this.rcitem = (RecyclerView) findViewById(R.id.rcitem);
        this.rcitem.setLayoutManager(new LinearLayoutManager(this));
        this.backBtn = (TextView) findViewById(R.id.backBtn);
        this.nextBtn = (TextView) findViewById(R.id.nextBtn);
        initData();
        this.titaltext.setText("Skills");
        this.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), ReferenceActivity.class));
                finish();
            }
        });
        this.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LanguageActivity.class));
            }
        });
        this.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SkillsActivity.this.getSupportFragmentManager().beginTransaction().add(R.id.f1_container, new SkillsFragment()).addToBackStack(null).commit();

            }
        });
        this.adapter = new SkillAdapter(this, this.dataList, this);
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
            if (this.store.contains(getString(R.string.skill1))) {
                Log.d("sharedpref>>1", this.store.getString(getString(R.string.skill1), ""));
                this.dataList.add(new SkillModel(this.store.getString(getString(R.string.skill1), "")));
            }
            if (this.store.contains(getString(R.string.skill2))) {
                Log.d("sharedpref>>2", this.store.getString(getString(R.string.skill2), ""));
                this.dataList.add(new SkillModel(this.store.getString(getString(R.string.skill2), "")));
            }
            if (this.store.contains(getString(R.string.skill3))) {
                Log.d("sharedpref>>3", this.store.getString(getString(R.string.skill3), ""));
                this.dataList.add(new SkillModel(this.store.getString(getString(R.string.skill3), "")));
            }
            if (this.store.contains(getString(R.string.skill4))) {
                Log.d("sharedpref>>4", this.store.getString(getString(R.string.skill4), ""));
                this.dataList.add(new SkillModel(this.store.getString(getString(R.string.skill4), "")));
            }
            if (this.store.contains(getString(R.string.skill5))) {
                Log.d("sharedpref>>5", this.store.getString(getString(R.string.skill5), ""));
                this.dataList.add(new SkillModel(this.store.getString(getString(R.string.skill5), "")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("sharedpref>>2", "null");
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
//            System.out.println("baaaaack");
            super.onBackPressed();
        } else if (SkillsFragment.showback) {
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
    public void callback(SkillModel skillModel) {
        this.itemdata = skillModel;
        nextflag();
    }

    private void nextflag() {
        Gson gson = new Gson();
        new SkillModel();
        SkillsFragment skillsFragment = new SkillsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", SkillAdapter.selectedposition);
        bundle.putString("obj", gson.toJson(this.dataList));
        skillsFragment.setArguments(bundle);
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.frag_slide_up, 0, 0, R.anim.frag_slide_down);
        beginTransaction.replace(R.id.f1_container, skillsFragment);
        beginTransaction.addToBackStack(null);
        beginTransaction.commit();
        Log.d("state>>put", gson.toJson(this.dataList));
    }

    @Override
    protected void onResume() {
        super.onResume();
        SkillAdapter skillAdapter = this.adapter;
        if (skillAdapter != null) {
            skillAdapter.notifyDataSetChanged();
        }

    }

    private void initToolbar() {
        Window window = getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
//        if (Build.VERSION.SDK_INT >= 21) {
//            window.setStatusBarColor(ContextCompat.getColor(this, R.color.color_skill));
//        }
        this.layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
//        this.layout_toolbar.setBackgroundResource(R.drawable.toolbar_skill);
        this.back = (LinearLayout) findViewById(R.id.back);
        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SkillsActivity.this.onBackPressed();
            }
        });
    }

    public void backdialog() {
        DialogInterface.OnClickListener r0 = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == -3) {
                    dialogInterface.dismiss();
                } else if (i == -1) {
                    dialogInterface.dismiss();
                    SkillsActivity.this.getSupportFragmentManager().popBackStack();
                    SkillsActivity.this.finish();
                    SkillsActivity.this.overridePendingTransition(0, 0);
                    SkillsActivity skillsActivity = SkillsActivity.this;
                    skillsActivity.startActivity(skillsActivity.getIntent());
                    SkillsActivity.this.overridePendingTransition(0, 0);
//                    finishAffinity();
//                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                }
            }
        };
        new AlertDialog.Builder(this).setMessage("Discard editing?").setPositiveButton("Ok", r0).setTitle("").setNeutralButton("Cancel", r0).show();
    }




}
