package com.resume.maker.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
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
import com.resume.maker.adapters.ExperienceAdapter;
import com.resume.maker.models.ExperienceModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class ExperienceFragment extends Fragment {
    public static boolean showback = true;
    Bundle bundle;
    String designaion;
    EditText et_Designaion;
    EditText et_Organization;
    EditText et_Role;
    int flag = 0;
    String fromdate;
    ImageView ll_save;
    String organization;
    int position;
    RadioGroup radiogpexp;
    RadioButton rb_Employed;
    RadioButton rb_Employee;
    String role;
    SharedPreferences store;
    String strObj;
    String todate;
    TextView tv_FromDate;
    TextView tv_toDate;
    String userdesignation;
    String userempradio;
    String userfromtime;
    String userorganization;
    String userrole;
    String usertotime;
    View view;
    ConstraintLayout bottomNavStep;



    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.activity_experience, viewGroup, false);




        AdAdmob adAdmob = new AdAdmob(getActivity());
        adAdmob.BannerAd((RelativeLayout) view.findViewById(R.id.bannerAd), getActivity());


        showback = true;
        this.store = SharedPreferences.getInstance(getActivity(), "resumemaker");
        this.et_Organization = (EditText) this.view.findViewById(R.id.et_Organization);
        this.et_Designaion = (EditText) this.view.findViewById(R.id.et_Designaion);
        this.tv_FromDate = (TextView) this.view.findViewById(R.id.tv_FromDate);
        this.tv_toDate = (TextView) this.view.findViewById(R.id.tv_toDate);
        this.radiogpexp = (RadioGroup) this.view.findViewById(R.id.radiogpexp);
        this.rb_Employed = (RadioButton) this.view.findViewById(R.id.rb_Employed);
        this.rb_Employee = (RadioButton) this.view.findViewById(R.id.rb_Employee);
        this.et_Role = (EditText) this.view.findViewById(R.id.et_Role);
        this.ll_save = (ImageView) getActivity().findViewById(R.id.ll_save);
        this.bottomNavStep = (ConstraintLayout) getActivity().findViewById(R.id.bottomNavStep);
        bottomNavStep.setVisibility(View.GONE);
        this.bundle = getArguments();
        if (this.bundle != null) {
            getGsonData();
        }
        this.tv_FromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExperienceFragment experienceFragment = ExperienceFragment.this;
                experienceFragment.setDate(view, experienceFragment.tv_FromDate);
            }
        });
        this.tv_toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExperienceFragment experienceFragment = ExperienceFragment.this;
                experienceFragment.setDate(view, experienceFragment.tv_toDate);
            }
        });
        this.radiogpexp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == ExperienceFragment.this.rb_Employed.getId()) {
                    ExperienceFragment.this.tv_toDate.setClickable(true);
                    ExperienceFragment.this.tv_toDate.setEnabled(true);
                    ExperienceFragment.this.tv_toDate.setHint("To time");
                    ExperienceFragment.this.tv_toDate.setText("");
                    return;
                }
                Log.d("radioid>>2", i + "");
                ExperienceFragment.this.tv_toDate.setClickable(false);
                ExperienceFragment.this.tv_toDate.setEnabled(false);
                ExperienceFragment.this.tv_toDate.setText("Continue");
            }
        });
        this.ll_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExperienceFragment experienceFragment = ExperienceFragment.this;
                if (experienceFragment.isEmpty(experienceFragment.et_Organization)) {
                    Toast.makeText(ExperienceFragment.this.getActivity(), "Please Enter Organization", Toast.LENGTH_SHORT).show();
                    return;
                }
                ExperienceFragment experienceFragment2 = ExperienceFragment.this;
                if (experienceFragment2.isEmpty(experienceFragment2.et_Designaion)) {
                    Toast.makeText(ExperienceFragment.this.getActivity(), "Please Enter Designaion", Toast.LENGTH_SHORT).show();
                    return;
                }
                ExperienceFragment experienceFragment3 = ExperienceFragment.this;
                if (experienceFragment3.isEmpty(experienceFragment3.et_Role)) {
                    Toast.makeText(ExperienceFragment.this.getActivity(), "Please Enter Role", Toast.LENGTH_SHORT).show();
                    return;
                }
                ExperienceFragment experienceFragment4 = ExperienceFragment.this;
                if (experienceFragment4.isEmpty(experienceFragment4.tv_FromDate)) {
                    Toast.makeText(ExperienceFragment.this.getActivity(), "Please Select From Time", Toast.LENGTH_SHORT).show();
                    return;
                }
                ExperienceFragment experienceFragment5 = ExperienceFragment.this;
                if (experienceFragment5.isEmpty(experienceFragment5.tv_toDate)) {
                    Toast.makeText(ExperienceFragment.this.getActivity(), "Please Select To Time", Toast.LENGTH_SHORT).show();
                    return;
                }
                ExperienceFragment experienceFragment6 = ExperienceFragment.this;
                experienceFragment6.organization = experienceFragment6.et_Organization.getText().toString();
                ExperienceFragment experienceFragment7 = ExperienceFragment.this;
                experienceFragment7.designaion = experienceFragment7.et_Designaion.getText().toString();
                ExperienceFragment experienceFragment8 = ExperienceFragment.this;
                experienceFragment8.role = experienceFragment8.et_Role.getText().toString();
                ExperienceFragment experienceFragment9 = ExperienceFragment.this;
                experienceFragment9.fromdate = experienceFragment9.tv_FromDate.getText().toString();
                ExperienceFragment experienceFragment10 = ExperienceFragment.this;
                experienceFragment10.todate = experienceFragment10.tv_toDate.getText().toString();
                RadioButton radioButton = (RadioButton) ExperienceFragment.this.radiogpexp.findViewById(ExperienceFragment.this.radiogpexp.getCheckedRadioButtonId());
                if (ExperienceFragment.this.bundle != null) {
                    if (ExperienceFragment.this.store.getString(ExperienceFragment.this.getString(R.string.experience_organization1), "").equals(ExperienceFragment.this.userorganization)) {
                        ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_organization1), ExperienceFragment.this.organization);
                        ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_designation1), ExperienceFragment.this.designaion);
                        ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_role1), ExperienceFragment.this.role);
                        ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_fromdate1), ExperienceFragment.this.fromdate);
                        ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_todate1), ExperienceFragment.this.todate);
                        ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_pre_cur1_radio), radioButton.getText().toString());
                            ExperienceFragment.showback = false;
                            ExperienceFragment.this.getActivity().onBackPressed();
                    }
                    if (ExperienceFragment.this.store.getString(ExperienceFragment.this.getString(R.string.experience_organization2), "").equals(ExperienceFragment.this.userorganization)) {
                        ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_organization2), ExperienceFragment.this.organization);
                        ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_designation2), ExperienceFragment.this.designaion);
                        ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_role2), ExperienceFragment.this.role);
                        ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_fromdate2), ExperienceFragment.this.fromdate);
                        ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_todate2), ExperienceFragment.this.todate);
                        ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_pre_cur2_radio), radioButton.getText().toString());
                            ExperienceFragment.showback = false;
                            ExperienceFragment.this.getActivity().onBackPressed();
                    }
                    if (ExperienceFragment.this.store.getString(ExperienceFragment.this.getString(R.string.experience_organization3), "").equals(ExperienceFragment.this.userorganization)) {
                        ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_organization3), ExperienceFragment.this.organization);
                        ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_designation3), ExperienceFragment.this.designaion);
                        ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_role3), ExperienceFragment.this.role);
                        ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_fromdate3), ExperienceFragment.this.fromdate);
                        ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_todate3), ExperienceFragment.this.todate);
                        ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_pre_cur3_radio), radioButton.getText().toString());
                            ExperienceFragment.showback = false;
                            ExperienceFragment.this.getActivity().onBackPressed();

                    }
                } else if (ExperienceFragment.this.store.getString(ExperienceFragment.this.getString(R.string.experience_organization1), "").equals("")) {
                    ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_organization1), ExperienceFragment.this.organization);
                    ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_designation1), ExperienceFragment.this.designaion);
                    ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_role1), ExperienceFragment.this.role);
                    ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_fromdate1), ExperienceFragment.this.fromdate);
                    ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_todate1), ExperienceFragment.this.todate);
                    ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_pre_cur1_radio), radioButton.getText().toString());
                        ExperienceFragment.showback = false;
                        ExperienceFragment.this.getActivity().onBackPressed();

                } else if (ExperienceFragment.this.store.getString(ExperienceFragment.this.getString(R.string.experience_organization2), "").equals("")) {
                    ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_organization2), ExperienceFragment.this.organization);
                    ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_designation2), ExperienceFragment.this.designaion);
                    ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_role2), ExperienceFragment.this.role);
                    ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_fromdate2), ExperienceFragment.this.fromdate);
                    ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_todate2), ExperienceFragment.this.todate);
                    ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_pre_cur2_radio), radioButton.getText().toString());
                        ExperienceFragment.showback = false;
                        ExperienceFragment.this.getActivity().onBackPressed();

                } else if (ExperienceFragment.this.store.getString(ExperienceFragment.this.getString(R.string.experience_organization3), "").equals("")) {
                    ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_organization3), ExperienceFragment.this.organization);
                    ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_designation3), ExperienceFragment.this.designaion);
                    ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_role3), ExperienceFragment.this.role);
                    ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_fromdate3), ExperienceFragment.this.fromdate);
                    ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_todate3), ExperienceFragment.this.todate);
                    ExperienceFragment.this.store.saveString(ExperienceFragment.this.getString(R.string.experience_pre_cur3_radio), radioButton.getText().toString());
                        ExperienceFragment.showback = false;
                        ExperienceFragment.this.getActivity().onBackPressed();
                        return;

                } else {
                    Toast.makeText(ExperienceFragment.this.getActivity(), "Sorry, more than 3 details are not allowed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return this.view;
    }

    public static boolean CheckDates(String str, String str2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            if (!simpleDateFormat.parse(str).before(simpleDateFormat.parse(str2))) {
                if (!simpleDateFormat.parse(str).equals(simpleDateFormat.parse(str2))) {
                    return false;
                }
            }
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setDate(View view, final TextView textView) {
        Calendar instance = Calendar.getInstance();
        new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                TextView textView2 = textView;
                textView2.setText(i3 + "-" + (i2 + 1) + "-" + i);
            }
        }, instance.get(1), instance.get(2), instance.get(5)).show();
    }


    public boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }


    public boolean isEmpty(TextView textView) {
        return textView.getText().toString().trim().length() == 0;
    }

    private void getSharedpref() {
        try {
            if (this.store.getString(getString(R.string.experience_organization1), "") != null) {
                Log.d("sharedpref>>2", this.store.getString(getString(R.string.experience_organization1), ""));
                String string = this.store.getString(getString(R.string.experience_organization1), "");
                String string2 = this.store.getString(getString(R.string.experience_designation1), "");
                String string3 = this.store.getString(getString(R.string.experience_role1), "");
                String string4 = this.store.getString(getString(R.string.experience_fromdate1), "");
                String string5 = this.store.getString(getString(R.string.experience_todate1), "");
                String string6 = this.store.getString(getString(R.string.experience_pre_cur1_radio), "");
                this.et_Organization.setText(string);
                this.et_Designaion.setText(string2);
                this.et_Role.setText(string3);
                this.tv_FromDate.setText(string4);
                this.tv_toDate.setText(string5);
                if (string6.equals("Previously employed")) {
                    this.rb_Employed.setChecked(true);
                } else {
                    this.rb_Employee.setChecked(true);
                }
            }
        } catch (Exception e) {
            while (true) {
                e.printStackTrace();
                Log.v("sharedpref>>2", "null");
                return;
            }
        }
    }

    private void getGsonData() {
        try {
            new Gson();
            this.strObj = this.bundle.getString("obj");
            this.position = this.bundle.getInt("position", 0);
            List list = (List) new Gson().fromJson(this.strObj, new TypeToken<List<ExperienceModel>>() {
            }.getType());
            this.userorganization = ((ExperienceModel) list.get(ExperienceAdapter.selectedposition)).getOrganization();
            this.userdesignation = ((ExperienceModel) list.get(ExperienceAdapter.selectedposition)).getDesignation();
            this.userrole = ((ExperienceModel) list.get(ExperienceAdapter.selectedposition)).getRole();
            this.userfromtime = ((ExperienceModel) list.get(ExperienceAdapter.selectedposition)).getFromtime();
            this.usertotime = ((ExperienceModel) list.get(ExperienceAdapter.selectedposition)).getTotime();
            this.userempradio = ((ExperienceModel) list.get(ExperienceAdapter.selectedposition)).getEmpradio();
            this.et_Organization.setText(this.userorganization);
            this.et_Designaion.setText(this.userdesignation);
            this.et_Role.setText(this.userrole);
            this.tv_FromDate.setText(this.userfromtime);
            this.tv_toDate.setText(this.usertotime);
            if (this.userempradio.equals("Previously employed")) {
                this.rb_Employed.setChecked(true);
                this.tv_toDate.setClickable(true);
                this.tv_toDate.setEnabled(true);
                return;
            }
            this.rb_Employee.setChecked(true);
            this.tv_toDate.setClickable(false);
            this.tv_toDate.setEnabled(false);
        } catch (Exception e) {
            while (true) {
                e.printStackTrace();
                Log.v("sharedpref>>2", "null");
                return;
            }
        }
    }
}
