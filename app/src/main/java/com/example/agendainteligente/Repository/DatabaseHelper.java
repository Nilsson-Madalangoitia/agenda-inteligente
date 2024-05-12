package com.example.agendainteligente.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.agendainteligente.Helpers.Utils;
import com.example.agendainteligente.Model.AgendaModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "agendadb.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "recordatorio";
    private static final String COLUMN_ID = "recordatorio_id";
    private static final String COLUMN_ASUNTO = "recordatorio_asunto";
    private static final String COLUMN_DESCRIPCION = "recordatorio_descripcion";
    private static final String COLUMN_FECHA = "recordatorio_fecha";
    private static final String COLUMN_HORA = "recordatorio_hora";
    private static final String COLUMN_HORA_RECORDAR = "recordatorio_hora_recordar";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ASUNTO + " TEXT, " +
                    COLUMN_DESCRIPCION + " TEXT, " +
                    COLUMN_FECHA + " TEXT, " +
                    COLUMN_HORA + " TEXT," +
                    COLUMN_HORA_RECORDAR + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No necesario para esta implementación básica
    }

    // Método para insertar un nuevo recordatorio en la base de datos
    public long agregarRecordatorio(AgendaModel recordatorio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ASUNTO, recordatorio.getAsunto());
        values.put(COLUMN_DESCRIPCION, recordatorio.getDescripcion());
        values.put(COLUMN_FECHA, recordatorio.getFecha());
        values.put(COLUMN_HORA, recordatorio.getHora());
        values.put(COLUMN_HORA_RECORDAR, recordatorio.getHora_recordatorio());
        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    // Método para obtener todos los recordatorios de la base de datos
    public List<AgendaModel> listarRecordatorios() {
        List<AgendaModel> recordatorioList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Calendar now = Calendar.getInstance();
        if (cursor.moveToFirst()) {
            do {
                AgendaModel recordatorio = new AgendaModel();
                recordatorio.setRecordatorio_id(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                recordatorio.setAsunto(cursor.getString(cursor.getColumnIndex(COLUMN_ASUNTO)));
                recordatorio.setDescripcion(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPCION)));
                recordatorio.setFecha(cursor.getString(cursor.getColumnIndex(COLUMN_FECHA)));
                recordatorio.setHora(cursor.getString(cursor.getColumnIndex(COLUMN_HORA)));
                recordatorio.setHora_recordatorio(cursor.getInt(cursor.getColumnIndex(COLUMN_HORA_RECORDAR)));

                Calendar reminderDateTime = Calendar.getInstance();
                reminderDateTime.setTime(Utils.parseDateTime(recordatorio.getFecha(), recordatorio.getHora()));

                long timeDifference = reminderDateTime.getTimeInMillis() - now.getTimeInMillis();
                recordatorio.setTimeDifference(timeDifference);

                if (timeDifference >= 0) {
                    recordatorioList.add(recordatorio);
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        Collections.sort(recordatorioList, new Comparator<AgendaModel>() {
            @Override
            public int compare(AgendaModel o1, AgendaModel o2) {
                return Long.compare(o1.getTimeDifference(), o2.getTimeDifference());
            }
        });

        return recordatorioList;
    }

    public List<AgendaModel> listarRecordatoriosHistorial() {
        List<AgendaModel> recordatorioList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Calendar now = Calendar.getInstance();
        if (cursor.moveToFirst()) {
            do {
                AgendaModel recordatorio = new AgendaModel();
                recordatorio.setRecordatorio_id(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                recordatorio.setAsunto(cursor.getString(cursor.getColumnIndex(COLUMN_ASUNTO)));
                recordatorio.setDescripcion(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPCION)));
                recordatorio.setFecha(cursor.getString(cursor.getColumnIndex(COLUMN_FECHA)));
                recordatorio.setHora(cursor.getString(cursor.getColumnIndex(COLUMN_HORA)));
                recordatorio.setHora_recordatorio(cursor.getInt(cursor.getColumnIndex(COLUMN_HORA_RECORDAR)));

                Calendar reminderDateTime = Calendar.getInstance();
                reminderDateTime.setTime(Utils.parseDateTime(recordatorio.getFecha(), recordatorio.getHora()));

                long timeDifference = reminderDateTime.getTimeInMillis() - now.getTimeInMillis();
                recordatorio.setTimeDifference(timeDifference);

                if (timeDifference <= 0) {
                    recordatorioList.add(recordatorio);
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        Collections.sort(recordatorioList, new Comparator<AgendaModel>() {
            @Override
            public int compare(AgendaModel o1, AgendaModel o2) {
                return Long.compare(o1.getTimeDifference(), o2.getTimeDifference());
            }
        });

        return recordatorioList;
    }

    // Método para actualizar un recordatorio existente en la base de datos
    public int actualizarRecordatorio(AgendaModel agenda) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ASUNTO, agenda.getAsunto());
        values.put(COLUMN_DESCRIPCION, agenda.getDescripcion());
        values.put(COLUMN_FECHA, agenda.getFecha());
        values.put(COLUMN_HORA, agenda.getHora());
        values.put(COLUMN_HORA_RECORDAR, agenda.getHora_recordatorio());
        // Actualizar el registro con el id proporcionado
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(agenda.getRecordatorio_id())});
    }

    // Método para eliminar un recordatorio de la base de datos
    public void eliminarRecordatorio(long recordatorio_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[]{String.valueOf(recordatorio_id)});
        db.close();
    }

}
