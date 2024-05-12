package com.example.agendainteligente.Service;

import com.example.agendainteligente.Helpers.Utils;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.agendainteligente.MainActivity;
import com.example.agendainteligente.Model.AgendaModel;
import com.example.agendainteligente.R;
import com.example.agendainteligente.Repository.DatabaseHelper;

import java.util.Calendar;
import java.util.List;

public class ReminderService extends Service {

    private static final long INTERVAL = 60 * 1000;

    private static final String CHANNEL_ID = "RecordatorioChannel";
    private static final int NOTIFICATION_ID = 1;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    private DatabaseHelper databaseHelper;
    List<AgendaModel> agendaList;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int requestCode = 123;
        Intent intent = new Intent(this, ReminderService.class);
        pendingIntent = PendingIntent.getService(this, requestCode, intent, PendingIntent.FLAG_IMMUTABLE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), INTERVAL, pendingIntent);
        checkReminders();
        Log.d("ReminderService", "checkReminders() llamado");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Cancelar la alarma cuando el servicio se detenga
        alarmManager.cancel(pendingIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void checkReminders() {
        Log.d("ReminderService", "checkReminders() iniciado");
        databaseHelper = new DatabaseHelper(this);
        agendaList = databaseHelper.listarRecordatorios();

        Calendar now = Calendar.getInstance();

        for (AgendaModel recordatorio : agendaList) {
            Calendar reminderTime = Calendar.getInstance();
            reminderTime.setTime(Utils.parseDateTime(recordatorio.getFecha(), recordatorio.getHora()));

            reminderTime.add(Calendar.MINUTE, -5);

            // Comparar con la hora actual
            if (now.compareTo(reminderTime) >= 0) {
                Log.d("ReminderService", "¡Hola mundo desde el servicio de recordatorio!");
                sendNotification(recordatorio);
            }
            Log.d("ReminderService", "Recordatorio encontrado: " + recordatorio.getAsunto()+ " " + recordatorio.getHora_recordatorio());
        }

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendNotification(AgendaModel recordatorio) {
        Log.d("ReminderService", "Mostrando notificación");
        // Crear un Intent para abrir la actividad cuando se haga clic en la notificación
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Crear el mensaje de la notificación
        String titulo = recordatorio.getAsunto();
        String mensaje = "Recordatorio: " + recordatorio.getDescripcion() + " " + recordatorio.getHora_recordatorio();

        // Crear la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Mostrar la notificación
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
        Log.d("ReminderService", "Notificación enviada");
    }


}
