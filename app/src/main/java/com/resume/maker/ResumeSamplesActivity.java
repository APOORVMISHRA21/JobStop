package com.resume.maker;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.resume.maker.adapters.ResumeSampleAdapter;
import com.resume.maker.models.ResumeSampleModel;
import java.util.ArrayList;
import java.util.List;


public class ResumeSamplesActivity extends AppCompatActivity {
    LinearLayout back;
    ResumeSampleModel itemdata;
    LinearLayout layout_toolbar;
    RecyclerView rcframe;
    List<ResumeSampleModel> dataList = null;
    int numframe = 15;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_resume_samples);
        initToolbar();
        if (this.dataList == null) {
            this.dataList = new ArrayList();
        }
        this.rcframe = (RecyclerView) findViewById(R.id.rcframe);
        this.rcframe.setLayoutManager(new GridLayoutManager(this, 2));
        prepareData("frm_", this.numframe);
        prepareData("frm", this.numframe);



    }

    private ArrayList prepareData(String str, int i) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 1; i2 <= i; i2++) {
            ResumeSampleModel resumeSampleModel = new ResumeSampleModel();
            Resources resources = getResources();
            int identifier = resources.getIdentifier("frm" + i2, "drawable", getPackageName());
            resumeSampleModel.setfrm1(identifier);
            Resources resources2 = getResources();
            resumeSampleModel.setfrm2(resources2.getIdentifier("frm_" + i2, "drawable", getPackageName()));
            Log.d("imageurl>>_", identifier + "");
            arrayList.add(resumeSampleModel);
        }
        this.rcframe.setAdapter(new ResumeSampleAdapter(this, arrayList, this));
        return arrayList;
    }

    private void initToolbar() {
        Window window = getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
//        if (Build.VERSION.SDK_INT >= 21) {
//            window.setStatusBarColor(ContextCompat.getColor(this, R.color.color_resumesample));
//        }
        this.layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
//        this.layout_toolbar.setBackgroundResource(R.drawable.resume_toolbar);
        this.back = (LinearLayout) findViewById(R.id.back);
        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResumeSamplesActivity.this.onBackPressed();
            }
        });
    }
}
