package com.example.novel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.ByteArrayOutputStream;


public class AddOnActivity extends AppCompatActivity {
    ImageView SelectedImage;
    StorageReference mStorageRef;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_on);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        SelectedImage = findViewById(R.id.selectedImage);
        Button browse = findViewById(R.id.browse);

        browse.setOnClickListener(View->{
            DispatchTakePicIntent();
        });

        FirebaseStorage storage = FirebaseStorage.getInstance();
 



    }

    @SuppressLint("QueryPermissionsNeeded")
    private void DispatchTakePicIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // Get the image data
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            // Set the image to the ImageView
            SelectedImage.setImageBitmap(imageBitmap);

            // Upload the image to Firebase storage
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageData = baos.toByteArray();
            String imageName = "image.jpg";

            UploadTask uploadTask = mStorageRef.child("images/" + imageName).putBytes(imageData);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AddOnActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("MainActivity", "Error uploading image: " + e.getMessage());
                    Toast.makeText(AddOnActivity.this, "Error uploading image", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}



//     public void onRequestPermissionResult(int requestCode, @NonNull String[] Permissions, @NonNull int[] grantResults){
//        super.onRequestPermissionsResult(requestCode, Permissions, grantResults);
//    }
