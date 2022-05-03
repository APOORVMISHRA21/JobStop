package com.resume.maker;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.demo.R;
import com.github.javiersantos.bottomdialogs.BottomDialog;

import com.resume.maker.crop.BasicActivity;
import com.resume.maker.utils.Util;

import de.hdodenhof.circleimageview.CircleImageView;

import java.util.regex.Pattern;


public class PersonalinfoActivity extends AppCompatActivity {
    public static final int SELECT_ABOUT_REQUEST_CODE = 123;
    public static final int SELECT_PROFILE_REQUEST_CODE = 12;
    public static Uri uri;
    String aboutme;
    String address;
    LinearLayout back;
    EditText et_Address;
    EditText et_MailAddress;
    EditText et_Name;
    EditText et_Phone;
    EditText et_aboutme;
    EditText et_field;
    String field;
    String getabout;
    CircleImageView iv_profile;
    LinearLayout layout_toolbar;
    ImageView ll_save;
    Activity mActivity;
    String mailAddress;
    String name;
    String phone;
    SharedPreferences store;
    TextView tv_aboutme,nextBtn,backBtn;
    private final int PICTURE_TAKEN_FROM_CAMERA = 1;
    private final int PICTURE_TAKEN_FROM_GALLERY = 2;
    int flag = 0;



    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_personalinfo);
        initToolbar();
        this.mActivity = this;
        this.store = SharedPreferences.getInstance(this, "resumemaker");
        this.iv_profile = (CircleImageView) findViewById(R.id.iv_profile);
        this.ll_save = (ImageView) findViewById(R.id.ll_save);
        this.et_Name = (EditText) findViewById(R.id.et_Name);
        this.et_Phone = (EditText) findViewById(R.id.et_Phone);
        this.et_MailAddress = (EditText) findViewById(R.id.et_MailAddress);
        this.et_Address = (EditText) findViewById(R.id.et_Address);
        this.et_field = (EditText) findViewById(R.id.et_field);
        this.et_aboutme = (EditText) findViewById(R.id.et_aboutme);
        this.tv_aboutme = (TextView) findViewById(R.id.tv_aboutme);
        this.backBtn = (TextView) findViewById(R.id.backBtn);
        this.nextBtn = (TextView) findViewById(R.id.nextBtn);
        getSharedpref();

        backBtn.setVisibility(View.INVISIBLE );
//        this.backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PersonalinfoActivity.this.showPictureDialog();
//            }
//        });
        this.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(save()) {
                    startActivity(new Intent(getApplicationContext(), AcademicActivity.class));
                }
            }
        });
        this.iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonalinfoActivity.this.showPictureDialog();
            }
        });
        this.tv_aboutme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonalinfoActivity.this.startActivityForResult(new Intent(PersonalinfoActivity.this, AboutmeActivity.class), 123);
            }
        });
        this.ll_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PersonalinfoActivity.this.store.getString(PersonalinfoActivity.this.getString(R.string.personalinfo_profile), "") == null || PersonalinfoActivity.this.store.getString(PersonalinfoActivity.this.getString(R.string.personalinfo_profile), "") == "") {
                    Toast.makeText(PersonalinfoActivity.this, "Please Select Picture", Toast.LENGTH_SHORT).show();
                    return;
                }
                PersonalinfoActivity personalinfoActivity = PersonalinfoActivity.this;
                if (personalinfoActivity.isEmpty(personalinfoActivity.et_Name)) {
                    Toast.makeText(PersonalinfoActivity.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                PersonalinfoActivity personalinfoActivity2 = PersonalinfoActivity.this;
                if (personalinfoActivity2.isEmpty(personalinfoActivity2.et_Phone)) {
                    Toast.makeText(PersonalinfoActivity.this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                PersonalinfoActivity personalinfoActivity3 = PersonalinfoActivity.this;
                if (personalinfoActivity3.isEmpty(personalinfoActivity3.et_MailAddress)) {
                    Toast.makeText(PersonalinfoActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                PersonalinfoActivity personalinfoActivity4 = PersonalinfoActivity.this;
                if (personalinfoActivity4.isEmpty(personalinfoActivity4.et_Address)) {
                    Toast.makeText(PersonalinfoActivity.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
                    return;
                }
                PersonalinfoActivity personalinfoActivity5 = PersonalinfoActivity.this;
                if (personalinfoActivity5.isEmpty(personalinfoActivity5.et_field)) {
                    Toast.makeText(PersonalinfoActivity.this, "Please Enter Field", Toast.LENGTH_SHORT).show();
                    return;
                }
                PersonalinfoActivity personalinfoActivity6 = PersonalinfoActivity.this;
                if (personalinfoActivity6.isEmpty(personalinfoActivity6.et_aboutme)) {
                    Toast.makeText(PersonalinfoActivity.this, "Please Enter or Choose About Me", Toast.LENGTH_SHORT).show();
                    return;
                }
                PersonalinfoActivity personalinfoActivity7 = PersonalinfoActivity.this;
                if (!personalinfoActivity7.isValidMobile(personalinfoActivity7.et_Phone.getText().toString())) {
                    Toast.makeText(PersonalinfoActivity.this, "Please Enter valid Phone Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                PersonalinfoActivity personalinfoActivity8 = PersonalinfoActivity.this;
                if (!personalinfoActivity8.isValidMail(personalinfoActivity8.et_MailAddress.getText().toString())) {
                    Toast.makeText(PersonalinfoActivity.this, "Please Enter valid Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                PersonalinfoActivity personalinfoActivity9 = PersonalinfoActivity.this;
                personalinfoActivity9.name = personalinfoActivity9.et_Name.getText().toString();
                PersonalinfoActivity personalinfoActivity10 = PersonalinfoActivity.this;
                personalinfoActivity10.phone = personalinfoActivity10.et_Phone.getText().toString();
                PersonalinfoActivity personalinfoActivity11 = PersonalinfoActivity.this;
                personalinfoActivity11.mailAddress = personalinfoActivity11.et_MailAddress.getText().toString();
                PersonalinfoActivity personalinfoActivity12 = PersonalinfoActivity.this;
                personalinfoActivity12.address = personalinfoActivity12.et_Address.getText().toString();
                PersonalinfoActivity personalinfoActivity13 = PersonalinfoActivity.this;
                personalinfoActivity13.field = personalinfoActivity13.et_field.getText().toString();
                PersonalinfoActivity personalinfoActivity14 = PersonalinfoActivity.this;
                personalinfoActivity14.aboutme = personalinfoActivity14.et_aboutme.getText().toString();
                Log.d("sharedpref>", PersonalinfoActivity.this.name + PersonalinfoActivity.this.phone + PersonalinfoActivity.this.mailAddress + PersonalinfoActivity.this.address + PersonalinfoActivity.this.field + PersonalinfoActivity.this.aboutme);
                PersonalinfoActivity.this.store.saveString(PersonalinfoActivity.this.getString(R.string.personalinfo_name), PersonalinfoActivity.this.name);
                PersonalinfoActivity.this.store.saveString(PersonalinfoActivity.this.getString(R.string.personalinfo_phone), PersonalinfoActivity.this.phone);
                PersonalinfoActivity.this.store.saveString(PersonalinfoActivity.this.getString(R.string.personalinfo_email), PersonalinfoActivity.this.mailAddress);
                PersonalinfoActivity.this.store.saveString(PersonalinfoActivity.this.getString(R.string.personalinfo_address), PersonalinfoActivity.this.address);
                PersonalinfoActivity.this.store.saveString(PersonalinfoActivity.this.getString(R.string.personalinfo_field), PersonalinfoActivity.this.field);
                PersonalinfoActivity.this.store.saveString(PersonalinfoActivity.this.getString(R.string.personalinfo_aboutme), PersonalinfoActivity.this.aboutme);
//                PersonalinfoActivity.this.finish();
                Toast.makeText(PersonalinfoActivity.this, "Saved", Toast.LENGTH_SHORT).show();

            }
        });
    }
private boolean save(){

        if (PersonalinfoActivity.this.store.getString(PersonalinfoActivity.this.getString(R.string.personalinfo_profile), "") == null || PersonalinfoActivity.this.store.getString(PersonalinfoActivity.this.getString(R.string.personalinfo_profile), "") == "") {
            Toast.makeText(PersonalinfoActivity.this, "Please Select Picture", Toast.LENGTH_SHORT).show();
            return false;
        }
        PersonalinfoActivity personalinfoActivity = PersonalinfoActivity.this;
        if (personalinfoActivity.isEmpty(personalinfoActivity.et_Name)) {
            Toast.makeText(PersonalinfoActivity.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        PersonalinfoActivity personalinfoActivity2 = PersonalinfoActivity.this;
        if (personalinfoActivity2.isEmpty(personalinfoActivity2.et_Phone)) {
            Toast.makeText(PersonalinfoActivity.this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        PersonalinfoActivity personalinfoActivity3 = PersonalinfoActivity.this;
        if (personalinfoActivity3.isEmpty(personalinfoActivity3.et_MailAddress)) {
            Toast.makeText(PersonalinfoActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return false;
        }
        PersonalinfoActivity personalinfoActivity4 = PersonalinfoActivity.this;
        if (personalinfoActivity4.isEmpty(personalinfoActivity4.et_Address)) {
            Toast.makeText(PersonalinfoActivity.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
            return false;
        }
        PersonalinfoActivity personalinfoActivity5 = PersonalinfoActivity.this;
        if (personalinfoActivity5.isEmpty(personalinfoActivity5.et_field)) {
            Toast.makeText(PersonalinfoActivity.this, "Please Enter Field", Toast.LENGTH_SHORT).show();
            return false;
        }
        PersonalinfoActivity personalinfoActivity6 = PersonalinfoActivity.this;
        if (personalinfoActivity6.isEmpty(personalinfoActivity6.et_aboutme)) {
            Toast.makeText(PersonalinfoActivity.this, "Please Enter or Choose About Me", Toast.LENGTH_SHORT).show();
            return false;
        }
        PersonalinfoActivity personalinfoActivity7 = PersonalinfoActivity.this;
        if (!personalinfoActivity7.isValidMobile(personalinfoActivity7.et_Phone.getText().toString())) {
            Toast.makeText(PersonalinfoActivity.this, "Please Enter valid Phone Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        PersonalinfoActivity personalinfoActivity8 = PersonalinfoActivity.this;
        if (!personalinfoActivity8.isValidMail(personalinfoActivity8.et_MailAddress.getText().toString())) {
            Toast.makeText(PersonalinfoActivity.this, "Please Enter valid Email", Toast.LENGTH_SHORT).show();
            return false;
        }
        PersonalinfoActivity personalinfoActivity9 = PersonalinfoActivity.this;
        personalinfoActivity9.name = personalinfoActivity9.et_Name.getText().toString();
        PersonalinfoActivity personalinfoActivity10 = PersonalinfoActivity.this;
        personalinfoActivity10.phone = personalinfoActivity10.et_Phone.getText().toString();
        PersonalinfoActivity personalinfoActivity11 = PersonalinfoActivity.this;
        personalinfoActivity11.mailAddress = personalinfoActivity11.et_MailAddress.getText().toString();
        PersonalinfoActivity personalinfoActivity12 = PersonalinfoActivity.this;
        personalinfoActivity12.address = personalinfoActivity12.et_Address.getText().toString();
        PersonalinfoActivity personalinfoActivity13 = PersonalinfoActivity.this;
        personalinfoActivity13.field = personalinfoActivity13.et_field.getText().toString();
        PersonalinfoActivity personalinfoActivity14 = PersonalinfoActivity.this;
        personalinfoActivity14.aboutme = personalinfoActivity14.et_aboutme.getText().toString();
        Log.d("sharedpref>", PersonalinfoActivity.this.name + PersonalinfoActivity.this.phone + PersonalinfoActivity.this.mailAddress + PersonalinfoActivity.this.address + PersonalinfoActivity.this.field + PersonalinfoActivity.this.aboutme);
        PersonalinfoActivity.this.store.saveString(PersonalinfoActivity.this.getString(R.string.personalinfo_name), PersonalinfoActivity.this.name);
        PersonalinfoActivity.this.store.saveString(PersonalinfoActivity.this.getString(R.string.personalinfo_phone), PersonalinfoActivity.this.phone);
        PersonalinfoActivity.this.store.saveString(PersonalinfoActivity.this.getString(R.string.personalinfo_email), PersonalinfoActivity.this.mailAddress);
        PersonalinfoActivity.this.store.saveString(PersonalinfoActivity.this.getString(R.string.personalinfo_address), PersonalinfoActivity.this.address);
        PersonalinfoActivity.this.store.saveString(PersonalinfoActivity.this.getString(R.string.personalinfo_field), PersonalinfoActivity.this.field);
        PersonalinfoActivity.this.store.saveString(PersonalinfoActivity.this.getString(R.string.personalinfo_aboutme), PersonalinfoActivity.this.aboutme);
//                PersonalinfoActivity.this.finish();

        Toast.makeText(PersonalinfoActivity.this, "Saved", Toast.LENGTH_SHORT).show();

 return true;
}
    private void initToolbar() {
        Window window = getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
//        if (Build.VERSION.SDK_INT >= 21) {
//            window.setStatusBarColor(ContextCompat.getColor(this, R.color.color_personalinfo));
//        }
        this.layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
//        this.layout_toolbar.setBackgroundResource(R.drawable.toolbar_personalinfo);
        this.back = (LinearLayout) findViewById(R.id.back);
        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonalinfoActivity.this.onBackPressed();
            }
        });
    }

    private void getSharedpref() {
        try {
            if (this.store.getString(getString(R.string.personalinfo_name), "") != null) {
                Log.d("sharedpref>>2", this.store.getString(getString(R.string.personalinfo_name), ""));
                String string = this.store.getString(getString(R.string.personalinfo_profile), "");
                String string2 = this.store.getString(getString(R.string.personalinfo_name), "");
                String string3 = this.store.getString(getString(R.string.personalinfo_phone), "");
                String string4 = this.store.getString(getString(R.string.personalinfo_email), "");
                String string5 = this.store.getString(getString(R.string.personalinfo_address), "");
                String string6 = this.store.getString(getString(R.string.personalinfo_field), "");
                String string7 = this.store.getString(getString(R.string.personalinfo_aboutme), "");
                this.iv_profile.setImageURI(Uri.parse(string));
                this.et_Name.setText(string2);
                this.et_Phone.setText(string3);
                this.et_MailAddress.setText(string4);
                this.et_Address.setText(string5);
                this.et_field.setText(string6);
                this.et_aboutme.setText(string7);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("sharedpref>>2", "null");
        }
    }


    public void showPictureDialog() {
        new BottomDialog.Builder(this).setTitle("Select Image from").setNegativeText("From camera").setPositiveText("From gallery").setNegativeTextColorResource(R.color.colorAccent).onPositive(new BottomDialog.ButtonCallback() {
            @Override
            public void onClick(@NonNull BottomDialog bottomDialog) {
                PersonalinfoActivity.this.openGallery();
            }
        }).onNegative(new BottomDialog.ButtonCallback() {
            @Override
            public void onClick(BottomDialog bottomDialog) {
                PersonalinfoActivity.this.openCamera();
            }
        }).show();
    }


    public void openCamera() {
        uri = FileProvider.getUriForFile(this, getPackageName(), Util.getOutputMediaFile(1));
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", uri);
        startActivityForResult(intent, 1);
    }


    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);
    }

    @Override
    protected void onActivityResult(int i, int i2, @Nullable Intent intent) {
        if (i != 1) {
            if (i != 2) {
                if (i != 12) {
                    if (i == 123 && i2 == -1 && intent != null) {
                        this.getabout = intent.getStringExtra("extra_sticker_id");
                        this.et_aboutme.setText(this.getabout);
                        Log.d("abouttext>>2", this.getabout);
                    }
                } else if (i2 == -1 && intent != null) {
                    uri = Uri.parse(intent.getStringExtra(AboutmeActivity.EXTRA_PROFILE_ID));
                    this.iv_profile.setImageURI(uri);
                    int flags = intent.getFlags() & 3;
                    try {
                        if (Build.VERSION.SDK_INT >= 19) {
                            this.mActivity.getContentResolver().takePersistableUriPermission(uri, flags);
                        }
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                    this.store.saveString(getString(R.string.personalinfo_profile), uri.toString());
                }
            } else if (i2 == -1) {
                uri = intent.getData();
                Intent intent2 = new Intent(this, BasicActivity.class);
                intent2.putExtra("imageUri", uri.toString());
                startActivityForResult(intent2, 12);
            }
        } else if (i2 == -1) {
            Intent intent3 = new Intent(this, BasicActivity.class);
            intent3.putExtra("imageUri", uri.toString());
            startActivityForResult(intent3, 12);
        }
        super.onActivityResult(i, i2, intent);
    }


    public boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }


    public boolean isValidMobile(String str) {
        if (Pattern.matches("[a-zA-Z]+", str) || str.length() <= 6 || str.length() > 13) {
            return false;
        }
        return true;
    }


    public boolean isValidMail(String str) {
        return Patterns.EMAIL_ADDRESS.matcher(str).matches();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        backdialog();
    }

    public void backdialog() {
        DialogInterface.OnClickListener r0 = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == -3) {
                    dialogInterface.dismiss();
                } else if (i == -1) {
                    dialogInterface.dismiss();
//                    PersonalinfoActivity.this.finish();
                    finishAffinity();
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                }

            }
        };
        new AlertDialog.Builder(this).setMessage("Discard editing?").setPositiveButton("Ok", r0).setTitle("").setNeutralButton("Cancel", r0).show();
    }



    @Override
    @RequiresApi(api = 19)
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }
}
