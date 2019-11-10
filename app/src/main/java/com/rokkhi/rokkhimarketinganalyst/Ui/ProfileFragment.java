package com.rokkhi.rokkhimarketinganalyst.Ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rokkhi.rokkhimarketinganalyst.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    View view;

    CircleImageView circleImageView;
    TextView userName,userAddress,userMail;

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    String userId;
    ProgressBar pro;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        userId=mAuth.getCurrentUser().getUid();
        pro=view.findViewById(R.id.progressBar);
        Wave wave=new Wave();
        pro.setIndeterminateDrawable(wave);

        userName=view.findViewById(R.id.pofile_frag_userName);
        userAddress=view.findViewById(R.id.profile_frag_userAddress);
        userMail=view.findViewById(R.id.profile_frag_mailId);
        circleImageView=view.findViewById(R.id.profile_frag_profilePic);
        showCurrentUserInfo();
    }

    private void showCurrentUserInfo() {

        db.collection("f_workers").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                pro.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();

                    if (documentSnapshot!=null && documentSnapshot.exists()){
                        String name=documentSnapshot.getString("fw_name");
                        String address=documentSnapshot.getString("fw_address");
                        String mail=documentSnapshot.getString("fw_mail");
                        String imageurl=documentSnapshot.getString("fw_imageUrl");

                        userName.setText(name);
                        userAddress.setText(address);
                        userMail.setText(mail);
                        Glide.with(getContext()).load(imageurl).into(circleImageView);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}
