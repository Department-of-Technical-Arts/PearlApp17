package com.dota.pearl17;

/**
 * Created by ADMIN on 6.3.17.
 */


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
//import android.support.v7.app.AlertDialog;
// import android.view.View;

/**
 * Created by ADMIN on 23.9.16.
 */
public class EventConfirmDialog extends android.support.v4.app.DialogFragment {

    public confirm mListener;
    public EventConfirmDialog() {
        // Required empty public constructor
    }
    public EventConfirmDialog(Context context){
        if (context instanceof confirm) {
            mListener = (confirm) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement deleteInteract");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.event_confirm, null, false);

        builder.setView(view);

        builder.setNegativeButton("Cancel",null);

        builder.setPositiveButton("Add Events", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {
                mListener.confirm_events();
            }

        });
        return builder.create();
    }

    public interface confirm{

        void confirm_events();
    }
}
