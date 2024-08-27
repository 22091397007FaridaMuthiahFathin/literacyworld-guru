package id.literacyworld.guru;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.literacy.literacyworld.R;

import java.util.ArrayList;

public class VideoListAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private ArrayList<String> videoUrls;
    private DatabaseReference databaseReference;

    public VideoListAdapter(@NonNull Context context, ArrayList<String> urls) {
        super(context, R.layout.activity_list_item_video, urls);
        this.mContext = context;
        this.videoUrls = urls;
        this.databaseReference = FirebaseDatabase.getInstance().getReference("videos");
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_list_item_video, parent, false);
        }

        Button videoButton = convertView.findViewById(R.id.videoButton);
        ImageView deleteIcon = convertView.findViewById(R.id.deleteIcon);

        String videoUrl = videoUrls.get(position);
        videoButton.setText("Lihat Video");

        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
                mContext.startActivity(intent);
            }
        });

        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapusVideo(videoUrl);
            }
        });

        return convertView;
    }

    private void hapusVideo(String videoUrl) {
        databaseReference.orderByValue().equalTo(videoUrl).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    postSnapshot.getRef().removeValue();
                }
                videoUrls.remove(videoUrl);
                notifyDataSetChanged();
                Toast.makeText(mContext, "Video dihapus", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(mContext, "Gagal menghapus video", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
