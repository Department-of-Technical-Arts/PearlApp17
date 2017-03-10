package com.dota.pearl17;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ADMIN on 9.3.17.
 */

public class AppUpdateDialog extends DialogFragment{

    ArrayList<String> events;
    TextView text;
    LayoutInflater inflater;
    Typeface custom_font;
    Context context;

    public AppUpdateDialog() {
        // Required empty public constructor
    }

    public AppUpdateDialog(Context context) {

        this.context = context;

        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/goodpro_condmedium.otf");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.app_update_dialog, null, false);

        text = (TextView) view.findViewById(R.id.updateText);
        text.setTypeface(custom_font);

        builder.setView(view);

        builder.setCancelable(true);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {
                //redirect to Pearl App PlayStore Page


            }

        });

        return builder.create();
    }
}
