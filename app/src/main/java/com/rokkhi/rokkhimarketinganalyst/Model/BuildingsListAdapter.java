package com.rokkhi.rokkhimarketinganalyst.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rokkhi.rokkhimarketinganalyst.R;

import java.util.List;

public class BuildingsListAdapter extends RecyclerView.Adapter<BuildingsListAdapter.BuildingViewholder>{

    public Context context;
    public List<FBuildings> fBuildingsList;


    public BuildingsListAdapter(List<FBuildings> fBuildingsList) {
       // this.context = context;
        this.fBuildingsList = fBuildingsList;
    }

    @NonNull
    @Override
    public BuildingViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BuildingViewholder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_layout,parent,false)

        );
    }

    @Override
    public void onBindViewHolder(@NonNull BuildingViewholder holder, int position) {

        FBuildings fBuildings=fBuildingsList.get(position);

        holder.build_address.setText("Building Address: "+fBuildings.getB_address());
        holder.build_name.setText("Building Name: "+fBuildings.getB_housename());
        holder.build_status.setText("Current Status: "+fBuildings.getB_status());
        holder.build_lastVisit.setText("Visit Date: "+fBuildings.getB_visiteddate());


    }

    @Override
    public int getItemCount() {
        return fBuildingsList.size();
    }

    public class BuildingViewholder extends RecyclerView.ViewHolder {

        TextView build_name,build_address,build_status,build_lastVisit;

        public BuildingViewholder(@NonNull View itemView) {
            super(itemView);

            build_name=itemView.findViewById(R.id.myhome_frag_bldngName);
            build_address=itemView.findViewById(R.id.myhome_frag_bldngAddress);
            build_status=itemView.findViewById(R.id.myhome_frag_bldngstatus);
            build_lastVisit=itemView.findViewById(R.id.myhome_frag_bldngvisitdate);


        }
    }
}
