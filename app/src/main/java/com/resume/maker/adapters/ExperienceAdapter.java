package com.resume.maker.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.resume.maker.SharedPreferences;
import com.resume.maker.interfaces.ExperienceClick;
import com.resume.maker.models.ExperienceModel;

import java.util.List;



public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.ItemHolder> {
    public static int selectedposition;
    List<ExperienceModel> datalist;
    private LayoutInflater layoutInflater;
    ExperienceClick listner;
    private Activity mActivity;
    SharedPreferences store;

    @RequiresApi(api = 24)
    public ExperienceAdapter(Activity activity, List<ExperienceModel> list, ExperienceClick experienceClick) {
        this.mActivity = activity;
        this.datalist = list;
        this.listner = experienceClick;
        this.layoutInflater = LayoutInflater.from(activity);
    }

    @NonNull
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.store = SharedPreferences.getInstance(this.mActivity, "resumemaker");
        return new ItemHolder(this.layoutInflater.inflate(R.layout.rowlayout_experience, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ItemHolder itemHolder, final int i) {
        if (this.datalist.get(i).getOrganization() == null || this.datalist.get(i).getOrganization().equals("")) {
            itemHolder.loutMain.setVisibility(View.GONE);
        } else {
            itemHolder.loutMain.setVisibility(View.VISIBLE);
            itemHolder.tvorgnm.setText(this.datalist.get(i).getOrganization());
            itemHolder.tvorgnm.setSelected(true);
            itemHolder.tvdesig.setText(this.datalist.get(i).getDesignation());
            itemHolder.tvdesig.setSelected(true);
            TextView textView = itemHolder.tvfromtime;
            textView.setText("(" + this.datalist.get(i).getFromtime());
            TextView textView2 = itemHolder.tvtotime;
            textView2.setText(this.datalist.get(i).getTotime() + ")");
            itemHolder.tvrole.setText(this.datalist.get(i).getRole());
        }
        itemHolder.loutMain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ExperienceAdapter.this.listner.callback(ExperienceAdapter.this.datalist.get(i));
                ExperienceAdapter.selectedposition = i;
            }
        });
        itemHolder.imgdelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ExperienceAdapter.selectedposition = i;
                ExperienceAdapter.this.removeItem(ExperienceAdapter.selectedposition);
                Log.d("pos>>selectedpos>>", i + ", selected:" + ExperienceAdapter.selectedposition);
            }
        });
    }

    public void removeItem(int i) {
        this.datalist.remove(i);
        notifyItemRemoved(i);
        if (i == 0) {
            this.store.removeValue(this.mActivity.getString(R.string.experience_organization1));
        } else if (i == 1) {
            this.store.removeValue(this.mActivity.getString(R.string.experience_organization2));
        } else {
            this.store.removeValue(this.mActivity.getString(R.string.experience_organization3));
        }
        notifyItemRangeChanged(i, this.datalist.size());
        selectedposition = i;
    }

    @Override
    public int getItemCount() {
        return this.datalist.size();
    }


    public class ItemHolder extends RecyclerView.ViewHolder {
        ImageView imgdelete;
        public LinearLayout loutMain;
        public TextView tvdesig;
        public TextView tvfromtime;
        public TextView tvorgnm;
        public TextView tvrole;
        public TextView tvtotime;

        public ItemHolder(@NonNull View view) {
            super(view);
            this.tvorgnm = (TextView) view.findViewById(R.id.tvorgnm);
            this.tvdesig = (TextView) view.findViewById(R.id.tvdesig);
            this.tvfromtime = (TextView) view.findViewById(R.id.tvfromtime);
            this.tvtotime = (TextView) view.findViewById(R.id.tvtotime);
            this.tvrole = (TextView) view.findViewById(R.id.tvrole);
            this.imgdelete = (ImageView) view.findViewById(R.id.imgdelete);
            this.loutMain = (LinearLayout) view.findViewById(R.id.loutMain);
        }
    }

    public void setFilter(List<ExperienceModel> list) {
        this.datalist = list;
        notifyDataSetChanged();
    }
}
