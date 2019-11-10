package com.rokkhi.rokkhimarketinganalyst.Ui;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.rokkhi.rokkhimarketinganalyst.Model.BuildingsListAdapter;
import com.rokkhi.rokkhimarketinganalyst.Model.FBuildings;
import com.rokkhi.rokkhimarketinganalyst.Model.Workers;
import com.rokkhi.rokkhimarketinganalyst.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyhomeFragment extends Fragment {

    FloatingActionButton flotbtn;
    RecyclerView recyclerView;
    List<FBuildings> fBuildingsList;
    FirebaseFirestore db;
    BuildingsListAdapter buildingsListAdapter;
    ProgressBar progressBar;
    Workers fworkers;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    String userID;
    CircleImageView profileImage;
    TextView f_name;
    ProgressBar profile_progressBar,spinKitProgress;

    public MyhomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_myhome, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();
        fworkers=new Workers();

        f_name=view.findViewById(R.id.myHome_frag_fwname);
        profile_progressBar=view.findViewById(R.id.profile_progress);
        spinKitProgress=view.findViewById(R.id.spin_kit);
        profileImage=view.findViewById(R.id.fw_myhomefrag_image);

        Wave wave=new Wave();
        spinKitProgress.setIndeterminateDrawable(wave);

        profile_progressBar.setVisibility(View.VISIBLE);
        showFworkersInfos();

        progressBar=view.findViewById(R.id.progressbar);
        recyclerView=view.findViewById(R.id.myhome_frag_recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        fBuildingsList=new ArrayList<>();
        buildingsListAdapter=new BuildingsListAdapter(fBuildingsList);
        recyclerView.setAdapter(buildingsListAdapter);

        spinKitProgress.setVisibility(View.VISIBLE);
        gettingAllHouseData();

        flotbtn = view.findViewById(R.id.floating_btn);

        flotbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddBuildingActivity.class);
                startActivity(intent);

            }
        });

    }

    private void showFworkersInfos() {

        firebaseUser=mAuth.getCurrentUser();
        userID=firebaseUser.getUid();
        db.collection("f_workers").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                profile_progressBar.setVisibility(View.GONE);

                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();

                    if (documentSnapshot!=null && documentSnapshot.exists()){
                        String name=documentSnapshot.getString("fw_name");
                        String imageurl=documentSnapshot.getString("fw_imageUrl");
                        f_name.setText(name);
                        Glide.with(getContext()).load(imageurl).into(profileImage);
                    }
                }
            }
        });


    }

    public void gettingAllHouseData(){
        db.collection("f_buildings").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                spinKitProgress.setVisibility(View.GONE);

                if (!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot d:list){
                        FBuildings fb=d.toObject(FBuildings.class);
                        fBuildingsList.add(fb);
                    }
                    buildingsListAdapter.notifyDataSetChanged();
                }
            }
        });
    }


}
