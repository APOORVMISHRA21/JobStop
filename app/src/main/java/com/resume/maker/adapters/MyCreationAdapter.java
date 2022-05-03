package com.resume.maker.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.resume.maker.FullScreenCreationActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MyCreationAdapter extends RecyclerView.Adapter<MyCreationAdapter.MyCreationViewHolder2> {
    private final Context context;
    ArrayList<String> itemList;
    private final LayoutInflater layoutInflater;
    private Activity mActivity;

    public MyCreationAdapter(Context context, Activity activity) {
        this.itemList = new ArrayList<>();
        this.mActivity = activity;
        this.itemList = this.itemList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    public MyCreationViewHolder2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyCreationViewHolder2(this.layoutInflater.inflate(R.layout.rowlayout_mycreation, viewGroup, false));
    }

    @RequiresApi(api = 21)
    public void onBindViewHolder(@NonNull MyCreationViewHolder2 myCreationViewHolder2, int i) {
        ParcelFileDescriptor parcelFileDescriptor;
        PdfRenderer pdfRenderer;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i2 = displayMetrics.heightPixels;
        Log.d("screenwidth>>", (displayMetrics.widthPixels / 2) + "");
        try {
            parcelFileDescriptor = ParcelFileDescriptor.open(new File(this.itemList.get(i)), 268435456);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            parcelFileDescriptor = null;
        }
        try {
            pdfRenderer = new PdfRenderer(parcelFileDescriptor);
        } catch (IOException e2) {
            e2.printStackTrace();
            pdfRenderer = null;
        }
        PdfRenderer.Page openPage = pdfRenderer.openPage(0);
        Bitmap createBitmap = Bitmap.createBitmap(openPage.getWidth(), openPage.getHeight(), Bitmap.Config.ARGB_8888);
        openPage.render(createBitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        final Uri imageUri = getImageUri(this.context, createBitmap);
        myCreationViewHolder2.image.setImageURI(imageUri);
        openPage.close();
        pdfRenderer.close();
        try {
            parcelFileDescriptor.close();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        myCreationViewHolder2.image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MyCreationAdapter.this.context, FullScreenCreationActivity.class);
                intent.putExtra("creationuri", imageUri.toString());
                MyCreationAdapter.this.context.startActivity(intent);
            }
        });
    }

    private Uri getImageUri(Context context, Bitmap bitmap) {
        File externalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(externalStorageDirectory.getAbsolutePath() + "/.temp/");
        file.mkdir();
        File file2 = null;
        try {
            file2 = File.createTempFile("resume_maker", ".jpg", file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            fileOutputStream.write(byteArray);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Uri.fromFile(file2);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public void add(String str) {
        this.itemList.add(str);
    }


    public class MyCreationViewHolder2 extends RecyclerView.ViewHolder {
        public ImageView image;

        public MyCreationViewHolder2(View view) {
            super(view);
            this.image = (ImageView) view.findViewById(R.id.imgsticker);
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view2) {
                    MyCreationViewHolder2.this.getAdapterPosition();
                }
            });
        }
    }

    public Bitmap decodeSampledBitmapFromUri(String str, int i, int i2) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        options.inSampleSize = calculateInSampleSize(options, i, i2);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(str, options);
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        if (i3 <= i2 && i4 <= i) {
            return 1;
        }
        if (i4 > i3) {
            return Math.round(((float) i3) / ((float) i2));
        }
        return Math.round(((float) i4) / ((float) i));
    }
}
