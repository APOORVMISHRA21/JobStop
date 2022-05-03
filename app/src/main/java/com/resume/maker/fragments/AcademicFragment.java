package com.resume.maker.fragments;

import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.demo.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.resume.maker.AdAdmob;
import com.resume.maker.SharedPreferences;
import com.resume.maker.adapters.AcademicAdapter;
import com.resume.maker.models.AcademicModel;

import java.util.List;


public class AcademicFragment extends Fragment {
    public static boolean showback = true;
    Bundle bundle;
    String degree;
    EditText et_Degree;
    EditText et_Institute;
    EditText et_Marks;
    EditText et_Year;
    int flag = 0;
    String institute;
    ImageView ll_save;
    String marks;
    int position;
    RadioGroup radiogp1;
    RadioGroup radiogp2;
    RadioButton rb_CGPA;
    RadioButton rb_Graduated;
    RadioButton rb_Percentage;
    RadioButton rb_Pursuing;
    SharedPreferences store;
    String strObj;
    String userdegree;
    String userinstitute;
    String userpercgpa;
    String userperradio;
    String useryear;
    String useryearradio;
    View view;
    String year;
    ConstraintLayout bottomNavStep;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.activity_academic, viewGroup, false);


        AdAdmob adAdmob = new AdAdmob(getActivity());
        adAdmob.BannerAd((RelativeLayout) view.findViewById(R.id.bannerAd), getActivity());
        adAdmob.FullscreenAd(getActivity());


        showback = true;
        this.store = SharedPreferences.getInstance(getActivity(), "resumemaker");
        this.et_Degree = (EditText) this.view.findViewById(R.id.et_Degree);
        this.et_Institute = (EditText) this.view.findViewById(R.id.et_Institute);
        this.et_Marks = (EditText) this.view.findViewById(R.id.et_Marks);
        this.et_Year = (EditText) this.view.findViewById(R.id.et_Year);
        this.ll_save = (ImageView) getActivity().findViewById(R.id.ll_save);

        this.radiogp1 = (RadioGroup) this.view.findViewById(R.id.radiogp1);
        this.radiogp2 = (RadioGroup) this.view.findViewById(R.id.radiogp2);
        this.rb_Percentage = (RadioButton) this.view.findViewById(R.id.rb_Percentage);
        this.rb_CGPA = (RadioButton) this.view.findViewById(R.id.rb_CGPA);
        this.rb_Graduated = (RadioButton) this.view.findViewById(R.id.rb_Graduated);
        this.rb_Pursuing = (RadioButton) this.view.findViewById(R.id.rb_Pursuing);
        this.bottomNavStep = (ConstraintLayout) getActivity().findViewById(R.id.bottomNavStep);
        bottomNavStep.setVisibility(View.GONE);
        this.radiogp2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == AcademicFragment.this.rb_Graduated.getId()) {
                    AcademicFragment.this.et_Year.setEnabled(true);
                    AcademicFragment.this.et_Year.setClickable(true);
                    AcademicFragment.this.et_Year.setHint("Year");
                    AcademicFragment.this.et_Year.setText("");
                    AcademicFragment.this.et_Year.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
                    return;
                }
                AcademicFragment.this.et_Year.setEnabled(false);
                AcademicFragment.this.et_Year.setClickable(true);
                AcademicFragment.this.et_Year.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                AcademicFragment.this.et_Year.setText("Pursuing");
            }
        });
        this.bundle = getArguments();
        if (this.bundle != null) {
            getGsonData();
        }
        this.ll_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton radioButton = (RadioButton) AcademicFragment.this.radiogp1.findViewById(AcademicFragment.this.radiogp1.getCheckedRadioButtonId());
                AcademicFragment academicFragment = AcademicFragment.this;
                if (academicFragment.isEmpty(academicFragment.et_Degree)) {
                    Toast.makeText(AcademicFragment.this.getActivity(), "Please Enter Degree Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                AcademicFragment academicFragment2 = AcademicFragment.this;
                if (academicFragment2.isEmpty(academicFragment2.et_Institute)) {
                    Toast.makeText(AcademicFragment.this.getActivity(), "Please Enter Institute Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                AcademicFragment academicFragment3 = AcademicFragment.this;
                if (academicFragment3.isEmpty(academicFragment3.et_Marks)) {
                    Toast.makeText(AcademicFragment.this.getActivity(), "Please Enter Percentage/CGPA", Toast.LENGTH_SHORT).show();
                    return;
                }
                AcademicFragment academicFragment4 = AcademicFragment.this;
                if (academicFragment4.isEmpty(academicFragment4.et_Year)) {
                    Toast.makeText(AcademicFragment.this.getActivity(), "Please Enter Year", Toast.LENGTH_SHORT).show();
                } else if (radioButton.getText().toString().equals("Percentage") && (Float.valueOf(AcademicFragment.this.et_Marks.getText().toString()).floatValue() <= 1.0f || Float.valueOf(AcademicFragment.this.et_Marks.getText().toString()).floatValue() >= 101.0f)) {
                    Toast.makeText(AcademicFragment.this.getActivity(), "please enter valid percentage", Toast.LENGTH_SHORT).show();
                } else if (!radioButton.getText().toString().equals("CGPA") || (Float.valueOf(AcademicFragment.this.et_Marks.getText().toString()).floatValue() > 1.0f && Float.valueOf(AcademicFragment.this.et_Marks.getText().toString()).floatValue() < 11.0f)) {
                    AcademicFragment academicFragment5 = AcademicFragment.this;
                    academicFragment5.degree = academicFragment5.et_Degree.getText().toString();
                    AcademicFragment academicFragment6 = AcademicFragment.this;
                    academicFragment6.institute = academicFragment6.et_Institute.getText().toString();
                    AcademicFragment academicFragment7 = AcademicFragment.this;
                    academicFragment7.marks = academicFragment7.et_Marks.getText().toString();
                    AcademicFragment academicFragment8 = AcademicFragment.this;
                    academicFragment8.year = academicFragment8.et_Year.getText().toString();
                    int checkedRadioButtonId = AcademicFragment.this.radiogp1.getCheckedRadioButtonId();
                    int checkedRadioButtonId2 = AcademicFragment.this.radiogp2.getCheckedRadioButtonId();
                    RadioButton radioButton2 = (RadioButton) AcademicFragment.this.radiogp1.findViewById(checkedRadioButtonId);
                    RadioButton radioButton3 = (RadioButton) AcademicFragment.this.radiogp2.findViewById(checkedRadioButtonId2);
                    if (AcademicFragment.this.bundle != null) {
                        if (AcademicFragment.this.store.getString(AcademicFragment.this.getString(R.string.academic_degree1), "").equals(AcademicFragment.this.userdegree)) {
                            AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_degree1), AcademicFragment.this.degree);
                            AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_institute1), AcademicFragment.this.institute);
                            AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_percgpa1), AcademicFragment.this.marks);
                            AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_year1), AcademicFragment.this.year);
                            AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_percgpa1_radio), radioButton2.getText().toString());
                            AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_year1_radio), radioButton3.getText().toString());
                            Log.d("academic>>1", "academic>>1" + AcademicAdapter.selectedposition);
                            AcademicFragment.showback = false;
                            AcademicFragment.this.getActivity().onBackPressed();
                        }
                        if (AcademicFragment.this.store.getString(AcademicFragment.this.getString(R.string.academic_degree2), "").equals(AcademicFragment.this.userdegree)) {
                            AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_degree2), AcademicFragment.this.degree);
                            AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_institute2), AcademicFragment.this.institute);
                            AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_percgpa2), AcademicFragment.this.marks);
                            AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_year2), AcademicFragment.this.year);
                            AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_percgpa2_radio), radioButton2.getText().toString());
                            AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_year2_radio), radioButton3.getText().toString());
                            Log.d("academic>>2", "academic>>2");
                            AcademicFragment.showback = false;
                            AcademicFragment.this.getActivity().onBackPressed();
                        }
                        if (AcademicFragment.this.store.getString(AcademicFragment.this.getString(R.string.academic_degree3), "").equals(AcademicFragment.this.userdegree)) {
                            AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_degree3), AcademicFragment.this.degree);
                            AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_institute3), AcademicFragment.this.institute);
                            AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_percgpa3), AcademicFragment.this.marks);
                            AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_year3), AcademicFragment.this.year);
                            AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_percgpa3_radio), radioButton2.getText().toString());
                            AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_year3_radio), radioButton3.getText().toString());
                            Log.d("academic>>3", "academic>>3");
                            AcademicFragment.showback = false;
                            AcademicFragment.this.getActivity().onBackPressed();

                        }
                    } else if (AcademicFragment.this.store.getString(AcademicFragment.this.getString(R.string.academic_degree1), "").equals("")) {
                        AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_degree1), AcademicFragment.this.degree);
                        AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_institute1), AcademicFragment.this.institute);
                        AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_percgpa1), AcademicFragment.this.marks);
                        AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_year1), AcademicFragment.this.year);
                        AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_percgpa1_radio), radioButton2.getText().toString());
                        AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_year1_radio), radioButton3.getText().toString());
                        Log.d("academic>>1", "academic>>1" + AcademicAdapter.selectedposition);
                        AcademicFragment.showback = false;
                        AcademicFragment.this.getActivity().onBackPressed();
                    } else if (AcademicFragment.this.store.getString(AcademicFragment.this.getString(R.string.academic_degree2), "").equals("")) {
                        AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_degree2), AcademicFragment.this.degree);
                        AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_institute2), AcademicFragment.this.institute);
                        AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_percgpa2), AcademicFragment.this.marks);
                        AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_year2), AcademicFragment.this.year);
                        AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_percgpa2_radio), radioButton2.getText().toString());
                        AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_year2_radio), radioButton3.getText().toString());
                        Log.d("academic>>2", "academic>>2");
                        AcademicFragment.showback = false;
                        AcademicFragment.this.getActivity().onBackPressed();

                    } else if (AcademicFragment.this.store.getString(AcademicFragment.this.getString(R.string.academic_degree3), "").equals("")) {
                        AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_degree3), AcademicFragment.this.degree);
                        AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_institute3), AcademicFragment.this.institute);
                        AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_percgpa3), AcademicFragment.this.marks);
                        AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_year3), AcademicFragment.this.year);
                        AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_percgpa3_radio), radioButton2.getText().toString());
                        AcademicFragment.this.store.saveString(AcademicFragment.this.getString(R.string.academic_year3_radio), radioButton3.getText().toString());
                        Log.d("academic>>3", "academic>>3");
                        AcademicFragment.showback = false;
                        AcademicFragment.this.getActivity().onBackPressed();

                    } else {
                        Toast.makeText(AcademicFragment.this.getActivity(), "Sorry, more than 3 details are not allowed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AcademicFragment.this.getActivity(), "please enter valid CGPA", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return this.view;
    }


    public boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    private boolean isEmpty(TextView textView) {
        return textView.getText().toString().trim().length() == 0;
    }

    private void getSharedpref() {
        try {
            if (this.store.getString(getString(R.string.academic_degree1), "") != null) {
                Log.d("sharedpref>>2", this.store.getString(getString(R.string.academic_degree1), ""));
                String string = this.store.getString(getString(R.string.academic_degree1), "");
                String string2 = this.store.getString(getString(R.string.academic_institute1), "");
                String string3 = this.store.getString(getString(R.string.academic_percgpa1), "");
                String string4 = this.store.getString(getString(R.string.academic_year1), "");
                String string5 = this.store.getString(getString(R.string.academic_percgpa1_radio), "");
                String string6 = this.store.getString(getString(R.string.academic_year1_radio), "");
                this.et_Degree.setText(string);
                this.et_Institute.setText(string2);
                this.et_Marks.setText(string3);
                this.et_Year.setText(string4);
                if (string5.equals("Percentage")) {
                    this.rb_Percentage.setChecked(true);
                } else {
                    this.rb_CGPA.setChecked(true);
                }
                if (string6.equals("Graduated")) {
                    this.rb_Graduated.setChecked(true);
                } else {
                    this.rb_Pursuing.setChecked(true);
                }
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
            List list = (List) new Gson().fromJson(this.strObj, new TypeToken<List<AcademicModel>>() {
            }.getType());
            this.userdegree = ((AcademicModel) list.get(AcademicAdapter.selectedposition)).getDegree();
            this.userinstitute = ((AcademicModel) list.get(AcademicAdapter.selectedposition)).getInstitute();
            this.userpercgpa = ((AcademicModel) list.get(AcademicAdapter.selectedposition)).getPercgpa();
            this.useryear = ((AcademicModel) list.get(AcademicAdapter.selectedposition)).getYear();
            this.userperradio = ((AcademicModel) list.get(AcademicAdapter.selectedposition)).getPerradio();
            this.useryearradio = ((AcademicModel) list.get(AcademicAdapter.selectedposition)).getYearradio();
            this.et_Degree.setText(this.userdegree);
            this.et_Institute.setText(this.userinstitute);
            this.et_Marks.setText(this.userpercgpa);
            this.et_Year.setText(this.useryear);
            if (this.userperradio.equals("Percentage")) {
                this.rb_Percentage.setChecked(true);
            } else {
                this.rb_CGPA.setChecked(true);
            }
            if (this.useryearradio.equals("Graduated")) {
                this.rb_Graduated.setChecked(true);
                this.et_Year.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
                return;
            }
            this.rb_Pursuing.setChecked(true);
            this.et_Year.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("sharedpref>>2", "null");
        }
    }
}
