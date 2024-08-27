package id.literacyworld.guru;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.literacy.literacyworld.R;

public class VUpload extends AppCompatActivity {

    private static final int PICK_VIDEO_REQUEST = 1;
    private View cardSelectVideo, cardUploadVideo, cardViewVideos;

    private Uri videoUri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vupload);

        // Mengaitkan ID dari XML ke variabel
        cardSelectVideo = findViewById(R.id.cardSelectVideo);
        cardUploadVideo = findViewById(R.id.cardUploadVideo);
        cardViewVideos = findViewById(R.id.cardViewVideos);

        storageReference = FirebaseStorage.getInstance().getReference("videos");
        databaseReference = FirebaseDatabase.getInstance().getReference("videos");

        // Set OnClickListener untuk CardView yang sesuai
        cardSelectVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        cardUploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoUri != null) {
                    uploadVideoToFirebase();
                } else {
                    Toast.makeText(VUpload.this, "No video selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cardViewVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VUpload.this, ViewVideosActivity.class);
                startActivity(intent);
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_VIDEO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            videoUri = data.getData();
            Toast.makeText(this, "Video selected: " + videoUri.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadVideoToFirebase() {
        StorageReference fileReference = storageReference.child(System.currentTimeMillis() + ".mp4");
        fileReference.putFile(videoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url = uri.toString();
                                String uploadId = databaseReference.push().getKey();
                                databaseReference.child(uploadId).setValue(url);
                                Toast.makeText(VUpload.this, "Video upload successful", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(VUpload.this, "Video upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
