package com.rokkhi.rokkhimarketinganalyst.Model;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rokkhi.rokkhimarketinganalyst.R;

import org.w3c.dom.Text;

import java.util.List;

public class BuildingsListAdapter extends RecyclerView.Adapter<BuildingsListAdapter.BuildingViewholder>{

    public Context context;
    public List<FBuildings> fBuildingsList;


    public BuildingsListAdapter(List<FBuildings> fBuildingsList) {

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

                FBuildings fBuildings=fBuildingsList.get(bv.getAdapterPosition());

                TextView textView=(TextView) dialog.findViewById(R.id.house_name);
                textView.setText("House Name: "+fBuildingsList.get(bv.getAdapterPosition()).getB_housename());

                TextView address=(TextView) dialog.findViewById(R.id.house_address);
                address.setText(fBuildingsList.get(bv.getAdapterPosition()).getB_address());

                TextView caretakerName=(TextView) dialog.findViewById(R.id.caretaker_name);
                caretakerName.setText(fBuildings.getB_caretakernam());

                TextView caretakerNumber=dialog.findViewById(R.id.caretaker_number);
                caretakerNumber.setText(fBuildings.getB_caretakernmbr());

                TextView ownerName=dialog.findViewById(R.id.owner_name);
                ownerName.setText(fBuildings.getB_ownername());

                TextView ownerNumber=dialog.findViewById(R.id.owner_number);
                ownerNumber.setText(fBuildings.getB_ownernmbr());

                TextView guardName=dialog.findViewById(R.id.guard_name);
                guardName.setText(fBuildings.getB_guardname());

                TextView guardNumber=dialog.findViewById(R.id.guard_number);
                guardNumber.setText(fBuildings.getB_guardnmbr());

                TextView visitdate=dialog.findViewById(R.id.visited_date);
                visitdate.setText(fBuildings.getB_visiteddate());

                TextView followupDate=dialog.findViewById(R.id.followup_date);
                followupDate.setText(fBuildings.getB_followupdate());

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
        public BuildingViewholder(@NonNull final View itemView) {
            super(itemView);

            relativeLayout=(RelativeLayout) itemView.findViewById(R.id.item_list_id);
            build_name=itemView.findViewById(R.id.myhome_frag_bldngName);
            build_address=itemView.findViewById(R.id.myhome_frag_bldngAddress);
            build_status=itemView.findViewById(R.id.myhome_frag_bldngstatus);
            build_lastVisit=itemView.findViewById(R.id.myhome_frag_bldngvisitdate);



        }


    }
}
