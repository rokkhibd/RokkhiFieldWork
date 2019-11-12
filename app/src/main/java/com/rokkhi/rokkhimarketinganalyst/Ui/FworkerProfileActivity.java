package com.rokkhi.rokkhimarketinganalyst.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rokkhi.rokkhimarketinganalyst.MainActivity;
import com.rokkhi.rokkhimarketinganalyst.Model.AllStringValues;
import com.rokkhi.rokkhimarketinganalyst.R;
import com.rokkhi.rokkhimarketinganalyst.Utils.Normalfunc;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

public class FworkerProfileActivity extends AppCompatActivity {

    AutoCompleteTextView f_area,f_gender;

    AllStringValues allStringValues;

    Button saveData;

    ImageView areaMenu,genderMenu,dobCal,joinDateCal;
    EditText f_name,f_road,f_block,f_houseno,f_roadletter,f_phone,f_nid,f_dob,f_uni,f_mail,f_joindate;

    //TODO: Creates String variable


    ArrayAdapter<String> adapter;

    List<String> areaList=new ArrayList<>();

    ListView roadNumberList,blockList,houseNoList,areaListView;
    EditText roadNumberEdit,blockEdit,houseNoEdit,areaEdit;

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseStorage firebaseStorage;
    StorageReference storageRef;
    FirebaseUser currentUser;

    String userId,downloadImageUri;

    DatePickerDialog datePickerDialog;
    CircleImageView circleImageView;

    Bitmap bitmap;
    Uri pickedImageUri;

    ProgressBar progressBar,spinKitProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fworker_profile);

        db=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        storageRef=FirebaseStorage.getInstance().getReference().child("fworkers_photo");

        currentUser=FirebaseAuth.getInstance().getCurrentUser();
        userId =currentUser.getUid();

        saveData=findViewById(R.id.fworker_data_btn);
        f_area=findViewById(R.id.fworker_address_area);
        f_gender=findViewById(R.id.fworker_gender_edit);
        f_name=findViewById(R.id.fworker_name_edit);
        f_road=findViewById(R.id.fworker_address_road);
        f_block=findViewById(R.id.fworker_address_block);
        f_houseno=findViewById(R.id.fworker_address_housenmbr);
        f_roadletter=findViewById(R.id.fworker_address_roadletter);
        f_phone=findViewById(R.id.fworker_phone_edit);
        f_nid=findViewById(R.id.fworker_nid_edit);
        f_dob=findViewById(R.id.fworker_dob_edit);
        f_uni=findViewById(R.id.fworker_uni_edit);
        f_mail=findViewById(R.id.fworker_mail_edit);
        f_joindate=findViewById(R.id.fworker_joining_edit);
        progressBar=findViewById(R.id.progressBar);

        spinKitProgressBar=findViewById(R.id.spin_kit);
        Wave wave=new Wave();
        spinKitProgressBar.setIndeterminateDrawable(wave);

        allStringValues=new AllStringValues();

        //TODO: ImageViews ID
        areaMenu=findViewById(R.id.area_menudown);
        genderMenu=findViewById(R.id.gender_menu);
        dobCal=findViewById(R.id.calendar);
        joinDateCal=findViewById(R.id.calendar_joining);
        circleImageView=findViewById(R.id.fworker_photo);


        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,allStringValues.gender);
        f_gender.setAdapter(adapter);

        checkProfileActvty();

        //TODO:Click listener of All Image views
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinKitProgressBar.setVisibility(View.VISIBLE);
                saveAllDataToFirestore();


            }
        });

        areaMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //f_area.showDropDown();
                showallAreas();
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
                            saveImageToStorage();

                        } else {
                            Toast.makeText(FworkerProfileActivity.this, r.getError().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                }).show(FworkerProfileActivity.this);

            }
        });

        f_road.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAvailableRoads();
            }
        });

        f_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAvailableBlock();
            }
        });
        f_houseno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAvailableHouseno();
            }
        });

        genderMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f_gender.showDropDown();
                String gender=f_gender.getText().toString();
            }
        });

        dobCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(FworkerProfileActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                f_dob.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        joinDateCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(FworkerProfileActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                f_joindate.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();

            }
        });

    }



    private void showallAreas() {

        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);

        View rowList = getLayoutInflater().inflate(R.layout.adress_list, null);
        areaListView=rowList.findViewById(R.id.listview);
        areaEdit=rowList.findViewById(R.id.search_edit);

        db.collection("area").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                areaList.clear();

                for (DocumentSnapshot documentSnapshot:queryDocumentSnapshots){

                    String area_eng=documentSnapshot.getString("english");
                    String area_ban=documentSnapshot.getString("bangla");

                    areaList.add(area_eng+"("+area_ban+")");

                   // areaList.add(documentSnapshot.getString("english"));
                    //areaList.add(documentSnapshot.getString("bangla"));
                }

                adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,areaList);
                adapter.notifyDataSetChanged();
                areaListView.setAdapter(adapter);

            }
        });

        //areListView.setAdapter(adapter);
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
                FworkerProfileActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        areaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String areatxt=String.valueOf(parent.getItemAtPosition(position));
                f_area.setText(areatxt);
                dialog.dismiss();
            }
        });

    }

    private void showAvailableHouseno() {

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

                FworkerProfileActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        houseNoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String houseno=String.valueOf(parent.getItemAtPosition(position));
                f_houseno.setText(houseno);
                dialog.dismiss();
            }
        });


    }

    private void showAvailableBlock() {

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
                FworkerProfileActivity.this.adapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        blockList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String blockno=String.valueOf(parent.getItemAtPosition(position));
                f_block.setText(blockno);
                dialog.dismiss();
            }
        });


    }

    private void showAvailableRoads() {

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

                FworkerProfileActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        roadNumberList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String roadno=String.valueOf(parent.getItemAtPosition(position));
                f_road.setText(roadno);

                dialog.dismiss();
            }
        });


    }



    public void saveAllDataToFirestore(){
        String fw_name=f_name.getText().toString();
        String fw_area=f_area.getText().toString();
        String fw_road=f_road.getText().toString();
        String fw_block=f_block.getText().toString();
        String fw_housenmbr=f_houseno.getText().toString();
        String fw_houseletter=f_roadletter.getText().toString();
        String fphone=f_phone.getText().toString();

        String phone=add88withNumb(fphone);

        String fw_nid=f_nid.getText().toString();
        String fw_dob=f_dob.getText().toString();
        String fw_uni=f_uni.getText().toString();
        String fw_joindate=f_joindate.getText().toString();
        String fw_mail=f_mail.getText().toString();
        String fw_gender=f_gender.getText().toString();
        String fw_address=fw_area+" "+fw_road+fw_block+" "+fw_housenmbr+fw_houseletter;

        Normalfunc normalfunc=new Normalfunc();
        List<String> fw_phone=normalfunc.splitstring(phone);

        Map<String,Object> fw_map=new HashMap<>();
        fw_map.put("fw_name",fw_name);
        fw_map.put("fw_address",fw_address);
        fw_map.put("fw_phone",fw_phone);
        fw_map.put("fw_nid",fw_nid);
        fw_map.put("fw_dob",fw_dob);
        fw_map.put("fw_uni",fw_uni);
        fw_map.put("fw_joindate",fw_joindate);
        fw_map.put("fw_mail",fw_mail);
        fw_map.put("fw_imageUrl",downloadImageUri);
        fw_map.put("fw_gender",fw_gender);
        fw_map.put("fw_uid",userId);


        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor=sharedPreferences.edit();


        editor.putString("fw_name",fw_name);
        //editor.putString("fw_area",fw_area);
        //editor.putString("fw_road",fw_road);
        //editor.putString("fw_block",fw_block);
       // editor.putString("fw_housenumber",fw_housenmbr);
        editor.putString("fw_phone",fphone);
        //editor.putString("fw_nid",fw_nid);
        //editor.putString("fw_dob",fw_dob);
        //editor.putString("fw_uni",fw_uni);
        editor.putString("fw_joindate",fw_joindate);
        //editor.putString("fw_mail",fw_mail);
        //editor.putString("fw_gender",fw_gender);
        editor.commit();



        db.collection("f_workers").document(userId).set(fw_map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    spinKitProgressBar.setVisibility(View.GONE);
                    Toast.makeText(FworkerProfileActivity.this,"Data saved!!",Toast.LENGTH_SHORT).show();
                    stayAtMainActvity();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(FworkerProfileActivity.this,"Error!!"+e,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveImageToStorage(){
        final StorageReference filePath=storageRef.child(pickedImageUri.getLastPathSegment()+".jpg");
        final UploadTask uploadTask=filePath.putFile(pickedImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressBar.setVisibility(View.GONE);
                Toast.makeText(FworkerProfileActivity.this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(FworkerProfileActivity.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if (!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadImageUri=filePath.getDownloadUrl().toString();

                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            downloadImageUri=task.getResult().toString();
                        }
                    }
                });
            }
        });

    }

    private void stayAtMainActvity() {
        Intent intent= new Intent(FworkerProfileActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public String add88withNumb(String s){
        String number="+88"+s;
        return number;
    }


    public void checkProfileActvty(){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (sharedPreferences.contains("fw_name") && sharedPreferences.contains("fw_nid") && sharedPreferences.contains("fw_phone")
                && sharedPreferences.contains("fw_joindate")){
            stayAtMainActvity();
        }else {

            String fw_name=sharedPreferences.getString("fw_name","no data");
            String fw_phone=sharedPreferences.getString("fw_phone","no data");
            String fw_area=sharedPreferences.getString("fw_area","no data");
            String fw_road=sharedPreferences.getString("fw_road","no data");
            String fw_block=sharedPreferences.getString("fw_block","no data");
            String fw_housenumber=sharedPreferences.getString("fw_housenumber","no data");
            String fw_nid=sharedPreferences.getString("fw_nid","no data");
            String fw_dob=sharedPreferences.getString("fw_dob","no data");
            String fw_uni=sharedPreferences.getString("fw_uni","no data");
            String fw_joindate=sharedPreferences.getString("fw_joindate","no data");
            String fw_gender=sharedPreferences.getString("fw_gender","no data");
            String fw_mail=sharedPreferences.getString("fw_mail","no data");

            f_name.setText(fw_name);
            f_phone.setText(fw_phone);
            f_area.setText(fw_area);


        }
    }

}
