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
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.rokkhi.rokkhimarketinganalyst.Model.BuildingsListAdapter;
import com.rokkhi.rokkhimarketinganalyst.Model.FBuildings;
import com.rokkhi.rokkhimarketinganalyst.R;

import java.util.ArrayList;
import java.util.List;

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

        progressBar.setVisibility(View.VISIBLE);
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

    public void gettingAllHouseData(){
        db.collection("f_buildings").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                progressBar.setVisibility(View.GONE);

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
