package com.example.agendainteligente;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agendainteligente.Helpers.DatePickerFragment;
import com.example.agendainteligente.Helpers.TimePickerFragment;
import com.example.agendainteligente.Model.AgendaModel;
import com.example.agendainteligente.Repository.DatabaseHelper;

public class AgregarRecordatorios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_recordatorio);

        EditText editTextAsunto = findViewById(R.id.editTextAsunto);
        EditText editTextDescripcion = findViewById(R.id.editTextDescripcion);
        EditText editTextFecha = findViewById(R.id.editTextFecha);
        EditText editTextHora = findViewById(R.id.editTextHora);
        EditText editTextHoraRecordatorio = findViewById(R.id.editTextHoraRecordatorio);
        Button buttonAgregar = findViewById(R.id.buttonAgregar);

        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtén los valores ingresados por el usuario
                String asunto = editTextAsunto.getText().toString();
                String descripcion = editTextDescripcion.getText().toString();
                String fecha = editTextFecha.getText().toString();
                String hora = editTextHora.getText().toString();
                String horaRecordatorio = editTextHoraRecordatorio.getText().toString();
                int recordar = Integer.parseInt(horaRecordatorio);

                AgendaModel agendaModel = new AgendaModel(asunto, descripcion, fecha, hora, recordar);
                DatabaseHelper databaseHelper = new DatabaseHelper(AgregarRecordatorios.this);
                long id = databaseHelper.agregarRecordatorio(agendaModel);

                // Verifica si el recordatorio se agregó correctamente
                if (id != -1) {
                    Toast.makeText(AgregarRecordatorios.this, "Recordatorio agregado con éxito", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AgregarRecordatorios.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AgregarRecordatorios.this, "Error al agregar el recordatorio", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void showDatePickerDialog(View view) {
        // Crear una instancia del DatePickerFragment
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        // Mostrar el selector de fecha
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View view) {
        // Crear una instancia del TimePickerFragment
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        // Mostrar el selector de hora
        timePickerFragment.show(getSupportFragmentManager(), "timePicker");
    }

}
