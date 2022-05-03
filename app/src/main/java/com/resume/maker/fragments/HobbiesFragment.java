package com.resume.maker.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.demo.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.resume.maker.AdAdmob;
import com.resume.maker.SharedPreferences;
import com.resume.maker.adapters.HobbiesAdapter;
import com.resume.maker.models.HobbiesModel;

import java.util.List;


public class HobbiesFragment extends Fragment {
    public static boolean showback = true;
    Bundle bundle;
    EditText et_hobbies;
    int flag = 0;
    String hobbies;
    ImageView ll_save;
    int position;
    SharedPreferences store;
    String strObj;
    String userhobby;
    View view;
    ConstraintLayout bottomNavStep;


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.activity_hobbies, viewGroup, false);
        showback = true;
        AdAdmob adAdmob = new AdAdmob(getActivity());
        adAdmob.FullscreenAd(getActivity());
        this.store = SharedPreferences.getInstance(getActivity(), "resumemaker");
        this.et_hobbies = (EditText) this.view.findViewById(R.id.et_hobbies);
        this.ll_save = (ImageView) getActivity().findViewById(R.id.ll_save);
        this.bottomNavStep = (ConstraintLayout) getActivity().findViewById(R.id.bottomNavStep);
        bottomNavStep.setVisibility(View.GONE);
        this.bundle = getArguments();
        if (this.bundle != null) {
            getGsonData();
        }
        this.ll_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HobbiesFragment hobbiesFragment = HobbiesFragment.this;
                if (hobbiesFragment.isEmpty(hobbiesFragment.et_hobbies)) {
                    Toast.makeText(HobbiesFragment.this.getActivity(), "Please Enter Hobby", Toast.LENGTH_SHORT).show();
                    return;
                }
                HobbiesFragment hobbiesFragment2 = HobbiesFragment.this;
                hobbiesFragment2.hobbies = hobbiesFragment2.et_hobbies.getText().toString();
                if (HobbiesFragment.this.bundle != null) {
                    if (HobbiesFragment.this.store.getString(HobbiesFragment.this.getString(R.string.hobby1), "").equals(HobbiesFragment.this.userhobby)) {
                        HobbiesFragment.this.store.saveString(HobbiesFragment.this.getString(R.string.hobby1), HobbiesFragment.this.hobbies);
                        HobbiesFragment.showback = false;
                        HobbiesFragment.this.getActivity().onBackPressed();

                    }
                    if (HobbiesFragment.this.store.getString(HobbiesFragment.this.getString(R.string.hobby2), "").equals(HobbiesFragment.this.userhobby)) {
                        HobbiesFragment.this.store.saveString(HobbiesFragment.this.getString(R.string.hobby2), HobbiesFragment.this.hobbies);
                        HobbiesFragment.showback = false;
                        HobbiesFragment.this.getActivity().onBackPressed();
                    }
                    if (HobbiesFragment.this.store.getString(HobbiesFragment.this.getString(R.string.hobby3), "").equals(HobbiesFragment.this.userhobby)) {
                        HobbiesFragment.this.store.saveString(HobbiesFragment.this.getString(R.string.hobby3), HobbiesFragment.this.hobbies);
                        HobbiesFragment.showback = false;
                        HobbiesFragment.this.getActivity().onBackPressed();

                    }
                    if (HobbiesFragment.this.store.getString(HobbiesFragment.this.getString(R.string.hobby4), "").equals(HobbiesFragment.this.userhobby)) {
                        HobbiesFragment.this.store.saveString(HobbiesFragment.this.getString(R.string.hobby4), HobbiesFragment.this.hobbies);
                        HobbiesFragment.showback = false;
                        HobbiesFragment.this.getActivity().onBackPressed();
                    }
                    if (HobbiesFragment.this.store.getString(HobbiesFragment.this.getString(R.string.hobby5), "").equals(HobbiesFragment.this.userhobby)) {
                        HobbiesFragment.this.store.saveString(HobbiesFragment.this.getString(R.string.hobby5), HobbiesFragment.this.hobbies);
                        HobbiesFragment.showback = false;
                        HobbiesFragment.this.getActivity().onBackPressed();

                    }
                } else if (HobbiesFragment.this.store.getString(HobbiesFragment.this.getString(R.string.hobby1), "").equals("")) {
                    HobbiesFragment.this.store.saveString(HobbiesFragment.this.getString(R.string.hobby1), HobbiesFragment.this.hobbies);
                    HobbiesFragment.showback = false;
                    HobbiesFragment.this.getActivity().onBackPressed();

                } else if (HobbiesFragment.this.store.getString(HobbiesFragment.this.getString(R.string.hobby2), "").equals("")) {
                    HobbiesFragment.this.store.saveString(HobbiesFragment.this.getString(R.string.hobby2), HobbiesFragment.this.hobbies);
                    HobbiesFragment.showback = false;
                    HobbiesFragment.this.getActivity().onBackPressed();

                } else if (HobbiesFragment.this.store.getString(HobbiesFragment.this.getString(R.string.hobby3), "").equals("")) {
                    HobbiesFragment.this.store.saveString(HobbiesFragment.this.getString(R.string.hobby3), HobbiesFragment.this.hobbies);
                    HobbiesFragment.showback = false;
                    HobbiesFragment.this.getActivity().onBackPressed();
                } else if (HobbiesFragment.this.store.getString(HobbiesFragment.this.getString(R.string.hobby4), "").equals("")) {
                    HobbiesFragment.this.store.saveString(HobbiesFragment.this.getString(R.string.hobby4), HobbiesFragment.this.hobbies);
                    HobbiesFragment.showback = false;
                    HobbiesFragment.this.getActivity().onBackPressed();

                } else if (HobbiesFragment.this.store.getString(HobbiesFragment.this.getString(R.string.hobby5), "").equals("")) {
                    HobbiesFragment.this.store.saveString(HobbiesFragment.this.getString(R.string.hobby5), HobbiesFragment.this.hobbies);
                    HobbiesFragment.showback = false;
                    HobbiesFragment.this.getActivity().onBackPressed();

                } else {
                    Toast.makeText(HobbiesFragment.this.getActivity(), "Sorry, more than 5 details are not allowed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return this.view;
    }


    public boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    private void getSharedpref() {
        try {
            if (this.store.getString(getString(R.string.hobby1), "") != null) {
                Log.d("sharedpref>>2", this.store.getString(getString(R.string.hobby1), ""));
                this.userhobby = this.store.getString(getString(R.string.hobby1), "");
                this.et_hobbies.setText(this.userhobby);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("sharedpref>>2", "null");
        }
    }

    private void getGsonData() {
        try {
            new Gson();
            this.strObj = this.bundle.getString("obj");
            this.position = this.bundle.getInt("position", 0);
            this.userhobby = ((HobbiesModel) ((List) new Gson().fromJson(this.strObj, new TypeToken<List<HobbiesModel>>() {
            }.getType())).get(HobbiesAdapter.selectedposition)).getHobby();
            this.et_hobbies.setText(this.userhobby);
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("sharedpref>>2", "null");
        }
    }
}
