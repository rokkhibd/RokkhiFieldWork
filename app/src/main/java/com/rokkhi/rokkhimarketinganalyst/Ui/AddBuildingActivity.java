package com.rokkhi.rokkhimarketinganalyst.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rokkhi.rokkhimarketinganalyst.Model.AllStringValues;
import com.rokkhi.rokkhimarketinganalyst.Model.FBuildings;
import com.rokkhi.rokkhimarketinganalyst.R;
import com.rokkhi.rokkhimarketinganalyst.Utils.Normalfunc;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddBuildingActivity extends AppCompatActivity {

    AllStringValues allStringValues;
    ArrayAdapter<String> adapter;

    ProgressDialog progressDialog;

    RelativeLayout relativeLayout;

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseStorage firebaseStorage;
    StorageReference storageRef;
    String currentUser;

    ListView roadNumberList, blockList, houseNoList, areaListView;
    EditText roadNumberEdit, blockEdit, houseNoEdit, areaEdit;

    Bitmap bitmap;
    Uri pickedImageUri;
    List<String> areaList = new ArrayList<>();

    CircleImageView circleImageView;
    EditText b_name, b_totalfloor, b_floorperflat, b_totalguard, b_ownername, b_ownernmbr, b_managername,
            b_managernmbr, b_lat, b_long, b_area, b_roadnumber, b_block, b_housenmbr, b_housefrmt,
            b_visit, b_follwing, b_caretakername, b_caretakernmbr,b_code,b_guardname,b_guardnmbr;

    Button saveBtn,tapCode,addInfoButton,checkHouseBtn;

    String areaListCode,roadListCode,blockListCode,houseListCode,housefrmntListCode,totalHouseCode
            ,status,flatformat,districtValue;

    String wholeAddress;

    ImageView visitCal, followpCal,statusMenu,flatfrmtMenu,district_Menu;

    DatePickerDialog datePickerDialog;

    AutoCompleteTextView b_status,b_flatfrmt,b_district;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_buildings);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser=FirebaseAuth.getInstance().getCurrentUser().getUid();
        storageRef = FirebaseStorage.getInstance().getReference().child("fworkers_photo");

        progressDialog=new ProgressDialog(this);

        relativeLayout=findViewById(R.id.addbldng_relative_layout);

        b_area = findViewById(R.id.bldng_edit_area);
        b_roadnumber = findViewById(R.id.bldng_edit_road);
        b_block = findViewById(R.id.bldng_edit_block);
        b_housenmbr = findViewById(R.id.bldng_edit_house);
        b_housefrmt = findViewById(R.id.bldng_edit_houseformat);
        b_district=findViewById(R.id.bldng_edit_disctrict);
        b_name = findViewById(R.id.bldng_edit_husename);
        b_floorperflat = findViewById(R.id.bldng_edit_totalflt);
        b_totalfloor = findViewById(R.id.bldng_edit_totalfloor);
        b_totalguard = findViewById(R.id.bldng_edit_totalguard);
        b_ownername = findViewById(R.id.bldng_edit_ownername);
        b_ownernmbr = findViewById(R.id.bldng_edit_ownernmbr);
        b_guardname=findViewById(R.id.bldng_edit_guardname);
        b_guardnmbr=findViewById(R.id.bldng_edit_guardnmbr);
        b_managername = findViewById(R.id.bldng_edit_managername);
        b_managernmbr = findViewById(R.id.bldng_edit_managernmbr);
        b_caretakername = findViewById(R.id.bldng_edit_caretakername);
        b_caretakernmbr = findViewById(R.id.bldng_edit_caretakernmbr);
        b_flatfrmt = findViewById(R.id.bldng_edit_flatformat);
        b_visit = findViewById(R.id.bldng_edit_visitdate);
        b_follwing = findViewById(R.id.bldng_edit_followingdate);
        b_code=findViewById(R.id.bldng_edit_bcode);
        b_status=findViewById(R.id.bldng_edit_status);
        b_lat=findViewById(R.id.bldng_edit_lat);
        b_long=findViewById(R.id.bldng_edit_long);

        tapCode=findViewById(R.id.tap_bcode);
        circleImageView = findViewById(R.id.building_photo);
        addInfoButton=findViewById(R.id.addblgnInfoBtn);
        checkHouseBtn=findViewById(R.id.bldng_button_housecheck);

        visitCal = findViewById(R.id.visitcalimg);
        followpCal = findViewById(R.id.followingcalimg);
        statusMenu=findViewById(R.id.statusMenu);
        flatfrmtMenu=findViewById(R.id.flatformatMenu);
        district_Menu=findViewById(R.id.districtMenu);

        allStringValues = new AllStringValues();

        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,allStringValues.status);
        b_status.setAdapter(adapter);

        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,allStringValues.flatformat);
        b_flatfrmt.setAdapter(adapter);

        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,allStringValues.district);
        b_district.setAdapter(adapter);


        addInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("wait,till saving data");
                saveBuildingDataInDB();
            }
        });

        statusMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b_status.showDropDown();
                status=b_status.getText().toString();
            }
        });

        flatfrmtMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b_flatfrmt.showDropDown();
                flatformat=b_flatfrmt.getText().toString();
            }
        });

        district_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b_district.showDropDown();



            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            Toast.makeText(AddBuildingActivity.this, r.getError().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                }).show(AddBuildingActivity.this);

            }
        });

        b_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllAreas();
            }
        });

        b_roadnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAvailableRoads();
                //showAddressAlert(Arrays.asList(allStringValues.road_no),b_roadnumber);
            }
        });

        b_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAvailableBlock();
                //showAddressAlert(Arrays.asList(allStringValues.block_numbers),b_block);
            }
        });

        b_housenmbr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoAvailableHouseNumner();

                //showAddressAlert(Arrays.asList(allStringValues.road_no),b_housenmbr);
            }
        });

        b_housefrmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAvailableHouseFormat();
                //showAddressAlert(Arrays.asList(allStringValues.block_numbers),b_housefrmt);
            }
        });

        checkHouseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String area=b_area.getText().toString();
                String road=b_roadnumber.getText().toString();
                String block=b_block.getText().toString();
                String houseNmbr=b_housenmbr.getText().toString();
                String housefrmt=b_housefrmt.getText().toString();
                districtValue=b_district.getText().toString();

                String theWholeAddress=area+" "+road+block+" "+houseNmbr+housefrmt+" "+districtValue;

                wholeAddress=theWholeAddress;

                area=areaListCode;
                road=roadListCode;
                block=blockListCode;
                houseNmbr=houseListCode;
                housefrmt=housefrmntListCode;

                districtValue= String.valueOf(1);

                Toast.makeText(AddBuildingActivity.this, districtValue, Toast.LENGTH_SHORT).show();

                String totalCode=areaListCode+"*"+roadListCode+"*"+blockListCode+"*"+houseListCode+"*"+housefrmntListCode+"*"+districtValue;

                totalHouseCode=areaListCode+roadListCode+blockListCode+houseListCode+housefrmntListCode+districtValue;

                //b_code.setText(totalHouseCode);
                checkTheHouseAvailability(totalCode);
            }
        });

    }


    public void checkTheHouseAvailability(String s){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Executing action...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        CollectionReference buildRef;
        buildRef=db.collection("f_buildings");

        Query buildingsQuery=buildRef.whereEqualTo("b_code",s);
        buildingsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){
                    if (task.getResult().size()>0){
                        for (DocumentSnapshot documentSnapshot:task.getResult()){
                            FBuildings fBuildings=documentSnapshot.toObject(FBuildings.class);
                            String status=fBuildings.getB_status();

                            if (status.equalsIgnoreCase("done")){
                               // Toast.makeText(AddBuildingActivity.this,"Done",Toast.LENGTH_SHORT).show();
                            }else if (status.equalsIgnoreCase("pending")){
                               // Toast.makeText(AddBuildingActivity.this, "pending", Toast.LENGTH_SHORT).show();
                                shoeAlertforPendingHouse();
                            }
                            progressDialog.dismiss();
                        }
                    }else {
                        //Toast.makeText(AddBuildingActivity.this, "No building found", Toast.LENGTH_SHORT).show();
                        shoeAlertforhouseNotfound();
                        progressDialog.dismiss();

                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }


    private void showAllAreas() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        View rowList = getLayoutInflater().inflate(R.layout.adress_list, null);
        areaListView = rowList.findViewById(R.id.listview);
        areaEdit = rowList.findViewById(R.id.search_edit);

        db.collection("area").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                areaList.clear();

                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                    String area_eng = documentSnapshot.getString("english");
                    String area_ban = documentSnapshot.getString("bangla");
                  //String bcode=documentSnapshot.getString("code");

                    areaList.add(area_eng + "("+area_ban+")");

                }

                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, areaList);
                adapter.notifyDataSetChanged();
                areaListView.setAdapter(adapter);

            }
        });

        ColorDrawable color = new ColorDrawable(this.getResources().getColor(R.color.lightorange));
        areaListView.setDivider(color);
        areaListView.setDividerHeight(1);


        alertDialog.setView(rowList);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();

        areaEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                AddBuildingActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        areaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String areatxt = String.valueOf(parent.getItemAtPosition(position));
                areaListCode= (String.valueOf(position+1));
                //Toast.makeText(AddBuildingActivity.this, areaListCode, Toast.LENGTH_SHORT).show();
                b_area.setText(areatxt);
                dialog.dismiss();


            }
        });
    }

    private void showAddressAlert(List<String> list, final EditText editText) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        View rowList = getLayoutInflater().inflate(R.layout.adress_list, null);
        ListView houseNoList = rowList.findViewById(R.id.listview);
        EditText houseNoEdit = rowList.findViewById(R.id.search_edit);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        houseNoList.setAdapter(adapter);
        ColorDrawable color = new ColorDrawable(this.getResources().getColor(R.color.lightorange));
        houseNoList.setDivider(color);
        houseNoList.setDividerHeight(2);
        houseNoList.setSelector(R.color.lightorange);
        adapter.notifyDataSetChanged();
        alertDialog.setView(rowList);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();

        houseNoEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                AddBuildingActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        houseNoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String houseno = String.valueOf(parent.getItemAtPosition(position));
                editText.setText(houseno);
                dialog.dismiss();
            }
        });
    }

    public void showAvailableRoads(){
        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);

        View rowList = getLayoutInflater().inflate(R.layout.adress_list, null);
        roadNumberList = rowList.findViewById(R.id.listview);
        roadNumberEdit=rowList.findViewById(R.id.search_edit);
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,allStringValues.road_no);
        roadNumberList.setAdapter(adapter);
        ColorDrawable color = new ColorDrawable(this.getResources().getColor(R.color.lightorange));
        roadNumberList.setDivider(color);
        roadNumberList.setDividerHeight(2);
        roadNumberList.setSelector(R.color.lightorange);

        adapter.notifyDataSetChanged();
        alertDialog.setView(rowList);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();

        roadNumberEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                AddBuildingActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        roadNumberList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String roadno=String.valueOf(parent.getItemAtPosition(position));
                roadListCode=roadno;
                b_roadnumber.setText(roadno);
                dialog.dismiss();
            }
        });

    }

    public void showAvailableBlock(){
        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);

        View rowList = getLayoutInflater().inflate(R.layout.adress_list, null);
        blockList = rowList.findViewById(R.id.listview);
        blockEdit=rowList.findViewById(R.id.search_edit);
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,allStringValues.block_numbers);
        blockList.setAdapter(adapter);
        ColorDrawable color = new ColorDrawable(this.getResources().getColor(R.color.lightorange));
        blockList.setDivider(color);
        blockList.setDividerHeight(2);
        blockList.setSelector(R.color.lightorange);

        adapter.notifyDataSetChanged();
        alertDialog.setView(rowList);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();

        blockEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                AddBuildingActivity.this.adapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        blockList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String blockno=String.valueOf(parent.getItemAtPosition(position));
                b_block.setText(blockno);
                blockListCode=String.valueOf(position+1);

                Toast.makeText(AddBuildingActivity.this,blockListCode,Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }

    public void shoAvailableHouseNumner(){
        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);

        View rowList = getLayoutInflater().inflate(R.layout.adress_list, null);
        houseNoList = rowList.findViewById(R.id.listview);
        houseNoEdit=rowList.findViewById(R.id.search_edit);
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,allStringValues.road_no);
        houseNoList.setAdapter(adapter);
        ColorDrawable color = new ColorDrawable(this.getResources().getColor(R.color.lightorange));
        houseNoList.setDivider(color);
        houseNoList.setDividerHeight(2);
        houseNoList.setSelector(R.color.lightorange);

        adapter.notifyDataSetChanged();
        alertDialog.setView(rowList);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();

        houseNoEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                AddBuildingActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        houseNoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String houseno=String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(AddBuildingActivity.this,houseno,Toast.LENGTH_SHORT).show();

                houseListCode=houseno;
                b_housenmbr.setText(houseno);


                dialog.dismiss();
            }
        });
    }

    public void showAvailableHouseFormat(){
        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);

        View rowList = getLayoutInflater().inflate(R.layout.adress_list, null);
        blockList = rowList.findViewById(R.id.listview);
        blockEdit=rowList.findViewById(R.id.search_edit);
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,allStringValues.block_numbers);
        blockList.setAdapter(adapter);
        ColorDrawable color = new ColorDrawable(this.getResources().getColor(R.color.lightorange));
        blockList.setDivider(color);
        blockList.setDividerHeight(2);
        blockList.setSelector(R.color.lightorange);

        adapter.notifyDataSetChanged();
        alertDialog.setView(rowList);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();

        blockEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                AddBuildingActivity.this.adapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        blockList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String blockno=String.valueOf(parent.getItemAtPosition(position));
                housefrmntListCode=String.valueOf(position+1);
                b_housefrmt.setText(blockno);
                dialog.dismiss();
            }
        });
    }

   public void saveBuildingDataInDB(){
        String housename=b_name.getText().toString();
        String caretakrname=b_caretakername.getText().toString();
        String caretakernmbr=b_caretakernmbr.getText().toString();

        String care_number=add88withNumb(caretakernmbr);

        String flatperfloor=b_floorperflat.getText().toString();
        String followupdate=b_follwing.getText().toString();
        String visit=b_visit.getText().toString();
        String guardname=b_guardname.getText().toString();
        String guardnmbr=b_guardnmbr.getText().toString();

        String guard_number=add88withNumb(guardnmbr);


        String ownername=b_ownername.getText().toString();
        String ownernmbr=b_ownernmbr.getText().toString();

        String owner_number=add88withNumb(ownernmbr);

        String guards=b_totalguard.getText().toString();
        String totalfloor=b_totalfloor.getText().toString();


        Normalfunc normalfunc=new Normalfunc();
        List<String> b_code_array=normalfunc.splitchar(totalHouseCode);

        Map<String,Object> areaMap=new HashMap<>();

       areaMap.put("b_address",wholeAddress);
       areaMap.put("b_flatfrmt",flatformat);
       areaMap.put("b_flatperfloor",flatperfloor);
       areaMap.put("b_followupdate",followupdate);
       areaMap.put("b_guardname",guardname);
       areaMap.put("b_guardnmbr",guard_number);
       areaMap.put("b_guards",guards);
       areaMap.put("b_housename",housename);
       areaMap.put("b_ownername",ownername);
       areaMap.put("b_ownernmbr",owner_number);
       areaMap.put("b_status",status);
       areaMap.put("b_code_array",b_code_array);
       areaMap.put("b_totalfloor",totalfloor);
       areaMap.put("b_visiteddate",visit);
       areaMap.put("b_caretakernam",caretakrname);
       areaMap.put("b_caretakernmbr",care_number);
       areaMap.put("b_uid",currentUser);

        db.collection("f_buildings").document().set(areaMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                progressDialog.dismiss();
                if (task.isSuccessful()){
                    Toast.makeText(AddBuildingActivity.this,"Data Saved",Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(AddBuildingActivity.this,"Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

   }

   public String add88withNumb(String s){
        String number="+88"+s;
        return number;
   }

   public void shoeAlertforhouseNotfound(){
        AlertDialog.Builder alert= new AlertDialog.Builder(AddBuildingActivity.this);
        View view=getLayoutInflater().inflate(R.layout.house_not_found,null);

        Button btn=view.findViewById(R.id.btn);
        alert.setView(view);

        final AlertDialog alertDialog1=alert.create();
        alertDialog1.setCanceledOnTouchOutside(true);
        alertDialog1.show();

       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               alertDialog1.dismiss();
               relativeLayout.setVisibility(View.VISIBLE);
           }
       });


   }

    public void shoeAlertforPendingHouse(){
        AlertDialog.Builder alert= new AlertDialog.Builder(AddBuildingActivity.this);
        View view=getLayoutInflater().inflate(R.layout.house_status_pending,null);

        Button btn=view.findViewById(R.id.btn);
        TextView txt=view.findViewById(R.id.txt);
        alert.setView(view);

        final AlertDialog alertDialog1=alert.create();
        alertDialog1.setCanceledOnTouchOutside(true);
        alertDialog1.show();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog1.dismiss();
                //relativeLayout.setVisibility(View.GO);
            }
        });


    }
}
