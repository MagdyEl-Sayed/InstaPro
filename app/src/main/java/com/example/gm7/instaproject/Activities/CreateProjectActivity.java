package com.example.gm7.instaproject.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.gm7.instaproject.DataManager.Constants;
import com.example.gm7.instaproject.DataManager.SharedData;
import com.example.gm7.instaproject.Model.PostModel;
import com.example.gm7.instaproject.R;
import com.example.gm7.instaproject.Utility.PermissionUtility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CreateProjectActivity extends AppCompatActivity {
    private ImageView mMainProjectImage;
    private ImageView mSecondProjectImage;
    private ImageView mThirdProjectImage;
    private ImageView mLastProjectImage;
    private EditText mEdProjectDescription;
    private Button mBtnSaveProject;
    private static final int MAIN_IMAGE_CODE = 1;
    private static final int SECOND_IMAGE_CODE = 2;
    private static final int THIRD_IMAGE_CODE = 3;
    private static final int LAST_IMAGE_CODE = 4;
    private int ImageSelectedCode = 0;
    private PermissionUtility permissionUtility;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseStorage storage;
    private StorageReference storageRefe;
    private String userID;
    private SharedData sharedData;
    private ProgressBar mLoadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);
        mMainProjectImage = findViewById(R.id.main_projectImage);
        mSecondProjectImage = findViewById(R.id.second_projectImage);
        mThirdProjectImage = findViewById(R.id.third_projectImage);
        mLastProjectImage = findViewById(R.id.last_projectImage);
        mEdProjectDescription = findViewById(R.id.ed_project_description);
        mBtnSaveProject = findViewById(R.id.btn_save_project);
        mLoadingProgress = findViewById(R.id.loading_progress);

        permissionUtility = new PermissionUtility(this);
        sharedData = new SharedData();

        //--------------- firebase handling --------//
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        storage = FirebaseStorage.getInstance();
        storageRefe = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        assert firebaseUser != null;
        userID = firebaseUser.getUid();
        mMainProjectImage.setOnClickListener(onChooseMainImage());
        mSecondProjectImage.setOnClickListener(onChooseSecondImage());
        mThirdProjectImage.setOnClickListener(onChooseThirdImage());
        mLastProjectImage.setOnClickListener(onChooseLastImage());
        mBtnSaveProject.setOnClickListener(onSaveClicked());
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtility.EXTERNAL_STORAGE_PERMISSION_CONSTANT) {
            if (ActivityCompat.checkSelfPermission(CreateProjectActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                onOpenGallery(ImageSelectedCode);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == MAIN_IMAGE_CODE && data != null) {
            mMainProjectImage.setImageBitmap(getImageFromUri(data));
        } else if (resultCode == RESULT_OK && requestCode == SECOND_IMAGE_CODE && data != null) {
            mSecondProjectImage.setImageBitmap(getImageFromUri(data));
        } else if (resultCode == RESULT_OK && requestCode== THIRD_IMAGE_CODE && data.getData() != null){
            mThirdProjectImage.setImageBitmap(getImageFromUri(data));
        } else if (resultCode == RESULT_OK && requestCode == LAST_IMAGE_CODE && data.getData() !=null){
            mLastProjectImage.setImageBitmap(getImageFromUri(data));
        }
    }

    //------------ request Image from Gallery --------------//
    private void onOpenGallery(int request){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,getString(R.string.select_image)),request);
    }

    //------------ create random string for files names --------------//
    private Bitmap getImageFromUri(Intent data){
        InputStream imageStream = null;
        Uri selectedImage = data.getData();
        try {
            imageStream = getContentResolver().openInputStream(selectedImage);
             return BitmapFactory.decodeStream(imageStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //----------- Listener for UI Components --------------//
    private View.OnClickListener onChooseMainImage(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageSelectedCode = MAIN_IMAGE_CODE;
                 //* user choose to get picture from library *//*
                //check if the user use android less than 23 level api
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
                    //check if the permission is granted or not
                    if (permissionUtility.checkExtraStoragePermission()) {
                        onOpenGallery(ImageSelectedCode);
                    }
                } else {
                    //user use android api less than 23
                    //no need for prompting permissions
                    onOpenGallery(ImageSelectedCode);
                }
            }
        };
    }

    private View.OnClickListener onChooseSecondImage(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageSelectedCode = SECOND_IMAGE_CODE;
                //* user choose to get picture from library *//*
                //check if the user use android less than 23 level api
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
                    //check if the permission is granted or not
                    if (permissionUtility.checkExtraStoragePermission()) {
                        onOpenGallery(ImageSelectedCode);
                    }
                } else {
                    //user use android api less than 23
                    //no need for prompting permissions
                    onOpenGallery(ImageSelectedCode);
                }
            }
        };
    }

    private View.OnClickListener onChooseThirdImage(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageSelectedCode = THIRD_IMAGE_CODE;
                //* user choose to get picture from library *//*
                //check if the user use android less than 23 level api
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
                    //check if the permission is granted or not
                    if (permissionUtility.checkExtraStoragePermission()) {
                        onOpenGallery(ImageSelectedCode);
                    }
                } else {
                    //user use android api less than 23
                    //no need for prompting permissions
                    onOpenGallery(ImageSelectedCode);
                }
            }
        };
    }

    private View.OnClickListener onChooseLastImage(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageSelectedCode = LAST_IMAGE_CODE;
                //* user choose to get picture from library *//*
                //check if the user use android less than 23 level api
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
                    //check if the permission is granted or not
                    if (permissionUtility.checkExtraStoragePermission()) {
                        onOpenGallery(ImageSelectedCode);
                    }
                } else {
                    //user use android api less than 23
                    //no need for prompting permissions
                    onOpenGallery(ImageSelectedCode);
                }
            }
        };
    }

    private View.OnClickListener onSaveClicked(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = mEdProjectDescription.getText().toString();
                if (TextUtils.isEmpty(description)){
                    mEdProjectDescription.setError(getString(R.string.invalid_input_error));
                } else if (ImageSelectedCode == 0) {

                } else {
                    sharedData.setProject_bio(description);
                    uploadingProjectMainImage();
                }

            }
        };
    }

    //----------- uploading Project images to Firebase storage --------------//
    private void uploadingProjectMainImage(){
        mLoadingProgress.setVisibility(View.VISIBLE);
        String email = firebaseUser.getEmail();
        // Create a reference to 'ProjectImages/<userEmail>/<randomName>.jpg'
        String imageName = Constants.PROFILE_IMAGES+"/"
                +email+"/"+randomName()+".jpg";
        StorageReference ImagesRef = storageRefe.child(imageName);
        // Get the data from an ImageView as bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        prepareImage(mMainProjectImage).compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = ImagesRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests")
                Uri downloadedUrl = taskSnapshot.getDownloadUrl();
                assert downloadedUrl !=null;
                sharedData.setMainProjectUrl(downloadedUrl.toString());

                uploadingProjectSecondImage();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


            }
        });
    }

    private void uploadingProjectSecondImage() {
        String email = firebaseUser.getEmail();
        // Create a reference to 'ProjectImages/<userEmail>/<randomName>.jpg'
        String imageName = Constants.PROFILE_IMAGES+"/"
                +email+"/"+randomName()+".jpg";
        StorageReference ImagesRef = storageRefe.child(imageName);
        // Get the data from an ImageView as bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        prepareImage(mSecondProjectImage).compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = ImagesRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests")
                Uri downloadedUrl = taskSnapshot.getDownloadUrl();
                assert downloadedUrl !=null;
                sharedData.setImage2Url(downloadedUrl.toString());

                uploadingProjectThirdImage();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


            }
        });
    }

    private void uploadingProjectThirdImage() {
        String email = firebaseUser.getEmail();
        // Create a reference to 'ProjectImages/<userEmail>/<randomName>.jpg'
        String imageName = Constants.PROFILE_IMAGES+"/"
                +email+"/"+randomName()+".jpg";
        StorageReference ImagesRef = storageRefe.child(imageName);
        // Get the data from an ImageView as bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        prepareImage(mThirdProjectImage).compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = ImagesRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests")
                Uri downloadedUrl = taskSnapshot.getDownloadUrl();
                assert downloadedUrl !=null;
                sharedData.setImage3Url(downloadedUrl.toString());

                uploadingProjectLastImage();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


            }
        });
    }

    private void uploadingProjectLastImage() {
        String email = firebaseUser.getEmail();
        // Create a reference to 'ProjectImages/<userEmail>/<randomName>.jpg'
        String imageName = Constants.PROFILE_IMAGES+"/"
                +email+"/"+randomName()+".jpg";
        StorageReference ImagesRef = storageRefe.child(imageName);
        // Get the data from an ImageView as bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        prepareImage(mLastProjectImage).compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = ImagesRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests")
                Uri downloadedUrl = taskSnapshot.getDownloadUrl();
                assert downloadedUrl !=null;
                sharedData.setImage4Url(downloadedUrl.toString());

                insertPostInfo();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


            }
        });
    }

    //------------ cache the image of the passed ImageView and get Image as bitmap --------------//
    private Bitmap prepareImage(ImageView imageView){
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        return imageView.getDrawingCache();
    }

    //------------ create random string for files names --------------//
    private String randomName(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        return  "IMG_"+timeStamp;
    }

    //------------ get current time stamp as string --------------//
    private String getTimeStamp(){
        return new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
    }

    //----------- inserting Post information in the Firebase database --------//
    private void insertPostInfo(){
        String mainImage = sharedData.getMainProjectUrl();
        String Image2 = sharedData.getImage2Url();
        String Image3 = sharedData.getImage3Url();
        String Image4 = sharedData.getImage4Url();
        String por_bio = sharedData.getProject_bio();
        String key=reference.child(userID).push().getKey();
        PostModel post =new PostModel(por_bio,mainImage,Image2,Image3,Image4,getTimeStamp(),userID);
        Map<String,Object> postValues=post.insertPost();
        Map<String,Object> childUpdates=new HashMap<>();
        childUpdates.put("posts/"+key,postValues);

        reference.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                mLoadingProgress.setVisibility(View.INVISIBLE);
                if ( task.isSuccessful()) {
                    finish();

                } else {
                    showErrorMessage();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(CreateProjectActivity.this);
                builder.setMessage(e.getMessage())
                        .setPositiveButton(android.R.string.ok,null);
                android.app.AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
    }

    //------------ Error message is something goes wrong --------------//
    private void showErrorMessage() {

    }
}
