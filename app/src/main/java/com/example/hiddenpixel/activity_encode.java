package com.example.hiddenpixel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class activity_encode extends AppCompatActivity implements TextEncodingCallback{
    private BottomNavigationView bottomNavigationView;
    private static final int GALLERY_REQUEST_CODE = 1;
    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "Encode Class";

    //Created variables for UI
    private TextView whether_encoded;
    private EditText message;
    private EditText secret_key;
    private ImageView imageView;

    //Objects needed for encoding
    private TextEncoding textEncoding;
    private ImageSteganography imageSteganography;
    private ProgressDialog save;
    private Uri filepath;

    //Bitmaps
    private Bitmap original_image;
    private Bitmap encoded_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode);
        imageView = findViewById(R.id.iv1);
        whether_encoded = findViewById(R.id.whether_encoded);
        message = findViewById(R.id.message);
        secret_key = findViewById(R.id.key);

        Button encode_button = findViewById(R.id.encode_button);
        ImageButton download = findViewById(R.id.download);
        ImageButton placeholder = findViewById(R.id.placeholder);

//        checkAndRequestPermissions();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        placeholder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        encode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whether_encoded.setText("");
                if (filepath != null) {
                    if (message.getText() != null) {

                        //ImageSteganography Object instantiation
                        imageSteganography = new ImageSteganography(message.getText().toString(), secret_key.getText().toString(), original_image);
                        //TextEncoding object Instantiation
                        textEncoding = new TextEncoding(activity_encode.this, activity_encode.this);
                        //Executing the encoding
                        textEncoding.execute(imageSteganography);
                    }
                }
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Bitmap imgToSave = encoded_image;
                Thread PerformEncoding = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        saveToInternalStorage(imgToSave);
                    }
                });
                save = new ProgressDialog(activity_encode.this);
                save.setMessage("Saving, Please Wait...");
                save.setTitle("Saving Image");
                save.setIndeterminate(false);
                save.setCancelable(false);
                save.show();
                PerformEncoding.start();
            }
        });

    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                filepath = selectedImageUri;
                try {
                    original_image = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                    imageView.setImageBitmap(original_image);
                } catch (IOException e) {
                    Log.e(TAG, "Error: " + e);
                }
            } else {
                Log.e(TAG, "Selected image URI is null.");
            }
        } else {
            Log.e(TAG, "Failed to select an image from the gallery.");
        }
    }


    public void onStartTextEncoding() {
        //Whatever you want to do at the start of text encoding
    }

    public void onCompleteTextEncoding(ImageSteganography result) {

        //By the end of textEncoding

        if (result != null && result.isEncoded()) {
            encoded_image = result.getEncoded_image();
            whether_encoded.setText("Encoded");
            imageView.setImageBitmap(encoded_image);
        }
    }

    private void saveToInternalStorage(Bitmap bitmapImage) {
        OutputStream fOut;
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), "Encoded" + ".PNG"); // the File to save ,
        try {
            fOut = new FileOutputStream(file);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fOut); // saving the Bitmap to a file
            fOut.flush(); // Not really required
            fOut.close(); // do not forget to close the stream
            whether_encoded.post(new Runnable() {
                @Override
                public void run() {
                    save.dismiss();
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//    private void checkAndRequestPermissions() {
//        int permissionWriteStorage = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        int ReadPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
//        List<String> listPermissionsNeeded = new ArrayList<>();
//        if (ReadPermission != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
//        }
//        if (permissionWriteStorage != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        }
//        if (!listPermissionsNeeded.isEmpty()) {
//            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[0]), 1);
//        }
//    }


