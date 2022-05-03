package com.resume.maker.fragments;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.demo.R;
import com.resume.maker.SharedPreferences;

import java.util.regex.Pattern;


public class ReferenceFragment extends Fragment {
    String desig;
    EditText et_rDesignation;
    EditText et_rMail;
    EditText et_rName;
    EditText et_rOrganization;
    EditText et_rPhone;
    ImageView ll_save;
    String mail;
    String name;
    String orgnm;
    String phone;
    SharedPreferences store;
    View view;
    ConstraintLayout bottomNavStep;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.activity_references, viewGroup, false);
        this.store = SharedPreferences.getInstance(getActivity(), "resumemaker");
        this.et_rName = (EditText) this.view.findViewById(R.id.et_rName);
        this.et_rMail = (EditText) this.view.findViewById(R.id.et_rMail);
        this.et_rPhone = (EditText) this.view.findViewById(R.id.et_rPhone);
        this.et_rOrganization = (EditText) this.view.findViewById(R.id.et_rOrganization);
        this.et_rDesignation = (EditText) this.view.findViewById(R.id.et_rDesignation);
        this.bottomNavStep = (ConstraintLayout) getActivity().findViewById(R.id.bottomNavStep);
        bottomNavStep.setVisibility(View.GONE);
        this.ll_save = (ImageView) this.view.findViewById(R.id.ll_save);
        getSharedpref();
        this.ll_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReferenceFragment referenceFragment = ReferenceFragment.this;
                if (referenceFragment.isEmpty(referenceFragment.et_rName)) {
                    Toast.makeText(ReferenceFragment.this.getActivity(), "Please Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                ReferenceFragment referenceFragment2 = ReferenceFragment.this;
                if (referenceFragment2.isEmpty(referenceFragment2.et_rMail)) {
                    Toast.makeText(ReferenceFragment.this.getActivity(), "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                ReferenceFragment referenceFragment3 = ReferenceFragment.this;
                if (referenceFragment3.isEmpty(referenceFragment3.et_rPhone)) {
                    Toast.makeText(ReferenceFragment.this.getActivity(), "Please Enter Contact", Toast.LENGTH_SHORT).show();
                    return;
                }
                ReferenceFragment referenceFragment4 = ReferenceFragment.this;
                if (referenceFragment4.isEmpty(referenceFragment4.et_rOrganization)) {
                    Toast.makeText(ReferenceFragment.this.getActivity(), "Please Enter Organization", Toast.LENGTH_SHORT).show();
                    return;
                }
                ReferenceFragment referenceFragment5 = ReferenceFragment.this;
                if (referenceFragment5.isEmpty(referenceFragment5.et_rDesignation)) {
                    Toast.makeText(ReferenceFragment.this.getActivity(), "Please Enter Designation", Toast.LENGTH_SHORT).show();
                    return;
                }
                ReferenceFragment referenceFragment6 = ReferenceFragment.this;
                if (!referenceFragment6.isValidMobile(referenceFragment6.et_rPhone.getText().toString())) {
                    Toast.makeText(ReferenceFragment.this.getActivity(), "Please Enter valid Phone Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                ReferenceFragment referenceFragment7 = ReferenceFragment.this;
                if (!referenceFragment7.isValidMail(referenceFragment7.et_rMail.getText().toString())) {
                    Toast.makeText(ReferenceFragment.this.getActivity(), "Please Enter valid Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                ReferenceFragment referenceFragment8 = ReferenceFragment.this;
                referenceFragment8.name = referenceFragment8.et_rName.getText().toString();
                ReferenceFragment referenceFragment9 = ReferenceFragment.this;
                referenceFragment9.mail = referenceFragment9.et_rMail.getText().toString();
                ReferenceFragment referenceFragment10 = ReferenceFragment.this;
                referenceFragment10.phone = referenceFragment10.et_rPhone.getText().toString();
                ReferenceFragment referenceFragment11 = ReferenceFragment.this;
                referenceFragment11.orgnm = referenceFragment11.et_rOrganization.getText().toString();
                ReferenceFragment referenceFragment12 = ReferenceFragment.this;
                referenceFragment12.desig = referenceFragment12.et_rDesignation.getText().toString();
                ReferenceFragment.this.store.saveString(ReferenceFragment.this.getString(R.string.reference_name1), ReferenceFragment.this.name);
                ReferenceFragment.this.store.saveString(ReferenceFragment.this.getString(R.string.reference_mail1), ReferenceFragment.this.mail);
                ReferenceFragment.this.store.saveString(ReferenceFragment.this.getString(R.string.reference_contact1), ReferenceFragment.this.phone);
                ReferenceFragment.this.store.saveString(ReferenceFragment.this.getString(R.string.reference_orgnm1), ReferenceFragment.this.orgnm);
                ReferenceFragment.this.store.saveString(ReferenceFragment.this.getString(R.string.reference_desig1), ReferenceFragment.this.desig);
                ReferenceFragment.this.getActivity().finish();
            }
        });
        return this.view;
    }


    public boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    private void getSharedpref() {
        try {
            if (this.store.getString(getString(R.string.reference_name1), "") != null) {
                Log.d("sharedpref>>2", this.store.getString(getString(R.string.reference_name1), ""));
                String string = this.store.getString(getString(R.string.reference_name1), "");
                String string2 = this.store.getString(getString(R.string.reference_mail1), "");
                String string3 = this.store.getString(getString(R.string.reference_contact1), "");
                String string4 = this.store.getString(getString(R.string.reference_orgnm1), "");
                String string5 = this.store.getString(getString(R.string.reference_desig1), "");
                this.et_rName.setText(string);
                this.et_rMail.setText(string2);
                this.et_rPhone.setText(string3);
                this.et_rOrganization.setText(string4);
                this.et_rDesignation.setText(string5);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("sharedpref>>2", "null");
        }
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
}
