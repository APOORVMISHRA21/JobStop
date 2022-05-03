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
import com.resume.maker.interfaces.AcademicClick;
import com.resume.maker.models.AcademicModel;

import java.util.Collections;
import java.util.List;



public class AcademicAdapter extends RecyclerView.Adapter<AcademicAdapter.ItemHolder> {
    public static int selectedposition;
    List<AcademicModel> datalist;
    private LayoutInflater layoutInflater;
    AcademicClick listner;
    private Activity mActivity;
    SharedPreferences store;

    @RequiresApi(api = 24)
    public AcademicAdapter(Activity activity, List<AcademicModel> list, AcademicClick academicClick) {
        this.mActivity = activity;
        this.datalist = list;
        this.listner = academicClick;
        this.layoutInflater = LayoutInflater.from(activity);
        Collections.shuffle(list);
    }

    @NonNull
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.store = SharedPreferences.getInstance(this.mActivity, "resumemaker");
        return new ItemHolder(this.layoutInflater.inflate(R.layout.rowlayout_academic, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ItemHolder itemHolder, final int i) {
        if (this.datalist.get(i).getDegree() == null || this.datalist.get(i).getDegree().equals("")) {
            itemHolder.loutMain.setVisibility(View.GONE);
        } else {
            itemHolder.loutMain.setVisibility(View.VISIBLE);
            itemHolder.tv_item.setText(this.datalist.get(i).getDegree());
            itemHolder.tv_item.setSelected(true);
            itemHolder.tv_institutenm.setText(this.datalist.get(i).getInstitute());
            itemHolder.tv_institutenm.setSelected(true);
            itemHolder.tvcgpa.setText(this.datalist.get(i).getPercgpa());
            itemHolder.tvyear.setText(this.datalist.get(i).getYear());
//            System.out.println(" radiooooo "+ this.datalist.get(i).getPerradio());
            if(this.datalist.get(i).getPerradio().equals("Percentage")){
//                System.out.println(" radiooooo "+ this.datalist.get(i).getPerradio());
                itemHolder.cgorper.setText("Percentage");
            }
        }
        itemHolder.loutMain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AcademicAdapter.this.listner.callback(AcademicAdapter.this.datalist.get(i));
                AcademicAdapter.selectedposition = i;
            }
        });
        itemHolder.imgdelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AcademicAdapter.selectedposition = i;
                AcademicAdapter.this.removeItem(AcademicAdapter.selectedposition);
                Log.d("pos>>selectedpos>>", i + ", selected:" + AcademicAdapter.selectedposition);
            }
        });
    }

    public void removeItem(int i) {
        this.datalist.remove(i);
        notifyItemRemoved(i);
        if (i == 0) {
            this.store.removeValue(this.mActivity.getString(R.string.academic_degree1));
        } else if (i == 1) {
            this.store.removeValue(this.mActivity.getString(R.string.academic_degree2));
        } else {
            this.store.removeValue(this.mActivity.getString(R.string.academic_degree3));
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
        public TextView tv_institutenm;
        public TextView tv_item;
        public TextView tvcgpa;
        public TextView cgorper;
        public TextView tvyear;

        public ItemHolder(@NonNull View view) {
            super(view);
            this.tv_item = (TextView) view.findViewById(R.id.tv_item);
            this.tv_institutenm = (TextView) view.findViewById(R.id.tv_institutenm);
            this.cgorper = (TextView) view.findViewById(R.id.cgorper);
            this.tvcgpa = (TextView) view.findViewById(R.id.tvcgpa);
            this.tvyear = (TextView) view.findViewById(R.id.tvyear);
            this.imgdelete = (ImageView) view.findViewById(R.id.imgdelete);
            this.loutMain = (LinearLayout) view.findViewById(R.id.loutMain);
        }
    }

    public void setFilter(List<AcademicModel> list) {
        this.datalist = list;
        notifyDataSetChanged();
    }
}
