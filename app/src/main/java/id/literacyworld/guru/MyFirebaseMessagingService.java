package id.literacyworld.guru;

import android.content.pm.PackageManager;
import android.widget.RemoteViews;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.literacy.literacyworld.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            String message = remoteMessage.getData().get("message");
            sendNotification(message);
        }

        if (remoteMessage.getNotification() != null) {
            String message = remoteMessage.getNotification().getBody();
            sendNotification(message);
        }
    }

    private void sendNotification(String messageBody) {
        // Setup layout for custom notification
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.activity_notif_layout);
        remoteViews.setTextViewText(R.id.notification_title, "Murid Login");
        remoteViews.setTextViewText(R.id.notification_message, messageBody);

        // Create notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.drawable.notification)
                .setContent(remoteViews)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(0, builder.build());
    }
}
