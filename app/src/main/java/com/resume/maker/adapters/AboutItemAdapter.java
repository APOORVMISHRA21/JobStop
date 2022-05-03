package com.resume.maker.adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.resume.maker.interfaces.AboutClick;
import com.resume.maker.models.AboutDataModel;
import java.util.List;



public class AboutItemAdapter extends RecyclerView.Adapter<AboutItemAdapter.ItemHolder> {
    public static int selectedposition;
    String abouttext;
    List<AboutDataModel> datalist;
    private LayoutInflater layoutInflater;
    AboutClick listner;
    private Activity mActivity;

    @RequiresApi(api = 24)
    public AboutItemAdapter(Activity activity, List<AboutDataModel> list, AboutClick aboutClick) {
        this.mActivity = activity;
        this.datalist = list;
        this.listner = aboutClick;
        this.layoutInflater = LayoutInflater.from(activity);
    }

    @NonNull
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemHolder(this.layoutInflater.inflate(R.layout.rowlayout_about, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ItemHolder itemHolder, final int i) {
        itemHolder.tv_aboutme.setText(this.datalist.get(i).getAbout());
        itemHolder.loutMain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AboutItemAdapter.selectedposition = i;
                AboutItemAdapter aboutItemAdapter = AboutItemAdapter.this;
                aboutItemAdapter.abouttext = aboutItemAdapter.datalist.get(i).getAbout();
                Log.d("abouttext>>1", AboutItemAdapter.this.abouttext);
                Intent intent = new Intent();
                intent.putExtra("extra_sticker_id", AboutItemAdapter.this.abouttext);
                AboutItemAdapter.this.mActivity.setResult(-1, intent);
                AboutItemAdapter.this.mActivity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.datalist.size();
    }


    public class ItemHolder extends RecyclerView.ViewHolder {
        public LinearLayout loutMain;
        public TextView tv_aboutme;

        public ItemHolder(@NonNull View view) {
            super(view);
            this.tv_aboutme = (TextView) view.findViewById(R.id.tv_aboutme);
            this.loutMain = (LinearLayout) view.findViewById(R.id.loutMain);
        }
    }

    public void setFilter(List<AboutDataModel> list) {
        this.datalist = list;
        notifyDataSetChanged();
    }
}
