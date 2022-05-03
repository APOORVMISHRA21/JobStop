package com.resume.maker.adapters;

import android.app.Activity;
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
import com.resume.maker.interfaces.SkillClick;
import com.resume.maker.models.SkillModel;

import java.util.List;



public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.ItemHolder> {
    public static int selectedposition;
    List<SkillModel> datalist;
    private LayoutInflater layoutInflater;
    SkillClick listner;
    private Activity mActivity;
    SharedPreferences store;

    @RequiresApi(api = 24)
    public SkillAdapter(Activity activity, List<SkillModel> list, SkillClick skillClick) {
        this.mActivity = activity;
        this.datalist = list;
        this.listner = skillClick;
        this.layoutInflater = LayoutInflater.from(activity);
    }

    @NonNull
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.store = SharedPreferences.getInstance(this.mActivity, "resumemaker");
        return new ItemHolder(this.layoutInflater.inflate(R.layout.rowlayout_itemlist, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ItemHolder itemHolder, final int i) {
        if (this.datalist.get(i).getSkill() == null || this.datalist.get(i).getSkill().equals("")) {
            itemHolder.loutMain.setVisibility(View.GONE);
        } else {
            itemHolder.loutMain.setVisibility(View.VISIBLE);
            itemHolder.tv_item.setText(this.datalist.get(i).getSkill());
        }
        itemHolder.tv_item.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SkillAdapter.this.listner.callback(SkillAdapter.this.datalist.get(i));
                SkillAdapter.selectedposition = i;
            }
        }); itemHolder.imgedit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SkillAdapter.this.listner.callback(SkillAdapter.this.datalist.get(i));
                SkillAdapter.selectedposition = i;
            }
        });


        itemHolder.imgdelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SkillAdapter.selectedposition = i;
                SkillAdapter.this.removeItem(SkillAdapter.selectedposition);
            }
        });
    }

    public void removeItem(int i) {
        this.datalist.remove(i);
        notifyItemRemoved(i);
        if (i == 0) {
            this.store.removeValue(this.mActivity.getString(R.string.skill1));
        } else if (i == 1) {
            this.store.removeValue(this.mActivity.getString(R.string.skill2));
        } else if (i == 2) {
            this.store.removeValue(this.mActivity.getString(R.string.skill3));
        } else if (i == 3) {
            this.store.removeValue(this.mActivity.getString(R.string.skill4));
        } else {
            this.store.removeValue(this.mActivity.getString(R.string.skill5));
        }
        notifyItemRangeChanged(i, this.datalist.size());
        selectedposition = i;
    }

    @Override
    public int getItemCount() {
        return this.datalist.size();
    }


    public class ItemHolder extends RecyclerView.ViewHolder {
        ImageView imgdelete,imgedit;
        public LinearLayout layout_bg;
        public LinearLayout loutMain;
        public TextView tv_item;

        public ItemHolder(@NonNull View view) {
            super(view);
            this.tv_item = (TextView) view.findViewById(R.id.tv_item);
            this.imgdelete = (ImageView) view.findViewById(R.id.imgdelete);
            this.imgedit = (ImageView) view.findViewById(R.id.imgedit);
            this.loutMain = (LinearLayout) view.findViewById(R.id.loutMain);
            this.layout_bg = (LinearLayout) view.findViewById(R.id.layout_bg);
            // this.layout_bg.setBackgroundResource(R.drawable.bg_skill);
        }
    }

    public void setFilter(List<SkillModel> list) {
        this.datalist = list;
        notifyDataSetChanged();
    }
}
