package com.example.agendainteligente.Model;

public class AgendaModel {

    private int recordatorio_id;
    private String asunto;
    private String descripcion;
    private String fecha;

    private String hora;

    private int hora_recordatorio;

    private long timeDifference;

    public AgendaModel() {
    }

    public AgendaModel(String asunto, String descripcion, String fecha, String hora, int hora_recordatorio) {
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.hora_recordatorio = hora_recordatorio;
    }

    public int getRecordatorio_id() {
        return recordatorio_id;
    }

    public void setRecordatorio_id(int recordatorio_id) {
        this.recordatorio_id = recordatorio_id;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getHora_recordatorio() {
        return hora_recordatorio;
    }

    public void setHora_recordatorio(int hora_recordatorio) {
        this.hora_recordatorio = hora_recordatorio;
    }

    public long getTimeDifference() {
        return timeDifference;
    }

    public void setTimeDifference(long timeDifference) {
        this.timeDifference = timeDifference;
    }
}
