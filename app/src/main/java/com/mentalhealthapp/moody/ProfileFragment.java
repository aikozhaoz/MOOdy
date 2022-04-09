package com.mentalhealthapp.moody;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProfileFragment extends Fragment {
    public static final int CAMERA_PERM_CODE = 101;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private String currentPhotoPath;
    FirebaseAuth auth;
    View profileView;
    private String userName = HomeActivity.userName;
    TextView helloMessage;
    ImageView profilePic;
    Button takePicButton, updatePasswordButton, deleteAccountButton, logOutButton;
    User currentUser;

    final private String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private DatabaseReference ref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private  void askCameraPermissions(){
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},  101 );
        }else{
            Toast.makeText(getActivity(), "Permission already granted", Toast.LENGTH_SHORT).show();
            dispatchTakePictureIntent();
        }
    }

    private ActivityResultLauncher<String> permissionResult =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    dispatchTakePictureIntent();
                } else {
                    Toast.makeText(getActivity(), "Camera Permission Is Required To Use Camera.", Toast.LENGTH_SHORT).show();
                }
            });

    public void dispatchTakePictureIntent(){
        Toast.makeText(getActivity(), "Camera Permission Is Granted!", Toast.LENGTH_SHORT).show();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(getActivity(),
//                        "com.example.android.fileprovider",
//                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        profileView = view;
        helloMessage = (TextView) profileView.findViewById(R.id.hello_message);
        takePicButton = (Button) profileView.findViewById(R.id.take_picture_button);
        updatePasswordButton = (Button) profileView.findViewById(R.id.update_password_button);
        deleteAccountButton = (Button) profileView.findViewById(R.id.delete_account_button);
        logOutButton = (Button) profileView.findViewById(R.id.log_out_button);
        String welcomeMessage = "Hello " + userName + " !";
        helloMessage.setText(welcomeMessage);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users/" + UID);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User value = snapshot.getValue(User.class);
                if (value != null){
                    currentUser = value;
                    setName();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        takePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermissions();
                permissionResult.launch(Manifest.permission.CAMERA);
            }
        });
        updatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), UpdatePasswordActivity.class);
                getActivity().startActivity(intent);
            }
        });
        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), DeleteAccountActivity.class);
                getActivity().startActivity(intent);
            }
        });
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                i.putExtra("Log Out", true);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().startActivity(i);
                auth.signOut();
                getActivity().finish();
            }
        });
    }

    private void setName() {
        String name = currentUser.getFirstName();
        helloMessage.setText("Hello "+name+"!");
    }


}
