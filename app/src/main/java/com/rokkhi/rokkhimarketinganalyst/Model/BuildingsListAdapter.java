package com.rokkhi.rokkhimarketinganalyst.Model;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rokkhi.rokkhimarketinganalyst.R;

import java.util.List;

public class BuildingsListAdapter extends RecyclerView.Adapter<BuildingsListAdapter.BuildingViewholder>{

    public Context context;
    public List<FBuildings> fBuildingsList;


    public BuildingsListAdapter(List<FBuildings> fBuildingsList) {
        //this.context = context;
        this.fBuildingsList = fBuildingsList;
    }

    @NonNull
    @Override
    public BuildingViewholder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {

        View v;

        v=LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_layout,parent,false);
        final BuildingViewholder bv=new BuildingViewholder(v);

        //TODO:Dialog initialize

        final Dialog dialog=new Dialog(parent.getContext());
        dialog.setContentView(R.layout.show_buildinginfo_layout);

        //TODO:Show buildings info in a Dialogue
        bv.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TextView textView=(TextView) dialog.findViewById(R.id.house_name);
                textView.setText("House Name: "+fBuildingsList.get(bv.getAdapterPosition()).getB_housename());

                TextView address=(TextView) dialog.findViewById(R.id.house_address);
                address.setText(fBuildingsList.get(bv.getAdapterPosition()).getB_address());

                Toast.makeText(parent.getContext(), String.valueOf(bv.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                dialog.show();
            }
        });
        return bv;
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
        RelativeLayout relativeLayout;
        public BuildingViewholder(@NonNull View itemView) {
            super(itemView);
            relativeLayout=(RelativeLayout) itemView.findViewById(R.id.item_list_id);
            build_name=itemView.findViewById(R.id.myhome_frag_bldngName);
            build_address=itemView.findViewById(R.id.myhome_frag_bldngAddress);
            build_status=itemView.findViewById(R.id.myhome_frag_bldngstatus);
            build_lastVisit=itemView.findViewById(R.id.myhome_frag_bldngvisitdate);


            //itemView.setOnClickListener(this);
        }


    }
}
