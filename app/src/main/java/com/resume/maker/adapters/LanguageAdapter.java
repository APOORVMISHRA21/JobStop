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
import com.resume.maker.interfaces.LanguageClick;
import com.resume.maker.models.LanguageModel;

import java.util.List;



public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ItemHolder> {
    public static int selectedposition;
    List<LanguageModel> datalist;
    private LayoutInflater layoutInflater;
    LanguageClick listner;
    private Activity mActivity;
    SharedPreferences store;

    @RequiresApi(api = 24)
    public LanguageAdapter(Activity activity, List<LanguageModel> list, LanguageClick languageClick) {
        this.mActivity = activity;
        this.datalist = list;
        this.listner = languageClick;
        this.layoutInflater = LayoutInflater.from(activity);
    }

    @NonNull
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.store = SharedPreferences.getInstance(this.mActivity, "resumemaker");
        return new ItemHolder(this.layoutInflater.inflate(R.layout.rowlayout_itemlist, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ItemHolder itemHolder, final int i) {
        if (this.datalist.get(i).getLanguage() == null || this.datalist.get(i).getLanguage().equals("")) {
            itemHolder.loutMain.setVisibility(View.GONE);
        } else {
            itemHolder.loutMain.setVisibility(View.VISIBLE);
            itemHolder.tv_item.setText(this.datalist.get(i).getLanguage());
        }
        itemHolder.tv_item.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LanguageAdapter.this.listner.callback(LanguageAdapter.this.datalist.get(i));
                LanguageAdapter.selectedposition = i;
            }
        });
        itemHolder.imgedit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LanguageAdapter.this.listner.callback(LanguageAdapter.this.datalist.get(i));
                LanguageAdapter.selectedposition = i;
            }
        });

        itemHolder.imgdelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LanguageAdapter.selectedposition = i;
                LanguageAdapter.this.removeItem(LanguageAdapter.selectedposition);
            }
        });
    }

    public void removeItem(int i) {
        this.datalist.remove(i);
        notifyItemRemoved(i);
        if (i == 0) {
            this.store.removeValue(this.mActivity.getString(R.string.language1));
        } else if (i == 1) {
            this.store.removeValue(this.mActivity.getString(R.string.language2));
        } else if (i == 2) {
            this.store.removeValue(this.mActivity.getString(R.string.language3));
        } else if (i == 3) {
            this.store.removeValue(this.mActivity.getString(R.string.language4));
        } else {
            this.store.removeValue(this.mActivity.getString(R.string.language5));
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
            // this.layout_bg.setBackgroundResource(R.drawable.bg_language);
        }
    }

    public void setFilter(List<LanguageModel> list) {
        this.datalist = list;
        notifyDataSetChanged();
    }
}
