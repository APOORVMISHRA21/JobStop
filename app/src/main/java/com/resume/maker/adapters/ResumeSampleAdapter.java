package com.resume.maker.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demo.R;
import com.google.gson.Gson;
import com.resume.maker.ResumeSample1Activity;
import com.resume.maker.interfaces.AboutClick;
import com.resume.maker.models.ResumeSampleModel;

import java.util.ArrayList;

public class ResumeSampleAdapter extends RecyclerView.Adapter<ResumeSampleAdapter.ImageViewHolder> {
    public static int selectedposition;
    private final Context context;
    private final LayoutInflater layoutInflater;
    AboutClick listner;
    private Activity mActivity;
    private ArrayList<ResumeSampleModel> stickerIds;

    public ResumeSampleAdapter(Context context, ArrayList<ResumeSampleModel> arrayList, Activity activity) {
        this.stickerIds = arrayList;
        this.context = context;
        this.mActivity = activity;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public ImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ImageViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rowlayout_resume_sample, viewGroup, false));
    }

    public void onBindViewHolder(ImageViewHolder imageViewHolder, final int i) {
        Glide.with(this.context).load(Integer.valueOf(this.stickerIds.get(i).getfrm1())).into(imageViewHolder.image);
        imageViewHolder.image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ResumeSampleAdapter.selectedposition = i;
                Gson gson = new Gson();
                new ResumeSampleModel();
                Intent intent = new Intent(ResumeSampleAdapter.this.context, ResumeSample1Activity.class);
                intent.putExtra("obj", gson.toJson(ResumeSampleAdapter.this.stickerIds));
                intent.putExtra("position", i);
                Log.d("state>>put", gson.toJson(ResumeSampleAdapter.this.stickerIds));
                ResumeSampleAdapter.this.context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.stickerIds.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public ImageView imgfrm1;

        public ImageViewHolder(View view) {
            super(view);
            this.image = (ImageView) view.findViewById(R.id.imgsticker);
        }
    }
}
