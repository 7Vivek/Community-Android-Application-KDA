package com.project.kda.AdminActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.kda.Model.AdminImageRegistration;
import com.project.kda.R;
import com.project.kda.Utils.Utils;

import java.io.IOException;
import java.util.Calendar;

public class ImageRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText addtitle, et_date;
    Button postbtn, btn_date;
    DatabaseReference databaseAdmin;
    private ImageView ProfileImage;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    Bitmap event_bitmap;
    String date = "";
    private int mYear, mMonth, mDay, mHour, mMinute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_register);
        Init();
        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidateData()) {
                    try {
                        if (event_bitmap != null) {
                            String id = databaseAdmin.push().getKey();
                            String title = addtitle.getText().toString().trim();
                            //String date = adddate.getText().toString().trim();
                            AdminImageRegistration register = new AdminImageRegistration(id, title, Utils.BitMapToString(event_bitmap), date);
                            databaseAdmin.child(id).setValue(register);
                            Toast.makeText(ImageRegisterActivity.this, "Image Uploaded Successfully!!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ImageRegisterActivity.this, "Please select image!", Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        Log.e("Error", e.getMessage());
                    }
                }
            }
        });
        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
            }
        });

        btn_date.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == btn_date) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                            et_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

    }

    private void Init() {
        databaseAdmin = FirebaseDatabase.getInstance().getReference().child("AdminImageRegistration");
        addtitle = (EditText) findViewById(R.id.addtitle);
        et_date = (EditText) findViewById(R.id.et_date);
        ProfileImage = (ImageView) findViewById(R.id.adminimg);
        postbtn = (Button) findViewById(R.id.postbtn);
        btn_date = (Button) findViewById(R.id.btn_date);
    }

    private boolean ValidateData() {
        int ErrCount = 0;
        if (addtitle.getText().toString().trim().equals("")) {
            addtitle.setError("Enter Title!");
            addtitle.requestFocus();
            ErrCount++;
            return false;
        }

        if (et_date.getText().toString().trim().equals("")) {
            et_date.setError("Select Event Date!");
            et_date.requestFocus();
            ErrCount++;
            return false;
        }

        if (ErrCount > 0)
            return false;
        else
            return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                event_bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                if (event_bitmap != null) {
                    ProfileImage.setImageBitmap(event_bitmap);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /*if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();

            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (requestCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    ProfileImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }
}
