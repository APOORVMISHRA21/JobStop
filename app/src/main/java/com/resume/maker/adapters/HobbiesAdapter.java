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
import com.resume.maker.interfaces.HobbiesClick;
import com.resume.maker.models.HobbiesModel;

import java.util.List;



public class HobbiesAdapter extends RecyclerView.Adapter<HobbiesAdapter.ItemHolder> {
    public static int selectedposition;
    List<HobbiesModel> datalist;
    private LayoutInflater layoutInflater;
    HobbiesClick listner;
    private Activity mActivity;
    SharedPreferences store;

    @RequiresApi(api = 24)
    public HobbiesAdapter(Activity activity, List<HobbiesModel> list, HobbiesClick hobbiesClick) {
        this.mActivity = activity;
        this.datalist = list;
        this.listner = hobbiesClick;
        this.layoutInflater = LayoutInflater.from(activity);
    }

    @NonNull
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.store = SharedPreferences.getInstance(this.mActivity, "resumemaker");
        return new ItemHolder(this.layoutInflater.inflate(R.layout.rowlayout_itemlist, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ItemHolder itemHolder, final int i) {
        if (this.datalist.get(i).getHobby() == null || this.datalist.get(i).getHobby().equals("")) {
            itemHolder.loutMain.setVisibility(View.GONE);
        } else {
            itemHolder.loutMain.setVisibility(View.VISIBLE);
            itemHolder.tv_item.setText(this.datalist.get(i).getHobby());
        }
        itemHolder.tv_item.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HobbiesAdapter.this.listner.callback(HobbiesAdapter.this.datalist.get(i));
                HobbiesAdapter.selectedposition = i;
            }
        });
        itemHolder.imgedit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HobbiesAdapter.this.listner.callback(HobbiesAdapter.this.datalist.get(i));
                HobbiesAdapter.selectedposition = i;
            }
        });
        itemHolder.imgdelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HobbiesAdapter.selectedposition = i;
                HobbiesAdapter.this.removeItem(HobbiesAdapter.selectedposition);
            }
        });
    }

    public void removeItem(int i) {
        this.datalist.remove(i);
        notifyItemRemoved(i);
        if (i == 0) {
            this.store.removeValue(this.mActivity.getString(R.string.hobby1));
        } else if (i == 1) {
            this.store.removeValue(this.mActivity.getString(R.string.hobby2));
        } else if (i == 2) {
            this.store.removeValue(this.mActivity.getString(R.string.hobby3));
        } else if (i == 3) {
            this.store.removeValue(this.mActivity.getString(R.string.hobby4));
        } else {
            this.store.removeValue(this.mActivity.getString(R.string.hobby5));
        }
        notifyItemRangeChanged(i, this.datalist.size());
        selectedposition = i;
    }

    @Override
    public int getItemCount() {
        Log.d("hobbysize>>", this.datalist.size() + "");
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
//            this.layout_bg.setBackgroundResource(R.drawable.bg_hobby);
        }
    }

    public void setFilter(List<HobbiesModel> list) {
        this.datalist = list;
        notifyDataSetChanged();
    }
}
