package com.example.agendainteligente;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agendainteligente.Model.AgendaModel;
import com.example.agendainteligente.Repository.DatabaseHelper;

import java.util.List;

public class HistorialRecordatorios extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    List<AgendaModel> agendaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        DatabaseHelper databaseHelper = new DatabaseHelper(HistorialRecordatorios.this);
        agendaList = databaseHelper.listarRecordatoriosHistorial();

        RecyclerView recyclerView = findViewById(R.id.recyclerViewHistorial);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AgendaAdapter(agendaList);
        recyclerView.setAdapter(adapter);

    }
}
