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
import com.resume.maker.adapters.AcademicAdapter;
import com.resume.maker.fragments.AcademicFragment;
import com.resume.maker.interfaces.AcademicClick;
import com.resume.maker.models.AcademicModel;

import java.util.ArrayList;
import java.util.List;


public class AcademicActivity extends AppCompatActivity implements AcademicClick {
    private static final String TAG = "AcademicActivity>>";
    String about;
    AcademicAdapter adapter;
    LinearLayout back;
    ImageView btn_add;
    List<AcademicModel> dataList = null;
    int flag = 0;
    AcademicModel itemdata;
    LinearLayout layout_toolbar;
    RecyclerView rcitem;
    SharedPreferences store;
    TextView titaltext;
     TextView backBtn,nextBtn;


    @Override

    @RequiresApi(api = 24)
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.items_list);
        initToolbar();
        this.store = SharedPreferences.getInstance(this, "resumemaker");
        this.titaltext = (TextView) findViewById(R.id.titaltext);
        this.btn_add = (ImageView) findViewById(R.id.btn_add);
        this.rcitem = (RecyclerView) findViewById(R.id.rcitem);
        this.backBtn = (TextView) findViewById(R.id.backBtn);
        this.nextBtn = (TextView) findViewById(R.id.nextBtn);
        this.rcitem.setLayoutManager(new LinearLayoutManager(this));
        initData();
        this.titaltext.setText("Education");
        this.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), PersonalinfoActivity.class));
                finish();
            }
        });
        this.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ExperienceActivity.class));
            }
        });
        this.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AcademicActivity.this.getSupportFragmentManager().beginTransaction().add(R.id.f1_container, new AcademicFragment()).addToBackStack(null).commit();

            }
        });
        this.adapter = new AcademicAdapter(this, this.dataList, this);
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
            if (this.store.contains(getString(R.string.academic_degree1))) {
                Log.d("sharedpref>>2", this.store.getString(getString(R.string.academic_degree1), ""));
                this.dataList.add(new AcademicModel(this.store.getString(getString(R.string.academic_degree1), ""), this.store.getString(getString(R.string.academic_institute1), ""), this.store.getString(getString(R.string.academic_percgpa1), ""), this.store.getString(getString(R.string.academic_year1), ""), this.store.getString(getString(R.string.academic_percgpa1_radio), ""), this.store.getString(getString(R.string.academic_year1_radio), "")));
                Log.d("academic>>>1", "academic>>>1");
            }
            if (this.store.contains(getString(R.string.academic_degree2))) {
                this.dataList.add(new AcademicModel(this.store.getString(getString(R.string.academic_degree2), ""), this.store.getString(getString(R.string.academic_institute2), ""), this.store.getString(getString(R.string.academic_percgpa2), ""), this.store.getString(getString(R.string.academic_year2), ""), this.store.getString(getString(R.string.academic_percgpa2_radio), ""), this.store.getString(getString(R.string.academic_year2_radio), "")));
                Log.d("academic>>>2", "academic>>>2");
            }
            if (this.store.contains(getString(R.string.academic_degree3))) {
                this.dataList.add(new AcademicModel(this.store.getString(getString(R.string.academic_degree3), ""), this.store.getString(getString(R.string.academic_institute3), ""), this.store.getString(getString(R.string.academic_percgpa3), ""), this.store.getString(getString(R.string.academic_year3), ""), this.store.getString(getString(R.string.academic_percgpa3_radio), ""), this.store.getString(getString(R.string.academic_year3_radio), "")));
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
        } else if (AcademicFragment.showback) {
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
    public void callback(AcademicModel academicModel) {
        this.itemdata = academicModel;
        nextflag();
    }

    private void nextflag() {
        Gson gson = new Gson();
        new AcademicModel();
        AcademicFragment academicFragment = new AcademicFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", AcademicAdapter.selectedposition);
        bundle.putString("obj", gson.toJson(this.dataList));
        academicFragment.setArguments(bundle);
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.frag_slide_up, 0, 0, R.anim.frag_slide_down);
        beginTransaction.replace(R.id.f1_container, academicFragment);
        beginTransaction.addToBackStack(null);
        beginTransaction.commit();
        Log.d("state>>put", gson.toJson(this.dataList));
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initToolbar() {
        Window window = getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
//        if (Build.VERSION.SDK_INT >= 21) {
//            window.setStatusBarColor(ContextCompat.getColor(this, R.color.color_academic));
//        }
        this.layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
//        this.layout_toolbar.setBackgroundResource(R.drawable.toolbar_academic);
        this.back = (LinearLayout) findViewById(R.id.back);
        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AcademicActivity.this.onBackPressed();
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
                    AcademicActivity.this.getSupportFragmentManager().popBackStack();
                    AcademicActivity.this.finish();
                    AcademicActivity.this.overridePendingTransition(0, 0);
                    AcademicActivity academicActivity = AcademicActivity.this;
                    academicActivity.startActivity(academicActivity.getIntent());
                    AcademicActivity.this.overridePendingTransition(0, 0);
//                    finishAffinity();
//                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                }
            }
        };
        new AlertDialog.Builder(this).setMessage("Discard editing?").setPositiveButton("Ok", r0).setTitle("").setNeutralButton("Cancel", r0).show();
    }
}
