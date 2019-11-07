package com.rokkhi.rokkhimarketinganalyst.Ui;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.storage.StorageReference;
import com.rokkhi.rokkhimarketinganalyst.R;
import com.rokkhi.rokkhimarketinganalyst.Utils.Normalfunc;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateProfileFragment extends Fragment {

    View view;
    CircleImageView f_photo;
    EditText fworker_name,fworker_phone,fworker_address,fworker_bloddgrp,fworker_nid,fworker_dob,
             fworker_uni,fworker_college,fworker_reffered;

    ImageView calendarImage;
    Button createProfilebtn;

    FirebaseFirestore db;
    StorageReference storageRef;
    FirebaseAuth mAuth;
    FirebaseUser fuser;
    DatePickerDialog datePickerDialog;
    ProgressBar progressBar;

   // FirebaseFunctions firebaseFunctions;
    FirebaseFirestore firebaseFirestore;
    Context context;
    Normalfunc normalfunc;
    String ref_id="";

    public CreateProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_create_profile, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        normalfunc=new Normalfunc(getContext());

        f_photo=view.findViewById(R.id.fworker_photo);
        fworker_name=view.findViewById(R.id.fworker_name_edit);
        fworker_phone=view.findViewById(R.id.fworker_phone_edit);
        fworker_address=view.findViewById(R.id.fworker_address_edit);
        fworker_bloddgrp=view.findViewById(R.id.fworker_bloodgrp_edit);
        fworker_nid=view.findViewById(R.id.fworker_nid_edit);
        fworker_dob=view.findViewById(R.id.fworker_bday_edit);
        fworker_uni=view.findViewById(R.id.fworker_uni_edit);
        fworker_college=view.findViewById(R.id.fworker_college_edit);
        fworker_reffered=view.findViewById(R.id.fworker_reffered_edit);
        calendarImage=view.findViewById(R.id.calendar);
        createProfilebtn=view.findViewById(R.id.create_profilebtn);
        progressBar=view.findViewById(R.id.progressbar);

        calendarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                fworker_dob.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        createProfilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                saveWorkerInfoToDB();
            }
        });



    }



    private void saveWorkerInfoToDB() {

        String f_name=fworker_name.getText().toString();
        String f_phn=fworker_phone.getText().toString();
        String f_address=fworker_address.getText().toString();
        String f_blood=fworker_bloddgrp.getText().toString();

        String f_nid=fworker_nid.getText().toString();
        String f_dob=fworker_dob.getText().toString();
        String f_uni=fworker_uni.getText().toString();
        String f_college=fworker_college.getText().toString();
        String f_refferred=fworker_reffered.getText().toString();

        List<String> f_phone=normalfunc.splitstring(f_phn);

        Map<String,Object> f_workers=new HashMap<>();
        f_workers.put("f_name",f_name);
        f_workers.put("f_phn",f_phone);
        f_workers.put("f_address",f_address);
        f_workers.put("f_blood",f_blood);
        f_workers.put("f_nid",f_nid);
        f_workers.put("f_dob",f_dob);
        f_workers.put("f_uni",f_uni);
        f_workers.put("f_college",f_college);
        f_workers.put("f_referred",f_refferred);
        f_workers.put("ref_id",ref_id);


        db.collection("fworkers").add(f_workers).
                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(),"DataSaved Successfully",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(),"Error:"+e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });


    }


}
