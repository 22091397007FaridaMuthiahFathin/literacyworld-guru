package id.literacyworld.guru;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.literacy.literacyworld.R;

public class FirebaseNotificationManager {
    private Context context;
    private DatabaseReference databaseReference;

    public FirebaseNotificationManager(Context context) {
        this.context = context;
        this.databaseReference = FirebaseDatabase.getInstance().getReference().child("videos");
        setupFirebaseListener();
    }

    private void setupFirebaseListener() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Logic untuk mendeteksi perubahan pada database
                // Contoh: Jika seorang siswa telah membaca atau melihat video, kirim notifikasi
                // Anda perlu menyesuaikan logika ini dengan struktur data Anda di Firebase
                for (DataSnapshot videoSnapshot : dataSnapshot.getChildren()) {
                    String videoTitle = videoSnapshot.child("title").getValue(String.class);
                    boolean watched = false /* Logika untuk mendeteksi apakah video sudah ditonton */;

                    if (watched) {
                        sendNotification("Video Baru Ditonton", "Siswa telah menonton video: " + videoTitle);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void sendNotification(String title, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Untuk Android Oreo dan versi di atasnya, diperlukan pembuatan saluran notifikasi
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = context.getString(R.string.default_notification_channel_id);
            CharSequence channelName = "Your channel name";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            notificationManager.createNotificationChannel(channel);
        }

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, context.getString(R.string.default_notification_channel_id))
                .setSmallIcon(android.R.drawable.ic_notification_overlay)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri);

        notificationManager.notify(0, notificationBuilder.build());
    }
}