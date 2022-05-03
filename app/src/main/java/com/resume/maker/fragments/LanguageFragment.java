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
import com.resume.maker.SharedPreferences;
import com.resume.maker.adapters.LanguageAdapter;
import com.resume.maker.models.LanguageModel;

import java.util.List;


public class LanguageFragment extends Fragment {
    public static boolean showback = true;
    Bundle bundle;
    EditText et_language;
    int flag = 0;
    String language;
    ImageView ll_save;
    int position;
    SharedPreferences store;
    String strObj;
    String userlanguage;
    View view;
    ConstraintLayout bottomNavStep;


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.activity_language, viewGroup, false);
        showback = true;
        this.store = SharedPreferences.getInstance(getActivity(), "resumemaker");
        this.et_language = (EditText) this.view.findViewById(R.id.et_language);
        this.ll_save = (ImageView)  getActivity().findViewById(R.id.ll_save);
        this.bottomNavStep = (ConstraintLayout) getActivity().findViewById(R.id.bottomNavStep);
        bottomNavStep.setVisibility(View.GONE);
        this.bundle = getArguments();
        if (this.bundle != null) {
            getGsonData();
        }
        this.ll_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LanguageFragment languageFragment = LanguageFragment.this;
                if (languageFragment.isEmpty(languageFragment.et_language)) {
                    Toast.makeText(LanguageFragment.this.getActivity(), "Please Enter Language", Toast.LENGTH_SHORT).show();
                    return;
                }
                LanguageFragment languageFragment2 = LanguageFragment.this;
                languageFragment2.language = languageFragment2.et_language.getText().toString();
                if (LanguageFragment.this.bundle != null) {
                    if (LanguageFragment.this.store.getString(LanguageFragment.this.getString(R.string.language1), "").equals(LanguageFragment.this.userlanguage)) {
                        LanguageFragment.this.store.saveString(LanguageFragment.this.getString(R.string.language1), LanguageFragment.this.language);
                        LanguageFragment.showback = false;
                        LanguageFragment.this.getActivity().onBackPressed();
                    }
                    if (LanguageFragment.this.store.getString(LanguageFragment.this.getString(R.string.language2), "").equals(LanguageFragment.this.userlanguage)) {
                        LanguageFragment.this.store.saveString(LanguageFragment.this.getString(R.string.language2), LanguageFragment.this.language);
                        LanguageFragment.showback = false;
                        LanguageFragment.this.getActivity().onBackPressed();

                    }
                    if (LanguageFragment.this.store.getString(LanguageFragment.this.getString(R.string.language3), "").equals(LanguageFragment.this.userlanguage)) {
                        LanguageFragment.this.store.saveString(LanguageFragment.this.getString(R.string.language3), LanguageFragment.this.language);
                        LanguageFragment.showback = false;
                        LanguageFragment.this.getActivity().onBackPressed();
                    }
                    if (LanguageFragment.this.store.getString(LanguageFragment.this.getString(R.string.language4), "").equals(LanguageFragment.this.userlanguage)) {
                        LanguageFragment.this.store.saveString(LanguageFragment.this.getString(R.string.language4), LanguageFragment.this.language);
                        LanguageFragment.showback = false;
                        LanguageFragment.this.getActivity().onBackPressed();
                    }
                    if (LanguageFragment.this.store.getString(LanguageFragment.this.getString(R.string.language5), "").equals(LanguageFragment.this.userlanguage)) {
                        LanguageFragment.this.store.saveString(LanguageFragment.this.getString(R.string.language5), LanguageFragment.this.language);
                        LanguageFragment.showback = false;
                        LanguageFragment.this.getActivity().onBackPressed();

                    }
                } else if (LanguageFragment.this.store.getString(LanguageFragment.this.getString(R.string.language1), "").equals("")) {
                    LanguageFragment.this.store.saveString(LanguageFragment.this.getString(R.string.language1), LanguageFragment.this.language);
                    LanguageFragment.showback = false;
                    LanguageFragment.this.getActivity().onBackPressed();

                } else if (LanguageFragment.this.store.getString(LanguageFragment.this.getString(R.string.language2), "").equals("")) {
                    LanguageFragment.this.store.saveString(LanguageFragment.this.getString(R.string.language2), LanguageFragment.this.language);
                    LanguageFragment.showback = false;
                    LanguageFragment.this.getActivity().onBackPressed();

                } else if (LanguageFragment.this.store.getString(LanguageFragment.this.getString(R.string.language3), "").equals("")) {
                    LanguageFragment.this.store.saveString(LanguageFragment.this.getString(R.string.language3), LanguageFragment.this.language);
                    LanguageFragment.showback = false;
                    LanguageFragment.this.getActivity().onBackPressed();

                } else if (LanguageFragment.this.store.getString(LanguageFragment.this.getString(R.string.language4), "").equals("")) {
                    LanguageFragment.this.store.saveString(LanguageFragment.this.getString(R.string.language4), LanguageFragment.this.language);
                    LanguageFragment.showback = false;
                    LanguageFragment.this.getActivity().onBackPressed();

                } else if (LanguageFragment.this.store.getString(LanguageFragment.this.getString(R.string.language5), "").equals("")) {
                    LanguageFragment.this.store.saveString(LanguageFragment.this.getString(R.string.language5), LanguageFragment.this.language);
                    LanguageFragment.showback = false;
                    LanguageFragment.this.getActivity().onBackPressed();

                } else {
                    Toast.makeText(LanguageFragment.this.getActivity(), "Sorry, more than 5 details are not allowed", Toast.LENGTH_SHORT).show();
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
            if (this.store.getString(getString(R.string.language1), "") != null) {
                Log.d("sharedpref>>2", this.store.getString(getString(R.string.language1), ""));
                this.userlanguage = this.store.getString(getString(R.string.language1), "");
                this.et_language.setText(this.userlanguage);
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
            this.userlanguage = ((LanguageModel) ((List) new Gson().fromJson(this.strObj, new TypeToken<List<LanguageModel>>() {
            }.getType())).get(LanguageAdapter.selectedposition)).getLanguage();
            this.et_language.setText(this.userlanguage);
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("sharedpref>>2", "null");
        }
    }
}
