package com.resume.maker.crop;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.demo.R;
import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;
import com.isseiaoki.simplecropview.util.Logger;
import com.isseiaoki.simplecropview.util.Utils;

import com.resume.maker.AboutmeActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions

public class BasicFragment extends Fragment {
    private static final String KEY_FRAME_RECT = "FrameRect";
    private static final String KEY_SOURCE_URI = "SourceUri";
    private static final String PROGRESS_DIALOG = "ProgressDialog";
    private static final int REQUEST_PICK_IMAGE = 10011;
    private static final int REQUEST_SAF_PICK_IMAGE = 10012;
    private static final String TAG = "BasicFragment";
    Bitmap bmp;
    ImageView imgback;
    ImageView imgcrop2;
    ImageView imgcrop_done;
    private CropImageView mCropView;
    View view;
    private Bitmap.CompressFormat mCompressFormat = Bitmap.CompressFormat.JPEG;
    private RectF mFrameRect = null;
    private Uri mSourceUri = null;
    private final View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button16_9:
                    BasicFragment.this.mCropView.setCropMode(CropImageView.CropMode.RATIO_16_9);
                    return;
                case R.id.button1_1:
                    BasicFragment.this.mCropView.setCropMode(CropImageView.CropMode.SQUARE);
                    return;
                case R.id.button3_4:
                    BasicFragment.this.mCropView.setCropMode(CropImageView.CropMode.RATIO_3_4);
                    return;
                case R.id.button4_3:
                    BasicFragment.this.mCropView.setCropMode(CropImageView.CropMode.RATIO_4_3);
                    return;
                case R.id.button9_16:
                    BasicFragment.this.mCropView.setCropMode(CropImageView.CropMode.RATIO_9_16);
                    return;
                case R.id.buttonCircle:
                    BasicFragment.this.mCropView.setCropMode(CropImageView.CropMode.CIRCLE);
                    return;
                case R.id.buttonCustom:
                    BasicFragment.this.mCropView.setCustomRatio(7, 5);
                    return;
                case R.id.buttonDone:
                case R.id.buttonPanel:
                case R.id.buttonPickImage:
                default:
                    return;
                case R.id.buttonFitImage:
                    BasicFragment.this.mCropView.setCropMode(CropImageView.CropMode.FIT_IMAGE);
                    return;
                case R.id.buttonFree:
                    BasicFragment.this.mCropView.setCropMode(CropImageView.CropMode.FREE);
                    return;
                case R.id.buttonRotateLeft:
                    BasicFragment.this.mCropView.rotateImage(CropImageView.RotateDegrees.ROTATE_M90D);
                    return;
                case R.id.buttonRotateRight:
                    BasicFragment.this.mCropView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
                    return;
                case R.id.buttonShowCircleButCropAsSquare:
                    BasicFragment.this.mCropView.setCropMode(CropImageView.CropMode.CIRCLE_SQUARE);
                    return;
            }
        }
    };
    private final LoadCallback mLoadCallback = new LoadCallback() {
        @Override
        public void onError(Throwable th) {
        }

        @Override
        public void onSuccess() {
        }
    };
    private final CropCallback mCropCallback = new CropCallback() {
        @Override
        public void onError(Throwable th) {
        }

        @Override
        public void onSuccess(Bitmap bitmap) {
        }
    };
    private final SaveCallback mSaveCallback = new SaveCallback() {
        @Override
        public void onSuccess(Uri uri) {
            BasicFragment.this.dismissProgress();
            ((BasicActivity) BasicFragment.this.getActivity()).startResultActivity(uri);
        }

        @Override
        public void onError(Throwable th) {
            BasicFragment.this.dismissProgress();
        }
    };

    public static BasicFragment newInstance() {
        BasicFragment basicFragment = new BasicFragment();
        basicFragment.setArguments(new Bundle());
        return basicFragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.fragment_basic, (ViewGroup) null, false);
        return this.view;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        bindViews(view);
        this.imgback = (ImageView) view.findViewById(R.id.imgback);
        this.imgcrop_done = (ImageView) view.findViewById(R.id.imgcrop_done);
        this.imgcrop2 = (ImageView) view.findViewById(R.id.imgcrop2);
        this.mCropView.setDebug(true);
        if (bundle != null) {
            this.mFrameRect = (RectF) bundle.getParcelable(KEY_FRAME_RECT);
            this.mSourceUri = (Uri) bundle.getParcelable(KEY_SOURCE_URI);
        }
        if (this.mSourceUri == null) {
            this.mSourceUri = Uri.parse(getActivity().getIntent().getStringExtra("imageUri"));
            this.imgcrop2.setImageURI(this.mSourceUri);
            this.imgcrop2.setDrawingCacheEnabled(true);
            this.bmp = ((BitmapDrawable) this.imgcrop2.getDrawable()).getBitmap();
            this.mSourceUri = getImageUri(getActivity(), this.bmp);
            this.mCropView.load(this.mSourceUri).initialFrameRect(this.mFrameRect).useThumbnail(false).execute(this.mLoadCallback);
            Log.e("aoki", "mSourceUri = " + this.mSourceUri);
        }
        this.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                BasicFragment.this.getActivity().finish();
            }
        });
        this.imgcrop_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                if (BasicFragment.this.mSourceUri != null) {
                    BasicFragment.this.mCropView.crop(BasicFragment.this.mSourceUri).execute(new CropCallback() {
                        @Override
                        public void onError(Throwable th) {
                        }

                        @Override
                        public void onSuccess(Bitmap bitmap) {
                            Uri imageUri = BasicFragment.this.getImageUri(BasicFragment.this.getActivity(), bitmap);
                            Intent intent = new Intent();
                            intent.putExtra("imageUri2", imageUri.toString());
                            intent.putExtra(AboutmeActivity.EXTRA_PROFILE_ID, imageUri.toString());
                            BasicFragment.this.getActivity().setResult(-1, intent);
                            BasicFragment.this.getActivity().finish();
                        }
                    });
                }
            }
        });
    }


    public Uri getImageUri(Context context, Bitmap bitmap) {
        File externalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(externalStorageDirectory.getAbsolutePath() + "/.temp/");
        file.mkdir();
        File file2 = null;
        try {
            file2 = File.createTempFile("baby_pic", ".jpg", file);
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
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelable(KEY_FRAME_RECT, this.mCropView.getActualCropRect());
        bundle.putParcelable(KEY_SOURCE_URI, this.mCropView.getSourceUri());
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            this.mFrameRect = null;
            if (i == REQUEST_PICK_IMAGE) {
                this.mSourceUri = intent.getData();
                this.mCropView.load(this.mSourceUri).initialFrameRect(this.mFrameRect).useThumbnail(true).execute(this.mLoadCallback);
            } else if (i == REQUEST_SAF_PICK_IMAGE) {
                this.mSourceUri = Utils.ensureUriPermission(getContext(), intent);
                this.mCropView.load(this.mSourceUri).initialFrameRect(this.mFrameRect).useThumbnail(true).execute(this.mLoadCallback);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
    }

    private void bindViews(View view) {
        this.mCropView = (CropImageView) view.findViewById(R.id.cropImageView);
        view.findViewById(R.id.buttonDone).setOnClickListener(this.btnListener);
        view.findViewById(R.id.buttonFitImage).setOnClickListener(this.btnListener);
        view.findViewById(R.id.button1_1).setOnClickListener(this.btnListener);
        view.findViewById(R.id.button3_4).setOnClickListener(this.btnListener);
        view.findViewById(R.id.button4_3).setOnClickListener(this.btnListener);
        view.findViewById(R.id.button9_16).setOnClickListener(this.btnListener);
        view.findViewById(R.id.button16_9).setOnClickListener(this.btnListener);
        view.findViewById(R.id.buttonFree).setOnClickListener(this.btnListener);
        view.findViewById(R.id.buttonPickImage).setOnClickListener(this.btnListener);
        view.findViewById(R.id.buttonRotateLeft).setOnClickListener(this.btnListener);
        view.findViewById(R.id.buttonRotateRight).setOnClickListener(this.btnListener);
        view.findViewById(R.id.buttonCustom).setOnClickListener(this.btnListener);
        view.findViewById(R.id.buttonCircle).setOnClickListener(this.btnListener);
        view.findViewById(R.id.buttonShowCircleButCropAsSquare).setOnClickListener(this.btnListener);
    }

    @NeedsPermission({"android.permission.READ_EXTERNAL_STORAGE"})
    public void pickImage() {
        if (Build.VERSION.SDK_INT < 19) {
            startActivityForResult(new Intent("android.intent.action.GET_CONTENT").setType("image/*"), REQUEST_PICK_IMAGE);
            return;
        }
        Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_SAF_PICK_IMAGE);
    }

    @NeedsPermission({"android.permission.WRITE_EXTERNAL_STORAGE"})
    public void cropImage() {
        showProgress();
        this.mCropView.crop(this.mSourceUri).execute(this.mCropCallback);
    }

    @OnShowRationale({"android.permission.READ_EXTERNAL_STORAGE"})
    public void showRationaleForPick(PermissionRequest permissionRequest) {
        showRationaleDialog(R.string.permission_pick_rationale, permissionRequest);
    }

    @OnShowRationale({"android.permission.WRITE_EXTERNAL_STORAGE"})
    public void showRationaleForCrop(PermissionRequest permissionRequest) {
        showRationaleDialog(R.string.permission_crop_rationale, permissionRequest);
    }

    public void showProgress() {
        getFragmentManager().beginTransaction().add(ProgressDialogFragment.getInstance(), PROGRESS_DIALOG).commitAllowingStateLoss();
    }

    public void dismissProgress() {
        FragmentManager fragmentManager;
        ProgressDialogFragment progressDialogFragment;
        if (isResumed() && (fragmentManager = getFragmentManager()) != null && (progressDialogFragment = (ProgressDialogFragment) fragmentManager.findFragmentByTag(PROGRESS_DIALOG)) != null) {
            getFragmentManager().beginTransaction().remove(progressDialogFragment).commitAllowingStateLoss();
        }
    }

    public Uri createSaveUri() {
        return createNewUri(getContext(), this.mCompressFormat);
    }

    public static String getDirPath() {
        File file;
        File externalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (externalStorageDirectory.canWrite()) {
            file = new File(externalStorageDirectory.getPath() + "/cropped images");
        } else {
            file = null;
        }
        if (file != null) {
            if (!file.exists()) {
                file.mkdirs();
            }
            if (file.canWrite()) {
                return file.getPath();
            }
        }
        return "";
    }

    public static Uri getUriFromDrawableResId(Context context, int i) {
        return Uri.parse("android.resource://" + context.getResources().getResourcePackageName(i) + "/" + context.getResources().getResourceTypeName(i) + "/" + context.getResources().getResourceEntryName(i));
    }

    public static Uri createNewUri(Context context, Bitmap.CompressFormat compressFormat) {
        long currentTimeMillis = System.currentTimeMillis();
        String format = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date(currentTimeMillis));
        String str = "scv" + format + "." + getMimeType(compressFormat);
        String str2 = getDirPath() + "/" + str;
        File file = new File(str2);
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", format);
        contentValues.put("_display_name", str);
        contentValues.put("mime_type", "image/" + getMimeType(compressFormat));
        contentValues.put("_data", str2);
        long j = currentTimeMillis / 1000;
        contentValues.put("date_added", Long.valueOf(j));
        contentValues.put("date_modified", Long.valueOf(j));
        if (file.exists()) {
            contentValues.put("_size", Long.valueOf(file.length()));
        }
        Uri insert = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Logger.i("SaveUri = " + insert);
        return insert;
    }




    public static  class AnonymousClass9 {
        static final  int[] $SwitchMap$android$graphics$Bitmap$CompressFormat = new int[Bitmap.CompressFormat.values().length];

        static {
            try {
                $SwitchMap$android$graphics$Bitmap$CompressFormat[Bitmap.CompressFormat.JPEG.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$CompressFormat[Bitmap.CompressFormat.PNG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static String getMimeType(Bitmap.CompressFormat compressFormat) {
        Logger.i("getMimeType CompressFormat = " + compressFormat);
        int i = AnonymousClass9.$SwitchMap$android$graphics$Bitmap$CompressFormat[compressFormat.ordinal()];
        if (i == 1) {
            return "jpeg";
        }
        if (i != 2) {
        }
        return "png";
    }

    public static Uri createTempUri(Context context) {
        return Uri.fromFile(new File(context.getCacheDir(), "cropped"));
    }

    private void showRationaleDialog(@StringRes int i, final PermissionRequest permissionRequest) {
        new AlertDialog.Builder(getActivity()).setPositiveButton(R.string.button_allow, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull DialogInterface dialogInterface, int i2) {
                permissionRequest.proceed();
            }
        }).setNegativeButton(R.string.button_deny, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull DialogInterface dialogInterface, int i2) {
                permissionRequest.cancel();
            }
        }).setCancelable(false).setMessage(i).show();
    }
}
