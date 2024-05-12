package com.example.agendainteligente;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agendainteligente.Helpers.DatePickerFragment;
import com.example.agendainteligente.Helpers.TimePickerFragment;
import com.example.agendainteligente.Model.AgendaModel;
import com.example.agendainteligente.Repository.DatabaseHelper;
import com.example.agendainteligente.Service.ReminderService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    List<AgendaModel> agendaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = new Intent(this, ReminderService.class);
        startService(intent);

        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
        agendaList = databaseHelper.listarRecordatorios();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AgendaAdapter(agendaList);
        recyclerView.setAdapter(adapter);

        Button buttonHistorial = findViewById(R.id.btn_historial);
        buttonHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, HistorialRecordatorios.class);
                startActivity(intent);
            }
        });

        Button buttonAgregar = findViewById(R.id.btn_modal_agregar);
        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AgregarRecordatorios.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cargar la lista de recordatorios cada vez que MainActivity se vuelva a mostrar
        cargarListaRecordatorios();
    }

    private void cargarListaRecordatorios() {
        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
        agendaList.clear();
        agendaList.addAll(databaseHelper.listarRecordatorios());
        adapter.notifyDataSetChanged();
    }

}