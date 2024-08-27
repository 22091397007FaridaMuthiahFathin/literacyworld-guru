package id.literacyworld.guru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.literacy.literacyworld.R;

import java.util.ArrayList;

public class ViewVideosActivity extends AppCompatActivity {

    private ListView listViewVideos;
    private ArrayList<String> videoUrls;
    private VideoListAdapter adapter;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_videos);

        listViewVideos = findViewById(R.id.listViewVideos);
        videoUrls = new ArrayList<>();
        adapter = new VideoListAdapter(this, videoUrls);
        listViewVideos.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("videos");

        listViewVideos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String videoUrl = videoUrls.get(position);
                putarVideo(videoUrl);
            }
        });

        ambilVideoUrls();
    }

    private void ambilVideoUrls() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                videoUrls.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    String url = postSnapshot.getValue(String.class);
                    videoUrls.add(url);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewVideosActivity.this, "Gagal memuat URL video", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void putarVideo(String url) {
        Intent intent = new Intent(ViewVideosActivity.this, VideoPlayerActivity.class);
        intent.putExtra("videoUrl", url);
        startActivity(intent);
    }
}