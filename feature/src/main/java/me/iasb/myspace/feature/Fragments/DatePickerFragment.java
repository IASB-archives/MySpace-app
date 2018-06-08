package me.iasb.myspace.feature.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import me.iasb.myspace.feature.MainActivity;
import me.iasb.myspace.feature.Utils.DateTimeUtils;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), this, 1999, 2, 2);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
        MainActivity.setYear(selectedYear);
        MainActivity.setMonth(selectedMonth + 1);
        MainActivity.setDay(selectedDay);

        MainActivity.setDateTextView(DateTimeUtils.DateString(selectedDay, selectedMonth, selectedYear, DateTimeUtils.FORWARD_SLASH));
    }
}
