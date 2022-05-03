package com.resume.maker.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.resume.maker.interfaces.AboutClick;
import com.resume.maker.models.InterviewTipsModel;

import java.util.List;
import java.util.Random;



public class InterviewTipsAdapter extends RecyclerView.Adapter<InterviewTipsAdapter.ItemHolder> {
    public static int bgColor;
    public static int selectedposition;
    String abouttext;
    private final int[] backgroundColors = {R.color.list_color1, R.color.list_color2, R.color.list_color3, R.color.list_color4, R.color.list_color5, R.color.list_color6, R.color.list_color7};
    List<InterviewTipsModel> datalist;
    private LayoutInflater layoutInflater;
    AboutClick listner;
    private Activity mActivity;

    @RequiresApi(api = 24)
    public InterviewTipsAdapter(Activity activity, List<InterviewTipsModel> list) {
        this.mActivity = activity;
        this.datalist = list;
        this.layoutInflater = LayoutInflater.from(activity);
    }

    @NonNull
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemHolder(this.layoutInflater.inflate(R.layout.rowlayout_interviewtips, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        selectedposition = i;
        bgColor = ContextCompat.getColor(this.mActivity, this.backgroundColors[(new Random().nextInt(18) + 1) % 7]);
        ItemHolder.card_view.setCardBackgroundColor(bgColor);
        ItemHolder.tv_title.setText(this.datalist.get(i).getTitle());
        ItemHolder.tv_desc.setText(this.datalist.get(i).getDesc());
        itemHolder.loutMain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d("rclistclick>>adapter", InterviewTipsAdapter.selectedposition + "m");
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.datalist.size();
    }


    public static class ItemHolder extends RecyclerView.ViewHolder {
        public static CardView card_view;
        public static TextView tv_desc;
        public static TextView tv_title;
        public LinearLayout loutMain;

        public ItemHolder(@NonNull View view) {
            super(view);
            card_view = (CardView) view.findViewById(R.id.card_view);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            this.loutMain = (LinearLayout) view.findViewById(R.id.loutMain);
        }
    }

    public void setFilter(List<InterviewTipsModel> list) {
        this.datalist = list;
        notifyDataSetChanged();
    }
}
