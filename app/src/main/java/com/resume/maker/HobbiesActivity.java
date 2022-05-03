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
import com.resume.maker.adapters.HobbiesAdapter;
import com.resume.maker.fragments.HobbiesFragment;
import com.resume.maker.interfaces.HobbiesClick;
import com.resume.maker.models.HobbiesModel;

import java.util.ArrayList;
import java.util.List;


public class HobbiesActivity extends AppCompatActivity implements HobbiesClick {
    private static final String TAG = "AcademicActivity>>";
    String about;
    HobbiesAdapter adapter;
    LinearLayout back;
    ImageView btn_add;
    List<HobbiesModel> dataList = null;
    int flag = 0;
    HobbiesModel itemdata;
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
//        this.btn_add.setImageResource(R.drawable.add7);
        this.rcitem = (RecyclerView) findViewById(R.id.rcitem);
        this.backBtn = (TextView) findViewById(R.id.backBtn);
        this.nextBtn = (TextView) findViewById(R.id.nextBtn);
        this.rcitem.setLayoutManager(new LinearLayoutManager(this));
        initData();
        this.titaltext.setText("Hobbies");
//        nextBtn.setVisibility(View.GONE);
        this.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), HobbiesActivity.class));
                finish();
            }
        });
this.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ResumeSamplesActivity.class));
            }
        });

        this.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HobbiesActivity.this.getSupportFragmentManager().beginTransaction().add(R.id.f1_container, new HobbiesFragment()).addToBackStack(null).commit();

            }
        });
        this.adapter = new HobbiesAdapter(this, this.dataList, this);
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
            if (this.store.contains(getString(R.string.hobby1))) {
                Log.d("sharedpref>>hobby1", this.store.getString(getString(R.string.hobby1), ""));
                this.dataList.add(new HobbiesModel(this.store.getString(getString(R.string.hobby1), "")));
            }
            if (this.store.contains(getString(R.string.hobby2))) {
                Log.d("sharedpref>>hobby2", this.store.getString(getString(R.string.hobby2), ""));
                this.dataList.add(new HobbiesModel(this.store.getString(getString(R.string.hobby2), "")));
            }
            if (this.store.contains(getString(R.string.hobby3))) {
                Log.d("sharedpref>>hobby3", this.store.getString(getString(R.string.hobby3), ""));
                this.dataList.add(new HobbiesModel(this.store.getString(getString(R.string.hobby3), "")));
            }
            if (this.store.contains(getString(R.string.hobby4))) {
                Log.d("sharedpref>>hobby4", this.store.getString(getString(R.string.hobby4), ""));
                this.dataList.add(new HobbiesModel(this.store.getString(getString(R.string.hobby4), "")));
            }
            if (this.store.contains(getString(R.string.hobby5))) {
                Log.d("sharedpref>>hobby5", this.store.getString(getString(R.string.hobby5), ""));
                this.dataList.add(new HobbiesModel(this.store.getString(getString(R.string.hobby5), "")));
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
        } else if (HobbiesFragment.showback) {
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
    public void callback(HobbiesModel hobbiesModel) {
        this.itemdata = hobbiesModel;
        nextflag();
    }

    private void nextflag() {
        Gson gson = new Gson();
        new HobbiesModel();
        HobbiesFragment hobbiesFragment = new HobbiesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", HobbiesAdapter.selectedposition);
        bundle.putString("obj", gson.toJson(this.dataList));
        hobbiesFragment.setArguments(bundle);
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.frag_slide_up, 0, 0, R.anim.frag_slide_down);
        beginTransaction.replace(R.id.f1_container, hobbiesFragment);
        beginTransaction.addToBackStack(null);
        beginTransaction.commit();
        Log.d("state>>put", gson.toJson(this.dataList));
    }

    @Override
    protected void onResume() {
        super.onResume();
        HobbiesAdapter hobbiesAdapter = this.adapter;
        if (hobbiesAdapter != null) {
            hobbiesAdapter.notifyDataSetChanged();
        }

    }

    private void initToolbar() {
        Window window = getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
//        if (Build.VERSION.SDK_INT >= 21) {
//            window.setStatusBarColor(ContextCompat.getColor(this, R.color.color_hobbies));
//        }
        this.layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
//        this.layout_toolbar.setBackgroundResource(R.drawable.toolbar_hobby);
        this.back = (LinearLayout) findViewById(R.id.back);
        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HobbiesActivity.this.onBackPressed();
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
                    HobbiesActivity.this.getSupportFragmentManager().popBackStack();
                    HobbiesActivity.this.finish();
                    HobbiesActivity.this.overridePendingTransition(0, 0);
                    HobbiesActivity hobbiesActivity = HobbiesActivity.this;
                    hobbiesActivity.startActivity(hobbiesActivity.getIntent());
                    HobbiesActivity.this.overridePendingTransition(0, 0);
//                    finishAffinity();
//                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                }
            }
        };
        new AlertDialog.Builder(this).setMessage("Discard editing?").setPositiveButton("Ok", r0).setTitle("").setNeutralButton("Cancel", r0).show();
    }
}
