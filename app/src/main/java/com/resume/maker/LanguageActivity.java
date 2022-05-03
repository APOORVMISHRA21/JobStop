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
import com.resume.maker.adapters.LanguageAdapter;
import com.resume.maker.fragments.LanguageFragment;
import com.resume.maker.interfaces.LanguageClick;
import com.resume.maker.models.LanguageModel;

import java.util.ArrayList;
import java.util.List;


public class LanguageActivity extends AppCompatActivity implements LanguageClick {
    private static final String TAG = "AcademicActivity>>";
    String about;
    LanguageAdapter adapter;
    LinearLayout back;
    ImageView btn_add;
    List<LanguageModel> dataList = null;
    int flag = 0;
    LanguageModel itemdata;
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
//        this.btn_add.setImageResource(R.drawable.add6);
        this.rcitem = (RecyclerView) findViewById(R.id.rcitem);
        this.backBtn = (TextView) findViewById(R.id.backBtn);
        this.nextBtn = (TextView) findViewById(R.id.nextBtn);
        this.rcitem.setLayoutManager(new LinearLayoutManager(this));
        initData();
        this.titaltext.setText("Language");
        this.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), SkillsActivity.class));
                finish();
            }
        });
        this.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HobbiesActivity.class));
            }
        });
        this.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LanguageActivity.this.getSupportFragmentManager().beginTransaction().add(R.id.f1_container, new LanguageFragment()).addToBackStack(null).commit();
            }
        });
        this.adapter = new LanguageAdapter(this, this.dataList, this);
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
            if (this.store.contains(getString(R.string.language1))) {
                Log.d("sharedpref>>1", this.store.getString(getString(R.string.language1), ""));
                this.dataList.add(new LanguageModel(this.store.getString(getString(R.string.language1), "")));
            }
            if (this.store.contains(getString(R.string.language2))) {
                Log.d("sharedpref>>2", this.store.getString(getString(R.string.language2), ""));
                this.dataList.add(new LanguageModel(this.store.getString(getString(R.string.language2), "")));
            }
            if (this.store.contains(getString(R.string.language3))) {
                Log.d("sharedpref>>3", this.store.getString(getString(R.string.language3), ""));
                this.dataList.add(new LanguageModel(this.store.getString(getString(R.string.language3), "")));
            }
            if (this.store.contains(getString(R.string.language4))) {
                Log.d("sharedpref>>4", this.store.getString(getString(R.string.language4), ""));
                this.dataList.add(new LanguageModel(this.store.getString(getString(R.string.language4), "")));
            }
            if (this.store.contains(getString(R.string.language5))) {
                Log.d("sharedpref>>5", this.store.getString(getString(R.string.language5), ""));
                this.dataList.add(new LanguageModel(this.store.getString(getString(R.string.language5), "")));
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
        } else if (LanguageFragment.showback) {
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
    public void callback(LanguageModel languageModel) {
        this.itemdata = languageModel;
        nextflag();
    }

    private void nextflag() {
        Gson gson = new Gson();
        new LanguageModel();
        LanguageFragment languageFragment = new LanguageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", LanguageAdapter.selectedposition);
        bundle.putString("obj", gson.toJson(this.dataList));
        languageFragment.setArguments(bundle);
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.frag_slide_up, 0, 0, R.anim.frag_slide_down);
        beginTransaction.replace(R.id.f1_container, languageFragment);
        beginTransaction.addToBackStack(null);
        beginTransaction.commit();
        Log.d("state>>put", gson.toJson(this.dataList));
    }

    private void initToolbar() {
        Window window = getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
//        if (Build.VERSION.SDK_INT >= 21) {
//            window.setStatusBarColor(ContextCompat.getColor(this, R.color.color_language));
//        }
        this.layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
//        this.layout_toolbar.setBackgroundResource(R.drawable.toolbar_language);
        this.back = (LinearLayout) findViewById(R.id.back);
        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LanguageActivity.this.onBackPressed();
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
                    LanguageActivity.this.getSupportFragmentManager().popBackStack();
                    LanguageActivity.this.finish();
                    LanguageActivity.this.overridePendingTransition(0, 0);
                    LanguageActivity languageActivity = LanguageActivity.this;
                    languageActivity.startActivity(languageActivity.getIntent());
                    LanguageActivity.this.overridePendingTransition(0, 0);
//                    finishAffinity();
//                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                }
            }
        };
        new AlertDialog.Builder(this).setMessage("Discard editing?").setPositiveButton("Ok", r0).setTitle("").setNeutralButton("Cancel", r0).show();
    }
}
