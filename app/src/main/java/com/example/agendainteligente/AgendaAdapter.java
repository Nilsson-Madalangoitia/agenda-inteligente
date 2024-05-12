package com.example.agendainteligente;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agendainteligente.Model.AgendaModel;

import java.util.List;

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.ReminderViewHolder> {

    private List<AgendaModel> agendaList;

    public AgendaAdapter(List<AgendaModel> agendaList) {
        this.agendaList = agendaList;
    }

    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ReminderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
        AgendaModel agenda = agendaList.get(position);
        holder.textViewAsunto.setText(agenda.getAsunto());
        holder.textViewDescripcion.setText(agenda.getDescripcion());
        holder.textViewFecha.setText(agenda.getFecha());
        holder.textViewHora.setText(agenda.getHora());
    }

    @Override
    public int getItemCount() {
        return agendaList.size();
    }

    public static class ReminderViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewAsunto;
        public TextView textViewDescripcion;
        public TextView textViewFecha;
        public TextView textViewHora;

        public TextView te;

        public ReminderViewHolder(View itemView) {
            super(itemView);
            textViewAsunto = itemView.findViewById(R.id.textViewAsunto);
            textViewDescripcion = itemView.findViewById(R.id.textViewDescripcion);
            textViewFecha = itemView.findViewById(R.id.textViewFecha);
            textViewHora = itemView.findViewById(R.id.textViewHora);
        }
    }
}
