package com.rokkhi.rokkhimarketinganalyst.Ui;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rokkhi.rokkhimarketinganalyst.R;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddBuildingsFragment extends Fragment {

    View view;
    AutoCompleteTextView areaText,b_area,b_road,b_block,b_housenmbr,b_nmbrletter;

    CircleImageView circleImageView;
    EditText b_name,b_address,b_totalflat,b_totalguard,b_ownername,b_ownernmbr,b_managername,
             b_managernmbr,b_flatfrmt,b_lat,b_long;

    String[] road_numbers=new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14",
            "15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"};

    String[] block_numbers=new String[]{"A","B","C","D","E","F","G","H","I","J"};

    Button addbldngInfobtn;
    Bitmap bitmap;
    Uri pickedImageUri;
    TextView txt;
    ProgressBar progressBar;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    DocumentReference docRef;
    ArrayAdapter<String> adapter;

    String bldng_id;
    ListView rodNmbrlist,blockList,housenmbr_list,numberLetterList;
    EditText roadnmbr_edit,block_edit,housenmbr_edit,numberLetterEdit;

    public AddBuildingsFragment(){


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_add_buildings, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         mAuth=FirebaseAuth.getInstance();
         db=FirebaseFirestore.getInstance();

         docRef=db.collection("f_buildings").document();

         final String[] areas = new String[]{"Gulshan 1", "Gulshan 2","Banani",
                "Baridhara","Baridhara DOHS","Uttara","Dhanmondi","Mirpur","Baily Road"};


         areaText=view.findViewById(R.id.bldng_edit_area);
         circleImageView=view.findViewById(R.id.building_photo);
         txt=view.findViewById(R.id.clickphototxt);
         b_name=view.findViewById(R.id.bldng_edit_husename);
         b_area=view.findViewById(R.id.bldng_edit_area);
         b_road=view.findViewById(R.id.bldng_edit_road);
         b_block=view.findViewById(R.id.bldng_edit_block);
         b_housenmbr=view.findViewById(R.id.bldng_edit_house);
         b_nmbrletter=view.findViewById(R.id.bldng_edit_houseformat);
         b_totalflat=view.findViewById(R.id.bldng_edit_totalflt);
         b_totalguard=view.findViewById(R.id.bldng_edit_totalguard);
         b_ownername=view.findViewById(R.id.bldng_edit_ownername);
         b_ownernmbr=view.findViewById(R.id.bldng_edit_ownernmbr);
         b_managername=view.findViewById(R.id.bldng_edit_managername);
         b_managernmbr=view.findViewById(R.id.bldng_edit_managernmbr);
         b_lat=view.findViewById(R.id.bldng_edit_lat);
         b_long=view.findViewById(R.id.bldng_edit_long);
         addbldngInfobtn=view.findViewById(R.id.addblgnInfoBtn);
         progressBar=view.findViewById(R.id.progressbar);


        ArrayAdapter<String> adapter=new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_dropdown_item_1line, areas);
        areaText.setAdapter(adapter);

        b_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllareas();
            }
        });

        b_road.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAvailableRoads();
            }
        });
        b_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAvailableBlock();
            }
        });

        b_housenmbr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAvailableHouseNumber();
            }
        });
        b_nmbrletter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHouseNumberLetter();
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"click",Toast.LENGTH_SHORT).show();
                PickSetup setup = new PickSetup().setWidth(100).setHeight(100)
                        .setTitle("Choose Photo")
                        .setBackgroundColor(Color.WHITE)
                        .setButtonOrientation(LinearLayout.HORIZONTAL)
                        .setGalleryButtonText("Gallery")
                        .setCameraIcon(R.mipmap.camera_colored)
                        .setGalleryIcon(R.mipmap.gallery_colored);

                PickImageDialog.build(setup, new IPickResult() {
                    @Override
                    public void onPickResult(PickResult r) {
                        if (r.getError() == null) {

                            pickedImageUri = r.getUri();
                            bitmap = r.getBitmap();
                            circleImageView.setImageBitmap(r.getBitmap());

                        } else {
                            Toast.makeText(getContext(), r.getError().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                }).show(getActivity());


            }
        });

        addbldngInfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
               // saveBuildingInfotoDB();
                showFullAddress();
            }
        });


    }


    public void showFullAddress(){
        String area=b_area.getText().toString();
        String road=b_road.getText().toString();
        String block=b_block.getText().toString();
        String numb=b_housenmbr.getText().toString();
        String leter=b_nmbrletter.getText().toString();

        String totolAddres=area+" "+road+block+" "+numb+leter;

        Toast.makeText(getContext(),totolAddres,Toast.LENGTH_SHORT).show();
    }

    public void showHouseNumberLetter(){
        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(getContext());

        View rowList = getLayoutInflater().inflate(R.layout.adress_list, null);
        numberLetterList = rowList.findViewById(R.id.listview);
        numberLetterEdit=rowList.findViewById(R.id.search_edit);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, block_numbers);
        numberLetterList.setAdapter(adapter);
        ColorDrawable color = new ColorDrawable(this.getResources().getColor(R.color.lightorange));
        numberLetterList.setDivider(color);
        numberLetterList.setDividerHeight(2);
        numberLetterList.setSelector(R.color.lightorange);

        adapter.notifyDataSetChanged();
        alertDialog.setView(rowList);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();

        numberLetterEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                AddBuildingsFragment.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        numberLetterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nmbrlettertxt=String.valueOf(parent.getItemAtPosition(position));
                b_nmbrletter.setText(nmbrlettertxt);
                dialog.dismiss();
            }
        });



    }

    private void showAvailableHouseNumber() {
        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(getContext());

        View rowList = getLayoutInflater().inflate(R.layout.adress_list, null);
        housenmbr_list = rowList.findViewById(R.id.listview);
        housenmbr_edit=rowList.findViewById(R.id.search_edit);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, road_numbers);
        housenmbr_list.setAdapter(adapter);
        ColorDrawable color = new ColorDrawable(this.getResources().getColor(R.color.lightorange));
        housenmbr_list.setDivider(color);
        housenmbr_list.setDividerHeight(2);
        housenmbr_list.setSelector(R.color.lightorange);

        adapter.notifyDataSetChanged();
        alertDialog.setView(rowList);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();

        housenmbr_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                AddBuildingsFragment.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        housenmbr_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String housenmbrtxt=String.valueOf(parent.getItemAtPosition(position));
                b_housenmbr.setText(housenmbrtxt);
                dialog.dismiss();
            }
        });

    }

    private void showAvailableBlock() {
        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(getContext());

        View rowList = getLayoutInflater().inflate(R.layout.adress_list, null);
        blockList = rowList.findViewById(R.id.listview);
        block_edit=rowList.findViewById(R.id.search_edit);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, block_numbers);
        blockList.setAdapter(adapter);
        ColorDrawable color = new ColorDrawable(this.getResources().getColor(R.color.lightorange));
        blockList.setDivider(color);
        blockList.setDividerHeight(2);
        blockList.setSelector(R.color.lightorange);

        adapter.notifyDataSetChanged();
        alertDialog.setView(rowList);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();

        block_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                AddBuildingsFragment.this.adapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        blockList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String blocknmbrtxt=String.valueOf(parent.getItemAtPosition(position));
                b_block.setText(blocknmbrtxt);
                dialog.dismiss();
            }
        });

    }

    private void showAvailableRoads() {

        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(getContext());

        View rowList = getLayoutInflater().inflate(R.layout.adress_list, null);
        rodNmbrlist = rowList.findViewById(R.id.listview);
        roadnmbr_edit=rowList.findViewById(R.id.search_edit);

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, road_numbers);
        rodNmbrlist.setAdapter(adapter);
        ColorDrawable color = new ColorDrawable(this.getResources().getColor(R.color.lightorange));
        rodNmbrlist.setDivider(color);
        rodNmbrlist.setDividerHeight(2);
        rodNmbrlist.setSelector(R.color.lightorange);

        adapter.notifyDataSetChanged();
        alertDialog.setView(rowList);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();

        roadnmbr_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                AddBuildingsFragment.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rodNmbrlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String roadnmbrtxt=String.valueOf(parent.getItemAtPosition(position));
                b_road.setText(roadnmbrtxt);
                dialog.dismiss();
            }
        });


    }

    private void showAllareas() {

        ListView listView=new ListView(getContext());
        ColorDrawable color = new ColorDrawable(this.getResources().getColor(R.color.lightorange));
        listView.setDivider(color);
        listView.setDividerHeight(2);
        listView.setSelector(R.color.lightorange);
        final String[] areas = new String[]{"Gulshan 1", "Gulshan 2","Banani","Basundhora",
                "Baridhara","Baridhara DOHS","Uttara","Dhanmondi","Mirpur","Baily Road"};

        ArrayAdapter<String > areaAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,areas);
        listView.setAdapter(areaAdapter) ;
        final AlertDialog.Builder  alertDialog= new AlertDialog.Builder(getContext());
        alertDialog.setCancelable(true);
        alertDialog.setView(listView );
        final AlertDialog al=alertDialog.create();
        al.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String areatxt=String.valueOf(parent.getItemAtPosition(position));
                b_area.setText(areatxt);
                al.dismiss();
            }
        });


    }

    private void saveBuildingInfotoDB() {

        //String area=areaText.getText().toString();
        String bldng_name=b_name.getText().toString();
        String bldng_address=b_address.getText().toString();
        String bldng_totalflat=b_totalflat.getText().toString();
        String bldng_totalguard=b_totalguard.getText().toString();
        String bldng_ownername=b_ownername.getText().toString();
        String bldng_ownernmbr=b_ownernmbr.getText().toString();
        String bldng_managername=b_managername.getText().toString();
        String bldng_managernmbr=b_managernmbr.getText().toString();
        String area=b_area.getText().toString();
        String road=b_road.getText().toString();
        String block=b_block.getText().toString();
        String numb=b_housenmbr.getText().toString();
        String leter=b_nmbrletter.getText().toString();


        String bldng_fulladdress=area+" "+road+block+" "+numb+leter;

        String bldng_lat=b_lat.getText().toString();
        String bldng_long=b_long.getText().toString();


        Map<String,Object> buildngInfo=new HashMap<>();

        buildngInfo.put("bldng_fulladdress",bldng_fulladdress);
        buildngInfo.put("bldng_name",bldng_name);
        buildngInfo.put("bldng_totalflat",bldng_totalflat);
        buildngInfo.put("bldng_totalguard",bldng_totalguard);
        buildngInfo.put("bldng_ownername",bldng_ownername);
        buildngInfo.put("bldng_ownernmbr",bldng_ownernmbr);
        buildngInfo.put("bldng_managername",bldng_managername);
        buildngInfo.put("bldng_managernmbr",bldng_managernmbr);
        buildngInfo.put("bldng_lat",bldng_lat);
        buildngInfo.put("bldng_long",bldng_long);
        buildngInfo.put("bldng_id",bldng_id);

        db.collection("f_buildings").add(buildngInfo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(),"Data saved succesfully",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(),"Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}
