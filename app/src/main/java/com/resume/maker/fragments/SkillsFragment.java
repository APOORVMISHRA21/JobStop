package com.resume.maker.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.demo.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.resume.maker.AdAdmob;
import com.resume.maker.SharedPreferences;
import com.resume.maker.adapters.SkillAdapter;
import com.resume.maker.models.SkillModel;

import java.util.List;

public class SkillsFragment extends Fragment {
    public static boolean showback = true;
    Bundle bundle;
    EditText et_skill;
    int flag = 0;
    ImageView ll_save;
    int position;
    String skill;
    SharedPreferences store;
    String strObj;
    String userskill;
    View view;
    ConstraintLayout bottomNavStep;


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.activity_skills, viewGroup, false);
        showback = true;
        AdAdmob adAdmob = new AdAdmob(getActivity());
        adAdmob.FullscreenAd(getActivity());
        this.store = SharedPreferences.getInstance(getActivity(), "resumemaker");
        this.et_skill = (EditText) this.view.findViewById(R.id.et_skill);
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
                SkillsFragment skillsFragment = SkillsFragment.this;
                if (skillsFragment.isEmpty(skillsFragment.et_skill)) {
                    Toast.makeText(SkillsFragment.this.getActivity(), "Please Enter Skill", Toast.LENGTH_SHORT).show();
                    return;
                }
                SkillsFragment skillsFragment2 = SkillsFragment.this;
                skillsFragment2.skill = skillsFragment2.et_skill.getText().toString();
                if (SkillsFragment.this.bundle != null) {
                    if (SkillsFragment.this.store.getString(SkillsFragment.this.getString(R.string.skill1), "").equals(SkillsFragment.this.userskill)) {
                        SkillsFragment.this.store.saveString(SkillsFragment.this.getString(R.string.skill1), SkillsFragment.this.skill);
                        SkillsFragment.showback = false;
                        SkillsFragment.this.getActivity().onBackPressed();


                    }
                    if (SkillsFragment.this.store.getString(SkillsFragment.this.getString(R.string.skill2), "").equals(SkillsFragment.this.userskill)) {
                        SkillsFragment.this.store.saveString(SkillsFragment.this.getString(R.string.skill2), SkillsFragment.this.skill);
                        SkillsFragment.showback = false;
                        SkillsFragment.this.getActivity().onBackPressed();

                    }
                    if (SkillsFragment.this.store.getString(SkillsFragment.this.getString(R.string.skill3), "").equals(SkillsFragment.this.userskill)) {
                        SkillsFragment.this.store.saveString(SkillsFragment.this.getString(R.string.skill3), SkillsFragment.this.skill);
                        SkillsFragment.showback = false;
                        SkillsFragment.this.getActivity().onBackPressed();
                    }
                    if (SkillsFragment.this.store.getString(SkillsFragment.this.getString(R.string.skill4), "").equals(SkillsFragment.this.userskill)) {
                        SkillsFragment.this.store.saveString(SkillsFragment.this.getString(R.string.skill4), SkillsFragment.this.skill);
                        SkillsFragment.showback = false;
                        SkillsFragment.this.getActivity().onBackPressed();
                    }
                    if (SkillsFragment.this.store.getString(SkillsFragment.this.getString(R.string.skill5), "").equals(SkillsFragment.this.userskill)) {
                        SkillsFragment.this.store.saveString(SkillsFragment.this.getString(R.string.skill5), SkillsFragment.this.skill);
                        SkillsFragment.showback = false;
                        SkillsFragment.this.getActivity().onBackPressed();

                    }
                } else if (SkillsFragment.this.store.getString(SkillsFragment.this.getString(R.string.skill1), "").equals("")) {
                    SkillsFragment.this.store.saveString(SkillsFragment.this.getString(R.string.skill1), SkillsFragment.this.skill);
                    SkillsFragment.showback = false;
                    SkillsFragment.this.getActivity().onBackPressed();

                } else if (SkillsFragment.this.store.getString(SkillsFragment.this.getString(R.string.skill2), "").equals("")) {
                    SkillsFragment.this.store.saveString(SkillsFragment.this.getString(R.string.skill2), SkillsFragment.this.skill);
                    SkillsFragment.showback = false;
                    SkillsFragment.this.getActivity().onBackPressed();

                } else if (SkillsFragment.this.store.getString(SkillsFragment.this.getString(R.string.skill3), "").equals("")) {
                    SkillsFragment.this.store.saveString(SkillsFragment.this.getString(R.string.skill3), SkillsFragment.this.skill);
                    SkillsFragment.showback = false;
                    SkillsFragment.this.getActivity().onBackPressed();

                } else if (SkillsFragment.this.store.getString(SkillsFragment.this.getString(R.string.skill4), "").equals("")) {
                    SkillsFragment.this.store.saveString(SkillsFragment.this.getString(R.string.skill4), SkillsFragment.this.skill);
                    SkillsFragment.showback = false;
                    SkillsFragment.this.getActivity().onBackPressed();

                } else if (SkillsFragment.this.store.getString(SkillsFragment.this.getString(R.string.skill5), "").equals("")) {
                    SkillsFragment.this.store.saveString(SkillsFragment.this.getString(R.string.skill5), SkillsFragment.this.skill);
                    SkillsFragment.showback = false;
                    SkillsFragment.this.getActivity().onBackPressed();

                } else {
                    Toast.makeText(SkillsFragment.this.getActivity(), "Sorry, more than 5 details are not allowed", Toast.LENGTH_SHORT).show();
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
            if (this.store.getString(getString(R.string.skill1), "") != null) {
                Log.d("sharedpref>>2", this.store.getString(getString(R.string.skill1), ""));
                this.et_skill.setText(this.store.getString(getString(R.string.skill1), ""));
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
            this.userskill = ((SkillModel) ((List) new Gson().fromJson(this.strObj, new TypeToken<List<SkillModel>>() {
            }.getType())).get(SkillAdapter.selectedposition)).getSkill();
            this.et_skill.setText(this.userskill);
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("sharedpref>>2", "null");
        }
    }
}
