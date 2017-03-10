package com.dota.pearl17;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by ADMIN on 6.3.17.
 */

public class PickDOBDialog extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    public DOB mListener;

    public PickDOBDialog() {
        // Required empty public constructor
    }

    public PickDOBDialog(Context context) {
        if (context instanceof DOB) {
            mListener = (DOB) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement deleteInteract");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog d = new DatePickerDialog(getActivity(), this, year, month, day);

        //change colour of d to blue

        return d;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        mListener.setDOB(year, month, day);
    }

    public interface DOB {

        void setDOB(int year, int month, int day);
    }
}


