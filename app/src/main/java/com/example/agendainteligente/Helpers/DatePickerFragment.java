package com.example.agendainteligente.Helpers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.agendainteligente.R;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Obtener la fecha actual
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Crear y retornar un nuevo selector de fecha
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // Obtener la fecha seleccionada y mostrarla en el EditText correspondiente
        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
        EditText editTextFecha = getActivity().findViewById(R.id.editTextFecha);
        editTextFecha.setText(selectedDate);
    }
}
